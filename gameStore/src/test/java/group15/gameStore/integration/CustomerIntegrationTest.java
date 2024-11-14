package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import group15.gameStore.dto.CustomerDto;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.PersonRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerIntegrationTest {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository customerRepo;

    private static final String VALID_NAME = "Johnny Doug";
    private static final String VALID_PASSWORD = "password1234";
    private static final String VALID_EMAIL = "johnny@gmail.com";
    private static final String VALID_ADDRESS = "1234 Mcgill St";
    private static final String VALID_PHONENUMBER = "4383709345";

    @AfterAll
	public void clearDatabase() {
		customerRepo.deleteAll();
	}

	@Test
	@Order(1)
	public void testCreateValidPerson() {
		// Arrange
		CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);

		// Act
		ResponseEntity<List> response = client.postForEntity("/customer", tim, List.class);

		// Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        List<Map<String, Object>> reqs = response.getBody();
        assertEquals(1, reqs.size());
        assertEquals(VALID_PASSWORD, reqs.get(0).get("password"));
        assertEquals(VALID_ADDRESS, reqs.get(0).get("address"));
        assertEquals(VALID_NAME, reqs.get(0).get("name"));
        assertEquals(VALID_PHONENUMBER, reqs.get(0).get("phoneNumber"));
	}

    //test update customer username
    @Test
    @Order(2)
    public void testUpdateCustomerUsername() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/username/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        tim.setUsername("Timmy");
        client.put("/customer/" + id, tim);

        // Assert
        response = client.getForEntity("/customer/" + id, CustomerDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Timmy", response.getBody().getUsername());
    }


    //test update customer password
    @Test
    @Order(3)
    public void testUpdateCustomerPassword() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/password/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        tim.setPassword("newPassword");
        client.put("/customer/" + id, tim);

        // Assert
        response = client.getForEntity("/customer/" + id, CustomerDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newPassword", response.getBody().getPassword());
    }

    //test update customer email
    @Test
    @Order(4)
    public void testUpdateCustomerEmail() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/email/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        tim.setEmail("newEmail");
        client.put("/customer/" + id, tim);

        // Assert
        response = client.getForEntity("/customer/" + id, CustomerDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newEmail", response.getBody().getEmail());
    }

    //test update customer address
    @Test
    @Order(5)
    public void testUpdateCustomerAddress() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/address/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        tim.setAddress("newAddress");
        client.put("/customer/" + id, tim);

        // Assert
        response = client.getForEntity("/customer/" + id, CustomerDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newAddress", response.getBody().getAddress());
    }

    //test update customer phone number
    @Test
    @Order(6)
    public void testUpdateCustomerPhoneNumber() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/phonenumber/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        tim.setPhoneNumber("newPhoneNumber");
        client.put("/customer/" + id, tim);

        // Assert
        response = client.getForEntity("/customer/" + id, CustomerDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newPhoneNumber", response.getBody().getPhoneNumber());
    }

    //test get customer by id
    @Test
    @Order(7)
    public void testGetCustomerById() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/username/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        response = client.getForEntity("/customer/" + id, CustomerDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Timmy", response.getBody().getUsername());
    }

    //test get customer by email
    @Test
    @Order(8)
    public void testGetCustomerByEmail() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/username/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        response = client.getForEntity("/customer/email/" + VALID_EMAIL, CustomerDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Timmy", response.getBody().getUsername());
    }

    //test get all customers
    @Test
    @Order(9)
    public void testGetAllCustomers() {
        // Arrange
        CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/username/0", tim, CustomerDto.class);
        int id = response.getBody().getUserId();

        // Act
        response = client.getForEntity("/customer", CustomerDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
