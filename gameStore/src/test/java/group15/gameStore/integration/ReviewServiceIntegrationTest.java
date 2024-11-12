package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ReviewRepository;
import group15.gameStore.service.ReviewService;

@SpringBootTest
@Transactional
public class ReviewServiceIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private CustomerRepository customerRepo;

    private Game game;
    private Customer customer;

    @BeforeEach
    void setUp() {
        // Create and save a Game
        game = new Game();
        game.setGameID(1);
        game.setTitle("Test Game");
        gameRepo.save(game);

        // Create and save a Customer
        customer = new Customer();
        customer.setUserID(1);
        customer.setUsername("testuser");
        customerRepo.save(customer);
    }

    @Test
    void testCreateReviewSuccess() {
        // Create and save a review
        Review review = reviewService.createReview(Rating.FIVE_STAR, "Great game!", game, customer);

        // Assert the review was created and saved correctly
        assertNotNull(review);
        assertEquals(Rating.FIVE_STAR, review.getRating());
        assertEquals("Great game!", review.getDescription());
        assertEquals(game.getGameID(), review.getGame().getGameID());
        assertEquals(customer.getUserID(), review.getCustomer().getUserID());

        // Verify it exists in the repository
        Review savedReview = reviewRepo.findByReviewID(review.getReviewID());
        assertNotNull(savedReview);
        assertEquals(review.getReviewID(), savedReview.getReviewID());
    }

    @Test
    void testUpdateReviewSuccess() {
        // Create and save a review
        Review review = reviewService.createReview(Rating.THREE_STAR, "It's okay.", game, customer);
        
        // Update the review
        review.setRating(Rating.FOUR_STAR);
        review.setDescription("It's good now.");
        Review updatedReview = reviewService.updateReview(review.getReviewID(), review, customer);

        // Assert the review was updated correctly
        assertNotNull(updatedReview);
        assertEquals(Rating.FOUR_STAR, updatedReview.getRating());
        assertEquals("It's good now.", updatedReview.getDescription());
    }

    @Test
    void testGetReviewByIdSuccess() {
        // Create and save a review
        Review review = reviewService.createReview(Rating.FIVE_STAR, "Excellent game!", game, customer);

        // Retrieve the review by ID
        Review retrievedReview = reviewService.getReviewById(review.getReviewID());

        // Assert the retrieved review matches the saved one
        assertNotNull(retrievedReview);
        assertEquals(review.getReviewID(), retrievedReview.getReviewID());
        assertEquals("Excellent game!", retrievedReview.getDescription());
    }

    @Test
    void testGetReviewByRatingSuccess() {
        // Create and save two reviews with the same rating
        reviewService.createReview(Rating.FIVE_STAR, "Amazing!", game, customer);
        reviewService.createReview(Rating.FIVE_STAR, "Fantastic!", game, customer);

        // Retrieve reviews by rating
        List<Review> reviews = reviewService.getReviewByRating(Rating.FIVE_STAR);

        // Assert that the reviews with the given rating are retrieved
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        assertEquals("Amazing!", reviews.get(0).getDescription());
        assertEquals("Fantastic!", reviews.get(1).getDescription());
    }

    @Test
    void testGetReviewByGameSuccess() {
        // Create and save a review for the game
        reviewService.createReview(Rating.THREE_STAR, "Average game", game, customer);

        // Retrieve reviews by game
        List<Review> reviews = reviewService.getReviewByGame(game);

        // Assert that the reviews for the game are retrieved
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals("Average game", reviews.get(0).getDescription());
    }

    @Test
    void testDeleteReviewSuccess() {
        // Create and save a review
        Review review = reviewService.createReview(Rating.TWO_STAR, "Not great", game, customer);

        // Delete the review
        reviewService.deleteReview(review.getReviewID(), customer);

        // Assert the review is no longer in the repository
        assertNull(reviewRepo.findByReviewID(review.getReviewID()));
    }

    @Test
    void testDeleteReviewUnauthorized() {
        // Create and save a review
        Review review = reviewService.createReview(Rating.FOUR_STAR, "Good game", game, customer);

        // Create another customer who is not the owner
        Customer anotherCustomer = new Customer();
        anotherCustomer.setUserID(2);
        anotherCustomer.setUsername("anotheruser");
        customerRepo.save(anotherCustomer);

        // Attempt to delete the review with an unauthorized customer
        Exception exception = assertThrows(SecurityException.class, () -> {
            reviewService.deleteReview(review.getReviewID(), anotherCustomer);
        });
        
        // Assert the exception message is correct
        assertEquals("Unauthorized access. Only the owner can delete this review.", exception.getMessage());
    }
}
