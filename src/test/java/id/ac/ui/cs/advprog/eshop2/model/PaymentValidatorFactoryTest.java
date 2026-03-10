package id.ac.ui.cs.advprog.eshop2.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentValidatorFactoryTest {
    @Test
    void testGetVoucherValidator() {
        PaymentValidator validator = PaymentValidatorFactory.getValidator("VOUCHER");
        assertNotNull(validator);
        assertInstanceOf(VoucherValidator.class, validator);
    }

    @Test
    void testGetCashOnDeliveryValidator() {
        PaymentValidator validator = PaymentValidatorFactory.getValidator("CASH_ON_DELIVERY");
        assertNotNull(validator);
        assertInstanceOf(CashOnDeliveryValidator.class, validator);
    }

    @Test
    void testGetUnknownValidatorReturnsNull() {
        PaymentValidator validator = PaymentValidatorFactory.getValidator("UNKNOWN");
        assertNull(validator);
    }

    @Test
    void testGetNullMethodReturnsNull() {
        PaymentValidator validator = PaymentValidatorFactory.getValidator(null);
        assertNull(validator);
    }

    @Test
    void testGetEmptyStringReturnsNull() {
        PaymentValidator validator = PaymentValidatorFactory.getValidator("");
        assertNull(validator);
    }
}