package id.ac.ui.cs.advprog.eshop2.functional;

import id.ac.ui.cs.advprog.eshop2.controller.PaymentController;
import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop2.model.Payment;
import id.ac.ui.cs.advprog.eshop2.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Model model;

    private List<Payment> payments;
    private Payment payment;

    @BeforeEach
    void setUp() {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        payment = new Payment("pay-1", null, "VOUCHER", voucherData);

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Merdeka No. 1");
        codData.put("deliveryFee", "10000");

        payments = new ArrayList<>();
        payments.add(payment);
        payments.add(new Payment("pay-2", null, "CASH_ON_DELIVERY", codData));
    }

    @Test
    void testDetailPage_shouldReturnCorrectView() {
        String viewName = paymentController.detailPage(model);
        assertEquals("payment/detail", viewName);
    }

    @Test
    void testDetailPage_shouldNotReturnWrongView() {
        String viewName = paymentController.detailPage(model);
        assertNotEquals("payment/list", viewName);
    }

    @Test
    void testDetailById_shouldReturnCorrectView() {
        when(paymentService.getPayment(payment.getPaymentId())).thenReturn(payment);

        String viewName = paymentController.detailById(payment.getPaymentId(), model);

        assertEquals("payment/detail", viewName);
        verify(paymentService, times(1)).getPayment(payment.getPaymentId());
        verify(model, times(1)).addAttribute("payment", payment);
    }

    @Test
    void testDetailById_notFound_shouldStillReturnView() {
        when(paymentService.getPayment("zczc")).thenReturn(null);

        String viewName = paymentController.detailById("zczc", model);

        assertEquals("payment/detail", viewName);
        verify(model, times(1)).addAttribute("payment", null);
    }

    @Test
    void testAdminList_shouldReturnCorrectView() {
        when(paymentService.getAllPayments()).thenReturn(payments);

        String viewName = paymentController.adminList(model);

        assertEquals("payment/list", viewName);
        verify(paymentService, times(1)).getAllPayments();
        verify(model, times(1)).addAttribute("payments", payments);
    }

    @Test
    void testAdminList_shouldNotReturnWrongView() {
        when(paymentService.getAllPayments()).thenReturn(payments);
        String viewName = paymentController.adminList(model);
        assertNotEquals("payment/detail", viewName);
    }

    @Test
    void testAdminDetail_shouldReturnCorrectView() {
        when(paymentService.getPayment(payment.getPaymentId())).thenReturn(payment);

        String viewName = paymentController.adminDetail(payment.getPaymentId(), model);

        assertEquals("payment/adminDetail", viewName);
        verify(paymentService, times(1)).getPayment(payment.getPaymentId());
        verify(model, times(1)).addAttribute("payment", payment);
    }

    @Test
    void testAdminDetail_shouldNotReturnWrongView() {
        when(paymentService.getPayment(payment.getPaymentId())).thenReturn(payment);
        String viewName = paymentController.adminDetail(payment.getPaymentId(), model);
        assertNotEquals("payment/list", viewName);
    }

    @Test
    void testSetStatus_shouldRedirectToAdminDetail() {
        when(paymentService.getPayment(payment.getPaymentId())).thenReturn(payment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(payment);

        String viewName = paymentController.setStatus(
                payment.getPaymentId(), PaymentStatus.SUCCESS.getValue(), model);

        assertEquals("redirect:/payment/admin/detail/" + payment.getPaymentId(), viewName);
        verify(paymentService, times(1)).setStatus(payment, PaymentStatus.SUCCESS.getValue());
    }

    @Test
    void testSetStatus_rejected_shouldRedirectToAdminDetail() {
        when(paymentService.getPayment(payment.getPaymentId())).thenReturn(payment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(payment);

        String viewName = paymentController.setStatus(
                payment.getPaymentId(), PaymentStatus.REJECTED.getValue(), model);

        assertEquals("redirect:/payment/admin/detail/" + payment.getPaymentId(), viewName);
        verify(paymentService, times(1)).setStatus(payment, PaymentStatus.REJECTED.getValue());
    }

    @Test
    void testSetStatus_shouldNotReturnWrongView() {
        when(paymentService.getPayment(payment.getPaymentId())).thenReturn(payment);
        when(paymentService.setStatus(any(Payment.class), anyString())).thenReturn(payment);

        String viewName = paymentController.setStatus(
                payment.getPaymentId(), PaymentStatus.SUCCESS.getValue(), model);

        assertNotEquals("payment/adminDetail", viewName);
        assertNotEquals("payment/list", viewName);
    }

}
