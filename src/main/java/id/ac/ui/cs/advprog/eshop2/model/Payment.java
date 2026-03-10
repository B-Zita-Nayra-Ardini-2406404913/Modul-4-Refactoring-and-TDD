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

        if (method.equals("VOUCHER")) {
            String value = paymentData.get("voucherCode");
            if (value == null || value.length() != 16 || !value.startsWith("ESHOP")) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }
            int digitCount = 0;
            for (char c : value.toCharArray()) {
                if (Character.isDigit(c)) digitCount++;
            }
            if (digitCount != 8) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }
        } else if (method.equals("CASH_ON_DELIVERY")) {
            String value1 = paymentData.get("address");
            String value2 = paymentData.get("deliveryFee");
            if (value1 == null || value2 == null || value1.isEmpty() || value2.isEmpty()) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
            return;
        }
        this.status = PaymentStatus.SUCCESS.getValue();
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

        if (method.equals("VOUCHER")) {
            String value = paymentData.get("voucherCode");
            if (value == null || value.length() != 16 || !value.startsWith("ESHOP")) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }
            int digitCount = 0;
            for (char c : value.toCharArray()) {
                if (Character.isDigit(c)) digitCount++;
            }
            if (digitCount != 8) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }
        } else if (method.equals("CASH_ON_DELIVERY")) {
            String value1 = paymentData.get("address");
            String value2 = paymentData.get("deliveryFee");
            if (value1 == null || value2 == null || value1.isEmpty() || value2.isEmpty()) {
                this.status = PaymentStatus.REJECTED.getValue();
                return;
            }
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
            return;
        }
        this.status = PaymentStatus.SUCCESS.getValue();
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