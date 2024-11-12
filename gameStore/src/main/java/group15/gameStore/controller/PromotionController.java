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

import group15.gameStore.dto.GameDto;
import group15.gameStore.dto.PromotionDto;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Game;
import group15.gameStore.model.Promotion;
import group15.gameStore.service.GameService;
import group15.gameStore.service.PromotionService;

@RestController
public class PromotionController{

    @Autowired
    PromotionService promotionService;

    @Autowired
    GameService gameService;


    /**
     * CreatePromotion: creates a new promotion record
     * @param promotionDto the PromotionDto containing the promotion details
     * @return the created promotion and the HTTP status "CREATED"
     */
    @PostMapping("/promotion")
    public ResponseEntity<PromotionDto> createPromotion(@RequestBody PromotionDto promotionDto, @RequestBody GameDto gameDto) {
        try {
            Game game = gameService.getGameByID(gameDto.getGameID());
            Promotion createdPromotion = promotionService.createPromotion(
                promotionDto.getPromotionCode(),promotionDto.getDiscountPercentage(),
                promotionDto.getValidUntil(),game);

            PromotionDto responseDto = new PromotionDto(createdPromotion);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  
        } 
        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

     /**
     * UpdatePromotion: updates an existing promotion record
     * @param promotionId the ID of the promotion to update
     * @param promotionDto the PromotionDto containing updated promotion details
     * @return the updated promotion information and the HTTP status "OK"
     */
    @PutMapping("/promotion/{promotionId}")
    public ResponseEntity<PromotionDto> updatePromotion(@PathVariable int promotionId,@RequestBody PromotionDto promotionDto,
        @RequestBody GameDto gameDto) {

        try {
            Game game = gameService.getGameByID(gameDto.getGameID());
            Promotion existingPromotion = promotionService.getPromotionById(promotionDto.getPromotionID());

            Promotion updatedPromotion = promotionService.updatePromotion(
                promotionId,existingPromotion,game);

            return new ResponseEntity<>(new PromotionDto(updatedPromotion), HttpStatus.OK); 

        } catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    /**
     * GetPromotionById: retrieves a promotion by ID
     * @param promotionId the ID of the promotion to retrieve
     * @return desired promotion information and the HTTP status "OK"
     */
    @GetMapping("/promotion/{promotionId}")
    public ResponseEntity<PromotionDto> getPromotionById(@PathVariable int promotionId) {
        try {
            Promotion promotion = promotionService.getPromotionById(promotionId);
            PromotionDto responseDto = new PromotionDto(promotion);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK);      
        } 
        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }

    /**
     * GetByPromotionCode: retrieves a promotion by its promotion code
     * @param promotionCode the promotion code of the promotion to retrieve
     * @return desired promotion information and the HTTP status "OK"
     */
    @GetMapping("/promotion/code/{promotionCode}")
    public ResponseEntity<PromotionDto> getByPromotionCode(@PathVariable String promotionCode) {
        try {
            Promotion promotion = promotionService.getByPromotionCode(promotionCode);
            PromotionDto responseDto = new PromotionDto(promotion);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK); 
        } 
        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }

    /**
     * GetByValidUntil: retrieves promotions valid until a specific date
     * @param validUntil the date until which promotions are valid
     * @return desired promotion information and the HTTP status "OK"
     */
    @GetMapping("/promotion/validUntil/{validUntil}")
    public ResponseEntity<List<PromotionDto>> getByValidUntil(@PathVariable Date validUntil) {
        try {
            List<Promotion> promotions = promotionService.getByValidUntil(validUntil);
            List<PromotionDto> responseDtoList = promotions.stream()
                    .map(PromotionDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);  
            
        } catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }

    /**
     * GetAllPromotion: retrieves all promotions in the system
     * @return all promotion information in the system and the HTTP status "OK"
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
     * @return HTTP status "NO CONTENT"
     */
    @DeleteMapping("/promotion/{promotionCode}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String promotionCode,
        @RequestBody GameDto gameDto) {
        try {
            Game game = gameService.getGameByID(gameDto.getGameID());

            promotionService.deletePromotion(promotionCode, game);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } 
        catch (GameStoreException e) {
            HttpStatus status = e.getStatus() == HttpStatus.FORBIDDEN ? HttpStatus.FORBIDDEN : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }
}
