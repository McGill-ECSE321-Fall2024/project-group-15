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

    public Order getOrderByID(int orderID) {
        Order order = orderRepo.findOrderByOrderID(orderID);
        if (order == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no order with ID %d", orderID));
        }
        return order;
    }

    public List<Order> getAllOrders(int orderID) {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no order in the system");
        }
        return orders;
    }

    //Method to find order's customer
    //Methods to find games in order: findAllGamesInOrder(int orderID)

    @Transactional
    public Order createOrder(String orderNumber, Status orderStatus, double price, Customer customer) {
        //TODO Handle date
        //Date today = Date.valueOf(LocalDate.now());
        if (orderNumber.isBlank() || orderStatus == null || customer == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (price < 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The price of an order cannot be negative");
        }
        if (customerRepo.findByUserID(customer.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The customer '%s' does not exist", customer.getUsername()));
        }
        Order order = new Order(orderNumber, orderStatus, price, customer);
        return orderRepo.save(order);
    }
    
    @Transactional
    public void deleteOrder(Order orderToDelete, Employee employee) {
        if (orderToDelete == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid order deletion request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        Order order = orderRepo.findOrderByOrderID(orderToDelete.getOrderID());
        if (order == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The order to delete does not exist");
        }
        orderRepo.delete(order);
    }

    @Transactional
    public Order updateOrder(int orderID, Order updatedOrder, Employee employee) {
        if (updatedOrder == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        Order order = orderRepo.findOrderByOrderID(orderID);
        if (order == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Cannot update the order since they do not exist");
        }
        order.setOrderNumber(updatedOrder.getOrderNumber());
        order.setOrderStatus(updatedOrder.getOrderStatus());
        order.setPrice(updatedOrder.getPrice());
        return orderRepo.save(order);
    }

}