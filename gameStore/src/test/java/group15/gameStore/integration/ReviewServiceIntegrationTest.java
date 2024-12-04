package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.*;

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
import org.springframework.http.*;

import group15.gameStore.dto.ReviewDto;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ReviewRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReviewServiceIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Game game;
    private Customer customer;
    private ReviewDto reviewDto;
    private ReviewDto updatedReviewDto;

    private int validGameId;
    private int validCustomerId;
    private int validReviewId;
    private Review review;
    private Review updatedReview;

    @BeforeEach
    public void setUp() {
        // Initialize and save a Game
        game = new Game();
        game.setTitle("Test Game");
        gameRepository.save(game);
        validGameId = game.getGameID();

        // Initialize and save a Customer
        customer = new Customer();
        customer.setUsername("testuser");
        customer.setEmail("testuser@mail.com");
        customer.setPassword("password123");
        customerRepository.save(customer);
        validCustomerId = customer.getUserID();

        //initializa a review
        review = new Review();
        review.setRating(Rating.FIVE_STAR);
        review.setDescription("Great game!");
        review.setGame(game);
        review.setCustomer(customer);
        this.validReviewId = review.getReviewID();
        reviewRepository.save(review);

        // Initialize an updated review
        updatedReview = new Review();
        updatedReview.setRating(Rating.FOUR_STAR);
        updatedReview.setDescription("It's good now.");
        updatedReview.setGame(game);
        updatedReview.setCustomer(customer);
        updatedReviewDto = new ReviewDto(updatedReview);
        


        // Initialize Review DTOs
        reviewDto = new ReviewDto(review);

        // updatedReviewDto = new ReviewDto();
        // updatedReviewDto.setRating(Rating.FOUR_STAR);
        // updatedReviewDto.setDescription("Good game!");
        // updatedReviewDto.setGameId(validGameId);
        // updatedReviewDto.setCustomerId(validCustomerId);
    }

    @AfterAll
    public void clearDatabase() {
        reviewRepository.deleteAll();
        gameRepository.deleteAll();
        customerRepository.deleteAll();
    }

    // Test to create a valid review
    @Test
    @Order(1)
    public void testCreateValidReview() {
        // Act
        ResponseEntity<ReviewDto> response = client.postForEntity(
                "/review",
                reviewDto,
                ReviewDto.class
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Test to update an existing review
    @Test
    @Order(2)
    public void testUpdateReviewSuccess() {
        // First, create a review to update
        Review savedReview = new Review();
        savedReview.setRating(Rating.THREE_STAR);
        savedReview.setDescription("It's okay.");
        savedReview.setGame(game);
        savedReview.setCustomer(customer);
        reviewRepository.save(savedReview);
        int reviewId = savedReview.getReviewID();

        // Update Review DTO
        ReviewDto updateDto = new ReviewDto(updatedReview);

        // Act
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReviewDto> entity = new HttpEntity<>(updateDto, headers);

        ResponseEntity<ReviewDto> response = client.exchange(
                "/reviews/update/" + reviewId,
                HttpMethod.PUT,
                entity,
                ReviewDto.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Rating.FOUR_STAR, response.getBody().getRating());
        assertEquals("It's good now.", response.getBody().getDescription());
    }

    // Test to retrieve a review by its ID
    @Test
    @Order(3)
    public void testGetReviewByIdSuccess() {
        // First, create a review to retrieve
        Review savedReview = new Review();
        savedReview.setRating(Rating.FIVE_STAR);
        savedReview.setDescription("Excellent game!");
        savedReview.setGame(game);
        savedReview.setCustomer(customer);
        reviewRepository.save(savedReview);
        int reviewId = savedReview.getReviewID();

        // Act
        ResponseEntity<ReviewDto> response = client.getForEntity(
                "/reviews/" + reviewId,
                ReviewDto.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    // Test to retrieve reviews by rating
    @Test
    @Order(4)
    public void testGetReviewsByRatingSuccess() {
        // Create two reviews with FIVE_STAR rating
        Review review1 = new Review();
        review1.setRating(Rating.FIVE_STAR);
        review1.setDescription("Amazing!");
        review1.setGame(game);
        review1.setCustomer(customer);
        reviewRepository.save(review1);

        Review review2 = new Review();
        review2.setRating(Rating.FIVE_STAR);
        review2.setDescription("Fantastic!");
        review2.setGame(game);
        review2.setCustomer(customer);
        reviewRepository.save(review2);

        // Act
        ResponseEntity<ReviewDto[]> response = client.getForEntity(
                "/reviews/rating/FIVE_STAR",
                ReviewDto[].class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().length);
        assertEquals("Amazing!", response.getBody()[0].getDescription());
        assertEquals("Fantastic!", response.getBody()[1].getDescription());
    }

    // Test to retrieve reviews by game
    @Test
    @Order(5)
    public void testGetReviewsByGameSuccess() {
        // Create a review for the game
        Review review = new Review();
        review.setRating(Rating.THREE_STAR);
        review.setDescription("Average game");
        review.setGame(game);
        review.setCustomer(customer);
        reviewRepository.save(review);

        // Act
        ResponseEntity<ReviewDto[]> response = client.getForEntity(
                "/reviews/game/" + validGameId,
                ReviewDto[].class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("Average game", response.getBody()[0].getDescription());
    }

    // Test to delete a review successfully
    @Test
    @Order(6)
    public void testDeleteReviewSuccess() {
        // First, create a review to delete
        Review savedReview = new Review();
        savedReview.setRating(Rating.TWO_STAR);
        savedReview.setDescription("Not great");
        savedReview.setGame(game);
        savedReview.setCustomer(customer);
        reviewRepository.save(savedReview);
        int reviewId = savedReview.getReviewID();

        // Act
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response = client.exchange(
                "/reviews/delete/" + reviewId,
                HttpMethod.DELETE,
                entity,
                Void.class
        );

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(reviewRepository.findById(reviewId).orElse(null));
    }

    // Test to delete a review with unauthorized access
    @Test
    @Order(7)
    public void testDeleteReviewUnauthorized() {
        // First, create a review
        Review savedReview = new Review();
        savedReview.setRating(Rating.FOUR_STAR);
        savedReview.setDescription("Good game");
        savedReview.setGame(game);
        savedReview.setCustomer(customer);
        reviewRepository.save(savedReview);
        int reviewId = savedReview.getReviewID();

        // Create another customer who is not the owner
        Customer anotherCustomer = new Customer();
        anotherCustomer.setUsername("anotheruser");
        anotherCustomer.setEmail("anotheruser@mail.com");
        anotherCustomer.setPassword("password456");
        customerRepository.save(anotherCustomer);
        int anotherCustomerId = anotherCustomer.getUserID();

        // Prepare headers or authentication as the unauthorized customer
        // Assuming the API uses some form of authentication token, which should be set here.
        // For simplicity, we'll assume passing customer ID in headers or similar.

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Customer-Id", String.valueOf(anotherCustomerId)); // Example header

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<String> response = client.exchange(
                "/reviews/delete/" + reviewId,
                HttpMethod.DELETE,
                entity,
                String.class
        );

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Unauthorized access. Only the owner can delete this review.", response.getBody());
    }
}
