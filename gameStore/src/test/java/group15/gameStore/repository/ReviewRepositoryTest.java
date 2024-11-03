package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Review;
import jakarta.transaction.Transactional;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;
import group15.gameStore.model.Rating;

import java.util.List;

@SpringBootTest
public class ReviewRepositoryTest {
    
    @Autowired
    private ReviewRepository repo;
    
    @Autowired
    private GameRepository gameRepo;
    
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ManagerRepository managerRepo;

    private Manager testManager = new Manager("ChadTheManager", "00password", "manager@mail.mcgill.ca", true, true);
    
    private Game testGame = new Game("Test Game", "A description of the game", 59.99, 0, null, false, testManager);
    //private Game testGame2 = new Game("Test Game", "A description of the game", 59.99, 0, null, false);
    private Customer testCustomer = new Customer("ChadTheGamer", "00password", "chad@gmail.com","123SesameStreet", "012345678");


    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        repo.deleteAll();
        gameRepo.deleteAll();
        customerRepo.deleteAll();
        managerRepo.deleteAll();

        // Create and save a Game to be used in the tests
        testManager = managerRepo.save(testManager);

        testGame = gameRepo.save(testGame);
        testCustomer = customerRepo.save(testCustomer);
    }

    @AfterEach
    public void tearDown() {
        // Clear the database after each test
        repo.deleteAll();
        gameRepo.deleteAll();
        customerRepo.deleteAll();
        managerRepo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadReview() {
        // Create a new Review
        Rating rating = Rating.FIVE_STAR;
        String description = "Amazing game!";
        Review review = new Review(rating, description, testGame, testCustomer);

        // Save the Review
        review = repo.save(review);
        int reviewId = review.getReviewID();

        // Read the Review back from the database
        Review reviewFromDb = repo.findByReviewID(reviewId);

        // Assertions
        assertNotNull(reviewFromDb);
        assertEquals(reviewId, reviewFromDb.getReviewID());
        assertEquals(rating, reviewFromDb.getRating());
        assertEquals(description, reviewFromDb.getDescription());
        assertEquals(testGame.getGameID(), reviewFromDb.getGame().getGameID());
    }

    @Test
    @Transactional
    public void testFindReviewsByRating() {
        // Create and save reviews with different ratings
        Review review1 = new Review(Rating.FIVE_STAR, "Great game!", testGame,testCustomer);
        Review review2 = new Review(Rating.THREE_STAR, "It’s okay", testGame,testCustomer);
        repo.save(review1);
        //repo.save(review2);

        // Find reviews by rating
        List<Review> fiveStarReviews = repo.findByRating(Rating.FIVE_STAR);

        // Assertions
        assertNotNull(fiveStarReviews);
        assertEquals(1, fiveStarReviews.size());
        assertEquals(Rating.FIVE_STAR, fiveStarReviews.get(0).getRating());
    }

    @Test
    public void testFindReviewsByDescription() {
        // Create and save reviews
        Review review = new Review(Rating.FOUR_STAR, "Incredible gameplay!", testGame,testCustomer);
        repo.save(review);

        // Find review by keyword in description (case-insensitive)
        List<Review> reviewsWithKeyword = repo.findByDescriptionContainingIgnoreCase("incredible");

        // Assertions
        assertNotNull(reviewsWithKeyword);
        assertEquals(1, reviewsWithKeyword.size());
        assertEquals("Incredible gameplay!", reviewsWithKeyword.get(0).getDescription());
    }

    @Test
    public void testFindReviewsByGameID() {
        // Create and save a review for the game
        Review review = new Review(Rating.FOUR_STAR, "Challenging and fun!", testGame,testCustomer);
        repo.save(review);

        // Find reviews for the specific game by game ID
        List<Review> reviewsForGame = repo.findByGame(testGame);

        // Assertions
        assertNotNull(reviewsForGame);
        assertEquals(1, reviewsForGame.size());
        assertEquals(testGame.getGameID(), reviewsForGame.get(0).getGame().getGameID());
    }

    @Test
    @Transactional
    public void testDeleteReviewByReviewID() {
        // Create and save a review
        Review review = new Review(Rating.THREE_STAR, "Average game", testGame,testCustomer);
        review = repo.save(review);
        int reviewId = review.getReviewID();

        // Delete the review by review ID
        repo.deleteByReviewID(reviewId);

        // Verify that the review was deleted
        //Review deletedReview = repo.findById(reviewId).orElse(null);
        Review deletedReview = repo.findByReviewID(reviewId);
        assertEquals(null, deletedReview);
    }
}
