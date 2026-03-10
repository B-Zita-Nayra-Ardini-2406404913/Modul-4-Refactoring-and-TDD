package id.ac.ui.cs.advprog.eshop2.model;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentCODTest {

    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    @Test
    void testCODValidSuccess() {
        paymentData.put("address", "Jl. Merdeka No. 1");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-1", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODValidDifferentValues() {
        paymentData.put("address", "Jl. Sudirman No. 99, Jakarta");
        paymentData.put("deliveryFee", "25000");
        Payment payment = new Payment("pay-2", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODAddressNullRejected() {
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-3", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODAddressEmptyRejected() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-4", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODAddressKeyMissingRejected() {
        paymentData.put("deliveryFee", "10000");
        Payment payment = new Payment("pay-5", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODDeliveryFeeNullRejected() {
        paymentData.put("address", "Jl. Merdeka No. 1");
        paymentData.put("deliveryFee", null);
        Payment payment = new Payment("pay-6", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODDeliveryFeeEmptyRejected() {
        paymentData.put("address", "Jl. Merdeka No. 1");
        paymentData.put("deliveryFee", "");
        Payment payment = new Payment("pay-7", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODDeliveryFeeKeyMissingRejected() {
        paymentData.put("address", "Jl. Merdeka No. 1");
        Payment payment = new Payment("pay-8", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODBothNullRejected() {
        paymentData.put("address", null);
        paymentData.put("deliveryFee", null);
        Payment payment = new Payment("pay-9", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODBothEmptyRejected() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "");
        Payment payment = new Payment("pay-10", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }

    @Test
    void testCODEmptyMapRejected() {
        Payment payment = new Payment("pay-11", "CASH_ON_DELIVERY", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals("CASH_ON_DELIVERY", payment.getMethod());
    }
}