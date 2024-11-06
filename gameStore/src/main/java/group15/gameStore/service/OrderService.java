package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Order;
import group15.gameStore.model.Game;
import group15.gameStore.model.Customer;
import group15.gameStore.repository.OrderRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.CustomerRepository;

import jakarta.transaction.Transactional;

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
            //Raise Error
        }
        return order;
    }

    public List<Order> findAllOrders(int orderID) {
        List<Order> orders = orderRepo.findAll();
        if (orders.isEmpty()) {
            //Raise error
        }
        return orders;
    }

    //Method to find order's customer
    //Methods to find games in order: findAllGamesInOrder(int orderID)

    @Transactional
    public Order createOrder() {
        return ;
    }
    
    @Transactional
    public Order deleteOrder() {
        return ;
    }

    @Transactional
    public Order updateOrder() {
        return ;
    }
}