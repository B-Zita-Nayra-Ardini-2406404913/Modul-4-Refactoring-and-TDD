package id.ac.ui.cs.advprog.eshop2.model;

import java.util.HashMap;
import java.util.Map;

public class PaymentValidatorFactory {
    private static final Map<String, PaymentValidator> validators = new HashMap<>();

    static {
        validators.put("VOUCHER", new VoucherValidator());
        validators.put("CASH_ON_DELIVERY", new CashOnDeliveryValidator());
    }

    public static PaymentValidator getValidator(String method) {
        return validators.get(method);
    }
}
