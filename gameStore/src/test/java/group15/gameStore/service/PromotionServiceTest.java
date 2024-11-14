package group15.gameStore.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import group15.gameStore.model.Promotion;
import group15.gameStore.model.Game;
import group15.gameStore.repository.PromotionRepository;
import group15.gameStore.repository.GameRepository;
import java.sql.Date;

public class PromotionServiceTest {

    @InjectMocks
    private PromotionService promotionService;

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private Game game;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePromotion_Success() {
        // Arrange
        Game game = new Game();
        game.setGameID(1); // Set the game ID or other necessary fields
        Date validUntil = Date.valueOf("2024-12-31");

        Promotion mockPromotion = new Promotion("PROMO2024", 20.0, validUntil, game);
        
        // Mock game repository behavior
        when(gameRepository.findGameByGameID(1)).thenReturn(game);

        // Mock promotion repository behavior
        when(promotionRepository.save(any(Promotion.class))).thenReturn(mockPromotion);

        // Act
        Promotion createdPromotion = promotionService.createPromotion("PROMO2024", 20.0, validUntil, game);

        // Assert
        assertNotNull(createdPromotion);
        assertEquals("PROMO2024", createdPromotion.getPromotionCode());
        assertEquals(20.0, createdPromotion.getDiscountPercentage());
        assertEquals(validUntil, createdPromotion.getValidUntil());
        verify(promotionRepository, times(1)).save(any(Promotion.class)); // Verify repository save was called
    }

    @Test
    void testGetPromotionById_Success() {
        // Arrange
        int promotionId = 1;
        Game game = new Game();
        Date validUntil = Date.valueOf("2024-12-31");
        Promotion mockPromotion = new Promotion("PROMO2024", 20.0, validUntil, game);
        mockPromotion.setPromotionID(promotionId);

        // Mock behavior for finding promotion by ID
        when(promotionRepository.findById(promotionId)).thenReturn(mockPromotion);

        // Act
        Promotion retrievedPromotion = promotionService.getPromotionById(promotionId);

        // Assert
        assertNotNull(retrievedPromotion);
        assertEquals(promotionId, retrievedPromotion.getPromotionID());
        assertEquals("PROMO2024", retrievedPromotion.getPromotionCode());
    }

    @Test
    void testGetPromotionById_PromotionNotFound() {
        // Arrange
        int promotionId = 1;

        // Mock behavior for finding promotion by ID
        when(promotionRepository.findById(promotionId)).thenReturn(null);

        // Act
        Promotion retrievedPromotion = promotionService.getPromotionById(promotionId);

        // Assert
        assertNull(retrievedPromotion); // Should return null if not found
    }

    @Test
    void testDeletePromotion_Success() {
        // Arrange
        String promotionCode = "PROMO2024";
        Game game = new Game();
        game.setGameID(1);

        Promotion mockPromotion = new Promotion(promotionCode, 20.0, Date.valueOf("2024-12-31"), game);

        // Mock behavior to find promotion
        when(promotionRepository.findByPromotionCode(promotionCode)).thenReturn(mockPromotion);

        // Act
        promotionService.deletePromotion(promotionCode, game);

        // Assert
        verify(promotionRepository, times(1)).delete(mockPromotion); // Verify delete was called on the mock
    }

    @Test
    void testDeletePromotion_NotFound() {
        // Arrange
        String promotionCode = "PROMO2024";
        Game game = new Game();

        // Mock behavior for non-existing promotion
        when(promotionRepository.findByPromotionCode(promotionCode)).thenReturn(null);

        // Act
        promotionService.deletePromotion(promotionCode, game);

        // Assert
        verify(promotionRepository, times(0)).delete(any(Promotion.class)); // Verify delete was NOT called
    }
}
