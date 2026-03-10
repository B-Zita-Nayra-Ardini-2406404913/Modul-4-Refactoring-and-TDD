package id.ac.ui.cs.advprog.eshop2.model;

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

        if (method.equals("VOUCHER")){
            String value = paymentData.get("voucherCode");
            if (value == null || value.length() != 16 || !value.startsWith("ESHOP")){
                this.status = "REJECTED";
                return;
            }

            int digitCount = 0;
            for (char c : value.toCharArray()) {
                if (Character.isDigit(c)) {
                    digitCount++;
                }
            }
            if (digitCount != 8) {
                this.status = "REJECTED";
                return;
            }
        } else if (method.equals("CASH_ON_DELIVERY")) {
            String value1 = paymentData.get("address");
            String value2 = paymentData.get("deliveryFee");

            if (value1 == null || value2 == null || value1.isEmpty() || value2.isEmpty()){
                this.status = "REJECTED";
                return;
            }
        } else {
            this.status = "REJECTED";
            return;
        }
        this.status = "SUCCESS";
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

        if (method.equals("VOUCHER")){
            String value = paymentData.get("voucherCode");
            if (value == null || value.length() != 16 || !value.startsWith("ESHOP")){
                this.status = "REJECTED";
                return;
            }

            int digitCount = 0;
            for (char c : value.toCharArray()) {
                if (Character.isDigit(c)) {
                    digitCount++;
                }
            }
            if (digitCount != 8) {
                this.status = "REJECTED";
                return;
            }
        } else if (method.equals("CASH_ON_DELIVERY")) {
            String value1 = paymentData.get("address");
            String value2 = paymentData.get("deliveryFee");

            if (value1 == null || value2 == null || value1.isEmpty() || value2.isEmpty()){
                this.status = "REJECTED";
                return;
            }
        } else {
            this.status = "REJECTED";
            return;
        }
        this.status = "SUCCESS";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (!status.equals("SUCCESS") && !status.equals("REJECTED")){
            throw new IllegalArgumentException("Invalid status" + status);
        }
        this.status = status;
    }

    public Map<String, String> getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(Map<String, String> paymentData) {
        this.paymentData = paymentData;
    }
}