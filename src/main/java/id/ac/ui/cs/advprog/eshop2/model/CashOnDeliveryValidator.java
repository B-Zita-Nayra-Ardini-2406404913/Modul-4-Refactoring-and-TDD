package id.ac.ui.cs.advprog.eshop2.model;

import java.util.Map;

public class CashOnDeliveryValidator implements PaymentValidator{
    @Override
    public boolean validate(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String fee = paymentData.get("deliveryFee");
        return address != null && fee != null
                && !address.isEmpty() && !fee.isEmpty();
    }
}