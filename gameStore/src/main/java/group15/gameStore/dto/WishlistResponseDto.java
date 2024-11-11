package group15.gameStore.dto;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Wishlist;

public class WishlistResponseDto {
    
    private int wishlistId;

    private Customer customer;

    public WishlistResponseDto(Wishlist wishlist) {
        this.wishlistId = wishlist.getWishListId();
        this.customer = wishlist.getCustomer();
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public Customer getCustomer() {
        return customer;
    }

}
