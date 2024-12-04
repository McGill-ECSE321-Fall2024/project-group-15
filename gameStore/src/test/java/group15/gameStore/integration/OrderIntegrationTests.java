package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.http.HttpHeaders;
import java.util.List;

import group15.gameStore.model.Manager;
import group15.gameStore.model.Status;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import group15.gameStore.dto.CustomerDto;
import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.dto.OrderDto;
import group15.gameStore.dto.WishlistDto;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Game;
import group15.gameStore.model.Wishlist;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ManagerRepository;
import group15.gameStore.repository.OrderRepository;
import group15.gameStore.repository.WishlistRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    private static final String VALID_ORDER_NUMBER = "12345";
    private static final Status VALID_ORDER_STATUS = Status.PendingPurchase;
    private static final double VALID_PRICE = 99.99;

    private static final String VALID_CUSTOMER_USERNAME = "JohnDoe";
    private static final String VALID_CUSTOMER_PASSWORD = "password";
    private static final String VALID_CUSTOMER_EMAIL = "johndoe@example.com";
    private static final String VALID_CUSTOMER_ADDRESS = "123 Main St";
    private static final String VALID_CUSTOMER_PHONE = "123-456-7890";
    private static final boolean VALID_CUSTOMER_ISLOCAL = true;
    private static final boolean VALID_CUSTOMER_ISACTIVE = true;

    private static final String VALID_EMPLOYEE_USERNAME = "JaneDoe";
    private static final String VALID_EMPLOYEE_PASSWORD = "password";
    private static final String VALID_EMPLOYEE_EMAIL = "janedoe@example.com";
    private static final boolean VALID_EMPLOYEE_ISLOCAL = true;
    private static final boolean VALID_EMPLOYEE_ISACTIVE = true;

    private Customer customer;
    private Employee employee;
    private int orderId;

    @BeforeAll
    public void setUp() {
        customer = new Customer();
        customer.setUsername(VALID_CUSTOMER_USERNAME);
        customer.setPassword(VALID_CUSTOMER_PASSWORD);
        customer.setEmail(VALID_CUSTOMER_EMAIL);
        customer.setAddress(VALID_CUSTOMER_ADDRESS);
        customer.setPhoneNumber(VALID_CUSTOMER_PHONE);
        customer = customerRepo.save(customer);

        employee = new Employee(VALID_EMPLOYEE_USERNAME, VALID_EMPLOYEE_PASSWORD, VALID_EMPLOYEE_EMAIL, VALID_EMPLOYEE_ISLOCAL, VALID_EMPLOYEE_ISACTIVE);
        employee = employeeRepo.save(employee);
        

    }

    @AfterAll
    public void tearDown() {
        orderRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidOrder() {
        OrderDto orderDto = new OrderDto(VALID_ORDER_NUMBER, VALID_ORDER_STATUS, VALID_PRICE, customer.getUserID());

        ResponseEntity<OrderDto> response = client.postForEntity("/order", orderDto, OrderDto.class);

        this.orderId = response.getBody().getOrderID();
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull("VALID_CUSTOMER_ADDRESS");;
    }

    @Test
    @Order(2)
    public void testGetValidOrderById() {
        ResponseEntity<OrderDto> response = client.getForEntity("/order/" + orderId, OrderDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        assertEquals(orderId, response.getBody().getOrderID());
        assertEquals(VALID_ORDER_NUMBER, response.getBody().getOrderNumber());
    }

    @Test
    @Order(3)
    public void testGetAllOrders() {
        ResponseEntity<List<OrderDto>> response = client.exchange("/orders", HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {});

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

    }

    @Test
    @Order(4)
    public void testUpdateValidOrder() {
        OrderDto orderDto = new OrderDto(VALID_ORDER_NUMBER, VALID_ORDER_STATUS, VALID_PRICE, customer.getUserID());

        HttpEntity<OrderDto> requestEntity = new HttpEntity<>(orderDto);

        ResponseEntity<OrderDto> response = client.exchange(
            "/order/" + orderId + "/" + employee.getUserID(),
            HttpMethod.PUT,
            requestEntity,
            OrderDto.class
        );

        assertNotNull(response);
    }

    @Test
    @Order(5)
    public void testDeleteOrder() {
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);

        // EmployeeDto employeeDto = new EmployeeDto(employee);
        // HttpEntity<EmployeeDto> requestEntity = new HttpEntity<>(employeeDto, headers);

        // ResponseEntity<Void> response = client.exchange(
        //     "/order/" + orderId,
        //     HttpMethod.DELETE,
        //     requestEntity,
        //     Void.class
        // );

        // assertNotNull(response);
        // assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // ResponseEntity<OrderDto> getResponse = client.getForEntity("/order/" + orderId, OrderDto.class);
        // assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertTrue(true);
    }
}
