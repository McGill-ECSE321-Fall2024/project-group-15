package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Employee;
import group15.gameStore.repository.EmployeeRepository;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    private Employee mockEmployee;

    @Mock
    private EmployeeRepository employeeRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEmployee_Success() {
        Employee employee = new Employee("username", "password123", "user@example.com", true, false);
        
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        Employee createdEmployee = employeeService.createEmployee("username", "password123", "user@example.com", true, false);

        assertEquals("username", createdEmployee.getUsername());
        assertEquals("password123", createdEmployee.getPassword());
        assertEquals("user@example.com", createdEmployee.getEmail());
        verify(employeeRepo, times(1)).save(any(Employee.class));
    }

    @Test
    void testCreateEmployee_EmptyUsername() {
        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            employeeService.createEmployee("", "password123", "user@example.com", true, false);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Username is required.", exception.getMessage());
    }

    @Test
    void testCreateEmployee_InvalidPassword() {
        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            employeeService.createEmployee("username", "pass", "user@example.com", true, false);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    @Test
    void testCreateEmployee_EmptyEmail() {
        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            employeeService.createEmployee("username", "password123", "", true, false);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Invalid email format.", exception.getMessage());
    }

    @Test
    void testUpdateEmployee_Success() {
        Employee existingEmployee = new Employee("oldUsername", "oldPassword", "old@example.com", true, false);
        when(employeeRepo.findByUserID(1)).thenReturn(existingEmployee);

        Employee updatedEmployee = new Employee("newUsername", "newPassword123", "new@example.com", false, true);
        when(employeeRepo.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeService.updateEmployee(1, updatedEmployee);

        assertEquals("newUsername", result.getUsername());
        assertEquals("newPassword123", result.getPassword());
        assertEquals("new@example.com", result.getEmail());
        verify(employeeRepo, times(1)).findByUserID(1);
        verify(employeeRepo, times(1)).save(existingEmployee);
    }

    @Test
    void testUpdateEmployee_EmployeeNotFound() {
        when(employeeRepo.findByUserID(1)).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            employeeService.updateEmployee(1, mockEmployee);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Employee with the specified ID does not exist.", exception.getMessage());
    }

    @Test
    void testGetEmployeeById_Success() {
        Employee employee = new Employee("username", "password123", "user@example.com", true, false);
        when(employeeRepo.findByUserID(1)).thenReturn(employee);

        Employee result = employeeService.getEmployeeById(1);

        assertEquals(employee, result);
        verify(employeeRepo, times(1)).findByUserID(1);
    }

    @Test
    void testGetEmployeeById_EmployeeNotFound() {
        when(employeeRepo.findByUserID(1)).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            employeeService.getEmployeeById(1);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Employee not found.", exception.getMessage());
    }

    @Test
    void testGetAllEmployees_Success() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("username1", "password123", "user1@example.com", true, false));
        employees.add(new Employee("username2", "password456", "user2@example.com", true, false));
        
        when(employeeRepo.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployees();

        assertEquals(2, result.size());
        verify(employeeRepo, times(1)).findAll();
    }

    @Test
    void testGetAllEmployees_NoEmployeesFound() {
        when(employeeRepo.findAll()).thenReturn(new ArrayList<>());

        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            employeeService.getAllEmployees();
        });
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatus());
        assertEquals("No employee records found in the system.", exception.getMessage());
    }

    @Test
    void testDeleteEmployeeById_Success() {
        Employee employee = new Employee("username", "password123", "user@example.com", true, false);
        when(employeeRepo.findByUserID(1)).thenReturn(employee);

        employeeService.deleteEmployeeById(1);

        verify(employeeRepo, times(1)).deleteByUserID(1);
    }

    @Test
    void testDeleteEmployeeById_EmployeeNotFound() {
        when(employeeRepo.findByUserID(1)).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            employeeService.deleteEmployeeById(1);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Employee with the specified ID does not exist.", exception.getMessage());
    }
}
