package group15.gearUp.service;

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

import group15.gearUp.exception.GearUpException;
import group15.gearUp.model.Customer;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Rating;
import group15.gearUp.model.Review;
import group15.gearUp.repository.CustomerRepository;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepo;

    @Mock
    private EquipmentRepository equipmentRepo;

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private ReviewService reviewService;

    private Rating rating;
    private Equipment equipment;
    public Customer customer;
    private Review review;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rating = Rating.FIVE_STAR;  // Assuming Rating is an enum
        equipment = new Equipment();
        equipment.setEquipmentID(1);
        customer = new Customer();
        customer.setUserID(1);
        review = new Review(rating, "Great equipment!", equipment, customer);
        review.setReviewID(1);
    }

    @Test
    void testCreateReviewSuccess() {
        when(equipmentRepo.findEquipmentByEquipmentID(equipment.getEquipmentID())).thenReturn(equipment);
        when(customerRepo.findByUserID(customer.getUserID())).thenReturn(customer);
        when(reviewRepo.save(any(Review.class))).thenReturn(review);

        Review createdReview = reviewService.createReview(rating, "Great equipment!", equipment, customer);

        assertNotNull(createdReview);
        assertEquals(rating, createdReview.getRating());
        assertEquals("Great equipment!", createdReview.getDescription());
        assertEquals(equipment, createdReview.getEquipment());
        assertEquals(customer, createdReview.getCustomer());
    }

    @Test
    void testCreateReviewInvalidRating() {
        Exception exception = assertThrows(GearUpException.class, () -> {
            reviewService.createReview(null, "Good equipment", equipment, customer);
        });
        assertEquals("Review atttribute is required.", exception.getMessage());
    }

    @Test
    void testCreateReviewInvalidDescription() {
        Exception exception = assertThrows(GearUpException.class, () -> {
            reviewService.createReview(rating, "", equipment, customer);
        });
        assertEquals("Review atttribute is required.", exception.getMessage());
    }


    @Test
    void testUpdateReviewSuccess() {
        Review updatedReview = new Review(rating, "Updated review", equipment, customer);
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

        Exception exception = assertThrows(GearUpException.class, () -> {
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
        GearUpException thrown = assertThrows(GearUpException.class, () -> {
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

        Exception exception = assertThrows(GearUpException.class, () -> {
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
        GearUpException thrown = assertThrows(GearUpException.class, () -> {
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

        GearUpException exception = assertThrows(GearUpException.class, () -> {
            reviewService.deleteReview(review.getReviewID(), anotherCustomer);
        });
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        assertEquals("Unauthorized access. Only the owner can delete this review.", exception.getMessage());
    }

    @Test
    void testDeleteReviewNotFound() {
        when(reviewRepo.findByReviewID(review.getReviewID())).thenReturn(null);

        GearUpException exception = assertThrows(GearUpException.class, () -> {
            reviewService.deleteReview(review.getReviewID(), customer);
        });
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Review with the specified ID does not exist.", exception.getMessage());

        verify(reviewRepo, never()).deleteByReviewID(review.getReviewID());
    }
}
