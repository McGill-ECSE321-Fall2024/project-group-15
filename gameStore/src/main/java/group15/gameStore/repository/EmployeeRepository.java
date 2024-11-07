package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Employee;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    // Find Employee by userID
    Employee findByUserID(Integer userID);

    // Find Employee by email
    Employee findByEmail(String email);

    // Delete Employee by userID
    void deleteByUserID(Integer userID);

    // Get all Employees
    @SuppressWarnings("null")
    List<Employee> findAll();
}
