package id.ac.ui.cs.advprog.eshop2.controller;

import id.ac.ui.cs.advprog.eshop2.model.Payment;
import id.ac.ui.cs.advprog.eshop2.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // GET /payment/detail
    @GetMapping("/detail")
    public String detailPage(Model model) {
        return "payment/detail";
    }

    // GET /payment/detail/{paymentId}
    @GetMapping("/detail/{paymentId}")
    public String detailById(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "payment/detail";
    }

    // GET /payment/admin/list
    @GetMapping("/admin/list")
    public String adminList(Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "payment/list";
    }

    // GET /payment/admin/detail/{paymentId}
    @GetMapping("/admin/detail/{paymentId}")
    public String adminDetail(@PathVariable String paymentId, Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        model.addAttribute("payment", payment);
        return "payment/adminDetail";
    }

    // POST /payment/admin/set-status/{paymentId}
    @PostMapping("/admin/set-status/{paymentId}")
    public String setStatus(@PathVariable String paymentId,
                            @RequestParam String status,
                            Model model) {
        Payment payment = paymentService.getPayment(paymentId);
        paymentService.setStatus(payment, status);
        return "redirect:/payment/admin/list";
    }
}