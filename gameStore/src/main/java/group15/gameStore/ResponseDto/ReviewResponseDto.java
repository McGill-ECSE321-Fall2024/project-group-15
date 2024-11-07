package main.java.group15.gameStore.ResponseDto;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import group15.gameStore.model.Review;

public class ReviewResponseDto {
    //Review Attributes
    private int reviewID;
    private Rating rating;
    private String description;

    private Game game;
    private Customer customer;

    @SuppressWarnings("unused" )
    private ReviewResponseDto(){
    }

    public ReviewResponseDto(Review reviewDto){
        this.reviewID = reviewDto.getReviewID();
        this.rating = reviewDto.getRating();
        this.description = reviewDto.getDescription();
        this.game = reviewDto.getGame();
        this.customer = reviewDto.getCustomer();
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
