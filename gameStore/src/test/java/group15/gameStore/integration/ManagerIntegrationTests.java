package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.dto.ErrorDto;
import group15.gameStore.dto.ManagerDto;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.ManagerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ManagerIntegrationTests {
    @Autowired
	private TestRestTemplate client;
    @Autowired
	private ManagerRepository managerRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    private static final String VALID_USERNAME = "PaulManager";
	private static final String VALID_EMAIL = "paul@mail.com";
	private static final String VALID_PASSWORD = "Paul12345";
    private static final boolean VALID_ISACTIVE = true;
    private static final boolean VALID_ISMANAGER = true;
    private static final Manager VALID_MANAGEREMPLOYEE = new Manager("SmithManager", "Smith123", "smith@mail.com", true, true);
    
    private int managerID;
    private Employee managerEmployee;

    public Manager manager = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, VALID_ISMANAGER);

    @AfterAll
	public void clearDatabase() {
        managerRepo.deleteAll();
	}
    /*public static void main(String[] args) {
        Manager managerTest = new Manager(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, VALID_ISMANAGER);
        ManagerDto managerDto = new ManagerDto(managerTest);
        System.out.println(managerDto.getPassword());
    }*/

    @Test
	@Order(1)
    public void testCreateValidManager() {
        // Arrange
        ManagerDto managerDto = new ManagerDto(manager);
        //ManagerDto managerDto = new ManagerDto(VALID_USERNAME, VALID_PASSWORD, VALID_EMAIL, VALID_ISACTIVE, VALID_ISMANAGER);

        ResponseEntity<ManagerDto> response = client.postForEntity("/manager", managerDto, ManagerDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        managerID = response.getBody().getUserID();
    }

    @Test
	@Order(2)
    public void testGetValidManagerByID() {
        ResponseEntity<ManagerDto> response = client.getForEntity(String.format("/manager/%d", managerID), ManagerDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(managerID, response.getBody().getUserID());
    }

    @Test
	@Order(3)
    public void testGetValidManagerByEmail() {
        ResponseEntity<ManagerDto> response = client.getForEntity(String.format("/manager/email/%s", VALID_EMAIL), ManagerDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());
    }

    @Test
	@Order(4)
    public void testGetValidManagersByUsername() {
        ResponseEntity<List<ManagerDto>> response = client.exchange(String.format("/manager/username/%s", VALID_USERNAME), HttpMethod.GET, null, new ParameterizedTypeReference<List<ManagerDto>>() {});

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(VALID_USERNAME, response.getBody().get(0).getUsername());
    }

    @Test
	@Order(5)
    public void testGetAllValidManagers() {
        ResponseEntity<List<ManagerDto>> response = client.exchange("/managers", HttpMethod.GET, null, new ParameterizedTypeReference<List<ManagerDto>>() {});
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
	@Order(6)
    public void testUpdateValidManagers() {
        
        manager.setPassword("newPassword");
        ManagerDto managerDto = new ManagerDto(manager);
        managerDto.setUserID(managerID);

        HttpEntity<ManagerDto> requestEntity = new HttpEntity<>(managerDto, null);

        ResponseEntity<ManagerDto> response = client.exchange(String.format("/manager/%d", managerID), HttpMethod.PUT, requestEntity, ManagerDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newPassword", response.getBody().getPassword());
    }
    
    @Test
	@Order(7)
    public void testDeleteValidManagers() {
        client.exchange(String.format("/manager/%d", managerID), HttpMethod.DELETE, null, Void.class);

        ResponseEntity<ErrorDto> response = client.exchange(String.format("/manager/%d", managerID), HttpMethod.GET, null, ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(String.format("There is no manager with ID %d", managerID), response.getBody().getErrors().get(0));
    }

}
