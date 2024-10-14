package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Employee;

@SpringBootTest
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadEmployee() {
        //Create Employee
        String username = "EmployeeDerek";
        String password = "employeepassword";
        String email = "employeederek@gamestore.com";
        boolean isActive = true;
        boolean isManager = false;

        Employee employee = new Employee(username, password, email, isActive, isManager);

        // Save in the database
        employee = repo.save(employee);
        int employeeId = employee.getUserID();

        // Read back from the database
        Employee employeeFromDb = repo.findByUserID(employeeId);

        // Assertions
        assertNotNull(employeeFromDb);
        assertEquals(employeeId, employeeFromDb.getUserID());
        assertEquals(username, employeeFromDb.getUsername());
        assertEquals(password, employeeFromDb.getPassword());
        assertEquals(email, employeeFromDb.getEmail());
        assertEquals(isActive, employeeFromDb.getIsActive());
        assertEquals(isManager, employeeFromDb.getIsManager());
    }
}
