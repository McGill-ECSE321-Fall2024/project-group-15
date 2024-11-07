package main.java.group15.gameStore.RequestDto;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;

public class ReviewRequestDto {
     //Review Attributes
    private Rating rating;
    private String description;

    private Game game;
    private Customer customer;

    @SuppressWarnings("unused" )
    private ReviewRequestDto(){
    }

    public ReviewRequestDto(Rating aRating, String aDescription, Game aGame, Customer aCustomer){
        this.rating = aRating;
        this.description = aDescription;
        this.game = aGame;
        this.customer = aCustomer;
    }

    //Generated Getters and Setters
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
