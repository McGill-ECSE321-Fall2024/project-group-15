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
     * @param customerID
     * @return the customer with the given ID
     */
    public Customer getCustomerByID(int customerID) {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if (customer == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no customer with ID %d", customerID));
        }
        return customer;
    }

    /**
     * Finds a customer by their email
     * @param email
     * @return the customer with the given email
     */
    public Customer getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no customer with email %s", email));
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
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no customers in the system");
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
     * @return the new customer
     */
    public Customer createCustomer(String username, String password, String email, String address, String phoneNumber) {
        Customer customer = new Customer(username, password, email, address, phoneNumber);
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer from the database
     * @param customerToDelete
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
     * @param customer
     * @param newUsername
     * @return
     */
    public Customer updateCustomerUsername(Customer customer, String newUsername) {
        customer.setUsername(newUsername);
        return customerRepository.save(customer);
    }

    /**
     * Updates the password of a customer
     * @param customer
     * @param newPassword
     * @return
     */
    public Customer updateCustomerPassword(Customer customer, String newPassword) {
        customer.setPassword(newPassword);
        return customerRepository.save(customer);
    }

    /**
     * Updates the email of a customer
     * @param customer
     * @param newEmail
     * @return
     */
    public Customer updateCustomerEmail(Customer customer, String newEmail) {
        customer.setEmail(newEmail);
        return customerRepository.save(customer);
    }

    /**
     * Updates the address of a customer
     * @param customer
     * @param newAddress
     * @return
     */
    public Customer updateCustomerAddress(Customer customer, String newAddress) {
        customer.setAddress(newAddress);
        return customerRepository.save(customer);
    }

    /**
     * Updates the phone number of a customer
     * @param customer
     * @param newPhoneNumber
     * @return
     */
    public Customer updateCustomerPhoneNumber(Customer customer, String newPhoneNumber) {
        customer.setPhoneNumber(newPhoneNumber);
        return customerRepository.save(customer);
    }

}
