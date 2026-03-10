package id.ac.ui.cs.advprog.eshop2.model;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;

import java.util.Map;

public class Payment {
    String paymentId;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment() {
    }

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.paymentId = id;
        this.method = method;
        this.paymentData = paymentData;

        PaymentValidator validator = PaymentValidatorFactory.getValidator(method);
        if (validator != null && validator.validate(paymentData)) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String id) {
        this.paymentId = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;

        PaymentValidator validator = PaymentValidatorFactory.getValidator(method);
        if (validator != null && validator.validate(this.paymentData)) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        this.paymentData = paymentData;
    }
}
