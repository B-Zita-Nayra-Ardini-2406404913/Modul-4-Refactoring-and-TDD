package id.ac.ui.cs.advprog.eshop2.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    Payment payment;

    @BeforeEach
    void setUp() {
        this.payment = new Payment();
        this.payment.setPaymentId("eb5589f-1c39-460e-8860-71af6af92cd7");
        this.payment.setMethod("VOUCHER");
        this.payment.setStatus("SUCCESS");
        this.payment.setPaymentData(
            new HashMap<String, String>() {{
                put("voucherCode", "ESHOP1234ABC5678");
            }}
        );
    }

    @Test
    void testGetPaymentId() {
        assertEquals("eb5589f-1c39-460e-8860-71af6af92cd7", this.payment.getPaymentId());
    }

    @Test
    void testGetPaymentIdFalse() {
        assertNotEquals("eb5589f-1c39-460e-8860-71af6af63be7", this.payment.getPaymentId());
    }

    @Test
    void testGetMethodPayment() {
        assertEquals("VOUCHER", this.payment.getMethod());
    }

    @Test
    void testGetMethodPaymentFalse() {
        assertNotEquals("Sampo Cap Bantu dong", this.payment.getMethod());
    }

    @Test
    void testGetStatusPayment() {
        assertEquals("SUCCESS", this.payment.getStatus());
    }

    @Test
    void testGetPaymentFalse() {
        assertNotEquals("REJECTED", this.payment.getStatus());
    }

    @Test
    void testGetPaymentData() {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("voucherCode", "ESHOP1234ABC5678");

        assertEquals(expected, payment.getPaymentData());
    }

    @Test
    void testGetPaymentDataFalse() {
        HashMap<String, String> expected = new HashMap<>();
        expected.put("voucherCode", "ESHOP1234ABC5679");

        assertNotEquals(expected, payment.getPaymentData());
    }

    @Test
    void testCreatePaymentSuccess() {
        Payment payment1 = new Payment(
            this.payment.getPaymentId(),
                this.payment.getMethod(),
                this.payment.getPaymentData()
        );

        assertEquals("SUCCESS", payment1.getStatus());
        assertEquals("VOUCHER", payment1.getMethod());
    }

    @Test
    void testCreatePaymentRejectedVoucher() {
        Payment payment1 = new Payment(
                this.payment.getPaymentId(),
                this.payment.getMethod(),
                new HashMap<String, String>() {{
                    put("voucherCode", "ESHOP1234ABC567a");
                }}
        );

        assertEquals("REJECTED", payment1.getStatus());
        assertEquals("VOUCHER", payment1.getMethod());
    }

    @Test
    void testCreatePaymentRejectedCOD() {
        Payment payment1 = new Payment(
                this.payment.getPaymentId(),
                "CASH_ON_DELIVERY",
                new HashMap<String, String>() {{
                    put("address", null);
                    put("deliveryFee", null);
                }}
        );

        assertEquals("REJECTED", payment1.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment1.getMethod());
    }
}
