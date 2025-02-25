package group15.gearUp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gearUp.exception.GearUpException;
import group15.gearUp.model.Customer;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Rating;
import group15.gearUp.model.Review;
import group15.gearUp.repository.CustomerRepository;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService{
    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    EquipmentRepository EquipmentRepo;

    @Autowired
    CustomerRepository customerRepo;

    /**
     * CreateReview: creates a new review record
     * @param aRating the rating given by the customer
     * @param aDescription the description of the review
     * @param aEquipment the Equipment associated with the review
     * @param aCustomer the customer creating the review
     * @return the new Review object
     * @throws GearUpException if the creation request is invalid
     */
    @Transactional
    public Review createReview(Rating aRating, String aDescription, Equipment aEquipment, Customer aCustomer) {
        if (aRating == null || aDescription == null || aDescription.trim().isEmpty() || aEquipment == null || aCustomer == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Review atttribute is required.");
        }
        Equipment equipment = EquipmentRepo.findEquipmentByEquipmentID(aEquipment.getEquipmentID());
   
        Customer customer = customerRepo.findByUserID(aCustomer.getUserID());
        Review review = new Review(aRating, aDescription, equipment, customer);
        reviewRepo.save(review);
        return review;
    }

     /**
     * UpdateReview: updates an existing review
     * @param reviewId the ID of the review to update
     * @param updatedReview the new review data to update
     * @param customer the customer for whom the review is being updated
     * @return the updated Review object
     * @throws GearUpException if update request is invalid or unauthorized
     */
    @Transactional
    public Review updateReview(int reviewId, Review updatedReview, Customer customer) {
        Review existingReview = reviewRepo.findByReviewID(reviewId);
    if (existingReview == null) {
        throw new GearUpException(HttpStatus.NOT_FOUND, "Review with the specified ID does not exist.");
    }
    if (updatedReview == null || customer == null || existingReview.getCustomer().getUserID() != customer.getUserID()) {
        throw new GearUpException(HttpStatus.FORBIDDEN, "Invalid update request or unauthorized customer.");
    }
    Rating rating = updatedReview.getRating();
    String description = updatedReview.getDescription();
    if (rating == null || description == null || description.trim().isEmpty()) {
        throw new GearUpException(HttpStatus.BAD_REQUEST, "A rating or description is required.");
    }
    existingReview.setRating(rating);
    existingReview.setDescription(description);
    return reviewRepo.save(existingReview);
    }

    /**
     * GetReviewById: retrieves a review by its ID
     * @param id the ID of the review to retrieve
     * @return the Review object with the specified ID
     * @throws GearUpException if the review with the given ID is not found
     */
    @Transactional
    public Review getReviewById(int id) {
        Review review = reviewRepo.findByReviewID(id);
        if (review == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Review not found.");
        }
        return review;
    }    

    /**
     * GetReviewByRating: retrieves reviews by a specific rating
     * @param rating the Rating to filter reviews
     * @return a list of Review objects with the specified rating
     * @throws GearUpException if no reviews with the given rating are found
     */
    @Transactional
    public List<Review> getReviewByRating(Rating rating) {
        if (rating == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "A rating is required.");
        }
        List<Review> reviews = reviewRepo.findByRating(rating);
        if (reviews.isEmpty()) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "No reviews found with the specified rating.");
        }
        return reviews;
    }

    /**
     * GetReviewByEquipment: retrieves reviews for a specific Equipment
     * @param equipment the Equipment object to filter reviews
     * @return a list of Review objects associated with the specified Equipment
     * @throws GearUpException if no reviews for the given Equipment are found
     */
    @Transactional
    public List<Review> getReviewByEquipment(Equipment equipment) {
        if (equipment == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "A Equipment is required.");
        }
        List<Review> reviews = reviewRepo.findByEquipment(equipment);
        if (reviews.isEmpty()) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "No reviews found for the specified Equipment.");
        }
        return reviews;
    }

    /**
     * GetAllReviews: retrieves all reviews in the system
     * @return a list of all Review objects
     * @throws GearUpException if no reviews are found
     */
    @Transactional
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        if (reviews.isEmpty()) {
            throw new GearUpException(HttpStatus.NOT_FOUND,"No reviews found in the system.");
        }
        return reviews;
    }

     /**
     * DeleteReview: deletes a review by review ID if the specified customer is the owner
     * @param reviewId the ID of the review to delete
     * @param customer the customer requesting the deletion
     * @throws IllegalArgumentException if the review is not found 
     * @throws GearUpException if the customer if not authorized
     */
    @Transactional
    public void deleteReview(int reviewId, Customer customer) {
        Review review = reviewRepo.findByReviewID(reviewId);
        if (review == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Review with the specified ID does not exist.");
        }  
        if (review.getCustomer().getUserID() != customer.getUserID()) {
            throw new GearUpException(HttpStatus.FORBIDDEN, "Unauthorized access. Only the owner can delete this review.");
        }
    
        reviewRepo.deleteByReviewID(reviewId);
    }    
}
