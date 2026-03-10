package id.ac.ui.cs.advprog.eshop2.model;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentVoucherTest {

    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testVoucherValidSuccess() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay-1", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherValidDifferentLetters() {
        paymentData.put("voucherCode", "ESHOP1234XYZ5678");
        Payment payment = new Payment("pay-2", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherNullCodeRejected() {
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("pay-3", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherMissingKeyRejected() {
        Payment payment = new Payment("pay-4", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherTooShortRejected() {
        paymentData.put("voucherCode", "ESHOP123ABC567");
        Payment payment = new Payment("pay-5", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherTooLongRejected() {
        paymentData.put("voucherCode", "ESHOP1234ABC56789");
        Payment payment = new Payment("pay-6", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherNotStartWithESHOPRejected() {
        paymentData.put("voucherCode", "TOKO1234ABC5678X");
        Payment payment = new Payment("pay-7", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherLowercaseESHOPRejected() {
        paymentData.put("voucherCode", "eshop1234ABC5678");
        Payment payment = new Payment("pay-8", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherTooFewDigitsRejected() {
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");
        Payment payment = new Payment("pay-9", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherTooManyDigitsRejected() {
        paymentData.put("voucherCode", "ESHOP123456789A");
        Payment payment = new Payment("pay-10", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherSevenDigitsRejected() {
        paymentData.put("voucherCode", "ESHOP1234ABCD567");
        Payment payment = new Payment("pay-11", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testVoucherLowercaseCharNotCountedAsDigit() {
        paymentData.put("voucherCode", "ESHOP1234ABC567a");
        Payment payment = new Payment("pay-12", "VOUCHER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }
}