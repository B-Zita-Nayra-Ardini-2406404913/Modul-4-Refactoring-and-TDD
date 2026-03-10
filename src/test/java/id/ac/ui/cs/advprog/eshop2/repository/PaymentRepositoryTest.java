package id.ac.ui.cs.advprog.eshop2.repository;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop2.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> codData = new HashMap<>();
        codData.put("address", "Jl. Merdeka No. 1");
        codData.put("deliveryFee", "10000");

        payments = new java.util.ArrayList<>();
        payments.add(new Payment("pay-1", "VOUCHER", voucherData));
        payments.add(new Payment("pay-2", "CASH_ON_DELIVERY", codData));
        payments.add(new Payment("pay-3", "VOUCHER", voucherData));
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
    void testFindByIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getPaymentId());
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
}