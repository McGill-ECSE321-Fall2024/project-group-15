package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Employee;
import group15.gameStore.service.EmployeeService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
@RestController
public class EmployeeController{

    @Autowired
    private EmployeeService employeeService;

    /**
     * CreateEmployee: creates a new employee
     * @param employeeDto the EmployeeDto containing the employee details
     * @return the created employee and HTTP Status "CREATED"
     */
    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee createdEmployee = employeeService.createEmployee(employeeDto.getUsername(), 
                employeeDto.getPassword(),employeeDto.getEmail(), employeeDto.isActive(),employeeDto.isManager());
        return new ResponseEntity<>(new EmployeeDto(createdEmployee), HttpStatus.CREATED);
    }

    /**
     * UpdateEmployee: updates an existing employee
     * @param employeeId the ID of the employee to update
     * @param employeeDto the EmployeeDto containing updated employee details
     * @return the updated employee and the HTTP status "OK"
     */
    @PutMapping("/employee/update/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("employeeId") int employeeId, 
            @RequestBody EmployeeDto employeeDto) {
        
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);
        existingEmployee.setUsername(employeeDto.getUsername());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setPassword(employeeDto.getPassword());
        existingEmployee.setIsActive(employeeDto.isActive());
        existingEmployee.setIsManager(employeeDto.isManager());
        Employee updatedEmployee = employeeService.updateEmployee(employeeId, existingEmployee);
        
        return new ResponseEntity<>(new EmployeeDto(updatedEmployee), HttpStatus.OK);
    }

    /**
     * GetEmployeeById: retrieves an employee by ID
     * @param employeeId the ID of the employee to retrieve
     * @return the desired employee information and the HTTP status "OK"
     */
    @GetMapping("/employee/id/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("employeeId") int employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(new EmployeeDto(employee), HttpStatus.OK);
    }

    /**
    * GetEmployeeByEmail: retrieves an employee by their email
    * @param email the email of the employee to retrieve
    * @return the desired employee information and the HTTP status "OK"
    */
    @GetMapping("/employee/email/{email}")
    public ResponseEntity<EmployeeDto> getEmployeeByEmail(@PathVariable("email") String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        return new ResponseEntity<>(new EmployeeDto(employee), HttpStatus.OK);
    }

    /**
     * Check if the logged-in employee is a manager.
     * @param authentication The authentication object for the current user.
     * @return True if the employee is a manager, false otherwise.
     */
    @GetMapping("/employee/is-manager")
    public ResponseEntity<Boolean> isManager(Authentication authentication) {
        try {
            // Retrieve the username of the logged-in employee
            String username = authentication.getUsername();

            boolean isManager = employeeService.getIsManager(username);

            return ResponseEntity.ok(isManager);
        } catch (GameStoreException ex) {
            return ResponseEntity.status(ex.getStatus()).body(false);
        }
    }

    /**
     * GetAllEmployees: retrieves all employee records in the system
     * @return a list of all employee information and the HTTP status "OK"
     */
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<EmployeeDto> responseDtoList = employees.stream().map(EmployeeDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * DeleteEmployee: deletes an employee by employee ID
     * @param employeeId the ID of the employee to delete
     * @return HTTP status "NO CONTENT" if the deletion is successful
     */
    @DeleteMapping("/employee/delete/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") int employeeId) {
        employeeService.deleteEmployeeById(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}