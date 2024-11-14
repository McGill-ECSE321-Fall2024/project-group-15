package group15.gameStore.integration;

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
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No employee records found in the system.", response.getBody());
    }

    @Test
    @Order(1)
    public void testCreateValidEmployee() {
        ResponseEntity<EmployeeDto> employeeResponse1 = client.postForEntity("/employees/create", employeeRequestDto, EmployeeDto.class);
        assertEquals(HttpStatus.CREATED, employeeResponse1.getStatusCode());
        assertNotNull(employeeResponse1.getBody());
        assertTrue(equals(employeeResponse1.getBody(), employeeRequestDto));

        ResponseEntity<EmployeeDto> employeeResponse2 = client.postForEntity("/employees/create", employeeRequestDto2, EmployeeDto.class);
        assertEquals(HttpStatus.CREATED, employeeResponse2.getStatusCode());
        assertNotNull(employeeResponse2.getBody());
        assertTrue(equals(employeeResponse2.getBody(), employeeRequestDto2));
    }

    @Test
    @Order(2)
    public void testGetEmployeeByUsername() {
        client.postForEntity("/employees/create", employeeRequestDto, EmployeeDto.class);
        ResponseEntity<EmployeeDto> response = client.getForEntity("/employees/username/Employee1", EmployeeDto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), employeeRequestDto));
    }

    @Test
    @Order(3)
    public void testUpdateValidEmployee() {
        client.postForEntity("/employees/create", employeeRequestDto, EmployeeDto.class);

        // Prepare updated employee details
        EmployeeDto updatedEmployeeDto = new EmployeeDto();
        updatedEmployeeDto.setUsername("Employee1Updated");
        updatedEmployeeDto.setPassword("newpassword123");
        updatedEmployeeDto.setEmail("new_employee1@gmail.com");
        updatedEmployeeDto.setActive(false);
        updatedEmployeeDto.setManager(true);

        HttpEntity<EmployeeDto> requestEntity = new HttpEntity<>(updatedEmployeeDto);
        ResponseEntity<EmployeeDto> response = client.exchange("/employees/update/Employee1", HttpMethod.PUT, requestEntity, EmployeeDto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), updatedEmployeeDto));
    }

    @Test
    @Order(4)
    public void testGetAllEmployees() {
        client.postForEntity("/employees/create", employeeRequestDto, EmployeeDto.class);
        client.postForEntity("/employees/create", employeeRequestDto2, EmployeeDto.class);
        
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
    @Order(5)
    public void testDeleteEmployee() {
        client.postForEntity("/employees/create", employeeRequestDto, EmployeeDto.class);

        ResponseEntity<Void> response = client.exchange("/employees/delete/Employee1", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteEmployee_NotFound() {
        ResponseEntity<String> response = client.exchange("/employees/delete/NonExistentEmployee", HttpMethod.DELETE, null, String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee with username NonExistentEmployee not found.", response.getBody());
    }

    private boolean equals(EmployeeDto employeeResponseDto, EmployeeDto employeeRequestDto) {
        return employeeResponseDto.getUsername().equals(employeeRequestDto.getUsername()) &&
               employeeResponseDto.getEmail().equals(employeeRequestDto.getEmail()) &&
               employeeResponseDto.getPassword().equals(employeeRequestDto.getPassword()) &&
               employeeResponseDto.isActive() == employeeRequestDto.isActive() &&
               employeeResponseDto.isManager() == employeeRequestDto.isManager();
    }
}
