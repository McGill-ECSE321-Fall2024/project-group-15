package group15.gameStore.dto;

import java.sql.Date;

import group15.gameStore.model.Game;
import group15.gameStore.model.Promotion;

public class PromotionDto {
    //Promotion attributes
    private int promotionID;
    private String promotionCode;
    private double discountPercentage;
    private Date validUntil;

    private GameDto game;

    @SuppressWarnings("unused")
    private PromotionDto(){
    }

    public PromotionDto(Promotion promotionDto){
        this.promotionID = promotionDto.getPromotionID();
        this.promotionCode = promotionDto.getPromotionCode();
        this.discountPercentage = promotionDto.getDiscountPercentage();
        this.validUntil = promotionDto.getValidUntil();
        this.game = new GameDto(promotionDto.getGame());
    }

    public PromotionDto(String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Game aGame){
        this.promotionCode = aPromotionCode;
        this.discountPercentage = aDiscountPercentage;
        this.validUntil = aValidUntil;
        this.game = new GameDto(aGame);
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

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }
}
