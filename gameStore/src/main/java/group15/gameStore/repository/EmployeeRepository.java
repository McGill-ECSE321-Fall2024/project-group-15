package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Employee;
import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    // Find Employee by employeeId
    Employee findByEmployeeId(Integer employeeId);

    // Find Employees by firstName or lastName (case-insensitive)
    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    // Find Employee by email
    Employee findByEmail(String email);

    // Delete Employee by employeeId
    void deleteByEmployeeId(Integer employeeId);

    // Get all Employees
    List<Employee> findAll();
}
