package group15.gameStore.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Wishlist;



public class WishlistDto {
    
    //Wishlist attributes
    private int wishListId;
    private String wishListName;
    
    private List<GameDto> games;
    private CustomerDto customer;

    //Constructor
    public WishlistDto(Wishlist wishlist) {
        this.wishListId = wishlist.getWishListId();
        this.wishListName = wishlist.getWishListName();
        this.games = wishlist.getGames().stream().map(game -> new GameDto(game)).collect(Collectors.toList());
        this.customer = new CustomerDto(wishlist.getCustomer());
    }

    @SuppressWarnings("unused")
    public WishlistDto(){
    }
    
    //Getters and Setters
    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public String getWishListName() {
        return wishListName;
    }

    public void setWishListName(String wishListName) {
        this.wishListName = wishListName;
    }

    public List<GameDto> getGames() {
        return games;
    }

    public void setGames(List<GameDto> games) {
        this.games = games;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
    
}
