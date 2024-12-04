package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Order;
import group15.gameStore.model.Status;

@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository repo;

    @Autowired
    private CustomerRepository customerRepo;

    private Customer testCustomer = new Customer("ChadTheGamer", "00password", "chad@gmail.com","123SesameStreet", "012345678");

    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        repo.deleteAll();
        customerRepo.deleteAll();

        testCustomer = customerRepo.save(testCustomer);

    }
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadOrder() {
        //Create Order
        String orderNumber = "12345";
        Status orderStatus = Status.DELIVERED;
        double price = 109.99;

        Order orderA = new Order(orderNumber, orderStatus, price, testCustomer);

        // Save in the database
        orderA = repo.save(orderA);
        int orderID = orderA.getOrderID();

        // Read back from the database
        Order orderFromDb = repo.findOrderByOrderID(orderID);

        // Assertions
        assertNotNull(orderFromDb);
        assertEquals(orderID, orderFromDb.getOrderID());
        assertEquals(orderNumber, orderFromDb.getOrderNumber());
        assertEquals(orderStatus, orderFromDb.getOrderStatus());
        assertEquals(price, orderFromDb.getPrice());
    }
}
