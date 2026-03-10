package id.ac.ui.cs.advprog.eshop2.repository;

import id.ac.ui.cs.advprog.eshop2.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int existingIndex = findIndexById(payment.getPaymentId());
        if (existingIndex >= 0) {
            paymentData.set(existingIndex, payment);
        } else {
            paymentData.add(payment);
        }
        return payment;
    }

    public Payment findById(String id) {
        int index = findIndexById(id);
        return index >= 0 ? paymentData.get(index) : null;
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentData);
    }

    private int findIndexById(String id) {
        for (int i = 0; i < paymentData.size(); i++) {
            if (paymentData.get(i).getPaymentId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}