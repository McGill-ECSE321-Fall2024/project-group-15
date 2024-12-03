package group15.gameStore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import group15.gameStore.model.Promotion;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Game;
import group15.gameStore.repository.PromotionRepository;
import group15.gameStore.repository.GameRepository;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
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
        game.setGameID(1); 
        Date validUntil = Date.valueOf("2024-12-31");

        Promotion mockPromotion = new Promotion("PROMO2024", 20.0, validUntil, game);

        when(gameRepository.findGameByGameID(anyInt())).thenReturn(game);
        when(promotionRepository.save(any(Promotion.class))).thenReturn(mockPromotion);

        // Act
        Promotion createdPromotion = promotionService.createPromotion("PROMO2024", 20.0, validUntil, game);

        // Assert
        assertNotNull(createdPromotion);
        assertEquals("PROMO2024", createdPromotion.getPromotionCode());
        assertEquals(20.0, createdPromotion.getDiscountPercentage());
        assertEquals(validUntil, createdPromotion.getValidUntil());
        assertEquals(game, createdPromotion.getGame());
        verify(promotionRepository, times(1)).save(any(Promotion.class)); // Verify save was called once
    }

    @Test
    void testGetPromotionById_PromotionNotFound() {
        // Arrange
        int promotionId = 1;

        // Mock behavior for finding promotion by ID
        when(promotionRepository.findById(promotionId)).thenReturn(null);

        // Act
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            promotionService.getPromotionById(promotionId);
        });

        // Assert
        assertEquals("Promotion not found.", thrown.getMessage());
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
        verify(promotionRepository, times(1)).deleteByPromotionCode(promotionCode); // Verify delete by promotion code was called
    }

    @Test
    void testDeletePromotion_NotFound() {
        // Arrange
        String promotionCode = "PROMO2024";
        Game game = new Game();

        // Mock behavior for non-existing promotion
        when(promotionRepository.findByPromotionCode(promotionCode)).thenReturn(null);

        // Act
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            promotionService.deletePromotion(promotionCode, game);
        });
        // Assert
        assertEquals("Promotion with the specified code does not exist.", thrown.getMessage());
    }

    @Test
    void testDeletePromotion_Unauthorized() {
        // Arrange
        String promotionCode = "PROMO2024";
        Game authorizedGame = new Game();
        authorizedGame.setGameID(1);
        
        Game unauthorizedGame = new Game();
        unauthorizedGame.setGameID(2);

        Promotion promotion = new Promotion();
        promotion.setPromotionCode(promotionCode);
        promotion.setGame(authorizedGame);

        when(promotionRepository.findByPromotionCode(promotionCode)).thenReturn(promotion);

        // Act
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            promotionService.deletePromotion(promotionCode, unauthorizedGame);
        });

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, thrown.getStatus());
        assertEquals("Unauthorized access. Only the associated game can delete this promotion.", thrown.getMessage());

        verify(promotionRepository, never()).deleteByPromotionCode(promotionCode);
    }

    @Test
    void testCreatePromotion_WithInvalidDiscount_ThrowsException() {
        // Arrange
        Game game = new Game();
        game.setGameID(1);
        Date validUntil = Date.valueOf("2024-12-31");

        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            promotionService.createPromotion("PROMO2024", -10.0, validUntil, game);
        });
        assertEquals("Discount percentage must be between 0 and 100.", thrown.getMessage());
    }

@Test
void testCreatePromotion_WithDiscountOver100_ThrowsException() {
    // Arrange
    Game game = new Game();
    game.setGameID(1);
    Date validUntil = Date.valueOf("2024-12-31");

    // Act & Assert
    GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
        promotionService.createPromotion("PROMO2024", 120.0, validUntil, game);
    });
    assertEquals("Discount percentage must be between 0 and 100.", thrown.getMessage());
}

@Test
void testCreatePromotion_WithExpiredDate_ThrowsException() {
    // Arrange
    Game game = new Game();
    game.setGameID(1);
    Date expiredDate = Date.valueOf("2023-01-01");

    // Act & Assert
    GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
        promotionService.createPromotion("PROMO2024", 20.0, expiredDate, game);
    });
    assertEquals("Valid until date must be a future date.", thrown.getMessage());
}

@Test
void testUpdatePromotion_Success() {
    // Arrange
    Game game = new Game();
    game.setGameID(1);

    Promotion existingPromotion = new Promotion("OLDPROMO", 10.0, Date.valueOf("2024-12-31"), game);
    existingPromotion.setPromotionID(1);

    Promotion updatedPromotion = new Promotion("NEWPROMO", 20.0, Date.valueOf("2025-12-31"), game);

    when(promotionRepository.findById(1));
    when(promotionRepository.save(existingPromotion)).thenReturn(existingPromotion);

    // Act
    Promotion result = promotionService.updatePromotion(1, updatedPromotion, game);

    // Assert
    assertNotNull(result);
    assertEquals("NEWPROMO", result.getPromotionCode());
    assertEquals(20.0, result.getDiscountPercentage());
    assertEquals(Date.valueOf("2025-12-31"), result.getValidUntil());
    verify(promotionRepository, times(1)).save(existingPromotion);
}

