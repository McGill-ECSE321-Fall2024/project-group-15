package group15.gameStore.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import group15.gameStore.service.PromotionService;
import group15.gameStore.model.Promotion;
import group15.gameStore.repository.PromotionRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.model.Game;
import org.springframework.http.MediaType;
import java.sql.Date;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PromotionServiceIntegrationTest {

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private GameRepository gameRepository;

    private MockMvc mockMvc;

    private Game game;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(promotionService).build();
        
        // Set up a sample game entity
        game = new Game();
        game.setGameID(1);
        game.setTitle("Test Game");
        gameRepository.save(game); // Save game to the database before tests

        // Clean up the promotions to avoid test interference
        promotionRepository.deleteAll();
    }

    @Test
    void testCreatePromotionIntegration() throws Exception {
        // Arrange
        String promotionCode = "PROMO2024";
        double discountPercentage = 20.0;
        Date validUntil = Date.valueOf("2024-12-31");

        // Act & Assert (performing the request to create promotion)
        mockMvc.perform(post("/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"promotionCode\": \"" + promotionCode + "\", " +
                        "\"discountPercentage\": " + discountPercentage + ", " +
                        "\"validUntil\": \"" + validUntil.toString() + "\", " +
                        "\"gameId\": " + game.getGameID() + " }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.promotionCode").value(promotionCode))
                .andExpect(jsonPath("$.discountPercentage").value(discountPercentage))
                .andExpect(jsonPath("$.validUntil").value(validUntil.toString()));
    }

    @Test
    void testGetPromotionByIdIntegration() throws Exception {
        // Arrange
        Promotion promotion = new Promotion("PROMO2024", 20.0, Date.valueOf("2024-12-31"), game);
        promotionRepository.save(promotion); // Save the promotion to the database

        // Act & Assert (performing the request to get promotion by ID)
        mockMvc.perform(get("/promotions/" + promotion.getPromotionID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionCode").value("PROMO2024"))
                .andExpect(jsonPath("$.discountPercentage").value(20.0));
    }

    @Test
    void testDeletePromotionIntegration() throws Exception {
        // Arrange
        Promotion promotion = new Promotion("PROMO2024", 20.0, Date.valueOf("2024-12-31"), game);
        promotionRepository.save(promotion); // Save the promotion to the database

        // Act & Assert (performing the request to delete promotion)
        mockMvc.perform(delete("/promotions/" + promotion.getPromotionID()))
                .andExpect(status().isNoContent());

        // Verify the promotion was actually deleted
        assertNull(promotionRepository.findById(promotion.getPromotionID()));
    }

    @Test
    void testGetPromotionNotFound() throws Exception {
        // Act & Assert (performing the request to get non-existing promotion by ID)
        mockMvc.perform(get("/promotions/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdatePromotionIntegration() throws Exception {
    // Arrange
        Promotion promotion = new Promotion("PROMO2024", 20.0, Date.valueOf("2024-12-31"), game);
        promotionRepository.save(promotion); // Save the initial promotion to the database

        String updatedPromotionCode = "PROMO2025";
        double updatedDiscountPercentage = 25.0;
        Date updatedValidUntil = Date.valueOf("2025-12-31");

        // Act & Assert (performing the request to update the promotion)
        mockMvc.perform(put("/promotions/" + promotion.getPromotionID())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"promotionCode\": \"" + updatedPromotionCode + "\", " +
                        "\"discountPercentage\": " + updatedDiscountPercentage + ", " +
                        "\"validUntil\": \"" + updatedValidUntil.toString() + "\", " +
                        "\"gameId\": " + game.getGameID() + " }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.promotionCode").value(updatedPromotionCode))
                .andExpect(jsonPath("$.discountPercentage").value(updatedDiscountPercentage))
                .andExpect(jsonPath("$.validUntil").value(updatedValidUntil.toString()));
}

    @Test
    void testCreatePromotionWithInvalidDiscountIntegration() throws Exception {
        // Arrange
        String promotionCode = "PROMO2024";
        double invalidDiscountPercentage = -10.0; // Invalid discount
        Date validUntil = Date.valueOf("2024-12-31");

        // Act & Assert (performing the request to create a promotion with an invalid discount)
        mockMvc.perform(post("/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"promotionCode\": \"" + promotionCode + "\", " +
                        "\"discountPercentage\": " + invalidDiscountPercentage + ", " +
                        "\"validUntil\": \"" + validUntil.toString() + "\", " +
                        "\"gameId\": " + game.getGameID() + " }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Discount percentage must be between 0 and 100."));
    }

    @Test
    void testGetAllPromotionsIntegration() throws Exception {
        // Arrange
        Promotion promo1 = new Promotion("PROMO2024", 10.0, Date.valueOf("2024-12-31"), game);
        Promotion promo2 = new Promotion("PROMO2025", 15.0, Date.valueOf("2025-12-31"), game);
        promotionRepository.save(promo1);
        promotionRepository.save(promo2);

        // Act & Assert (performing the request to get all promotions)
        mockMvc.perform(get("/promotions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].promotionCode").value("PROMO2024"))
                .andExpect(jsonPath("$[1].promotionCode").value("PROMO2025"))
                .andExpect(jsonPath("$[0].discountPercentage").value(10.0))
                .andExpect(jsonPath("$[1].discountPercentage").value(15.0));
    }

    @Test
    void testCreatePromotionWithExpiredDateIntegration() throws Exception {
        // Arrange
        String promotionCode = "PROMO2024";
        double discountPercentage = 20.0;
        Date expiredDate = Date.valueOf("2023-01-01"); // Date in the past

        // Act & Assert (performing the request to create a promotion with an expired date)
        mockMvc.perform(post("/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"promotionCode\": \"" + promotionCode + "\", " +
                        "\"discountPercentage\": " + discountPercentage + ", " +
                        "\"validUntil\": \"" + expiredDate.toString() + "\", " +
                        "\"gameId\": " + game.getGameID() + " }"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Promotion date must be in the future."));
    }

    @Test
    void testUpdatePromotionNotFoundIntegration() throws Exception {
        // Arrange
        int nonExistentPromotionId = 999;
        String promotionCode = "PROMO2024";
        double discountPercentage = 20.0;
        Date validUntil = Date.valueOf("2024-12-31");

        // Act & Assert (performing the request to update a non-existent promotion)
        mockMvc.perform(put("/promotions/" + nonExistentPromotionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"promotionCode\": \"" + promotionCode + "\", " +
                        "\"discountPercentage\": " + discountPercentage + ", " +
                        "\"validUntil\": \"" + validUntil.toString() + "\", " +
                        "\"gameId\": " + game.getGameID() + " }"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Promotion not found."));
    }

    @Test
    void testDeletePromotionNotFoundIntegration() throws Exception {
        // Arrange
        int nonExistentPromotionId = 999;

        // Act & Assert (performing the request to delete a non-existent promotion)
        mockMvc.perform(delete("/promotions/" + nonExistentPromotionId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Promotion not found."));
    }



}
