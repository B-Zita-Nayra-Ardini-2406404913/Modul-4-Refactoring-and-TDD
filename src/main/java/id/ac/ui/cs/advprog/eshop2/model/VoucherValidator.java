package id.ac.ui.cs.advprog.eshop2.model;

import java.util.Map;

public class VoucherValidator implements PaymentValidator{
    @Override
    public boolean validate(Map<String, String> paymentData) {
        String value = paymentData.get("voucherCode");
        if (value == null || value.length() != 16 || !value.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : value.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
        }
        return digitCount == 8;
    }
}
