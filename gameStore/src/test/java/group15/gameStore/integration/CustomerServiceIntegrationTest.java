package group15.gameStore.integration;

import group15.gameStore.dto.CustomerDto;
import group15.gameStore.repository.CustomerRepository;
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
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate client;

    private CustomerDto customerRequestDto;
    private CustomerDto customerRequestDto2;

    @BeforeEach
    public void setUp() {
        customerRequestDto = new CustomerDto();
        customerRequestDto.setUsername("Joe");
        customerRequestDto.setEmail("joe@gmail.com");
        customerRequestDto.setPhoneNumber("1234567890");

        customerRequestDto2 = new CustomerDto();
        customerRequestDto2.setUsername("Sally");
        customerRequestDto2.setEmail("sally@gmail.com");
        customerRequestDto2.setPhoneNumber("0987654321");

        // Clear repository to ensure a clean slate for each test
        customerRepository.deleteAll();
    }

    @Test
    @Order(0)
    public void testGetAllEmptyCustomers() {
        ResponseEntity<String> response = client.getForEntity("/customers", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No customers found in the system.", response.getBody());
    }

    @Test
    @Order(1)
    public void testCreateValidCustomer() {
        ResponseEntity<CustomerDto> customerResponse1 = client.postForEntity("/customers/create", customerRequestDto, CustomerDto.class);
        assertEquals(HttpStatus.CREATED, customerResponse1.getStatusCode());
        assertNotNull(customerResponse1.getBody());
        assertTrue(equals(customerResponse1.getBody(), customerRequestDto));

        ResponseEntity<CustomerDto> customerResponse2 = client.postForEntity("/customers/create", customerRequestDto2, CustomerDto.class);
        assertEquals(HttpStatus.CREATED, customerResponse2.getStatusCode());
        assertNotNull(customerResponse2.getBody());
        assertTrue(equals(customerResponse2.getBody(), customerRequestDto2));
    }

    @Test
    @Order(2)
    public void testGetCustomerByUsername() {
        client.postForEntity("/customers/create", customerRequestDto, CustomerDto.class);
        ResponseEntity<CustomerDto> response = client.getForEntity("/customers/username/Joe", CustomerDto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), customerRequestDto));
    }

    @Test
    @Order(3)
    public void testUpdateValidCustomer() {
        client.postForEntity("/customers/create", customerRequestDto, CustomerDto.class);

        CustomerDto updatedCustomerDto = new CustomerDto();
        updatedCustomerDto.setUsername("JoeUpdated");
        updatedCustomerDto.setEmail("newjoe@gmail.com");
        updatedCustomerDto.setPhoneNumber("1231231234");

        HttpEntity<CustomerDto> requestEntity = new HttpEntity<>(updatedCustomerDto);
        ResponseEntity<CustomerDto> response = client.exchange("/customers/update/Joe", HttpMethod.PUT, requestEntity, CustomerDto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), updatedCustomerDto));
    }

    @Test
    @Order(4)
    public void testGetAllCustomers() {
        client.postForEntity("/customers/create", customerRequestDto, CustomerDto.class);
        client.postForEntity("/customers/create", customerRequestDto2, CustomerDto.class);

        @SuppressWarnings("rawtypes")
        ResponseEntity<List> response = client.getForEntity("/customers", List.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> customers = response.getBody();
        assertEquals(customerRequestDto.getUsername(), customers.get(0).get("username"));
        assertEquals(customerRequestDto.getEmail(), customers.get(0).get("email"));
        assertEquals(customerRequestDto2.getUsername(), customers.get(1).get("username"));
        assertEquals(customerRequestDto2.getEmail(), customers.get(1).get("email"));
    }

    @Test
    @Order(5)
    public void testDeleteCustomer() {
        client.postForEntity("/customers/create", customerRequestDto, CustomerDto.class);

        ResponseEntity<Void> response = client.exchange("/customers/delete/Joe", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCustomer_NotFound() {
        ResponseEntity<String> response = client.exchange("/customers/delete/NonExistentCustomer", HttpMethod.DELETE, null, String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer with username NonExistentCustomer not found.", response.getBody());
    }

    private boolean equals(CustomerDto customerResponseDto, CustomerDto customerRequestDto) {
        return customerResponseDto.getUsername().equals(customerRequestDto.getUsername()) &&
               customerResponseDto.getEmail().equals(customerRequestDto.getEmail()) &&
               customerResponseDto.getPhoneNumber().equals(customerRequestDto.getPhoneNumber());
    }
}
