package main.java.group15.gameStore.RequestDto;

import java.sql.Date;

import group15.gameStore.model.Game;

public class PromotionRequestDto {
    //Promotion attributes
    private String promotionCode;
    private double discountPercentage;
    private Date validUntil;

    private Game game;

    @SuppressWarnings("unused")
    private PromotionRequestDto(){
    }

    public PromotionRequestDto(String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Game aGame){
        this.promotionCode = aPromotionCode;
        this.discountPercentage = aDiscountPercentage;
        this.validUntil = aValidUntil;
        this.game = aGame;
    }

    //Generated Getters and Setters
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
