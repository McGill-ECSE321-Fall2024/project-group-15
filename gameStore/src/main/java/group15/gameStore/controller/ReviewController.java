package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.CustomerDto;
import group15.gameStore.dto.GameDto;
import group15.gameStore.dto.ReviewDto;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;
import group15.gameStore.service.CustomerService;
import group15.gameStore.service.GameService;
import group15.gameStore.service.ReviewService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
@RestController
public class ReviewController{

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private GameService gameService;
    
    @Autowired
    private CustomerService customerService;


    /**
     * CreateReview: creates a new review record
     * @param reviewDto the ReviewDto containing the review details
     * @return the created review and the HTTP status "CREATED"
     */
    @PostMapping("/review")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        Game game = gameService.getGameByID(reviewDto.getGameId());
        Customer customer = customerService.getCustomerByID(reviewDto.getCustomerId());
        Review createdReview = reviewService.createReview(reviewDto.getRating(),
            reviewDto.getDescription(),game,customer);
        ReviewDto responseDto = new ReviewDto(createdReview);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  
    }

    /**
     * UpdateReview: updates an existing review record
     * @param reviewId the ID of the review to update
     * @param reviewDto the ReviewDto containing updated review details
     * @return the updated review information and the HTTP status "OK"
     */
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable int reviewId,@RequestBody ReviewDto reviewDto) {
        Customer customer = customerService.getCustomerByID(reviewDto.getCustomerId());
        Review existingReview = reviewService.getReviewById(reviewDto.getReviewID());
        Review updatedReview = reviewService.updateReview(reviewId,existingReview,customer);
        return new ResponseEntity<>(new ReviewDto(updatedReview), HttpStatus.OK);  
    }

    /**
     * GetReviewById: retrieves a review by ID
     * @param reviewId the ID of the review to retrieve
     * @return desired review information and the HTTP status "OK"
     */
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable int reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        ReviewDto responseDto = new ReviewDto(review);    
        return new ResponseEntity<>(responseDto, HttpStatus.OK); 
    }

    /**
     * GetReviewByRating: retrieves reviews by a specific rating
     * @param rating the rating of the reviews to retrieve
     * @return desired review information and the HTTP status "OK"
     */
    @GetMapping("/review/rating/{rating}")
    public ResponseEntity<List<ReviewDto>> getReviewByRating(@PathVariable Rating rating) {
        List<Review> reviews = reviewService.getReviewByRating(rating);
        List<ReviewDto> responseDtoList = reviews.stream().map(ReviewDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);     
    }

    /**
     * GetReviewByGame: retrieves reviews for a specific game
     * @param gameId the ID of the game for which to retrieve reviews
     * @return desired review information and the HTTP status "OK"
     */
    @GetMapping("/review/game/{gameId}")
    public ResponseEntity<List<ReviewDto>> getReviewByGame(@PathVariable Game game) {
        List<Review> reviews = reviewService.getReviewByGame(game);
        List<ReviewDto> responseDtoList = reviews.stream().map(ReviewDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);  
    }

    /**
     * GetAllReviews: retrieves all reviews in the system
     * @return desired review information and the HTTP status "OK"
     */
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();

        if (reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
        }

        List<ReviewDto> responseDtoList = reviews.stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);  
    }

    /**
     * DeleteReview: deletes a review by review ID if the specified customer is the owner
     * @param reviewId the ID of the review to delete
     * @param customerDto the CustomerRequestDto containing the customer details for authorization
     * @return HTTP status "NO CONTENT"
     */
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable int reviewId,
        @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserID());
        reviewService.deleteReview(reviewId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
}
