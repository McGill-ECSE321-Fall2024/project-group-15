package group15.gearUp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import group15.gearUp.model.Manager;
import org.junit.jupiter.api.AfterAll;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import group15.gearUp.dto.CustomerDto;
import group15.gearUp.dto.WishlistDto;
import group15.gearUp.model.Customer;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Wishlist;
import group15.gearUp.repository.CustomerRepository;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.ManagerRepository;
import group15.gearUp.repository.WishlistRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishlistIntegrationTest {
    @Autowired
	private TestRestTemplate client;

    @Autowired
    private WishlistRepository wishlistRepository;

	@Autowired
	private CustomerRepository customerRepository;

    @Autowired
    private ManagerRepository  managerRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    private Equipment equipment;
    private Customer customer;
    private Manager manager;
    private CustomerDto customerDto;

    private int validCustomerId;
    private int validWishlistId;
    private int validEquipmentId;



	@BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUsername("John Doe");
        customer.setPhoneNumber("123456789");
        customer.setEmail("john@mail.mcgill.ca");
        customer.setAddress("1234 McGill College");
        customer.setPassword("johndoe123");
        customerRepository.save(customer);
        this.validCustomerId = customer.getUserID();

        manager = new Manager("ChadTheManager", "manager123","chad@mail.mcgill.ca", true, true);
        managerRepository.save(manager);
        

        equipment = new Equipment();
        equipment.setTitle("Test equipment");
        equipmentRepository.save(equipment);
        this.validEquipmentId = equipment.getEquipmentID();

        Wishlist wishlist = new Wishlist("wishlist1", customer);
        wishlistRepository.save(wishlist);
        this.validWishlistId = wishlist.getWishListId();

        customerDto = new CustomerDto(customer);
    }

    @AfterAll
    public void clearDatabase() {
        wishlistRepository.deleteAll();
        customerRepository.deleteAll();
        equipmentRepository.deleteAll();
    }


    // Test to create a valid wishlist
	@Test
	@Order(1)
	public void testCreateValidWishlist() {
        // Act
        ResponseEntity<WishlistDto> response = client.postForEntity("/wishlist/create/" + validCustomerId + "/wishlist1", customerDto, WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Test to add a equipment to a wishlist
    @Test
    @Order(2)
    public void testAddEquipmentToWishlist() {
        // Act
        ResponseEntity<WishlistDto> response = client.exchange("/wishlist/addEquipment/" + validWishlistId+"/" + validEquipmentId, HttpMethod.PUT, new HttpEntity<>(customerDto), WishlistDto.class);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
	}

    // Test to remove a equipment from a wishlist
    @Test
    @Order(3)
    public void testRemoveEquipmentFromWishlist() {
        // Act
        ResponseEntity<WishlistDto> response = client.exchange("/wishlist/RemoveEquipment/" + validWishlistId+ "/" + validEquipmentId, HttpMethod.DELETE, new HttpEntity<>(customerDto), WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Test to get a wishlist by its ID
    @Test
    @Order(4)
    public void testGetWishlistByWishlistId() {
        // Act
        ResponseEntity<WishlistDto> response = client.getForEntity("/wishlist/byId/" + validWishlistId, WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
