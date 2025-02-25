package group15.gearUp.dto;

import java.sql.Date;

import group15.gearUp.model.Promotion;

public class PromotionDto {
    //Promotion attributes
    private int promotionID;
    private String promotionCode;
    private double discountPercentage;
    private Date validUntil;

    private int EquipmentId;

    @SuppressWarnings("unused")
    private PromotionDto(){
    }
    
    //Constructor
    public PromotionDto(Promotion promotionDto){
        this.promotionID = promotionDto.getPromotionID();
        this.promotionCode = promotionDto.getPromotionCode();
        this.discountPercentage = promotionDto.getDiscountPercentage();
        this.validUntil = promotionDto.getValidUntil();
        this.EquipmentId = promotionDto.getEquipment().getEquipmentID();
    }

    public PromotionDto(String aPromotionCode, double aDiscountPercentage, Date aValidUntil, int EquipmentId){
        this.promotionCode = aPromotionCode;
        this.discountPercentage = aDiscountPercentage;
        this.validUntil = aValidUntil;
        this.EquipmentId = EquipmentId;
    }

    //Generated Getters and Setters
    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }

    public int getEquipmentId() {
        return EquipmentId;
    }

    public void setEquipmentId(int EquipmentId) {
        this.EquipmentId = EquipmentId;
    }
}
