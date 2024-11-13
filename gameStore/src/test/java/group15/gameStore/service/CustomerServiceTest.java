package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
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
        assertEquals(c1, allCustomers.get(0));
        assertEquals(c2, allCustomers.get(1));
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

    // Test get customer by valid id
    @Test
    public void testGetCustomerByID_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findById(c1.getUserID())).thenReturn(java.util.Optional.of(c1));

        // Act
        Customer result = customerService.getCustomerByID(c1.getUserID());

        // Assert
        assertEquals(c1, result);
    }

    // Test get customer by invalid id
    @Test
    public void testGetCustomerByID_InvalidId() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findById(c1.getUserID())).thenReturn(java.util.Optional.of(c1));

        // Act
        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            customerService.getCustomerByID(2);
        });

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("There is no customer with ID 2", exception.getMessage());
    }

    // Test get customer by valid email
    @Test
    public void testGetCustomerByEmail_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findByEmail(email)).thenReturn(c1);

        // Act
        Customer result = customerService.getCustomerByEmail(email);

        // Assert
        assertEquals(c1, result);
    }

    // Test get customer by invalid email
    @Test
    public void testGetCustomerByEmail_InvalidEmail() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findByEmail(email)).thenReturn(c1);

        // Act
        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            customerService.getCustomerByEmail("notvalidemail@fmail.com");
        });

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    // Test update Customer Username

    @Test
    public void testUpdateCustomerUsername_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findByEmail(email)).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerUsername(c1, "newUsername");

        // Assert
        assertEquals("newUsername", result.getUsername());
    }

    //Test update Customer password
    @Test
    public void testUpdateCustomerPassword_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findByEmail(email)).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerPassword(c1, "newPassword");

        // Assert
        assertEquals("newPassword", result.getPassword());
    }

    // Test update Customer email
    @Test
    public void testUpdateCustomerEmail_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findByEmail(email)).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerEmail(c1, "newemail");

        // Assert
        assertEquals("newemail", result.getEmail());
    }

    // Test update Customer address
    @Test
    public void testUpdateCustomerAddress_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findByEmail(email)).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerAddress(c1, "newaddress");

        // Assert

        assertEquals("newaddress", result.getAddress());
    }

    // Test update Customer phone number
    @Test
    public void testUpdateCustomerPhoneNumber_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepository.findByEmail(email)).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerPhoneNumber(c1, "newPhoneNumber");

        // Assert
        assertEquals("newPhoneNumber", result.getPhoneNumber());
    }


}
