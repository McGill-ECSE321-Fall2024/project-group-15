package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ReviewRepository;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepo;

    @Mock
    private GameRepository gameRepo;

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private ReviewService reviewService;

    private Rating rating;
    private Game game;
    public Customer customer;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = Rating.FIVE_STAR;  // Assuming Rating is an enum
        game = new Game();
        game.setGameID(1);
        customer = new Customer();
        customer.setUserID(1);
        review = new Review(rating, "Great game!", game, customer);
        review.setReviewID(1);
    }

    @Test
    void testCreateReviewSuccess() {
        when(gameRepo.findGameByGameID(game.getGameID())).thenReturn(game);
        when(customerRepo.findByUserID(customer.getUserID())).thenReturn(customer);
        when(reviewRepo.save(any(Review.class))).thenReturn(review);

        Review createdReview = reviewService.createReview(rating, "Great game!", game, customer);

        assertNotNull(createdReview);
        assertEquals(rating, createdReview.getRating());
        assertEquals("Great game!", createdReview.getDescription());
        assertEquals(game, createdReview.getGame());
        assertEquals(customer, createdReview.getCustomer());
    }

    @Test
    void testCreateReviewInvalidRating() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(null, "Good game", game, customer);
        });
        assertEquals("A Rating is required.", exception.getMessage());
    }

    @Test
    void testCreateReviewInvalidDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(rating, "", game, customer);
        });
        assertEquals("Description cannot be empty.", exception.getMessage());
    }

    @Test
    void testCreateReviewInvalidGame() {
        when(gameRepo.findGameByGameID(game.getGameID())).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(rating, "Great game!", game, customer);
        });
        assertEquals("Game must be valid and exist in the system.", exception.getMessage());
    }

    @Test
    void testUpdateReviewSuccess() {
        Review updatedReview = new Review(rating, "Updated review", game, customer);
        updatedReview.setReviewID(1);

        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(review);
        when(reviewRepo.save(review)).thenReturn(review);

        Review result = reviewService.updateReview(review.getReviewID(), updatedReview, customer);

        assertNotNull(result);
        assertEquals("Updated review", result.getDescription());
        assertEquals(rating, result.getRating());
    }

    @Test
    void testUpdateReviewUnauthorized() {
        Customer anotherCustomer = new Customer();
        anotherCustomer.setUserID(2);  // Different user ID

        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(review);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(review.getReviewID(), review, anotherCustomer);
        });
        assertEquals("Invalid update request or unauthorized customer.", exception.getMessage());
    }

    @Test
    void testGetReviewByIdSuccess() {
        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(review);

        Review foundReview = reviewService.getReviewById(review.getReviewID());

        assertNotNull(foundReview);
        assertEquals(review.getReviewID(), foundReview.getReviewID());
    }

    @Test
    void testGetReviewByIdNotFound() {
        when(reviewRepo.findByReviewID(999)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.getReviewById(999);
        });
        assertEquals("Review not found.", exception.getMessage());
    }

    @Test
    void testGetReviewByRatingSuccess() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(review);

        when(reviewRepo.findByRating(rating)).thenReturn(reviews);

        List<Review> result = reviewService.getReviewByRating(rating);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteReviewSuccess() {
        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(review);

        reviewService.deleteReview(review.getReviewID(), customer);

        verify(reviewRepo, times(1)).deleteByReviewID(review.getReviewID());
    }

    @Test
    void testDeleteReviewUnauthorized() {
        Customer anotherCustomer = new Customer();
        anotherCustomer.setUserID(2);

        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(review);

        Exception exception = assertThrows(SecurityException.class, () -> {
            reviewService.deleteReview(review.getReviewID(), anotherCustomer);
        });
        assertEquals("Unauthorized access. Only the owner can delete this review.", exception.getMessage());
    }
}
