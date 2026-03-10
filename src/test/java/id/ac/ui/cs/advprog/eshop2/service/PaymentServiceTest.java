package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop2.model.Order;
import id.ac.ui.cs.advprog.eshop2.model.Payment;
import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396079b",
                products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Merdeka No. 1");
        codData.put("deliveryFee", "10000");

        payments = new ArrayList<>();
        payments.add(new Payment("pay-1", null, "VOUCHER", voucherData));
        payments.add(new Payment("pay-2", null, "CASH_ON_DELIVERY", codData));
    }

    @Test
    void testAddPaymentVoucherSuccess() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals("VOUCHER", result.getMethod());
    }

    @Test
    void testAddPaymentCODSuccess() {
        Payment payment = payments.get(1);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, payment.getMethod(), payment.getPaymentData());

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals("CASH_ON_DELIVERY", result.getMethod());
    }

    @Test
    void testAddPaymentVoucherRejected() {
        Map<String, String> invalidData = new HashMap<>();
        invalidData.put("voucherCode", "INVALID");
        Payment rejected = new Payment("pay-3", null, "VOUCHER", invalidData);
        doReturn(rejected).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", invalidData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testAddPaymentCODRejected() {
        Map<String, String> invalidData = new HashMap<>();
        invalidData.put("address", null);
        invalidData.put("deliveryFee", null);
        Payment rejected = new Payment("pay-4", null, "CASH_ON_DELIVERY", invalidData);
        doReturn(rejected).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "CASH_ON_DELIVERY", invalidData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = payments.get(0);
        payment.setOrder(order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusRejectedUpdatesOrderToFailed() {
        Payment payment = payments.get(0);
        payment.setOrder(order);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusInvalidThrowsException() {
        Payment payment = payments.get(0);

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.setStatus(payment, "INVALID_STATUS"));

        verify(paymentRepository, times(0)).save(any(Payment.class));
    }

    @Test
    void testGetPaymentFound() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getPaymentId());

        Payment result = paymentService.getPayment(payment.getPaymentId());
        assertEquals(payment.getPaymentId(), result.getPaymentId());
    }

    @Test
    void testGetPaymentNotFound() {
        doReturn(null).when(paymentRepository).findById("zczc");

        Payment result = paymentService.getPayment("zczc");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> results = paymentService.getAllPayments();
        assertEquals(2, results.size());
    }

    @Test
    void testGetAllPaymentsEmpty() {
        doReturn(new ArrayList<>()).when(paymentRepository).getAllPayments();

        List<Payment> results = paymentService.getAllPayments();
        assertTrue(results.isEmpty());
    }

    @Test
    void testSetStatusRejectedBranchCoverage() {
        Payment payment = new Payment("pay-x", order, "VOUCHER", new HashMap<>());
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(OrderStatus.FAILED.getValue(), payment.getOrder().getStatus());
    }
}