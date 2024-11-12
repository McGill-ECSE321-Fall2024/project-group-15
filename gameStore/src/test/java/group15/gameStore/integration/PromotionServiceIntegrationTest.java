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
        assertTrue(promotionRepository.findById(promotion.getPromotionID()).isEmpty());
    }

    @Test
    void testGetPromotionNotFound() throws Exception {
        // Act & Assert (performing the request to get non-existing promotion by ID)
        mockMvc.perform(get("/promotions/999"))
                .andExpect(status().isNotFound());
    }
}
