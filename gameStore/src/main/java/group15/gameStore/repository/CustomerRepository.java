package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Customer;
import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    // Find Customer by customerId
    Customer findByUserID(Integer userID);

    // Find Customer by email
    Customer findByEmail(String email);

    // Delete Customer by customerId
    void deleteByUserID(Integer userID);

    // Get all Customers
    @SuppressWarnings("null")
    List<Customer> findAll();
}
