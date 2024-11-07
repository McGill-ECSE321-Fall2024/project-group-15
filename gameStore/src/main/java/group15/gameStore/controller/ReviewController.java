package main.java.group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;
import group15.gameStore.service.CustomerService;
import group15.gameStore.service.GameService;
import main.java.group15.gameStore.RequestDto.ReviewRequestDto;
import main.java.group15.gameStore.ResponseDto.ReviewResponseDto;
import main.java.group15.gameStore.service.ReviewService;

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
     * @param reviewDto the ReviewRequestDto containing the review details
     * @return the created review 
     */
    @PostMapping("/review")
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto reviewDto) {
        try {
            Review createdReview = reviewService.createReview(reviewDto.getRating(),
                reviewDto.getDescription(),reviewDto.getGame(),reviewDto.getCustomer());

            ReviewResponseDto responseDto = new ReviewResponseDto(createdReview);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    /**
     * UpdateReview: updates an existing review record
     * @param reviewId the ID of the review to update
     * @param reviewDto the ReviewRequestDto containing updated review details
     * @return the updated review information
     */
    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable("reviewId") int reviewId,
        @RequestBody ReviewRequestDto reviewDto) {
        try {
            Review existingReview = reviewService.getReviewById(reviewId);

            Review updatedReview = reviewService.updateReview(reviewId,existingReview,
                reviewDto.getCustomer());

            return new ResponseEntity<>(new ReviewResponseDto(updatedReview), HttpStatus.OK);  

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    /**
     * GetReviewById: retrieves a review by ID
     * @param reviewId the ID of the review to retrieve
     * @return desired review information
     */
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable int reviewId) {
        try {
            Review review = reviewService.getReviewById(reviewId);
            ReviewResponseDto responseDto = new ReviewResponseDto(review);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK); 
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }

    /**
     * GetReviewByRating: retrieves reviews by a specific rating
     * @param rating the rating of the reviews to retrieve
     * @return desired review information
     */
    @GetMapping("/review/rating/{rating}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewByRating(@PathVariable Rating rating) {
        try {
            List<Review> reviews = reviewService.getReviewByRating(rating);
            List<ReviewResponseDto> responseDtoList = reviews.stream()
                    .map(ReviewResponseDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);  
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }



}
