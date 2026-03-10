package id.ac.ui.cs.advprog.eshop2.service;

import id.ac.ui.cs.advprog.eshop2.model.Order;
import id.ac.ui.cs.advprog.eshop2.model.Product;
import id.ac.ui.cs.advprog.eshop2.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop2.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;


    private List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order("13652556-012a-4c07-b546-54eb1396079b",
                products, 1708560000L, "Safira Sudrajat");
        orders.add(order1);
        Order order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products, 1708570000L, "Safira Sudrajat");
        orders.add(order2);
    }

    @Test
    void testCreateOrder() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).save(order);

        Order result = orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testCreateOrderIfAlreadyExists() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalStateException.class,
                () -> orderService.createOrder(order));
        verify(orderRepository, times(0)).save(order);
    }

    @Test
    void testUpdateStatus() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());
        doReturn(order).when(orderRepository).save(order);

        Order result = orderService.updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        assertEquals(order.getId(), result.getId());
        assertEquals(OrderStatus.SUCCESS.getValue(), result.getStatus());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testUpdateStatusInvalidStatus() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        assertThrows(IllegalArgumentException.class,
                () -> orderService.updateStatus(order.getId(), "NEW"));

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testUpdateStatusInvalidOrderId() {
        doReturn(null).when(orderRepository).findById("zzzc");

        assertThrows(NoSuchElementException.class,
                () -> orderService.updateStatus("zzzc", OrderStatus.SUCCESS.getValue()));

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void testFindByIdIfFound() {
        Order order = orders.get(1);
        doReturn(order).when(orderRepository).findById(order.getId());

        Order result = orderService.findById(order.getId());
        assertEquals(order.getId(), result.getId());
    }

    @Test
    void testFindByIdIfNotFound() {
        doReturn(null).when(orderRepository).findById("zzc");

        Order result = orderService.findById("zzc");
        assertNull(result);
    }

    @Test
    void testFindAllByAuthorIfAuthorCorrect() {
        String author = orders.get(1).getAuthor();
        doReturn(orders).when(orderRepository).findAllByAuthor(author);

        List<Order> results = orderService.findAllByAuthor(author);
        for (Order result : results) {
            assertEquals(author, result.getAuthor());
        }
        assertEquals(2, results.size());
    }

    @Test
    void testFindAllByAuthorIfAllUpperCase() {
        String author = orders.get(1).getAuthor();
        String lowerCaseAuthor = author.toLowerCase();
        doReturn(new ArrayList<Order>()).when(orderRepository)
                .findAllByAuthor(lowerCaseAuthor);

        List<Order> results = orderService.findAllByAuthor(lowerCaseAuthor);
        assertTrue(results.isEmpty());
    }
}