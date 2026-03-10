package id.ac.ui.cs.advprog.eshop2.model;

import java.util.Map;

public interface PaymentValidator {
    boolean validate(Map<String, String> paymentData);
}
