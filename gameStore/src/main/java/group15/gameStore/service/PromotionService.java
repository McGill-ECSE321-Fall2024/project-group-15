package group15.gameStore.service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Game;
import group15.gameStore.model.Promotion;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.PromotionRepository;

@Service
public class PromotionService {

    @Autowired
    PromotionRepository promotionRepo;

    @Autowired
    GameRepository gameRepo;

    /**
     * CreatePromotion: creates a new promotion record
     * @param aPromotionCode the unique promotion code for the discount
     * @param aDiscountPercentage the percentage discount for the promotion
     * @param aValidUntil the expiration date of the promotion
     * @param aGame the game to which the promotion applies
     * @return the new Promotion
     * @throws GameStoreException if creation request invalid or the game is not found
     */
    @Transactional
    public Promotion createPromotion(String aPromotionCode, double aDiscountPercentage, Date aValidUntil, Game aGame) {
        if (aPromotionCode == null || aPromotionCode.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Promotion code cannot be null or empty.");
        }
        if (aDiscountPercentage <= 0 || aDiscountPercentage > 100) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Discount percentage must be between 0 and 100.");
        }
        if (aValidUntil == null || aValidUntil.before(Date.from(Instant.now()))) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Valid until date must be a future date.");
        }
        if (aGame == null || gameRepo.findGameByGameID(aGame.getGameID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Game must be valid and exist in the system.");
        }
    
        Promotion promotion = new Promotion(aPromotionCode, aDiscountPercentage, aValidUntil, aGame);
        promotionRepo.save(promotion);
        return promotion;
    }

    /**
     * UpdatePromotion: updates an existing promotion record
     * @param promotionId the ID of the promotion to update
     * @param updatedPromotion the new promotion details to update to
     * @param game the game associated with the promotion
     * @return the updated Promotion object
     * @throws GameStoreException if update request is invalid
     */
    @Transactional
    public Promotion updatePromotion(int promotionId, Promotion updatedPromotion, Game game) {
        Promotion existingPromotion = promotionRepo.findById(promotionId);
        if (existingPromotion == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Promotion with the specified ID does not exist.");
        }
        if (updatedPromotion == null || game == null || existingPromotion.getGame().getGameID() != game.getGameID()) {
            throw new GameStoreException(HttpStatus.FORBIDDEN, "Invalid update request or unauthorized game.");
        }
        String promotionCode = updatedPromotion.getPromotionCode();
        if (promotionCode == null || promotionCode.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Promotion code cannot be null or empty.");
        }
        double discountPercentage = updatedPromotion.getDiscountPercentage();
        if (discountPercentage <= 0 || discountPercentage > 100) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Discount percentage must be between 0 and 100.");
        }
        Date validUntil = updatedPromotion.getValidUntil();
        if (validUntil == null || validUntil.before(Date.from(Instant.now()))) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Valid until date must be a future date.");
        }
        existingPromotion.setPromotionCode(promotionCode);
        existingPromotion.setDiscountPercentage(discountPercentage);
        existingPromotion.setValidUntil(validUntil);

        return promotionRepo.save(existingPromotion);
    }

    /**
     * GetPromotionById: retrieves a promotion by its ID
     * @param id the ID of the promotion to retrieve
     * @return the Promotion object with the specified ID
     * @throws GameStoreException if the promotion with the given ID is not found
     */
    @Transactional
    public Promotion getPromotionById(int id) {
        Promotion promotion = promotionRepo.findById(id);
        if (promotion == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Promotion not found.");
        }
        return promotion;
    }

    /**
     * GetByPromotionCode: retrieves a promotion by its promotion code
     * @param promotionCode the unique promotion code of the promotion to retrieve
     * @return the Promotion object with the specified promotion code
     * @throws GameStoreException if the promotion code is invalid or the promotion is not found
     */
    @Transactional
    public Promotion getByPromotionCode(String promotionCode) {
        if (promotionCode == null || promotionCode.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid promotion code. Code cannot be null or empty.");
        }
        
        Promotion promotion = promotionRepo.findByPromotionCode(promotionCode);
        if (promotion == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Promotion with the specified code does not exist.");
        }
        
        return promotion;
    }

    /**
     * GetByValidUntil: retrieves promotions that are valid until the specified date
     * @param validUntil the expiration date to filter promotions
     * @return a list of Promotion objects valid until the specified date
     * @throws GameStoreException if the date is null
     */
    @Transactional
    public List<Promotion> getByValidUntil(Date validUntil) {
        if (validUntil == null || validUntil.before(Date.from(Instant.now()))) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Valid until date must be a future date.");
        }
        List<Promotion> promotions = promotionRepo.findByValidUntilAfter(validUntil);
        if (promotions.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "No promotions found valid until the specified date.");
        }

        return promotions;
    }

    /**
     * GetAllPromotion: retrieves all promotions in the system
     * @return a list of all Promotion objects
     * @throws GameStoreException if no promotions are found
     */
    @Transactional
    public List<Promotion> getAllPromotion() {
        List<Promotion> promotions = promotionRepo.findAll();
        if (promotions.isEmpty()) {
            throw new GameStoreException(HttpStatus.NO_CONTENT, "No promotions found in the system.");
        }
        return promotions;
    }

     /**
     * DeletePromotion: deletes a promotion by promotion code and game
     * @param promotionCode the promotion code of the promotion to delete
     * @param game the game associated with the promotion
     * @throws GameStoreException if the promotion is not found or unauthorized game
     */
    @Transactional
    public void deletePromotion(String promotionCode, Game game) {
        Promotion promotion = promotionRepo.findByPromotionCode(promotionCode);
        if (promotion == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Promotion with the specified code does not exist.");
        }
        if (promotion.getGame().getGameID() != game.getGameID()) {
            throw new GameStoreException(HttpStatus.FORBIDDEN, "Unauthorized access. Only the associated game can delete this promotion.");
        }

        promotionRepo.deleteByPromotionCode(promotionCode);
    }
}
