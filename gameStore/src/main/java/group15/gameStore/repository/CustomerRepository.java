package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Customer;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    // Find Customer by customerId
    Customer findByCustomerId(Integer customerId);

    // Find Customers by firstName or lastName (case-insensitive)
    List<Customer> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    // Find Customer by email
    Customer findByEmail(String email);

    // Delete Customer by customerId
    void deleteByCustomerId(Integer customerId);

    // Get all Customers
    List<Customer> findAll();
}
