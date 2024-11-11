package group15.gameStore.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.model.Game;
import group15.gameStore.model.Promotion;
import group15.gameStore.service.GameService;
import group15.gameStore.service.PromotionService;
import group15.gameStore.dto.GameRequestDto;
import group15.gameStore.dto.PromotionDto;

@RestController
public class PromotionController{

    @Autowired
    PromotionService promotionService;

    @Autowired
    GameService gameService;


    /**
     * CreatePromotion: creates a new promotion record
     * @param promotionDto the PromotionDto containing the promotion details
     * @return the created promotion
     */
    @PostMapping("/promotion")
    public ResponseEntity<PromotionDto> createPromotion(@RequestBody PromotionDto promotionDto) {
        try {
            Promotion createdPromotion = promotionService.createPromotion(
                promotionDto.getPromotionCode(),promotionDto.getDiscountPercentage(),
                promotionDto.getValidUntil(),promotionDto.getGame());

            PromotionDto responseDto = new PromotionDto(createdPromotion);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

     /**
     * UpdatePromotion: updates an existing promotion record
     * @param promotionId the ID of the promotion to update
     * @param promotionDto the PromotionDto containing updated promotion details
     * @return the updated promotion information
     */
    @PutMapping("/promotion/{promotionId}")
    public ResponseEntity<PromotionDto> updatePromotion(@PathVariable("promotionId") int promotionId,
        @RequestBody PromotionDto promotionDto) {

        try {
            Promotion existingPromotion = promotionService.getPromotionById(promotionId);

            Promotion updatedPromotion = promotionService.updatePromotion(
                promotionId,existingPromotion,promotionDto.getGame());

            return new ResponseEntity<>(new PromotionDto(updatedPromotion), HttpStatus.OK); 

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    /**
     * GetPromotionById: retrieves a promotion by ID
     * @param promotionId the ID of the promotion to retrieve
     * @return desired promotion information
     */
    @GetMapping("/promotion/{promotionId}")
    public ResponseEntity<PromotionDto> getPromotionById(@PathVariable int promotionId) {
        try {
            Promotion promotion = promotionService.getPromotionById(promotionId);
            PromotionDto responseDto = new PromotionDto(promotion);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK);      
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }

    /**
     * GetByPromotionCode: retrieves a promotion by its promotion code
     * @param promotionCode the promotion code of the promotion to retrieve
     * @return desired promotion information
     */
    @GetMapping("/promotion/code/{promotionCode}")
    public ResponseEntity<PromotionDto> getByPromotionCode(@PathVariable String promotionCode) {
        try {
            Promotion promotion = promotionService.getByPromotionCode(promotionCode);
            PromotionDto responseDto = new PromotionDto(promotion);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK); 
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }

    /**
     * GetByValidUntil: retrieves promotions valid until a specific date
     * @param validUntil the date until which promotions are valid
     * @return desired promotion information
     */
    @GetMapping("/promotion/validUntil/{validUntil}")
    public ResponseEntity<List<PromotionDto>> getByValidUntil(@PathVariable Date validUntil) {
        try {
            List<Promotion> promotions = promotionService.getByValidUntil(validUntil);
            List<PromotionDto> responseDtoList = promotions.stream()
                    .map(PromotionDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);  
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }

    /**
     * GetAllPromotion: retrieves all promotions in the system
     * @return all promotion information in the system
     */
    @GetMapping("/promotions")
    public ResponseEntity<List<PromotionDto>> getAllPromotion() {
        List<Promotion> promotions = promotionService.getAllPromotion();

        if (promotions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  
        }

        List<PromotionDto> responseDtoList = promotions.stream()
                .map(PromotionDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);  
    }

     /**
     * DeletePromotion: deletes a promotion by promotion code and game
     * @param promotionCode the promotion code of the promotion to delete
     * @param gameDto the GameRequestDto containing the game details for the promotion
     * @return HTTP status
     */
    @DeleteMapping("/promotion/{promotionCode}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String promotionCode,
        @RequestBody GameRequestDto gameDto) {
        try {
            Game game = gameService.getGameById(gameDto.getGameId());

            promotionService.deletePromotion(promotionCode, game);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        } 
        catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }
}
