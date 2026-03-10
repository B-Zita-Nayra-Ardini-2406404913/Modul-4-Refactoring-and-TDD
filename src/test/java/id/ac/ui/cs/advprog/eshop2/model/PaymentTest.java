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
    void testGetStatusPayment() {
        assertEquals(PaymentStatus.SUCCESS.getValue(), this.payment.getStatus());
    }

    @Test
    void testGetStatusPaymentFalse() {
        assertNotEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        this.payment.setStatus(PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), this.payment.getStatus());
    }

    @Test
    void testSetStatusRejected() {
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
    void testCreatePaymentVoucherSuccess() {
        Payment p = new Payment(
                "id-1", "VOUCHER",
                new HashMap<String, String>() {{ put("voucherCode", "ESHOP1234ABC5678"); }}
        );
        assertEquals(PaymentStatus.SUCCESS.getValue(), p.getStatus());
        assertEquals("VOUCHER", p.getMethod());
    }

    @Test
    void testCreatePaymentVoucherNullCode() {
        Payment p = new Payment(
                "id-2", "VOUCHER",
                new HashMap<String, String>() {{ put("voucherCode", null); }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("VOUCHER", p.getMethod());
    }

    @Test
    void testCreatePaymentVoucherWrongLength() {
        Payment p = new Payment(
                "id-3", "VOUCHER",
                new HashMap<String, String>() {{ put("voucherCode", "ESHOP123"); }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("VOUCHER", p.getMethod());
    }

    @Test
    void testCreatePaymentVoucherNotStartWithESHOP() {
        Payment p = new Payment(
                "id-4", "VOUCHER",
                new HashMap<String, String>() {{ put("voucherCode", "TOKO1234ABC5678X"); }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("VOUCHER", p.getMethod());
    }

    @Test
    void testCreatePaymentVoucherWrongDigitCount() {
        Payment p = new Payment(
                "id-5", "VOUCHER",
                new HashMap<String, String>() {{ put("voucherCode", "ESHOP1234ABCDEFG"); }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("VOUCHER", p.getMethod());
    }

    @Test
    void testCreatePaymentRejectedVoucher() {
        Payment payment1 = new Payment(
                this.payment.getPaymentId(),
                this.payment.getMethod(),
                new HashMap<String, String>() {{ put("voucherCode", "ESHOP1234ABC567a"); }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), payment1.getStatus());
        assertEquals("VOUCHER", payment1.getMethod());
    }

    @Test
    void testCreatePaymentCODSuccess() {
        Payment p = new Payment(
                "id-6", "CASH_ON_DELIVERY",
                new HashMap<String, String>() {{
                    put("address", "Jl. Merdeka No. 1");
                    put("deliveryFee", "10000");
                }}
        );
        assertEquals(PaymentStatus.SUCCESS.getValue(), p.getStatus());
        assertEquals("CASH_ON_DELIVERY", p.getMethod());
    }

    @Test
    void testCreatePaymentCODAddressNull() {
        Payment p = new Payment(
                "id-7", "CASH_ON_DELIVERY",
                new HashMap<String, String>() {{
                    put("address", null);
                    put("deliveryFee", "10000");
                }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("CASH_ON_DELIVERY", p.getMethod());
    }

    @Test
    void testCreatePaymentCODFeeNull() {
        Payment p = new Payment(
                "id-8", "CASH_ON_DELIVERY",
                new HashMap<String, String>() {{
                    put("address", "Jl. Merdeka No. 1");
                    put("deliveryFee", null);
                }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("CASH_ON_DELIVERY", p.getMethod());
    }

    @Test
    void testCreatePaymentCODAddressEmpty() {
        Payment p = new Payment(
                "id-9", "CASH_ON_DELIVERY",
                new HashMap<String, String>() {{
                    put("address", "");
                    put("deliveryFee", "10000");
                }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("CASH_ON_DELIVERY", p.getMethod());
    }

    @Test
    void testCreatePaymentCODFeeEmpty() {
        Payment p = new Payment(
                "id-10", "CASH_ON_DELIVERY",
                new HashMap<String, String>() {{
                    put("address", "Jl. Merdeka No. 1");
                    put("deliveryFee", "");
                }}
        );
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("CASH_ON_DELIVERY", p.getMethod());
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
        Payment p = new Payment("id-11", "UNKNOWN_METHOD", new HashMap<>());
        assertEquals(PaymentStatus.REJECTED.getValue(), p.getStatus());
        assertEquals("UNKNOWN_METHOD", p.getMethod());
    }

    @Test
    void testSetMethodVoucherSuccess() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("voucherCode", "ESHOP1234ABC5678");
        }});
        this.payment.setMethod("VOUCHER");
        assertEquals(PaymentStatus.SUCCESS.getValue(), this.payment.getStatus());
        assertEquals("VOUCHER", this.payment.getMethod());
    }

    @Test
    void testSetMethodVoucherNullRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("voucherCode", null);
        }});
        this.payment.setMethod("VOUCHER");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("VOUCHER", this.payment.getMethod());
    }

    @Test
    void testSetMethodVoucherWrongLengthRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("voucherCode", "ESHOP123");
        }});
        this.payment.setMethod("VOUCHER");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("VOUCHER", this.payment.getMethod());
    }

    @Test
    void testSetMethodVoucherNotStartESHOPRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("voucherCode", "TOKO1234ABC5678X");
        }});
        this.payment.setMethod("VOUCHER");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("VOUCHER", this.payment.getMethod());
    }

    @Test
    void testSetMethodVoucherWrongDigitRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("voucherCode", "ESHOP1234ABCDEFG");
        }});
        this.payment.setMethod("VOUCHER");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("VOUCHER", this.payment.getMethod());
    }

    @Test
    void testSetMethodCODSuccess() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("address", "Jl. Merdeka No. 1");
            put("deliveryFee", "10000");
        }});
        this.payment.setMethod("CASH_ON_DELIVERY");
        assertEquals(PaymentStatus.SUCCESS.getValue(), this.payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", this.payment.getMethod());
    }

    @Test
    void testSetMethodCODAddressNullRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("address", null);
            put("deliveryFee", "10000");
        }});
        this.payment.setMethod("CASH_ON_DELIVERY");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", this.payment.getMethod());
    }

    @Test
    void testSetMethodCODFeeNullRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("address", "Jl. Merdeka No. 1");
            put("deliveryFee", null);
        }});
        this.payment.setMethod("CASH_ON_DELIVERY");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", this.payment.getMethod());
    }

    @Test
    void testSetMethodCODAddressEmptyRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("address", "");
            put("deliveryFee", "10000");
        }});
        this.payment.setMethod("CASH_ON_DELIVERY");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", this.payment.getMethod());
    }

    @Test
    void testSetMethodCODFeeEmptyRejected() {
        this.payment.setPaymentData(new HashMap<String, String>() {{
            put("address", "Jl. Merdeka No. 1");
            put("deliveryFee", "");
        }});
        this.payment.setMethod("CASH_ON_DELIVERY");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", this.payment.getMethod());
    }

    @Test
    void testSetMethodUnknownRejected() {
        this.payment.setMethod("UNKNOWN_METHOD");
        assertEquals(PaymentStatus.REJECTED.getValue(), this.payment.getStatus());
        assertEquals("UNKNOWN_METHOD", this.payment.getMethod());
    }
}