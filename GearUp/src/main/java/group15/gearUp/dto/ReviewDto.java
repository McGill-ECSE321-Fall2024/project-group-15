package group15.gearUp.dto;

import group15.gearUp.model.Rating;
import group15.gearUp.model.Review;

public class ReviewDto {
    //Review Attributes
    private int reviewID;
    private Rating rating;
    private String description;

    private int EquipmentId;
    private int customerId;

    @SuppressWarnings("unused" )
    private ReviewDto(){
    }

    //Constructor
    public ReviewDto(Review review){
        this.reviewID = review.getReviewID();
        this.rating = review.getRating();
        this.description = review.getDescription();
        this.EquipmentId = review.getEquipment().getEquipmentID();
        this.customerId = review.getCustomer().getUserID();
    }

    public ReviewDto(Rating aRating, String aDescription, int aEquipmentId, int aCustomerId){
        this.rating = aRating;
        this.description = aDescription;
        this.EquipmentId = aEquipmentId;
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

    public int getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(int EquipmentId) {
        this.EquipmentId = EquipmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
