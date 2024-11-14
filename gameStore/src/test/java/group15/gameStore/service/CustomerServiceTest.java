package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

   

    @Mock
    private CustomerRepository customerRepo;
    private Customer mockCustomer;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCustomers_Success() {
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);

        String name2 = "Joe Smith";
        String password2 = "joesmith1234";
        String email2 = "joe@gmail.com";
        String address2 = "432 Sesame St";
        String phoneNumber2 = "123-456-7891";
        Customer c2 = new Customer(email2, name2, password2, address2, phoneNumber2);

        List<Customer> customers = new ArrayList<>();
        customers.add(c1);
        customers.add(c2);

        when(customerRepo.findAll()).thenReturn(customers);
        List<Customer> allCustomers = customerService.findAllCustomers();

        assertNotNull(allCustomers);
        assertFalse(allCustomers.isEmpty());
        assertEquals(2, allCustomers.size());
        assertEquals(c1, allCustomers.get(0));
        assertEquals(c2, allCustomers.get(1));
    }

    @Test
    public void testGetAllCustomers_EmptyList() {
        List<Customer> customers = new ArrayList<>();
        when(customerRepo.findAll()).thenReturn(customers);

        GameStoreException e = assertThrows(GameStoreException.class, () -> customerService.findAllCustomers());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        assertEquals("There are no customers in the system", e.getMessage());
    }

    @Test
    public void testGetCustomerByID_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.findById(c1.getUserID())).thenReturn(java.util.Optional.of(c1));

        // Act
        Customer result = customerService.getCustomerByID(c1.getUserID());

        // Assert
        assertNotNull(result);
        assertEquals(c1, result);
    }

    @Test
    public void testGetCustomerByID_InvalidId() {
        when(customerRepo.findById(2)).thenReturn(java.util.Optional.empty());

        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            customerService.getCustomerByID(2);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("There is no customer with ID 2", exception.getMessage());
    }

    @Test
    public void testGetCustomerByEmail_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "1234567890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.findByEmail(email)).thenReturn(c1);

        // Act
        Customer result = customerService.getCustomerByEmail(email);

        // Assert
        assertEquals(c1, result);
    }

    
    @Test
    public void testGetCustomerByEmail_InvalidEmail() {
        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            customerService.getCustomerByEmail("notvalidemail@fmail.com");
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("There is no customer with email notvalidemail@fmail.com", exception.getMessage());
    }

    @Test
    public void testUpdateCustomerUsername_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "1234567890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.save(any(Customer.class))).thenReturn(c1);
       
        // Act
        Customer result = customerService.updateCustomerUsername(c1, "newUsername");

        // Assert
        assertNotNull(result);
        assertEquals("newUsername", result.getUsername());
        verify(customerRepo, times(1)).save(any(Customer.class));
    }
   
   
    @Test
    public void testUpdateCustomerPassword_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "123-456-7890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.save(any(Customer.class))).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerPassword(c1, "newPassword");

        // Assert
        assertNotNull(result);
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
        String phoneNumber = "1234567890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.save(any(Customer.class))).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerEmail(c1, "newemail@gmail.com");

        // Assert
        assertNotNull(result);
        assertEquals("newemail@gmail.com", result.getEmail());
    }

    @Test
    public void testUpdateCustomerAddress_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "1234567890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.save(any(Customer.class))).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerAddress(c1, "newaddress");

        // Assert
        assertNotNull(result);
        assertEquals("newaddress", result.getAddress());
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    @Test
    public void testUpdateCustomerPhoneNumber_Success() {
        // Arrange
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "1234567890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.save(any(Customer.class))).thenReturn(c1);

        // Act
        Customer result = customerService.updateCustomerPhoneNumber(c1, "0987654321");

        // Assert
        assertNotNull(result);
        assertEquals("0987654321", result.getPhoneNumber());
    }
       

    @Test
    public void testCreateCustomer_Success() {
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "1234567890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.save(any(Customer.class))).thenReturn(c1);
        Customer createdCustomer = customerService.createCustomer(name, password, email, address, phoneNumber);

        assertNotNull(createdCustomer);
        assertEquals(c1, createdCustomer);
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    
    @Test
    public void testCreateCustomer_EmailTaken() {
        String name = "Dana White";
        String password = "password1234";
        String email = "dana@gmail.com";
        String address = "1234 Main St";
        String phoneNumber = "1234567890";
        Customer c1 = new Customer(name, password, email, address, phoneNumber);
        when(customerRepo.findByEmail(email)).thenReturn(c1);

        GameStoreException e = assertThrows(GameStoreException.class, () -> customerService.createCustomer(name, password, email, address, phoneNumber));
        assertEquals(HttpStatus.CONFLICT, e.getStatus());
        assertEquals("Email is already taken.", e.getMessage());
    }
    
    @Test
    public void testCreateCustomer_EmptyName() {
        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.createCustomer("", "password123", "email@example.com", "123 Main St", "123-456-7890"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Username is required.", exception.getMessage());
    }

    @Test
    public void testCreateCustomer_EmptyPassword() {
        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.createCustomer("username", "", "email@example.com", "123 Main St", "123-456-7890"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    @Test
    public void testCreateCustomer_EmptyEmail() {
        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.createCustomer("username", "password123", "", "123 Main St", "123-456-7890"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid email format.", exception.getMessage());
    }

    @Test
    public void testCreateCustomer_EmptyAddress() {
        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.createCustomer("username", "password123", "email@example.com", "", "123-456-7890"));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Address is required.", exception.getMessage());
    }

    @Test
    public void testCreateCustomer_EmptyPhoneNumber() {
        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.createCustomer("username", "password123", "email@example.com", "123 Main St", ""));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid phone number format.", exception.getMessage());
    }
   
    @Test
    public void testUpdateCustomer_EmptyName() {
        Customer customer = new Customer("oldName", "password123", "email@example.com", "123 Main St", "123-456-7890");

        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.updateCustomerUsername(customer, ""));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Username is required.", exception.getMessage());
    }

    @Test
    public void testUpdateCustomer_EmptyPassword() {
        Customer customer = new Customer("username", "oldPassword", "email@example.com", "123 Main St", "123-456-7890");

        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.updateCustomerPassword(customer, ""));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    @Test
    public void testUpdateCustomer_EmptyEmail() {
        Customer customer = new Customer("username", "password123", "oldEmail@example.com", "123 Main St", "123-456-7890");

        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.updateCustomerEmail(customer, ""));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid email format.", exception.getMessage());
    }

    @Test
    public void testUpdateCustomer_EmptyAddress() {
        Customer customer = new Customer("username", "password123", "email@example.com", "oldAddress", "123-456-7890");

        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.updateCustomerAddress(customer, ""));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Address is required.", exception.getMessage());
    }

    @Test
    public void testUpdateCustomer_EmptyPhoneNumber() {
        Customer customer = new Customer("username", "password123", "email@example.com", "123 Main St", "oldPhone");

        GameStoreException exception = assertThrows(GameStoreException.class, 
            () -> customerService.updateCustomerPhoneNumber(customer, ""));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid phone number format.", exception.getMessage());
    }

    @Test
    public void testDeleteCustomer_Success() {
        when(customerRepo.findByUserID(mockCustomer.getUserID())).thenReturn(mockCustomer);

        customerService.deleteCustomer(mockCustomer);

        verify(customerRepo, times(1)).delete(mockCustomer);
    }

    @Test
    public void testDeleteCustomerNotFound() {
        // Create a customer with userID set to 2
        Customer customer = new Customer();
        customer.setUserID(1);

        GameStoreException e = assertThrows(GameStoreException.class, () -> customerService.deleteCustomer(customer));
        assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        assertEquals("The customer to delete does not exist", e.getMessage());
    }
}
