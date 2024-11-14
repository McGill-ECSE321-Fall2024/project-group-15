package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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

import group15.gameStore.dto.CustomerDto;
import group15.gameStore.dto.PaymentInfoDto;
import group15.gameStore.dto.WishlistDto;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Wishlist;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.PersonRepository;
import group15.gameStore.repository.WishlistRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WishlistIntegrationTest {
    @Autowired
	private TestRestTemplate client;
	@Autowired
	private CustomerRepository customerRepository;
    @Autowired
    private GameRepository gameRepository;

    private Game game;
    private Customer customer;
    private WishlistDto wishlistDto;
    private WishlistDto wishlistDto2;
    private CustomerDto customerDto;


	// private static final String VALID_NAME = "Tim";
	// private static final String VALID_EMAIL = "tim@yahoo.com";
	// private static final String VALID_PASSWORD = "abcde";
	// private int personId;
    // private Customer customer = new Customer("Joe Smith", "joesmith1234", "joe@gmail.com", "432 Sesami St", "123-456-7891");
	// private Wishlist wishlistDT = new Wishlist("Joe's Wishlist", customer);

	@BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUserID(1);
        customer.setUsername("John Doe");
        customer.setPhoneNumber("123456789");
        customerRepository.save(customer);

        game = new Game();
        game.setGameID(1);
        game.setTitle("Test Game");
        gameRepository.save(game);

        customerDto = new CustomerDto(customer);
        wishlistDto = new WishlistDto();
        wishlistDto2 = new WishlistDto();
    }


    // Test to create a valid wishlist
	@Test
	@Order(1)
	public void testCreateValidPerson() {
        // Act
        ResponseEntity<WishlistDto> response = client.postForEntity("/wishlist/create/1/wishlist1", customerDto, WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Test to add a game to a wishlist
    @Test
    @Order(2)
    public void testAddGameToWishlist() {
        // Act
        ResponseEntity<WishlistDto> response = client.exchange("/wishlist/addgame/1/1", HttpMethod.PUT, new HttpEntity<>(customerDto), WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
	}

    // Test to remove a game from a wishlist
    @Test
    @Order(3)
    public void testRemoveGameFromWishlist() {
        // Act
        ResponseEntity<WishlistDto> response = client.exchange("/wishlist/removegame/1/1", HttpMethod.DELETE, new HttpEntity<>(customerDto), WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Test to get a wishlist by its ID
    @Test
    @Order(4)
    public void testGetWishlistByWishlistId() {
        // Act
        ResponseEntity<WishlistDto> response = client.getForEntity("/wishlist/byId/1", WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    


}
