package group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Order;
import group15.gameStore.model.Status;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.OrderRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * GetOrderByID: retrieves an order by its ID
     * @param orderID the ID of the order to retrieve
     * @return the Order object with the specified ID
     * @throws GameStoreException if the order with the given ID is not found
     */
    public Order getOrderByID(int orderID) {
        Order order = orderRepo.findOrderByOrderID(orderID);
        if (order == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no order with ID %d", orderID));
        }
        return order;
    }

    /**
     * GetAllOrders: retrieves all orders in the system
     * @return a list of all Order objects in the system
     * @throws GameStoreException if there are no orders in the system
     */
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no orders in the system");
        }
        return orders;
    }


    /**
     * CreateOrder: creates a new order with order number, status, price, and associated customer
     * @param orderNumber the unique order number
     * @param orderStatus the status of the order
     * @param price the total price of the order
     * @param customer the customer who placed the order
     * @return the newly created Order object
     * @throws GameStoreException if any field is missing or invalid
     */
    @Transactional
    public Order createOrder(String orderNumber, Status orderStatus, double price, Customer customer) {
        if (orderNumber == null || orderNumber.trim().isEmpty() || orderStatus == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Order number or Order Status is required.");
        }
        if (price < 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The price of an order cannot be negative.");
        }
        if (customer == null || customerRepo.findByUserID(customer.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "The specified customer does not exist.");
        }
        Order order = new Order(orderNumber, orderStatus, price, customer);
        orderRepo.save(order);
        return order;
    }
    
    /**
     * deleteOrder: deletes an order from the database
     * @param orderToDelete the order object to delete
     * @param employee the employee requesting the deletion
     * @throws GameStoreException if the order or employee is not found or the request is invalid
     */
    @Transactional
    public void deleteOrder(Order orderToDelete, Employee employee) {
        if (orderToDelete == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid order deletion request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        Order order = orderRepo.findOrderByOrderID(orderToDelete.getOrderID());
        orderRepo.delete(order);
    }

    /**
     * UpdateOrder: updates an existing order's information
     * @param orderID the ID of the order to update
     * @param updatedOrder the new order information to update to
     * @param employee the employee making the update request
     * @return the updated Order object
     * @throws GameStoreException if the update request is invalid
     */
    @Transactional
    public Order updateOrder(int orderID, Order updatedOrder, Employee employee) {
        Order existingOrder = orderRepo.findOrderByOrderID(orderID);
        if (existingOrder == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Order with the specified ID does not exist.");
        }
        if (updatedOrder == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid update request: no information provided.");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        String orderNumber = updatedOrder.getOrderNumber();
        Status orderStatus = updatedOrder.getOrderStatus();
        if (orderNumber == null || orderNumber.trim().isEmpty() || orderStatus == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Order number or Order status is required.");
        }
        double price = updatedOrder.getPrice();
        if (price < 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Price must be non-negative.");
        }

        existingOrder.setOrderNumber(orderNumber);
        existingOrder.setOrderStatus(orderStatus);
        existingOrder.setPrice(price);

        return orderRepo.save(existingOrder);
    }

}