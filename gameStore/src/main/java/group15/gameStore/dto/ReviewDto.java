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

    private Game game;
    private Customer customer;

    @SuppressWarnings("unused" )
    private ReviewDto(){
    }

    public ReviewDto(Review reviewDto){
        this.reviewID = reviewDto.getReviewID();
        this.rating = reviewDto.getRating();
        this.description = reviewDto.getDescription();
        this.game = reviewDto.getGame();
        this.customer = reviewDto.getCustomer();
    }

    public ReviewDto(Rating aRating, String aDescription, Game aGame, Customer aCustomer){
        this.rating = aRating;
        this.description = aDescription;
        this.game = aGame;
        this.customer = aCustomer;
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
