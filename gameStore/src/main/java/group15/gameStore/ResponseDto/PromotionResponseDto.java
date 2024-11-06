package main.java.group15.gameStore.ResponseDto;

import java.sql.Date;

import group15.gameStore.model.Game;
import group15.gameStore.model.Promotion;

public class PromotionResponseDto {
    //Promotion attributes
    private int promotionID;
    private String promotionCode;
    private double discountPercentage;
    private Date validUntil;

    private Game game;

    @SuppressWarnings("unused")
    private PromotionResponseDto(){
    }

    public PromotionResponseDto(Promotion promotionDto){
        this.promotionID = promotionDto.getPromotionID();
        this.promotionCode = promotionDto.getPromotionCode();
        this.discountPercentage = promotionDto.getDiscountPercentage();
        this.validUntil = promotionDto.getValidUntil();
        this.game = promotionDto.getGame();
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
