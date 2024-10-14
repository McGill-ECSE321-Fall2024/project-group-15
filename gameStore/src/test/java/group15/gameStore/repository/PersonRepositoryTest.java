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

import group15.gameStore.model.Person;

@SpringBootTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadPerson() {
        //Create Person
        String username = "BradTheGamer";
        String password = "password123";
        String email = "brad@example.com";

        Person person = new Person(username, password, email);

        // Save in the database
        person = repo.save(person);
        int personId = person.getUserID();

        // Read back from the database
        Person personFromDb = repo.findByUserID(personId);

        // Assertions
        assertNotNull(personFromDb);
        assertEquals(personId, personFromDb.getUserID());
        assertEquals(username, personFromDb.getUsername());
        assertEquals(password, personFromDb.getPassword());
        assertEquals(email, personFromDb.getEmail());
    }
}
