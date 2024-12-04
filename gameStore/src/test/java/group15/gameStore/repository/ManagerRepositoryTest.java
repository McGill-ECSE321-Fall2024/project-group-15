package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Manager;

@SpringBootTest
public class ManagerRepositoryTest {
    @Autowired
    private ManagerRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadManager() {
        //Create Manager
        String username = "ManagerJames";
        String password = "managerpassword";
        String email = "managerjames@gamestore.com";
        boolean isActive = true;
        boolean isManager = false;

        Manager manager = new Manager(username, password, email, isActive, isManager);

        // Save in the database
        manager = repo.save(manager);
        int managerId = manager.getUserID();

        // Read back from the database
        Manager managerFromDb = repo.findManagerByUserID(managerId);

        // Assertions
        assertNotNull(managerFromDb);
        assertEquals(managerId, managerFromDb.getUserID());
        assertEquals(username, managerFromDb.getUsername());
        assertEquals(password, managerFromDb.getPassword());
        assertEquals(email, managerFromDb.getEmail());
        assertEquals(isActive, managerFromDb.getIsActive());
        assertEquals(isManager, managerFromDb.getIsManager());
    }
}
