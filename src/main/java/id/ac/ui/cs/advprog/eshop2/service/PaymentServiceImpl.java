// PaymentServiceImpl.java
package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.model.Order;
import id.ac.ui.cs.advprog.eshop2.model.Payment;
import id.ac.ui.cs.advprog.eshop2.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) { return null; }

    @Override
    public Payment setStatus(Payment payment, String status) { return null; }

    @Override
    public Payment getPayment(String paymentId) { return null; }

    @Override
    public List<Payment> getAllPayments() { return null; }
}