package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Customer;

@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadCustomer() {
        //Create Customer
        String username = "ChadTheGamer";
        String password = "0000password";
        String email = "chad@fakeemail.com";
        String address = "123 Sesame Street";
        String phoneNumber = "123-456-7890";
        boolean isPaymentInfoSaved = true;

        Customer customer = new Customer(username, password, email, address, phoneNumber, isPaymentInfoSaved);

        // Save in the database
        customer = repo.save(customer);
        int customerId = customer.getUserID();

        // Read back from the database
        Customer customerFromDb = repo.findByCustomerId(customerId);

        // Assertions
        assertNotNull(customerFromDb);
        assertEquals(customerId, customerFromDb.getUserID());
        assertEquals(username, customerFromDb.getUsername());
        assertEquals(password, customerFromDb.getPassword());
        assertEquals(email, customerFromDb.getEmail());
        assertEquals(address, customerFromDb.getAddress());
        assertEquals(phoneNumber, customerFromDb.getPhoneNumber());
        assertEquals(isPaymentInfoSaved, customerFromDb.getIsPaymentInfoSaved());        
    }
}
