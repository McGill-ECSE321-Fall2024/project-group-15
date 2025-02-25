 package group15.gearUp.repository;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertNotNull;

 import java.sql.Date;

 import org.junit.jupiter.api.AfterEach;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;

 import group15.gearUp.model.Equipment;
 import group15.gearUp.model.Manager;
 import group15.gearUp.model.Promotion;

 @SpringBootTest
 public class PromotionRepositoryTest {
     @Autowired
     private PromotionRepository repo;

     @Autowired
     private ManagerRepository managerRepo;

     private Manager testManager = new Manager("ChadTheManager", "00password", "manager@mail.mcgill.ca", true, true);

     @BeforeEach
     public void setUp() {
         // Clear the database before each test
         repo.deleteAll();
         managerRepo.deleteAll();

         testManager = managerRepo.save(testManager);

     }
     @AfterEach
     public void clearDatabase() {
         repo.deleteAll();
         managerRepo.deleteAll();
     }

     @Test
     public void testCreateSaveAndReadPromotion() {
         //Equipment attributes for equipment to use for Promotion
         String title = "Minecraft";
         String description = "The ultimate sandbox equipment.";
         double price = 49.99;
         int stock = 100;
         String image = "https://minecraft.com";
         boolean isApproved = true;

         //Create Promotion
         String promotionCode = "HOLIDAYS";
         double discountPercentage = 0.40;
         Date validUntil = Date.valueOf("2025-12-31");
         Equipment equipment = new Equipment(title, description, price, stock, image, isApproved, testManager);

         Promotion promotion = new Promotion(promotionCode, discountPercentage, validUntil, equipment);

         // Save in the database
         promotion = repo.save(promotion);
         int promotionId = promotion.getPromotionID();

         // Read back from the database
         Promotion promotionFromDb = repo.findByPromotionID(promotionId);

         // Assertions
         assertNotNull(promotionFromDb);
         assertEquals(promotionId, promotionFromDb.getPromotionID());
         assertEquals(promotionCode, promotionFromDb.getPromotionCode());
         assertEquals(discountPercentage, promotionFromDb.getDiscountPercentage());
         assertEquals(validUntil, promotionFromDb.getValidUntil());
         assertEquals(equipment.getEquipmentID(), promotionFromDb.getEquipment().getEquipmentID());
     }
 }
