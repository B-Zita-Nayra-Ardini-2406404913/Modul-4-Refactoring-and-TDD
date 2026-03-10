package id.ac.ui.cs.advprog.eshop2.repository;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop2.model.Order;
import id.ac.ui.cs.advprog.eshop2.model.Payment;
import id.ac.ui.cs.advprog.eshop2.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("13652556-012a-4c07-b546-54eb1396079b",
                products, 1708560000L, "Safira Sudrajat");

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Merdeka No. 1");
        codData.put("deliveryFee", "10000");

        payments = new ArrayList<>();
        payments.add(new Payment("pay-1", order, "VOUCHER", voucherData));
        payments.add(new Payment("pay-2", order, "CASH_ON_DELIVERY", codData));
        payments.add(new Payment("pay-3", order, "VOUCHER", voucherData));
    }

    @Test
    void testSaveNewPayment() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getPaymentId());
        assertEquals(payment.getPaymentId(), result.getPaymentId());
        assertEquals(payment.getPaymentId(), findResult.getPaymentId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdatePayment() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        payment.setStatus(PaymentStatus.REJECTED.getValue());
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getPaymentId());
        assertEquals(payment.getPaymentId(), result.getPaymentId());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }

    @Test
    void testSaveReturnsSavedPayment() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);
        assertNotNull(result);
        assertEquals(payment.getPaymentId(), result.getPaymentId());
    }

    @Test
    void testSaveMultiplePayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }
        assertEquals(3, paymentRepository.getAllPayments().size());
    }

    @Test
    void testFindByIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getPaymentId());
        assertNotNull(findResult);
        assertEquals(payments.get(1).getPaymentId(), findResult.getPaymentId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("zczc");
        assertNull(findResult);
    }

    @Test
    void testFindByIdEmptyRepository() {
        Payment findResult = paymentRepository.findById("pay-1");
        assertNull(findResult);
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> allPayments = paymentRepository.getAllPayments();
        assertEquals(3, allPayments.size());
    }

    @Test
    void testGetAllPaymentsEmpty() {
        List<Payment> allPayments = paymentRepository.getAllPayments();
        assertTrue(allPayments.isEmpty());
    }

    @Test
    void testGetAllPaymentsReturnsCopy() {
        paymentRepository.save(payments.get(0));

        List<Payment> allPayments = paymentRepository.getAllPayments();
        allPayments.clear();

        assertEquals(1, paymentRepository.getAllPayments().size());
    }

    @Test
    void testSaveUpdateDoesNotIncreaseSize() {
        paymentRepository.save(payments.get(0));
        assertEquals(1, paymentRepository.getAllPayments().size());

        payments.get(0).setStatus(PaymentStatus.REJECTED.getValue());
        paymentRepository.save(payments.get(0));

        assertEquals(1, paymentRepository.getAllPayments().size());
    }

    @Test
    void testSaveUpdateKeepsLatestData() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        payment.setStatus(PaymentStatus.REJECTED.getValue());
        paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getPaymentId());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getStatus());
    }
}