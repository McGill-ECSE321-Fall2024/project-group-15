package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Order;
import group15.gameStore.model.Person;
import group15.gameStore.model.Game;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Status;

import group15.gameStore.repository.OrderRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.CustomerRepository;

import group15.gameStore.exception.GameStoreException;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepo;
    private GameRepository gameRepo;
    private CustomerRepository customerRepo;

    public Order findOrderByID(int orderID) {
        Order order = orderRepo.findOrderByOrderID(orderID);
        if (order == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no order with ID %d", orderID));
        }
        return order;
    }

    public List<Order> findAllOrders(int orderID) {
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
        //Handle date
        Date today = Date.valueOf(LocalDate.now());
        Order order = new Order(orderNumber, orderStatus, price, customer);
        return orderRepo.save(order);
    }
    
    @Transactional
    public void deleteOrder(Order orderToDelete) {
        //Verify if it's an employee
        Order order = orderRepo.findOrderByOrderID(orderToDelete.getOrderID());
        if (order == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The order to delete does not exist");
        }
        orderRepo.delete(order);
    }

    @Transactional
    public Order updateOrderNumber(Order orderToUpdate, String newOrderNumber) {
        Order order = orderRepo.findOrderByOrderID(orderToUpdate.getOrderID());
        if (order == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the title since the game does not exist");
        }
        order.setOrderNumber(newOrderNumber);
        return orderRepo.save(order);
    }

    @Transactional
    public Order updateOrderStatus(Order orderToUpdate, Status newOrderStatus) {
        Order order = orderRepo.findOrderByOrderID(orderToUpdate.getOrderID());
        if (order == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the status since the game does not exist");
        }
        order.setOrderStatus(newOrderStatus);
        return orderRepo.save(order);
    }

    @Transactional
    public Order updateOrderPrice(Order orderToUpdate, double newPrice) {
        Order order = orderRepo.findOrderByOrderID(orderToUpdate.getOrderID());
        if (order == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the price since the game does not exist");
        }
        order.setPrice(newPrice);
        return orderRepo.save(order);
    }
    
}