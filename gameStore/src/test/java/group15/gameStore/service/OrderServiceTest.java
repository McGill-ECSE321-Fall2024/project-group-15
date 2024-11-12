package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Order;
import group15.gameStore.model.Status;
import group15.gameStore.repository.OrderRepository;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Customer customer;
    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize sample data
        customer = new Customer("John", "Doe", "john.doe@example.com", null, null);
        employee = new Employee("Jane", "Doe", "jane.doe@example.com", false, false);

        order = new Order("ORD123", Status.PendingPurchase, 99.99, customer);
        order.setOrderID(1);
    }

    @Test
    void testCreateOrder() {
        // Arrange
        when(customerService.getCustomerByID(customer.getUserID())).thenReturn(customer);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order createdOrder = orderService.createOrder(order.getOrderNumber(), order.getOrderStatus(), order.getPrice(), customer);

        // Assert
        assertNotNull(createdOrder);
        assertEquals(order.getOrderNumber(), createdOrder.getOrderNumber());
        assertEquals(order.getOrderStatus(), createdOrder.getOrderStatus());
        assertEquals(order.getPrice(), createdOrder.getPrice());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testUpdateOrder() {
        // Arrange
        when(orderRepository.findById(order.getOrderID())).thenReturn(Optional.of(order));
        when(employeeService.getEmployeeById(employee.getUserID())).thenReturn(employee);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Act
        Order updatedOrder = orderService.updateOrder(order.getOrderID(), order, employee);

        // Assert
        assertNotNull(updatedOrder);
        assertEquals(order.getOrderID(), updatedOrder.getOrderID());
        assertEquals(order.getOrderNumber(), updatedOrder.getOrderNumber());
        verify(orderRepository, times(1)).findById(order.getOrderID());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetOrderByID() {
        // Arrange
        when(orderRepository.findById(order.getOrderID())).thenReturn(Optional.of(order));

        // Act
        Order foundOrder = orderService.getOrderByID(order.getOrderID());

        // Assert
        assertNotNull(foundOrder);
        assertEquals(order.getOrderID(), foundOrder.getOrderID());
        verify(orderRepository, times(1)).findById(order.getOrderID());
    }

    @Test
    void testGetAllOrders() {
        // Arrange
        List<Order> orders = Arrays.asList(order, new Order("ORD124", Status.PendingPurchase, 150.0, customer));
        when(orderRepository.findAll()).thenReturn(orders);

        // Act
        List<Order> allOrders = orderService.getAllOrders(0);

        // Assert
        assertNotNull(allOrders);
        assertEquals(2, allOrders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        when(orderRepository.findById(order.getOrderID())).thenReturn(Optional.of(order));
        when(employeeService.getEmployeeById(employee.getUserID())).thenReturn(employee);
        doNothing().when(orderRepository).delete(order);

        // Act
        orderService.deleteOrder(order, employee);

        // Assert
        verify(orderRepository, times(1)).delete(order);
    }
}

