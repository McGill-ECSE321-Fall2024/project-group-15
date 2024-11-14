package group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.repository.CustomerRepository;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Finds a customer by their ID
     * @param customerID the ID of a customer
     * @return the customer with the given ID
     */
    public Customer getCustomerByID(int customerID) {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, String.format("There is no customer with ID %d", customerID));
        }
        return customer;
    }

    /**
     * Finds a customer by their email
     * @param email the email of the customer
     * @return the customer with the given email
     */
    public Customer getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, String.format("There is no customer with email %s", email));
        }
        return customer;
    }

    /**
     * Finds all customers in the database
     * @return a list of all customers
     * @throws GameStoreException if there are no customers in the system
     */
    public List<Customer> findAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, "There are no customers in the system");
        }
        return customers;
    }

    /**
     * Creates a new customer in the database
     * @param username the username of the customer
     * @param password the password of the customer
     * @param email the email of the customer
     * @param address the address of the customer
     * @param phoneNumber the phone number of the customer
     * @return the newly created Customer object
     * @throws GameStoreException if any field is missing or invalid
     */
    public Customer createCustomer(String username, String password, String email, String address, String phoneNumber) {
        if (username == null || username.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        if (password == null || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }
        if (customerRepository.findByEmail(email) != null) {
            throw new GameStoreException(HttpStatus.CONFLICT, "Email is already taken.");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Address is required.");
        }
        if (phoneNumber == null || !phoneNumber.matches("^\\+?[0-9]{10,15}$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid phone number format.");
        }

        Customer customer = new Customer(username, password, email, address, phoneNumber);
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer from the database
     * @param customerToDelete the customer to delete
     * @throws GameStoreException if the customer to delete does not exist
     * @return void
     */
    public void deleteCustomer(Customer customerToDelete) {
        if (customerRepository.findByUserID(customerToDelete.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The customer to delete does not exist");
        }
        customerRepository.delete(customerToDelete);
    }


    /**
     * Updates the username of a customer
     * @param customer the customer whose username is to be updated
     * @param newUsername the new username to set
     * @return the updated Customer object
     * @throws GameStoreException if the customer or new username is invalid
     */
    public Customer updateCustomerUsername(Customer customer, String newUsername) {
        if (customer == null) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, "Customer not found.");
        }
        if (newUsername == null || newUsername.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        customer.setUsername(newUsername);
        return customerRepository.save(customer);
    }

    /**
     * Updates the password of a customer
     * @param customer the customer whose password is to be updated
     * @param newPassword the new password to set
     * @return the updated Customer object
     * @throws GameStoreException if the customer or new password is invalid
     */
    public Customer updateCustomerPassword(Customer customer, String newPassword) {
        if (customer == null) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, "Customer not found.");
        }
        if (newPassword == null || newPassword.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        customer.setPassword(newPassword);
        return customerRepository.save(customer);
    }

    /**
     * Updates the email of a customer
     * @param customer the customer whose email is to be updated
     * @param newEmail the new email to set
     * @return the updated Customer object
     * @throws GameStoreException if the customer or new email is invalid
     */
    public Customer updateCustomerEmail(Customer customer, String newEmail) {
        if (customer == null) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, "Customer not found.");
        }
        if (newEmail == null || !newEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }
        customer.setEmail(newEmail);
        return customerRepository.save(customer);
    }

    /**
     * Updates the address of a customer
     * @param customer the customer whose address is to be updated
     * @param newAddress the new address to set
     * @return the updated Customer object
     * @throws GameStoreException if the customer or new address is invalid
     */
    public Customer updateCustomerAddress(Customer customer, String newAddress) {
        if (customer == null) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, "Customer not found.");
        }
        if (newAddress == null || newAddress.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Address is required.");
        }
        customer.setAddress(newAddress);
        return customerRepository.save(customer);
    }

    /**
     * Updates the phone number of a customer
     * @param customer the customer whose phone number is to be updated
     * @param newPhoneNumber the new phone number to set
     * @return the updated Customer object
     * @throws GameStoreException if the customer or new phone number is invalid
     */
    public Customer updateCustomerPhoneNumber(Customer customer, String newPhoneNumber) {
        if (customer == null) {
            throw new GameStoreException(HttpStatus.BAD_GATEWAY, "Customer not found.");
        }
        if (newPhoneNumber == null || !newPhoneNumber.matches("\\d{10,15}")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid phone number format.");
        }
        customer.setPhoneNumber(newPhoneNumber);
        return customerRepository.save(customer);
    }

}
