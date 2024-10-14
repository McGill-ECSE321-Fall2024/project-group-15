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

@SpringBootTest
public class GameRepositoryTest {
    @Autowired
    private GameRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadGame() {
        //Create Game
        String title = "Minecraft";
        String description = "Build anything you can imagine, uncover eerie mysteries, and survive the night in the ultimate sandbox game.";
        double price = 49.99;
        int stock = 100;
        String image = "https://minecraft.com";
        boolean isApproved = true;

        Game game = new Game(title, description, price, stock, image, isApproved);

        // Save in the database
        game = repo.save(game);
        int gameID = game.getGameID();

        // Read back from the database
        Game gameFromDb = repo.findGameByGameID(gameID);

        // Assertions
        assertNotNull(gameFromDb);
        assertEquals(gameID, gameFromDb.getGameID());
        assertEquals(title, gameFromDb.getTitle());
        assertEquals(description, gameFromDb.getDescription());
        assertEquals(price, gameFromDb.getPrice());
        assertEquals(stock, gameFromDb.getStock());
        assertEquals(image, gameFromDb.getImage());
        assertEquals(isApproved, gameFromDb.getIsApproved());
    }
}
