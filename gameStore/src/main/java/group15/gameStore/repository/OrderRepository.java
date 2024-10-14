package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Order;
import group15.gameStore.model.Status;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    // Find an order by its order ID
    Order findOrderByOrderID(int orderID);
    
    // Find an order by its order number
    Order findOrderByOrderNumber(String orderNumber);

    // Find orders by their status
    List<Order> findOrdersByOrderStatus(Status status);

    // Find orders by price range
    List<Order> findOrdersByPriceBetween(double minPrice, double maxPrice);

    // Delete an order by its order number
    void deleteOrderByOrderNumber(String orderNumber);

    // Get all orders
    List<Order> findAll();
}
