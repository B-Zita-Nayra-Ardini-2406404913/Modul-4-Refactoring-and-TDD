package id.ac.ui.cs.advprog.eshop2.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CashOnDeliveryValidatorTest {

    private final CashOnDeliveryValidator validator = new CashOnDeliveryValidator();

    @Test
    void testValidCOD() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jl. Merdeka No. 1");
        data.put("deliveryFee", "10000");
        assertTrue(validator.validate(data));
    }

    @Test
    void testAddressNull() {
        Map<String, String> data = new HashMap<>();
        data.put("address", null);
        data.put("deliveryFee", "10000");
        assertFalse(validator.validate(data));
    }

    @Test
    void testDeliveryFeeNull() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jl. Merdeka No. 1");
        data.put("deliveryFee", null);
        assertFalse(validator.validate(data));
    }

    @Test
    void testAddressEmpty() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "10000");
        assertFalse(validator.validate(data));
    }

    @Test
    void testDeliveryFeeEmpty() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Jl. Merdeka No. 1");
        data.put("deliveryFee", "");
        assertFalse(validator.validate(data));
    }

    @Test
    void testBothKeysMissing() {
        Map<String, String> data = new HashMap<>();
        assertFalse(validator.validate(data));
    }

    @Test
    void testBothNull() {
        Map<String, String> data = new HashMap<>();
        data.put("address", null);
        data.put("deliveryFee", null);
        assertFalse(validator.validate(data));
    }

    @Test
    void testBothEmpty() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        data.put("deliveryFee", "");
        assertFalse(validator.validate(data));
    }
}