@Test
void testUpdatePromotion_BadRequest() {
    // Arrange
    Game game = new Game();
    game.setGameID(1);

    Promotion existingPromotion = new Promotion("PROMO2024", 15.0, Date.valueOf("2024-12-31"), game);
    existingPromotion.setPromotionID(1);

    // Invalid updated promotion with empty promotion code and out-of-range discount percentage
    Promotion invalidUpdatedPromotion = new Promotion("", 150.0, Date.valueOf("2025-12-31"), game);

    when(promotionRepository.findById(1));

    // Act & Assert
    GameStoreException exception = assertThrows(GameStoreException.class, () -> {
        promotionService.updatePromotion(1, invalidUpdatedPromotion, game);
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("Promotion code cannot be null or empty.", exception.getMessage()); // Adjust message as per your validation order
    verify(promotionRepository, never()).save(any(Promotion.class));
}

@Test
void testUpdatePromotion_NotFound() {
    // Arrange
    Game game = new Game();
    game.setGameID(1);

    Promotion updatedPromotion = new Promotion("NEWPROMO2025", 20.0, Date.valueOf("2025-12-31"), game);

    when(promotionRepository.findById(1)).thenReturn(null); // Promotion with ID 1 does not exist

    // Act & Assert
    GameStoreException exception = assertThrows(GameStoreException.class, () -> {
        promotionService.updatePromotion(1, updatedPromotion, game);
    });

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("Promotion with the specified ID does not exist.", exception.getMessage());
    verify(promotionRepository, never()).save(any(Promotion.class));
}

@Test
void testGetPromotionById_NotFound() {
    // Arrange
    when(promotionRepository.findById(1)).thenReturn(null);

    // Act & Assert
    GameStoreException exception = assertThrows(GameStoreException.class, () -> {
        promotionService.getPromotionById(1);
    });

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("Promotion not found.", exception.getMessage());
}

@Test
void testGetByPromotionCode_Success() {
    // Arrange
    Promotion promotion = new Promotion("PROMO2024", 20.0, Date.valueOf("2024-12-31"), new Game());
    when(promotionRepository.findByPromotionCode("PROMO2024")).thenReturn(promotion);

    // Act
    Promotion retrievedPromotion = promotionService.getByPromotionCode("PROMO2024");

    // Assert
    assertNotNull(retrievedPromotion);
    assertEquals("PROMO2024", retrievedPromotion.getPromotionCode());
}

@Test
void testGetByPromotionCode_InvalidCode() {
    // Act & Assert
    GameStoreException exception = assertThrows(GameStoreException.class, () -> {
        promotionService.getByPromotionCode("");
    });

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("Invalid promotion code. Code cannot be null or empty.", exception.getMessage());
}

@Test
void testGetByPromotionCode_NotFound() {
    // Arrange
    when(promotionRepository.findByPromotionCode("PROMO2024")).thenReturn(null);

    // Act & Assert
    GameStoreException exception = assertThrows(GameStoreException.class, () -> {
        promotionService.getByPromotionCode("PROMO2024");
    });

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("Promotion with the specified code does not exist.", exception.getMessage());
}

@Test
void testGetByValidUntil_Success() {
    // Arrange
    Date validUntil = Date.valueOf("2024-12-31");
    Promotion promotion = new Promotion("PROMO2024", 20.0, validUntil, new Game());
    List<Promotion> promotions = List.of(promotion);

    when(promotionRepository.findByValidUntilAfter(validUntil)).thenReturn(promotions);

    // Act
    List<Promotion> retrievedPromotions = promotionService.getByValidUntil(validUntil);

    // Assert
    assertFalse(retrievedPromotions.isEmpty());
    assertEquals(1, retrievedPromotions.size());
    assertEquals("PROMO2024", retrievedPromotions.get(0).getPromotionCode());
}

@Test
void testGetByValidUntil_NoPromotionsFound() {
    // Arrange
    Date validUntil = Date.valueOf("2024-12-31");
    when(promotionRepository.findByValidUntilAfter(validUntil)).thenReturn(Collections.emptyList());

    // Act & Assert
    GameStoreException exception = assertThrows(GameStoreException.class, () -> {
        promotionService.getByValidUntil(validUntil);
    });

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("No promotions found valid until the specified date.", exception.getMessage());
}

@Test
void testGetAllPromotion_Success() {
    // Arrange
    Promotion promotion1 = new Promotion("PROMO2024", 20.0, Date.valueOf("2024-12-31"), new Game());
    Promotion promotion2 = new Promotion("PROMO2025", 15.0, Date.valueOf("2025-12-31"), new Game());
    List<Promotion> promotions = List.of(promotion1, promotion2);

    when(promotionRepository.findAll()).thenReturn(promotions);

    // Act
    List<Promotion> retrievedPromotions = promotionService.getAllPromotion();

    // Assert
    assertFalse(retrievedPromotions.isEmpty());
    assertEquals(2, retrievedPromotions.size());
}

@Test
void testGetAllPromotion_NoPromotionsFound() {
    // Arrange
    when(promotionRepository.findAll()).thenReturn(Collections.emptyList());

    // Act & Assert
    GameStoreException exception = assertThrows(GameStoreException.class, () -> {
        promotionService.getAllPromotion();
    });

    assertEquals(HttpStatus.NO_CONTENT, exception.getStatus());
    assertEquals("No promotions found in the system.", exception.getMessage());
}

}
