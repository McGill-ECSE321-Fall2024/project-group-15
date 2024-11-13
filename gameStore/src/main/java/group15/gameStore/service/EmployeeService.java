package group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Employee;
import group15.gameStore.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepo;

    /**
     * CreateEmployee: creates a new employee with username, password, email, active status, and manager status
     * @param username the username of the employee
     * @param password the password of the employee
     * @param email the email of the employee
     * @param isActive the active status of the employee
     * @param isManager the manager status of the employee
     * @return the newly created Employee object
     * @throws GameStoreException if any field is missing or invalid
     */
    @Transactional 
    public Employee createEmployee(String username, String password, String email, boolean isActive, boolean isManager) {
        if (username == null || username.trim().isEmpty()) {
           throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        if (password == null || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }

        Employee employee = new Employee(username, password, email, isActive, isManager);
        employeeRepo.save(employee);
        return employee;
    }

    /**
     * UpdateEmployee: updates an existing employee's information
     * @param employeeId the ID of the employee to update
     * @param updatedEmployee the new employee information to update to
     * @return the updated Employee object
     * @throws GameStoreException if the update request is invalid
     */
    @Transactional
    public Employee updateEmployee(int employeeId, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepo.findByUserID(employeeId);
        if (existingEmployee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Employee with the specified ID does not exist.");
        }
        if (updatedEmployee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid update request: no information provided.");
        }
        String username = updatedEmployee.getUsername();
        if (username == null || username.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        String password = updatedEmployee.getPassword();
        if (password == null || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        String email = updatedEmployee.getEmail();
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }

        existingEmployee.setUsername(username);
        existingEmployee.setPassword(password);
        existingEmployee.setEmail(email);
        existingEmployee.setIsActive(updatedEmployee.getIsActive());
        existingEmployee.setIsManager(updatedEmployee.getIsManager());

        return employeeRepo.save(existingEmployee);
    }

    /**
     * GetEmployeeById: retrieves an employee by their ID
     * @param id the ID of the employee
     * @return the Employee with the specified ID
     * @throws GameStoreException if the employee with the given ID is not found
     */
    @Transactional
    public Employee getEmployeeById(int id) {
        Employee employee = employeeRepo.findByUserID(id);
        if (employee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
        return employee;
    }

    /**
     * GetEmployeeByEmail: retrieves an employee by their email
     * @param email the email of the employee
     * @return the Employee with the specified email
     * @throws GameStoreException if the employee with the given email is not found
     */
    @Transactional
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepo.findByEmail(email);
        if (employee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Employee not found.");
        }
        return employee;
    }

     /**
     * GetAllEmployees: retrieves all employee records in the system
     * @return a list of all Employee objects
     * @throws GameStoreException if no employee records are found
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        if (employees.isEmpty()) {
            throw new GameStoreException(HttpStatus.NO_CONTENT, "No employee records found in the system.");
        }
        return employees;
    }
   
    /**
     * DeleteEmployeeById: deletes an employee by their ID
     * @param id the ID of the employee to delete
     * @throws GameStoreException if the employee with the given ID is not found
     */
    @Transactional
    public void deleteEmployeeById(int id) {
        Employee employee = employeeRepo.findByUserID(id);
        if (employee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Employee with the specified ID does not exist.");
        }

        employeeRepo.deleteByUserID(id);
    }
}
