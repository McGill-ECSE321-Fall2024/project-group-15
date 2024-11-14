package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.CustomerDto;
import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.dto.OrderDto;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Order;
import group15.gameStore.service.CustomerService;
import group15.gameStore.service.EmployeeService;
import group15.gameStore.service.OrderService;

@RestController
public class OrderController{
    
    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * CreateOrder: creates a new order
     * @param orderDto the OrderDto containing the order details
     * @param customerDto the CustomerDto representing the customer
     * @return the created order and HTTP Status "CREATED"
     */
    @PostMapping("/order")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        Order createdOrder = orderService.createOrder(orderDto.getOrderNumber(), orderDto.getOrderStatus(),
                orderDto.getPrice(), customer);
        return new ResponseEntity<>(new OrderDto(createdOrder), HttpStatus.CREATED);
    }

    /**
     * UpdateOrder: updates an existing order
     * @param orderId the ID of the order to update
     * @param orderDto the OrderDto containing updated order details
     * @param employeeDto the EmployeeDto representing the employee making the request
     * @return the updated order and the HTTP status "OK"
     */
    @PutMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable int orderId, @RequestBody OrderDto orderDto,
                                                @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.getEmployeeById(employeeDto.getUserID());
        Order existingOrder = orderService.getOrderByID(orderDto.getOrderID());
        Order updatedOrder = orderService.updateOrder(orderId, existingOrder, employee);
        return new ResponseEntity<>(new OrderDto(updatedOrder), HttpStatus.OK);
    }

    /**
     * GetOrderById: retrieves an order by ID
     * @param orderId the ID of the order to retrieve
     * @return the desired order information and the HTTP status "OK"
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable int orderId) {
        Order order = orderService.getOrderByID(orderId);
        return new ResponseEntity<>(new OrderDto(order), HttpStatus.OK);
    }

    /**
     * GetAllOrders: retrieves all order records in the system
     * @return a list of all order information and the HTTP status "OK"
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderDto> responseDtoList = orders.stream().map(OrderDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * DeleteOrder: deletes an order by ID
     * @param orderId the ID of the order to delete
     * @param employeeDto the EmployeeDto representing the employee making the request
     * @return HTTP status "NO CONTENT" if the deletion is successful
     */
    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId, @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeService.getEmployeeById(employeeDto.getUserID());
        Order orderToDelete = orderService.getOrderByID(orderId);
        orderService.deleteOrder(orderToDelete, employee);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}