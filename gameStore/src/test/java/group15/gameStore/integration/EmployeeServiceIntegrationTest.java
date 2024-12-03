package group15.gameStore.integration;

import group15.gameStore.dto.CategoryDto;
import group15.gameStore.dto.CustomerDto;
import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestRestTemplate client;

    private EmployeeDto employeeRequestDto;
    private EmployeeDto employeeRequestDto2;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        employeeRequestDto = new EmployeeDto();
        employeeRequestDto.setUsername("Employee1");
        employeeRequestDto.setPassword("password123");
        employeeRequestDto.setEmail("employee1@gmail.com");
        employeeRequestDto.setActive(true);
        employeeRequestDto.setManager(false);

        employeeRequestDto2 = new EmployeeDto();
        employeeRequestDto2.setUsername("Manager");
        employeeRequestDto2.setPassword("password456");
        employeeRequestDto2.setEmail("manager@gmail.com");
        employeeRequestDto2.setActive(true);
        employeeRequestDto2.setManager(true);

        // Clear repository for a clean slate in each test
        employeeRepository.deleteAll();
    }

    @Test
    @Order(0)
    public void testGetAllEmptyEmployees() {
        ResponseEntity<String> response = client.getForEntity("/employees", String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @Order(1)
    public void testCreateValidEmployee() {
        // Arrange
        EmployeeDto request = new EmployeeDto("Emp1", "password089", "emp1@gmail.com", true, false);

        // Act
        ResponseEntity<EmployeeDto> response = client.postForEntity("/employee", request, EmployeeDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        EmployeeDto createdEmployee = response.getBody();
        assertNotNull(createdEmployee);
        assertEquals("Emp1", createdEmployee.getUsername());
        assertEquals("emp1@gmail.com", createdEmployee.getEmail());
        assertEquals("password089", createdEmployee.getPassword());
        assertEquals(true, createdEmployee.isActive());
        assertEquals(false, createdEmployee.isManager());
        assertNotNull(createdEmployee.getUserID());
        assertTrue(createdEmployee.getUserID() > 0, "Response should have a positive ID.");

    }

    /* 
    @Test
    @Order(2)
    public void testGetEmployeeByUsername() {
        client.postForEntity("/employee", employeeRequestDto, EmployeeDto.class);
        ResponseEntity<EmployeeDto> response = client.getForEntity("/employees/username/Employee1", EmployeeDto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), employeeRequestDto));
    }
    */
    @Test
    @Order(2)
    public void testGetEmployeeById() {
        // Arrange
        EmployeeDto request = new EmployeeDto("Emp1", "password089", "emp1@gmail.com", true, false);
        ResponseEntity<EmployeeDto> createResponse = client.postForEntity("/employee", request, EmployeeDto.class);
        int id = createResponse.getBody().getUserID();

        // Act
        ResponseEntity<EmployeeDto> response = client.getForEntity("/employee/id/" + id, EmployeeDto.class);
        
        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getUserID());
        assertEquals("Emp1", response.getBody().getUsername());
        assertEquals("password089", response.getBody().getPassword());
        assertEquals("emp1@gmail.com", response.getBody().getEmail());
        assertEquals(true, response.getBody().isActive());
        assertEquals(false, response.getBody().isManager());

    }

    @Test
    @Order(3)
    public void testGetEmployeeByEmail() {
        // Arrange
        EmployeeDto request = new EmployeeDto("Emp1", "password089", "emp1@gmail.com", true, false);
        ResponseEntity<EmployeeDto> createResponse = client.postForEntity("/employee", request, EmployeeDto.class);
        String email = createResponse.getBody().getEmail();

        // Act
        ResponseEntity<EmployeeDto> response = client.getForEntity("/employee/email/" + email, EmployeeDto.class);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(email, response.getBody().getEmail());
        assertEquals("Emp1", response.getBody().getUsername());
        assertEquals("password089", response.getBody().getPassword());
        assertEquals(true, response.getBody().isActive());
        assertEquals(false, response.getBody().isManager());
    }

    @Test
    @Order(4)
    public void testUpdateValidEmployee() {
        // Arrange
        EmployeeDto request = new EmployeeDto("Emp1", "password089", "emp1@gmail.com", true, false);
        ResponseEntity<EmployeeDto> createResponse = client.postForEntity("/employee", request, EmployeeDto.class);
        int id = createResponse.getBody().getUserID();

        EmployeeDto request2 = new EmployeeDto("Emp1Updated", "newpassword089", "new_emp1@gmail.com", false, false);
        client.postForEntity("/employee", request2, EmployeeDto.class);
        // Act
        client.put("/employee/update/" + id, request2);
        
        // Assert
        ResponseEntity<EmployeeDto> response = client.getForEntity("/employee/id/" + id, EmployeeDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(response.getBody(), request2);
        assertTrue(equals(response.getBody(), request2));
    }

    @Test
    @Order(5)
    public void testGetAllEmployees() {
        client.postForEntity("/employee", employeeRequestDto, EmployeeDto.class);
        client.postForEntity("/employee", employeeRequestDto2, EmployeeDto.class);
        
        @SuppressWarnings("rawtypes")
        ResponseEntity<List> response = client.getForEntity("/employees", List.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> employees = response.getBody();
        assertEquals(employeeRequestDto.getUsername(), employees.get(0).get("username"));
        assertEquals(employeeRequestDto.getEmail(), employees.get(0).get("email"));
        assertEquals(employeeRequestDto2.getUsername(), employees.get(1).get("username"));
        assertEquals(employeeRequestDto2.getEmail(), employees.get(1).get("email"));
    }

    @Test
    @Order(6)
    public void testDeleteEmployee() {
        // Arrange
        EmployeeDto request = new EmployeeDto("Emp1", "password089", "emp1@gmail.com", true, false);
        ResponseEntity<EmployeeDto> createResponse = client.postForEntity("/employee", request, EmployeeDto.class);
        int id = createResponse.getBody().getUserID();

        ResponseEntity<Void> response = client.exchange("/employee/delete/" + id, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeleteEmployee_NotFound() {        
        ResponseEntity<Void> response = client.exchange("/employee/delete/" + 0, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private boolean equals(EmployeeDto employeeResponseDto, EmployeeDto employeeRequestDt2o) {
        return employeeResponseDto.getUsername().equals(employeeRequestDto2.getUsername()) &&
               employeeResponseDto.getEmail().equals(employeeRequestDto2.getEmail()) &&
               employeeResponseDto.getPassword().equals(employeeRequestDto2.getPassword()) &&
               employeeResponseDto.isActive() == employeeRequestDto2.isActive() &&
               employeeResponseDto.isManager() == employeeRequestDto2.isManager();
    }
}
