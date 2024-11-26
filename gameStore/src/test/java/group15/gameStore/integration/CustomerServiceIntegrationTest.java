package group15.gameStore.integration;

import group15.gameStore.dto.CategoryDto;
import group15.gameStore.dto.CustomerDto;
import group15.gameStore.model.Customer;
import group15.gameStore.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
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
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate client;

    private CustomerDto customerRequestDto;
    private CustomerDto customerRequestDto2;
    private static final String VALID_NAME = "Johnny Doug";
    private static final String VALID_PASSWORD = "password1234";
    private static final String VALID_EMAIL = "johnny@gmail.com";
    private static final String VALID_ADDRESS = "1234 Mcgill St";
    private static final String VALID_PHONENUMBER = "4383709345";
    private int validId;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();

        customerRequestDto = new CustomerDto();
        customerRequestDto.setUsername("Joe");
        customerRequestDto.setEmail("joe@gmail.com");
        customerRequestDto.setPhoneNumber("1234567890");
        customerRequestDto2 = new CustomerDto();
        customerRequestDto2.setUsername("Sally");
        customerRequestDto2.setEmail("sally@gmail.com");
        customerRequestDto2.setPhoneNumber("0987654321");
    }

    /* 
    @AfterAll
	public void clearDatabase() {
		customerRepository.deleteAll();
	}
    

    @Test
    @Order(0)
    public void testGetAllEmptyCustomers() {
        ResponseEntity<String> response = client.getForEntity("/customers", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No customers found in the system.", response.getBody());
    }
    */
    
    @Test
    @Order(1)
    public void testCreateValidCustomer() {
        // Arrange
        CustomerDto request = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);

        // Act
        ResponseEntity<CustomerDto> response = client.postForEntity("/customer/create", request, CustomerDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CustomerDto createdCustomer = response.getBody();
        assertNotNull(createdCustomer);
        assertEquals(VALID_NAME, createdCustomer.getUsername());
        assertEquals(VALID_PASSWORD, createdCustomer.getPassword());
        assertEquals(VALID_EMAIL, createdCustomer.getEmail());
        assertEquals(VALID_ADDRESS, createdCustomer.getAddress());
        assertEquals(VALID_PHONENUMBER, createdCustomer.getPhoneNumber());
        assertNotNull(createdCustomer.getUserId());
        assertTrue(createdCustomer.getUserId() > 0, "Response should have a positive ID.");
        this.validId = createdCustomer.getUserId();
    }


     @Test
     @Order(2)
     public void testUpdateCustomerUsername() {
         // Arrange
         CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
         ResponseEntity<CustomerDto> createResponse = client.postForEntity("/customer/create", tim, CustomerDto.class);
         int id = createResponse.getBody().getUserId();
 
         // Act
         tim.setUsername("Timmy");
         ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/username/" + id, tim, CustomerDto.class);
 
         // Assert
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertNotNull(response.getBody());
         assertEquals("Timmy", response.getBody().getUsername());
     }
 
     @Test
     @Order(3)
     public void testUpdateCustomerPassword() {
         // Arrange
         CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
         ResponseEntity<CustomerDto> createResponse = client.postForEntity("/customer/create", tim, CustomerDto.class);
         int id = createResponse.getBody().getUserId();
 
         // Act
         tim.setPassword("newPassword");
         ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/password/" + id, tim, CustomerDto.class);
 
         // Assert
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertNotNull(response.getBody());
         assertEquals("newPassword", response.getBody().getPassword());
     }
 
     @Test
     @Order(4)
     public void testUpdateCustomerEmail() {
         // Arrange
         CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
         ResponseEntity<CustomerDto> createResponse = client.postForEntity("/customer/create", tim, CustomerDto.class);
         int id = createResponse.getBody().getUserId();
 
         // Act
         tim.setEmail("newEmail@gmail.com");
         ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/email/" + id, tim, CustomerDto.class);

 
         // Assert
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertNotNull(response.getBody());
         assertEquals("newEmail@gmail.com", response.getBody().getEmail());
     }
 
     @Test
     @Order(5)
     public void testUpdateCustomerAddress() {
         // Arrange
         CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
         ResponseEntity<CustomerDto> createResponse = client.postForEntity("/customer/create", tim, CustomerDto.class);
         int id = createResponse.getBody().getUserId();
 
         // Act
         tim.setAddress("123 newAddress Lane");
         ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/address/" + id, tim, CustomerDto.class);
 
         // Assert
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertNotNull(response.getBody());
         assertEquals("123 newAddress Lane", response.getBody().getAddress());
     }
 
     @Test
     @Order(6)
     public void testUpdateCustomerPhoneNumber() {
         // Arrange
         CustomerDto tim = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
         ResponseEntity<CustomerDto> createResponse = client.postForEntity("/customer/create", tim, CustomerDto.class);
         int id = createResponse.getBody().getUserId();
 
         // Act
         tim.setPhoneNumber("5141234567");
         ResponseEntity<CustomerDto> response = client.postForEntity("/customer/update/phoneNumber/" + id, tim, CustomerDto.class);
 
         // Assert
         assertEquals(HttpStatus.OK, response.getStatusCode());
         assertNotNull(response.getBody());
         assertEquals("5141234567", response.getBody().getPhoneNumber());
     }
 
     @Test
     @Order(7)
     public void testGetCustomerById() {
        // Arrange
        CustomerDto request = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> createResponse = client.postForEntity("/customer/create", request, CustomerDto.class);
        int id = createResponse.getBody().getUserId();
 
        // Act
        ResponseEntity<CustomerDto> response = client.getForEntity("/customer/id/" + id, CustomerDto.class);
 
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getUserId());
     }
 
     @Test
     @Order(8)
     public void testGetCustomerByEmail() {
        // Arrange
        CustomerDto request = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        client.postForEntity("/customer/create", request, CustomerDto.class);

        // Act
        ResponseEntity<CustomerDto> response = client.getForEntity("/customer/email/" + VALID_EMAIL, CustomerDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(VALID_EMAIL, response.getBody().getEmail());

     } 

    @Test
    @Order(9)
    public void testGetAllCustomers() {
        // Arrange
        client.postForEntity("/customer/create", customerRequestDto, CustomerDto.class);
        client.postForEntity("/customer/create", customerRequestDto2, CustomerDto.class);

        // Act
        @SuppressWarnings("rawtypes")
        /* ResponseEntity<List<Map<String, Object>>> response = client.exchange(
            "/customer",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );*/

        ResponseEntity<List> response = client.getForEntity("/customer", List.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().size() > 0);

        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> customers = response.getBody();
        assertEquals(customerRequestDto.getUsername(), customers.get(0).get("username"));
        assertEquals(customerRequestDto.getEmail(), customers.get(0).get("email"));
        assertEquals(customerRequestDto2.getUsername(), customers.get(1).get("username"));
        assertEquals(customerRequestDto2.getEmail(), customers.get(1).get("email"));
        


    }

    @Test
    @Order(10)
    public void testDeleteCustomer() {
        // Arrange
        CustomerDto request = new CustomerDto(VALID_NAME, VALID_PASSWORD, VALID_EMAIL, VALID_ADDRESS, VALID_PHONENUMBER);
        ResponseEntity<CustomerDto> createResponse = client.postForEntity("/customer/create", request, CustomerDto.class);
        int id = createResponse.getBody().getUserId();

        // Act
        String url = "/customer/delete/" + id;
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(11)
    public void testDeleteCustomer_NotFound() {
        ResponseEntity<String> response = client.exchange("/customers/delete/NonExistentCustomer", HttpMethod.DELETE, null, String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private boolean equals(CustomerDto customerResponseDto, CustomerDto customerRequestDto) {
        return customerResponseDto.getUsername().equals(customerRequestDto.getUsername()) &&
               customerResponseDto.getEmail().equals(customerRequestDto.getEmail()) &&
               customerResponseDto.getPhoneNumber().equals(customerRequestDto.getPhoneNumber());
    }
}
