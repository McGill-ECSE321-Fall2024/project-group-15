package group15.gearUp.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gearUp.dto.PromotionDto;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Promotion;
import group15.gearUp.service.EquipmentService;
import group15.gearUp.service.PromotionService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
@RestController
public class PromotionController{

    @Autowired
    PromotionService promotionService;

    @Autowired
    EquipmentService equipmentService;


    /**
     * CreatePromotion: creates a new promotion record
     * @param promotionDto the PromotionDto containing the promotion details
     * @return the created promotion and the HTTP status "CREATED"
     */
    @PostMapping("/promotion")
    public ResponseEntity<PromotionDto> createPromotion(@RequestBody PromotionDto promotionDto) {
        Equipment equipment = equipmentService.getEquipmentByID(promotionDto.getEquipmentId());
        Promotion createdPromotion = promotionService.createPromotion(
            promotionDto.getPromotionCode(),promotionDto.getDiscountPercentage(),
            promotionDto.getValidUntil(), equipment);
        PromotionDto responseDto = new PromotionDto(createdPromotion);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  
    }

     /**
     * UpdatePromotion: updates an existing promotion record
     * @param promotionId the ID of the promotion to update
     * @param promotionDto the PromotionDto containing updated promotion details
     * @return the updated promotion information and the HTTP status "OK"
     */
    @PutMapping("/promotion/{promotionId}")
    public ResponseEntity<PromotionDto> updatePromotion(@PathVariable int promotionId,@RequestBody PromotionDto promotionDto) {
        Equipment equipment = equipmentService.getEquipmentByID(promotionDto.getEquipmentId());
        Promotion existingPromotion = promotionService.getPromotionById(promotionDto.getPromotionID());
        Promotion updatedPromotion = promotionService.updatePromotion(promotionId,existingPromotion, equipment);
        return new ResponseEntity<>(new PromotionDto(updatedPromotion), HttpStatus.OK); 
    }

    /**
     * GetPromotionByEquipmentId: retrieves a promotion by Equipment ID
     * @param EquipmentId the ID of the Equipment to retrieve promotions for
     * @return desired promotion information and the HTTP status "OK"
     */
    @GetMapping("/promotion/Equipment/{EquipmentId}")
    public ResponseEntity<PromotionDto> getPromotionByEquipmentId(@PathVariable int EquipmentId) {
        Promotion promotions = promotionService.getPromotionByEquipmentId(EquipmentId);
        PromotionDto responseDto = new PromotionDto(promotions);
        return new ResponseEntity<>(responseDto, HttpStatus.OK); 
    }
    
    /**
     * GetPromotionById: retrieves a promotion by ID
     * @param promotionId the ID of the promotion to retrieve
     * @return desired promotion information and the HTTP status "OK"
     */
    @GetMapping("/promotion/id/{promotionId}")
    public ResponseEntity<PromotionDto> getPromotionById(@PathVariable int promotionId) {
            Promotion promotion = promotionService.getPromotionById(promotionId);
            PromotionDto responseDto = new PromotionDto(promotion);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);      
    }

    /**
     * GetByPromotionCode: retrieves a promotion by its promotion code
     * @param promotionCode the promotion code of the promotion to retrieve
     * @return desired promotion information and the HTTP status "OK"
     */
    @GetMapping("/promotion/code/{promotionCode}")
    public ResponseEntity<PromotionDto> getByPromotionCode(@PathVariable String promotionCode) {
        Promotion promotion = promotionService.getByPromotionCode(promotionCode);
        PromotionDto responseDto = new PromotionDto(promotion);
        return new ResponseEntity<>(responseDto, HttpStatus.OK); 
    }

    /**
     * GetByValidUntil: retrieves promotions valid until a specific date
     * @param validUntil the date until which promotions are valid
     * @return desired promotion information and the HTTP status "OK"
     */
    @GetMapping("/promotion/validUntil/{validUntil}")
    public ResponseEntity<List<PromotionDto>> getByValidUntil(@PathVariable Date validUntil) {
        List<Promotion> promotions = promotionService.getByValidUntil(validUntil);
        List<PromotionDto> responseDtoList = promotions.stream().map(PromotionDto::new)
            .collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);     
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
     * DeletePromotion: deletes a promotion by promotion code and Equipment
     * @param promotionCode the promotion code of the promotion to delete
     * @param EquipmentDto the EquipmentRequestDto containing the Equipment details for the promotion
     * @return HTTP status "NO CONTENT"
     */
    @DeleteMapping("/promotion/{promotionCode}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String promotionCode) {
        
        Promotion promotion = promotionService.getByPromotionCode(promotionCode);
        Equipment equipment = equipmentService.getEquipmentByID(promotion.getEquipment().getEquipmentID());
        promotionService.deletePromotion(promotionCode, equipment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
