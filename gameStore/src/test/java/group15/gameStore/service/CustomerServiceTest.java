package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.repository.CustomerRepository;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;


    // Test for getting all customers
    @Test
    public void testGetAllCustomers() {
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);

        String name2 = "Joe Smith";
        String password2 = "joesmith1234";
        String email2 = "joe@gmail.com";
        String address2 = "432 Sesami St";
        String phoneNumber2 = "123-456-7891";
        Customer c2 = new Customer(email2, name2, password2, address2, phoneNumber2);

        List<Customer> customers = new ArrayList<>();
        customers.add(c1);
        customers.add(c2);

        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> allCustomers = customerService.findAllCustomers();

        assertEquals(2, allCustomers.size());
        Iterable<Customer> iterable = allCustomers;
        assertEquals(c1, iterable.iterator().next());
        assertEquals(c2, iterable.iterator().next());
    }

    // Test get all customers when none exist
    @Test
    public void testGetAllCustomersNoneExist() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customers);

        GameStoreException e = assertThrows(GameStoreException.class, () -> customerService.findAllCustomers());
        assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
        assertEquals(e.getMessage(), "There are no customers in the system");
    }

    // Test get customer with valid information

}
