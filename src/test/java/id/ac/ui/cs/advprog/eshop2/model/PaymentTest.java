package id.ac.ui.cs.advprog.eshop2.model;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
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
        this.payment.setStatus(PaymentStatus.SUCCESS.getValue());
        this.payment.setPaymentData(
            new HashMap<String, String>() {{
                put("voucherCode", "ESHOP1234ABC5678");
            }}
        );
        this.payment.setMethod("VOUCHER");
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
    void testSetPaymentId() {
        this.payment.setPaymentId("new-id-123");
        assertEquals("new-id-123", this.payment.getPaymentId());
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
    void testSetMethodUpdatesStatusToSuccess() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("voucherCode", "ESHOP1234ABC5678");
        }});
        this.payment.setMethod("VOUCHER");
        assertEquals(PaymentStatus.SUCCESS.getValue(), this.payment.getStatus());
    }

    @Test
    void testSetMethodUpdatesStatusToRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("voucherCode", "INVALID");
        }});
        this.payment.setMethod("VOUCHER");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
    }

    @Test
    void testSetMethodUnknownMethodRejected() {
        this.payment.setMethod("UNKNOWN_METHOD");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
    }

    @Test
    void testGetStatusPayment() {
        assertEquals(PaymentStatus.SUCCESS.getValue(), this.payment.getStatus());
    }

    @Test
    void testGetPaymentFalse() {
        assertNotEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
    }

    @Test
    void testSetStatusValid() {
        this.payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
    }

    @Test
    void testSetStatusInvalidThrowsException() {
        assertThrows(IllegalArgumentException.class, () ->
                this.payment.setStatus("INVALID_STATUS")
        );
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
    void testSetPaymentData() {
        HashMap<String, String> newData = new HashMap<>();
        newData.put("voucherCode", "ESHOP9999XYZ1111");
        this.payment.setPaymentData(newData);
        assertEquals(newData, this.payment.getPaymentData());
    }

    @Test
    void testCreatePaymentSuccess() {
        Payment payment1 = new Payment(
                this.payment.getPaymentId(),
                this.payment.getMethod(),
                this.payment.getPaymentData()
        );
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment1.getStatus());
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
        assertEquals(PaymentStatus.REJECTED.getValue(), payment1.getStatus());
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
        assertEquals(PaymentStatus.REJECTED.getValue(), payment1.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment1.getMethod());
    }

    @Test
    void testCreatePaymentUnknownMethodRejected() {
        Payment payment1 = new Payment(
                "some-id",
                "UNKNOWN_METHOD",
                new HashMap<>()
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), payment1.getStatus());
    }
}