package group15.gameStore.dto;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;

public class ReviewDto {
    //Review Attributes
    private int reviewID;
    private Rating rating;
    private String description;

    private int gameId;
    private int customerId;

    @SuppressWarnings("unused" )
    private ReviewDto(){
    }

    //Constructor
    public ReviewDto(Review review){
        this.reviewID = review.getReviewID();
        this.rating = review.getRating();
        this.description = review.getDescription();
        this.gameId = review.getGame().getGameID();
        this.customerId = review.getCustomer().getUserID();
    }

    public ReviewDto(Rating aRating, String aDescription, int aGameId, int aCustomerId){
        this.rating = aRating;
        this.description = aDescription;
        this.gameId = aGameId;
        this.customerId = aCustomerId;
    }

    //Generated Getters and Setters
    public int getReviewID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
