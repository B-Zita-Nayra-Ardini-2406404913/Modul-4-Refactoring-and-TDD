package id.ac.ui.cs.advprog.eshop2.model;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop2.model.Payment;

import java.util.Map;

public class CODPayment extends Payment {

    public CODPayment(String id, Map<String, String> paymentData) {
        this.paymentId = id;
        this.method = "CASH_ON_DELIVERY";
        this.paymentData = paymentData;

        if (isValidCOD(paymentData)) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }

    private boolean isValidCOD(Map<String, String> data) {
        String address = data.get("address");
        String deliveryFee = data.get("deliveryFee");

        return address != null && deliveryFee != null
                && !address.isEmpty() && !deliveryFee.isEmpty();
    }
}