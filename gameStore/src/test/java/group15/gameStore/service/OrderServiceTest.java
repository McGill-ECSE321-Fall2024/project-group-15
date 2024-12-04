package group15.gameStore.service;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Order;
import group15.gameStore.model.Status;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private EmployeeRepository employeeRepo;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;
    private Employee employee;
    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize mock objects
        customer = new Customer();
        customer.setUserID(1);

        employee = new Employee("emp1", "pass1", "emp1@gmail.com", true, true);
        employee.setUserID(1);
        employee.setUsername("Admin");

        order = new Order("123ABC", Status.PendingPurchase, 100.0, customer);
        order.setOrderID(1);
    }

    @Test
    public void testGetOrderByID_Success() {
        when(orderRepo.findOrderByOrderID(1)).thenReturn(order);

        Order result = orderService.getOrderByID(1);
        assertNotNull(result);
        assertEquals(order, result);
    }

    @Test
    public void testGetOrderByID_OrderNotFound() {
        when(orderRepo.findOrderByOrderID(1)).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> orderService.getOrderByID(1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("There is no order with ID 1", exception.getMessage());
    }

    @Test
    public void testGetAllOrders_OrdersExist() {
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        when(orderRepo.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order, result.get(0));
        verify(orderRepo, times(1)).findAll();
    }

    @Test
    public void testGetAllOrders_NoOrders() {
        when(orderRepo.findAll()).thenReturn(new ArrayList<>());

        GameStoreException exception = assertThrows(GameStoreException.class, () -> orderService.getAllOrders());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("There are no orders in the system", exception.getMessage());
    }

    @Test
    public void testCreateOrder_ValidOrder() {
        when(customerRepo.findByUserID(customer.getUserID())).thenReturn(customer);
        when(orderRepo.save(any(Order.class))).thenReturn(order);

        Order result = orderService.createOrder("123ABC", Status.PendingPurchase, 100.0, customer);
        assertNotNull(result);
        assertEquals("123ABC", result.getOrderNumber());
        assertEquals(Status.PendingPurchase, result.getOrderStatus());
        assertEquals(100.0, result.getPrice());
        assertEquals(customer, result.getCustomer());
    }

    @Test
    public void testCreateOrder_EmptyOrderNumber() {
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.createOrder("", Status.PendingPurchase, 100.0, customer));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Order number or Order Status is required.", exception.getMessage());
    }

    @Test
    public void testCreateOrder_EmptyOrderStatus() {
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.createOrder("123ABC", null, 100.0, customer));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Order number or Order Status is required.", exception.getMessage());
    }

    @Test
    public void testCreateOrder_InvalidPrice() {
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.createOrder("123ABC", Status.PendingPurchase, -10.0, customer));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The price of an order cannot be negative.", exception.getMessage());
    }

    @Test
    public void testCreateOrder_CustomerNotFound() {
        when(customerRepo.findByUserID(customer.getUserID())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.createOrder("123ABC", Status.PendingPurchase, 100.0, customer));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("The specified customer does not exist.", exception.getMessage());
    }

    @Test
    public void testDeleteOrder_ValidRequest() {
        when(orderRepo.findOrderByOrderID(order.getOrderID())).thenReturn(order);
        when(employeeRepo.findByUserID(employee.getUserID())).thenReturn(employee);

        assertDoesNotThrow(() -> orderService.deleteOrder(order, employee));
        verify(orderRepo, times(1)).delete(order);
    }

    @Test
    public void testDeleteOrder_OrderNotFound() {
        when(orderRepo.findOrderByOrderID(order.getOrderID())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.deleteOrder(order, employee));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Invalid order deletion request: missing attributes", exception.getMessage());
    }

    @Test
    public void testDeleteOrder_EmployeeNotFound() {
        when(employeeRepo.findByUserID(employee.getUserID())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.deleteOrder(order, employee));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("The employee 'Admin' that made the request does not exist", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_Success() {
        order = new Order("123ABC", Status.PendingPurchase, 100.0, customer);
        order.setOrderID(1);
        Order updatedOrder = new Order("456DEF", Status.DELIVERED, 150.0, customer);

        when(orderRepo.findOrderByOrderID(order.getOrderID())).thenReturn(order);
        when(employeeRepo.findByUserID(employee.getUserID())).thenReturn(employee);
        when(orderRepo.save(any(Order.class))).thenReturn(updatedOrder);

        Order result = orderService.updateOrder(1, updatedOrder, employee);
        assertNotNull(result);
        assertEquals("456DEF", result.getOrderNumber());
        assertEquals(Status.DELIVERED, result.getOrderStatus());
        assertEquals(150.0, result.getPrice());
    }

    @Test
    public void testUpdateOrder_OrderNotFound() {
        Order updatedOrder = new Order("456DEF", Status.DELIVERED, 150.0, customer);
        when(orderRepo.findOrderByOrderID(1)).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.updateOrder(1, updatedOrder, employee));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Order with the specified ID does not exist.", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_EmployeeNotFound() {
        Order updatedOrder = new Order("456DEF", Status.DELIVERED, 150.0, customer);
        when(orderRepo.findOrderByOrderID(1)).thenReturn(order);
        when(employeeRepo.findByUserID(employee.getUserID())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.updateOrder(1, updatedOrder, employee));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("The employee 'Admin' that made the request does not exist", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_EmptyOrder() {
        when(orderRepo.findOrderByOrderID(1)).thenReturn(order);
        when(employeeRepo.findByUserID(employee.getUserID())).thenReturn(employee);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.updateOrder(1, null, employee));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid update request: no information provided.", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_EmptyEmployee() {
        Order updatedOrder = new Order("456DEF", Status.DELIVERED, 150.0, customer);
        when(orderRepo.findOrderByOrderID(1)).thenReturn(order);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.updateOrder(1, updatedOrder, null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid update request: no information provided.", exception.getMessage());
    }

    @Test
    public void testUpdateOrder_InvalidPrice() {
        order = new Order("123ABC", Status.PendingPurchase, 100.0, customer);
        order.setOrderID(1);
        Order updatedOrder = new Order("456DEF", Status.DELIVERED, -50.0, customer);
        when(orderRepo.findOrderByOrderID(order.getOrderID())).thenReturn(order);
        when(employeeRepo.findByUserID(employee.getUserID())).thenReturn(employee);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                orderService.updateOrder(1, updatedOrder, employee));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Price must be non-negative.", exception.getMessage());
    }
}
