package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Game;
import group15.gameStore.model.Person;
import group15.gameStore.model.Promotion;

@SpringBootTest
public class PromotionRepositoryTest {
    @Autowired
    private PromotionRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadPromotion() {
        //Game attributes for game to use for Promotion
        String title = "Minecraft";
        String description = "Build anything you can imagine, uncover eerie mysteries, and survive the night in the ultimate sandbox game. In Minecraft, every playthrough is different, and unforgettable adventures await behind every corner. Explore and craft your way through an infinite world that’s yours to shape, one block at a time.";
        double price = 49.99;
        int stock = 100;
        String image = "https://minecraft.com";
        boolean isApproved = true;

        //Create Promotion
        String promotionCode = "HOLIDAYS";
        double discountPercentage = 0.40;
        Date validUntil = Date.valueOf("2025-12-31");
        Game game = new Game(title, description, price, stock, image, isApproved);

        Promotion promotion = new Promotion(promotionCode, discountPercentage, validUntil, game);

        // Save in the database
        promotion = repo.save(promotion);
        int promotionId = promotion.getPromotionID();

        // Read back from the database
        Promotion promotionFromDb = repo.findById(promotionId);

        // Assertions
        assertNotNull(promotionFromDb);
        assertEquals(promotionId, promotionFromDb.getPromotionID());
        assertEquals(promotionCode, promotionFromDb.getPromotionCode());
        assertEquals(discountPercentage, promotionFromDb.getDiscountPercentage());
        assertEquals(validUntil, promotionFromDb.getValidUntil());
        assertEquals(game, promotionFromDb.getGame());
    }
}
