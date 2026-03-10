package id.ac.ui.cs.advprog.eshop2.model;

import id.ac.ui.cs.advprog.eshop2.enums.PaymentStatus;

import java.util.Map;

public class VoucherPayment extends Payment {

    public VoucherPayment(String id, Map<String, String> paymentData) {
        this.paymentId = id;
        this.method = "VOUCHER";
        this.paymentData = paymentData;

        if (isValidVoucher(paymentData.get("voucherCode"))) {
            this.status = PaymentStatus.SUCCESS.getValue();
        } else {
            this.status = PaymentStatus.REJECTED.getValue();
        }
    }

    private boolean isValidVoucher(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
        }
        return digitCount == 8;
    }
}