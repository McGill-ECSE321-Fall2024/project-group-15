package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.eventregistration.exception.EventRegistrationException;
import ca.mcgill.ecse321.eventregistration.model.Person;
import ca.mcgill.ecse321.eventregistration.repository.PersonRepository;

@SpringBootTest
public class WishlistServiceTest {
    @Mock
    private PersonRepository repo;
    @InjectMocks
    private WishlistService WishlistService;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidPerson() {
        // Arrange
        String name = "Bob";
        String email = "bob@mail.mcgill.ca";
        String password = "12345678";
        Person bob = new Person(name, email, password, Date.valueOf(LocalDate.now()));
        when(repo.save(any(Person.class))).thenReturn(bob);
        // You could also do it with thenAnswer(), which is more flexible but more
        // verbose
        // when(repo.save(notNull(Person.class))).thenAnswer((InvocationOnMock iom) ->
        // iom.getArgument(0));

        // Act
        Person createdPerson = service.createPerson(name, email, password);

        // Assert
        assertNotNull(createdPerson);
        assertEquals(name, createdPerson.getName());
        assertEquals(email, createdPerson.getEmail());
        assertEquals(password, createdPerson.getPassword());
        assertEquals(Date.valueOf(LocalDate.now()), createdPerson.getCreationDate());
        verify(repo, times(1)).save(bob);
    }

    @Test
    public void testReadPersonByValidId() {
        // Arrange
        int id = 42;
        Person charlie = new Person("Charlie", "charlie@mail.mcgill.ca", "password123", Date.valueOf("2024-03-01"));
        when(repo.findPersonById(id)).thenReturn(charlie);

        // Act
        Person person = service.findPersonById(id);

        // Assert
        assertNotNull(person);
        assertEquals(charlie.getName(), person.getName());
        assertEquals(charlie.getEmail(), person.getEmail());
        assertEquals(charlie.getPassword(), person.getPassword());
        assertEquals(charlie.getCreationDate(), person.getCreationDate());
    }

    @Test
    public void testReadPersonByInvalidId() {
        // Set up
        int id = 42;
        // Default is to return null, so you could omit this
        when(repo.findPersonById(id)).thenReturn(null);

        // Act
        // Assert
        EventRegistrationException e = assertThrows(EventRegistrationException.class, () -> service.findPersonById(id));
        assertEquals("There is no person with ID " + id + ".", e.getMessage());
        // assertThrows is basically like the following:
        // try {
        // service.findPersonById(id);
        // fail("No exception was thrown.");
        // } catch (IllegalArgumentException e) {
        // assertEquals("There is no person with ID " + id + ".", e.getMessage());
        // }
    }
}
