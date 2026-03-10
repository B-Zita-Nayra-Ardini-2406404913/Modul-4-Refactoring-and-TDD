package id.ac.ui.cs.advprog.eshop2.model;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;
import java.util.Map;

public class Payment {
    String paymentId;
    private Order order;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment() {
    }

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.paymentId = id;
        this.order = order;
        this.method = method;
        this.paymentData = paymentData;
        this.status = resolveStatus(method, paymentData);
    }

    private String resolveStatus(String method, Map<String, String> paymentData) {
        PaymentValidator validator = PaymentValidatorFactory.getValidator(method);
        if (validator != null && validator.validate(paymentData)) {
            return PaymentStatus.SUCCESS.getValue();
        }
        return PaymentStatus.REJECTED.getValue();
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
        this.status = resolveStatus(method, this.paymentData);
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}