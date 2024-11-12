package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Order;
import group15.gameStore.model.Status;
import group15.gameStore.repository.OrderRepository;
import group15.gameStore.service.CustomerService;
import group15.gameStore.service.EmployeeService;
import group15.gameStore.service.OrderService;
import jakarta.transaction.Transactional;

@SpringBootTest
@ActiveProfiles("test") // Use a test profile to avoid affecting the production database
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    private Customer customer;
    private Employee employee;

    @BeforeEach
    void setUp() {
        // Initialize sample data and save to database
        customer = new Customer("John", "Doe", "john.doe@example.com", null, null);
        customer = customerService.createCustomer(customer);

        employee = new Employee("Jane", "Doe", "jane.doe@example.com", false, false);
        employee = employeeService.createEmployee(employee);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        Order order = new Order("ORD123", Status.PendingPurchase, 99.99, customer);

        // Act
        Order createdOrder = orderService.createOrder(order.getOrderNumber(), order.getOrderStatus(), order.getPrice(), customer);

        // Assert
        assertNotNull(createdOrder);
        assertEquals("ORD123", createdOrder.getOrderNumber());
        assertEquals(Status.PendingPurchase, createdOrder.getOrderStatus());
        assertEquals(99.99, createdOrder.getPrice());
        assertEquals(customer.getUserID(), createdOrder.getCustomer().getUserID());
    }

    @Test
    void testUpdateOrder() {
        // Arrange
        Order order = new Order("ORD123", Status.PendingPurchase, 99.99, customer);
        order = orderService.createOrder(order.getOrderNumber(), order.getOrderStatus(), order.getPrice(), customer);
        
        // Update status and price
        order.setOrderStatus(Status.DELIVERED);
        order.setPrice(120.0);

        // Act
        Order updatedOrder = orderService.updateOrder(order.getOrderID(), order, employee);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(Status.DELIVERED, updatedOrder.getOrderStatus());
        assertEquals(120.0, updatedOrder.getPrice());
    }

    @Test
    void testGetOrderByID() {
        // Arrange
        Order order = new Order("ORD123", Status.PendingPurchase, 99.99, customer);
        order = orderService.createOrder(order.getOrderNumber(), order.getOrderStatus(), order.getPrice(), customer);

        // Act
        Order foundOrder = orderService.getOrderByID(order.getOrderID());

        // Assert
        assertNotNull(foundOrder);
        assertEquals(order.getOrderID(), foundOrder.getOrderID());
        assertEquals("ORD123", foundOrder.getOrderNumber());
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        Order order1 = new Order("ORD123", Status.PendingPurchase, 99.99, customer);
        Order order2 = new Order("ORD124", Status.DELIVERED, 150.0, customer);
        orderService.createOrder(order1.getOrderNumber(), order1.getOrderStatus(), order1.getPrice(), customer);
        orderService.createOrder(order2.getOrderNumber(), order2.getOrderStatus(), order2.getPrice(), customer);

        // Act
        List<Order> allOrders = orderService.getAllOrders(0);

        // Assert
        assertNotNull(allOrders);
        assertEquals(2, allOrders.size());
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        Order order = new Order("ORD123", Status.PendingPurchase, 99.99, customer);
        order = orderService.createOrder(order.getOrderNumber(), order.getOrderStatus(), order.getPrice(), customer);

        // Act
        orderService.deleteOrder(order, employee);

        // Assert
        assertFalse(orderRepository.findById(order.getOrderID()).isPresent());
    }
}
