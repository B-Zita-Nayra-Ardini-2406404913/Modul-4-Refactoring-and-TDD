package id.ac.ui.cs.advprog.eshop2.controller;

import id.ac.ui.cs.advprog.eshop2.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop2.model.Order;
import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.service.OrderService;
import id.ac.ui.cs.advprog.eshop2.service.PaymentService;
import id.ac.ui.cs.advprog.eshop2.service.ProductQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Model model;

    @Mock
    private ProductQueryService productQueryService;

    private List<Order> orders;
    private Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("13652556-012a-4c07-b546-54eb1396079b",
                products, 1708560000L, "Safira Sudrajat");

        orders = new ArrayList<>();
        orders.add(order);
        orders.add(new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat"));
    }

    @Test
    void testCreateOrderPage_shouldReturnCorrectView() {
        when(productQueryService.findAll()).thenReturn(new ArrayList<>());
        String viewName = orderController.createOrderPage(model);
        assertEquals("order/createOrder", viewName);
    }

    @Test
    void testCreateOrderPage_shouldNotReturnWrongView() {
        when(productQueryService.findAll()).thenReturn(new ArrayList<>());
        String viewName = orderController.createOrderPage(model);
        assertNotEquals("order/history", viewName);
    }

    @Test
    void testHistoryPage_shouldReturnCorrectView() {
        String viewName = orderController.historyPage(model);
        assertEquals("order/history", viewName);
    }

    @Test
    void testHistoryPage_shouldNotReturnWrongView() {
        String viewName = orderController.historyPage(model);
        assertNotEquals("order/createOrder", viewName);
    }

    @Test
    void testHistoryPost_shouldReturnOrdersForAuthor() {
        String author = "Safira Sudrajat";
        when(orderService.findAllByAuthor(author)).thenReturn(orders);

        String viewName = orderController.historyPost(author, model);

        assertEquals("order/history", viewName);
        verify(orderService, times(1)).findAllByAuthor(author);
        verify(model, times(1)).addAttribute("orders", orders);
    }

    @Test
    void testHistoryPost_shouldReturnEmptyForUnknownAuthor() {
        String author = "Unknown";
        when(orderService.findAllByAuthor(author)).thenReturn(new ArrayList<>());

        String viewName = orderController.historyPost(author, model);

        assertEquals("order/history", viewName);
        verify(model, times(1)).addAttribute("orders", new ArrayList<>());
    }

    @Test
    void testPayOrderPage_shouldReturnCorrectView() {
        String orderId = order.getId();
        when(orderService.findById(orderId)).thenReturn(order);

        String viewName = orderController.payOrderPage(orderId, model);

        assertEquals("order/pay", viewName);
        verify(orderService, times(1)).findById(orderId);
        verify(model, times(1)).addAttribute("order", order);
    }

    @Test
    void testPayOrderPage_shouldNotReturnWrongView() {
        String orderId = order.getId();
        when(orderService.findById(orderId)).thenReturn(order);

        String viewName = orderController.payOrderPage(orderId, model);

        assertNotEquals("order/history", viewName);
        assertNotEquals("order/createOrder", viewName);
    }

    @Test
    void testPayOrderPost_shouldReturnPayResultView() {
        String orderId = order.getId();
        when(orderService.findById(orderId)).thenReturn(order);

        String viewName = orderController.payOrderPost(
                orderId, "VOUCHER", "ESHOP1234ABC5678",
                null, null, model);

        assertEquals("order/payResult", viewName);
        verify(model, times(1)).addAttribute(eq("payment"), any());
    }

    @Test
    void testPayOrderPost_COD_shouldReturnPayResultView() {
        String orderId = order.getId();
        when(orderService.findById(orderId)).thenReturn(order);

        String viewName = orderController.payOrderPost(
                orderId, "CASH_ON_DELIVERY", null,
                "Jl. Merdeka No.1", "10000", model);

        assertEquals("order/payResult", viewName);
        verify(model, times(1)).addAttribute(eq("payment"), any());
    }

}