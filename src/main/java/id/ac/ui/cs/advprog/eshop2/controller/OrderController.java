package id.ac.ui.cs.advprog.eshop2.controller;

import id.ac.ui.cs.advprog.eshop2.model.Order;
import id.ac.ui.cs.advprog.eshop2.model.Payment;
import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.service.OrderService;
import id.ac.ui.cs.advprog.eshop2.service.PaymentService;
import id.ac.ui.cs.advprog.eshop2.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductQueryService productService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        model.addAttribute("products", productService.findAll());
        return "order/createOrder";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam String author,
                                  @RequestParam List<String> productIds) {
        List<Product> selectedProducts = productIds.stream()
                .map(productService::findById)
                .collect(java.util.stream.Collectors.toList());

        Order order = new Order(
                java.util.UUID.randomUUID().toString(),
                selectedProducts,
                System.currentTimeMillis() / 1000L,
                author
        );
        orderService.createOrder(order);
        return "redirect:/order/history";
    }
    @GetMapping("/history")
    public String historyPage(Model model) {
        return "order/history";
    }

    @PostMapping("/history")
    public String historyPost(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "order/history";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order/pay";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId,
                               @RequestParam String method,
                               @RequestParam(required = false) String voucherCode,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) String deliveryFee,
                               Model model) {
        Order order = orderService.findById(orderId);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", voucherCode);
        paymentData.put("address", address);
        paymentData.put("deliveryFee", deliveryFee);

        Payment payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("payment", payment);
        return "order/payResult";
    }
}