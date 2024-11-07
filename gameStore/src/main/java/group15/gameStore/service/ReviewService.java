package main.java.group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ReviewRepository;
import jakarta.transaction.Transactional;

@Service
public class ReviewService{
    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    GameRepository gameRepo;

    @Autowired
    CustomerRepository customerRepo;

    /**
     * CreateReview: creates a new review record
     * @param aRating the rating given by the customer
     * @param aDescription the description of the review
     * @param aGame the game associated with the review
     * @param aCustomer the customer creating the review
     * @return the new Review object
     * @throws IllegalArgumentException if the creation request is invalid
     */
    @Transactional
    public Review createReview(Rating aRating, String aDescription, Game aGame, Customer aCustomer) {
        if (aRating == null) {
            throw new IllegalArgumentException("A Rating is required.");
        }
        if (aDescription == null || aDescription.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        if (aGame == null || gameRepo.findGameByGameID(aGame.getId()).isEmpty()) {
            throw new IllegalArgumentException("Game must be valid and exist in the system.");
        }
        if (aCustomer == null || customerRepo.findByUserID(aCustomer.getId()).isEmpty()) {
            throw new IllegalArgumentException("Customer must be valid and registered.");
        }

        Review review = new Review(aRating, aDescription, aGame, aCustomer);
        reviewRepo.save(review);
        return review;
    }

     /**
     * UpdateReview: updates an existing review
     * @param reviewId the ID of the review to update
     * @param updatedReview the new review data to update
     * @param customer the customer for whom the review is being updated
     * @return the updated Review object
     * @throws IllegalArgumentException if update request is invalid or unauthorized
     */
    @Transactional
    public Review updateReview(int reviewId, Review updatedReview, Customer customer) {
        Review existingReview = reviewRepo.findByReviewID(reviewId).orElse(null);
        if (existingReview == null) {
            throw new IllegalArgumentException("Review with the specified ID does not exist.");
        }
        if (updatedReview == null || customer == null || !existingReview.getCustomer().getId().equals(customer.getId())) {
            throw new IllegalArgumentException("Invalid update request or unauthorized customer.");
        }
        Rating rating = updatedReview.getRating();
        if (rating == null) {
            throw new IllegalArgumentException("A rating is required.");
        }
        String description = updatedReview.getDescription();
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }
        existingReview.setRating(rating);
        existingReview.setDescription(description);
        return reviewRepo.save(existingReview);
    }

    /**
     * GetReviewById: retrieves a review by its ID
     * @param id the ID of the review to retrieve
     * @return the Review object with the specified ID
     * @throws IllegalArgumentException if the review with the given ID is not found
     */
    @Transactional
    public Review getReviewById(int id) {
        Review review = reviewRepo.findByReviewID(id).orElse(null);
        if (review == null) {
            throw new IllegalArgumentException("Review not found.");
        }
        return review;
    }    

    /**
     * GetReviewByRating: retrieves reviews by a specific rating
     * @param rating the Rating to filter reviews
     * @return a list of Review objects with the specified rating
     * @throws IllegalArgumentException if no reviews with the given rating are found
     */
    @Transactional
    public List<Review> getReviewByRating(Rating rating) {
        if (rating == null) {
            throw new IllegalArgumentException("A rating is required.");
        }
        List<Review> reviews = reviewRepo.findByRating(rating);
        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("No reviews found with the specified rating.");
        }
        return reviews;
    }

    /**
     * GetReviewByGame: retrieves reviews for a specific game
     * @param game the Game object to filter reviews
     * @return a list of Review objects associated with the specified game
     * @throws IllegalArgumentException if no reviews for the given game are found
     */
    @Transactional
    public List<Review> getReviewByGame(Game game) {
        if (game == null) {
            throw new IllegalArgumentException("A game is required.");
        }
        List<Review> reviews = reviewRepo.findByGame(game);
        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("No reviews found for the specified game.");
        }
        return reviews;
    }

    /**
     * GetAllReviews: retrieves all reviews in the system
     * @return a list of all Review objects
     * @throws IllegalArgumentException if no reviews are found
     */
    @Transactional
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("No reviews found in the system.");
        }
        return reviews;
    }

     /**
     * DeleteReview: deletes a review by review ID if the specified customer is the owner
     * @param reviewId the ID of the review to delete
     * @param customer the customer requesting the deletion
     * @throws IllegalArgumentException if the review is not found or if the customer is unauthorized
     */
    @Transactional
    public void deleteReview(int reviewId, Customer customer) {
        Review review = reviewRepo.findByReviewID(reviewId).orElse(null);
        if (review == null) {
            throw new IllegalArgumentException("Review with the specified ID does not exist.");
        }
        if (!review.getCustomer().getId().equals(customer.getId())) {
            throw new UnauthorizedAccessException("Unauthorized access. Only the owner can delete this review.");
        }

        reviewRepo.deleteByReviewID(reviewId);
    }    
}
