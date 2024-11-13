package group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.exception.GameStoreException;
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
     * @throws GameStoreException if the creation request is invalid
     */
    @Transactional
    public Review createReview(Rating aRating, String aDescription, Game aGame, Customer aCustomer) {
        if (aRating == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "A rating is required");
        }
        if (aDescription == null || aDescription.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Description cannot be empty.");
        }
        if (aGame == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Game must be provided.");
        }
        Game game = gameRepo.findGameByGameID(aGame.getGameID());
        if (game == null) {            
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Game must be valid and exist in the system.");
        }
        if (aCustomer == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Customer must be provided.");
        }
        Customer customer = customerRepo.findByUserID(aCustomer.getUserID());
        if (customer == null) {            
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Customer must be valid and registered.");
        }

        Review review = new Review(aRating, aDescription, game, customer);
        reviewRepo.save(review);
        return review;
    }

     /**
     * UpdateReview: updates an existing review
     * @param reviewId the ID of the review to update
     * @param updatedReview the new review data to update
     * @param customer the customer for whom the review is being updated
     * @return the updated Review object
     * @throws GameStoreException if update request is invalid or unauthorized
     */
    @Transactional
    public Review updateReview(int reviewId, Review updatedReview, Customer customer) {
        Review existingReview = reviewRepo.findByReviewID(reviewId);
    if (existingReview == null) {
        throw new GameStoreException(HttpStatus.NOT_FOUND, "Review with the specified ID does not exist.");
    }
    if (updatedReview == null || customer == null || existingReview.getCustomer().getUserID() != customer.getUserID()) {
        throw new GameStoreException(HttpStatus.FORBIDDEN, "Invalid update request or unauthorized customer.");
    }
    Rating rating = updatedReview.getRating();
    if (rating == null) {
        throw new GameStoreException(HttpStatus.BAD_REQUEST, "A rating is required.");
    }
    String description = updatedReview.getDescription();
    if (description == null || description.trim().isEmpty()) {
        throw new GameStoreException(HttpStatus.BAD_REQUEST, "Description cannot be empty.");
    }
    existingReview.setRating(rating);
    existingReview.setDescription(description);
    return reviewRepo.save(existingReview);
    }

    /**
     * GetReviewById: retrieves a review by its ID
     * @param id the ID of the review to retrieve
     * @return the Review object with the specified ID
     * @throws GameStoreException if the review with the given ID is not found
     */
    @Transactional
    public Review getReviewById(int id) {
        Review review = reviewRepo.findByReviewID(id);
        if (review == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Review not found.");
        }
        return review;
    }    

    /**
     * GetReviewByRating: retrieves reviews by a specific rating
     * @param rating the Rating to filter reviews
     * @return a list of Review objects with the specified rating
     * @throws GameStoreException if no reviews with the given rating are found
     */
    @Transactional
    public List<Review> getReviewByRating(Rating rating) {
        if (rating == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "A rating is required.");
        }
        List<Review> reviews = reviewRepo.findByRating(rating);
        if (reviews.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "No reviews found with the specified rating.");
        }
        return reviews;
    }

    /**
     * GetReviewByGame: retrieves reviews for a specific game
     * @param game the Game object to filter reviews
     * @return a list of Review objects associated with the specified game
     * @throws GameStoreException if no reviews for the given game are found
     */
    @Transactional
    public List<Review> getReviewByGame(Game game) {
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "A game is required.");
        }
        List<Review> reviews = reviewRepo.findByGame(game);
        if (reviews.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "No reviews found for the specified game.");
        }
        return reviews;
    }

    /**
     * GetAllReviews: retrieves all reviews in the system
     * @return a list of all Review objects
     * @throws GameStoreException if no reviews are found
     */
    @Transactional
    public List<Review> getAllReviews() {
        List<Review> reviews = reviewRepo.findAll();
        if (reviews.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND,"No reviews found in the system.");
        }
        return reviews;
    }

     /**
     * DeleteReview: deletes a review by review ID if the specified customer is the owner
     * @param reviewId the ID of the review to delete
     * @param customer the customer requesting the deletion
     * @throws IllegalArgumentException if the review is not found 
     * @throws GameStoreException if the customer if not authorized
     */
    @Transactional
    public void deleteReview(int reviewId, Customer customer) {
        Review review = reviewRepo.findByReviewID(reviewId);
        if (review == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Review with the specified ID does not exist.");
        }  
        if (review.getCustomer().getUserID() != customer.getUserID()) {
            throw new GameStoreException(HttpStatus.FORBIDDEN, "Unauthorized access. Only the owner can delete this review.");
        }
    
        reviewRepo.deleteByReviewID(reviewId);
    }    
}
