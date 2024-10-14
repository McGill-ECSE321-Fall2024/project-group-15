package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.checkerframework.checker.units.qual.t;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Order;
import group15.gameStore.model.Status;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadOrder() {
        //Create Order
        String orderNumber = "12345";
        Status orderStatus = Status.DELIVERED;
        double price = 109.99;

        Order order = new Order(orderNumber, orderStatus, price);

        // Save in the database
        order = repo.save(order);
        int orderID = order.getOrderID();

        // Read back from the database
        Order orderFromDb = repo.findOrderByOrderId(orderID);

        // Assertions
        assertNotNull(orderFromDb);
        assertEquals(orderID, orderFromDb.getOrderID());
        assertEquals(orderNumber, orderFromDb.getOrderNumber());
        assertEquals(orderStatus, orderFromDb.getOrderStatus());
        assertEquals(price, orderFromDb.getPrice());
    }
}
