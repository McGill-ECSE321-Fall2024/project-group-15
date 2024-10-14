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

import group15.gameStore.model.Category;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadCategory() {
        //Create Category
        String name = "Action";
        Category action = new Category(name);

        // Save in the database
        action = repo.save(action);
        int actionId = action.getCategoryID();

        // Read back from the database
        Category categoryFromDb = repo.findByCategoryId(actionId);

        // Assertions
        assertNotNull(categoryFromDb);
        assertEquals(actionId, categoryFromDb.getCategoryID());
        assertEquals(name, categoryFromDb.getName());
    }
}
