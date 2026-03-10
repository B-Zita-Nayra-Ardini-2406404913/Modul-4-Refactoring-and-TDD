package id.ac.ui.cs.advprog.eshop2.model;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class VoucherValidatorTest {

    private final VoucherValidator validator = new VoucherValidator();

    @Test
    void testValidVoucher() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        assertTrue(validator.validate(data));
    }

    @Test
    void testVoucherCodeNull() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", null);
        assertFalse(validator.validate(data));
    }

    @Test
    void testVoucherCodeKeyMissing() {
        Map<String, String> data = new HashMap<>();
        assertFalse(validator.validate(data));
    }

    @Test
    void testVoucherCodeWrongLength() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123ABC567");
        assertFalse(validator.validate(data));
    }

    @Test
    void testVoucherCodeNotStartWithESHOP() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "TOKO1234ABC5678X");
        assertFalse(validator.validate(data));
    }

    @Test
    void testVoucherCodeTooFewDigits() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOPABCDEFGHIJK");
        assertFalse(validator.validate(data));
    }

    @Test
    void testVoucherCodeTooManyDigits() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123456789A");
        assertFalse(validator.validate(data));
    }

    @Test
    void testVoucherCodeExactlyEightDigits() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234XYZ5678");
        assertTrue(validator.validate(data));
    }
}