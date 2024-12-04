package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
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
        Exception exception = assertThrows(GameStoreException.class, () -> {
            reviewService.createReview(null, "Good game", game, customer);
        });
        assertEquals("Review atttribute is required.", exception.getMessage());
    }

    @Test
    void testCreateReviewInvalidDescription() {
        Exception exception = assertThrows(GameStoreException.class, () -> {
            reviewService.createReview(rating, "", game, customer);
        });
        assertEquals("Review atttribute is required.", exception.getMessage());
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

        Exception exception = assertThrows(GameStoreException.class, () -> {
            reviewService.updateReview(review.getReviewID(), review, anotherCustomer);
        });
        assertEquals("Invalid update request or unauthorized customer.", exception.getMessage());
    }

    @Test
    public void testUpdateReview_BadRequest() {
        // Arrange
        Review updatedReview = new Review();
        updatedReview.setRating(Rating.FIVE_STAR);
        updatedReview.setDescription("");  // Empty description

        when(reviewRepo.findByReviewID(1)).thenReturn(review);

        // Act 
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            reviewService.updateReview(1, updatedReview, customer);
        });

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        assertEquals("A rating or description is required.", thrown.getMessage());
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

        Exception exception = assertThrows(GameStoreException.class, () -> {
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
    public void testGetReviewByRating_NotFound() {
        // Arrange
        Rating rating = Rating.FIVE_STAR;  
        when(reviewRepo.findByRating(rating)).thenReturn(Collections.emptyList()); 

        // Act 
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            reviewService.getReviewByRating(rating);
        });

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
        assertEquals("No reviews found with the specified rating.", thrown.getMessage());
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
        anotherCustomer.setUserID(5);

        review.setCustomer(customer);
        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(review);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            reviewService.deleteReview(review.getReviewID(), anotherCustomer);
        });
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        assertEquals("Unauthorized access. Only the owner can delete this review.", exception.getMessage());
    }

    @Test
    void testDeleteReviewNotFound() {
        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            reviewService.deleteReview(review.getReviewID(), customer);
        });
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Review with the specified ID does not exist.", exception.getMessage());

        verify(reviewRepo, never()).deleteByReviewID(review.getReviewID());
    }
}
