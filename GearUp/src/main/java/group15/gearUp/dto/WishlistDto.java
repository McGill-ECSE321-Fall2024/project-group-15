package group15.gearUp.dto;

import java.util.List;
import java.util.stream.Collectors;

import group15.gearUp.model.Wishlist;



public class WishlistDto {
    
    //Wishlist attributes
    private int wishListId;
    private String wishListName;
    
    private List<EquipmentDto> Equipments;
    private CustomerDto customer;

    //Constructor
    public WishlistDto(Wishlist wishlist) {
        this.wishListId = wishlist.getWishListId();
        this.wishListName = wishlist.getWishListName();
        this.Equipments = wishlist.getEquipments().stream().map(Equipment -> new EquipmentDto(Equipment)).collect(Collectors.toList());
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

    public List<EquipmentDto> getEquipments() {
        return Equipments;
    }

    public void setEquipments(List<EquipmentDto> Equipments) {
        this.Equipments = Equipments;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }
    
}
