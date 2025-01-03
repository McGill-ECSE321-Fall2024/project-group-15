package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Person;
import group15.gameStore.repository.PersonRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @Mock
    private PersonRepository personRepo;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePerson_ValidInputs() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String email = "testuser@example.com";
        Person person = new Person(username, password, email);
        
        when(personRepo.save(any(Person.class))).thenReturn(person);
        
        // Act
        Person createdPerson = personService.createPerson(username, password, email);
        
        // Assert
        assertNotNull(createdPerson);
        assertEquals(username, createdPerson.getUsername());
        assertEquals(password, createdPerson.getPassword());
        assertEquals(email, createdPerson.getEmail());
        verify(personRepo, times(1)).save(any(Person.class));
    }

    @Test
    void testCreatePerson_InvalidUsername() {
        // Arrange
        String username = "";
        String password = "password123";
        String email = "testuser@example.com";
        
        // Act & Assert
        Exception exception = assertThrows(GameStoreException.class, () -> 
            personService.createPerson(username, password, email)
        );
        assertEquals("Username is required.", exception.getMessage());
    }

    @Test
    void testUpdatePersonInfo_ValidUpdate() {
        // Arrange
        int personId = 1;
        Person existingPerson = new Person("oldUser", "password123", "old@example.com");
        Person updatedPerson = new Person("newUser", "newPassword123", "new@example.com");
        
        when(personRepo.findByUserID(personId)).thenReturn(existingPerson);
        when(personRepo.save(existingPerson)).thenReturn(existingPerson);

        // Act
        Person result = personService.updatePerson(personId, updatedPerson);

        // Assert
        assertEquals("newUser", result.getUsername());
        assertEquals("newPassword123", result.getPassword());
        assertEquals("new@example.com", result.getEmail());
        verify(personRepo, times(1)).findByUserID(personId);
        verify(personRepo, times(1)).save(existingPerson);
    }

    @Test
    void testGetPersonById_ExistingPerson() {
        // Arrange
        int personId = 1;
        Person person = new Person("testuser", "password123", "testuser@example.com");
        person.setUserID(personId);
        when(personRepo.findByUserID(personId)).thenReturn(person);

        // Act
        Person foundPerson = personService.getPersonById(personId);

        // Assert
        assertNotNull(foundPerson);
        assertEquals(personId, foundPerson.getUserID());
        verify(personRepo, times(1)).findByUserID(personId);
    }

    @Test
    void testGetPersonByUsername_NotFound() {
        // Arrange
        String username = "unknownUser";
        when(personRepo.findByUsername(username)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(GameStoreException.class, () -> 
            personService.getPersonByUsername(username)
        );
        assertEquals("User not found.", exception.getMessage());
    }

    @Test
    void testDeletePersonByUsername_SuccessfulDelete() {
        // Arrange
        String username = "testuser";
        Person person = new Person(username, "password123", "testuser@example.com");
        when(personRepo.findByUsername(username)).thenReturn(person);

        // Act
        personService.deletePersonByUsername(username);

        // Assert
        verify(personRepo, times(1)).deleteByUsername(username);
    }

    @Test
    void testGetAllPersons_NoPersonsFound() {
        // Arrange
        when(personRepo.findAll()).thenReturn(List.of());

        // Act & Assert
        Exception exception = assertThrows(GameStoreException.class, () -> 
            personService.getAllPersons()
        );
        assertEquals("No user records found in the system.", exception.getMessage());
    }

    @Test
    void testUpdatePersonInfo_NonExistentPerson() {
        // Arrange
        int personId = 99;
        Person updatedPerson = new Person("newUser", "newPassword123", "new@example.com");

        when(personRepo.findByUserID(personId)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(GameStoreException.class, () -> 
            personService.updatePerson(personId, updatedPerson)
        );
        assertEquals("User with the specified ID does not exist.", exception.getMessage());
        verify(personRepo, never()).save(any(Person.class));
    }

    @Test
    void testGetPersonById_NonExistentPerson() {
        // Arrange
        int personId = 100;

        when(personRepo.findByUserID(personId)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(GameStoreException.class, () -> 
            personService.getPersonById(personId)
        );
        assertEquals("User not found.", exception.getMessage());
        verify(personRepo, times(1)).findByUserID(personId);
    }

    @Test
    void testDeletePersonByUsername_NotFound() {
        // Arrange
        String username = "nonExistentUser";

        when(personRepo.findByUsername(username)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(GameStoreException.class, () -> 
            personService.deletePersonByUsername(username)
        );
        assertEquals("Person with the specified username does not exist.", exception.getMessage());
        verify(personRepo, never()).deleteByUsername(username);
    }

    @Test
    void testGetAllPersons_Success() {
        // Arrange
        Person person1 = new Person("user1", "password123", "user1@example.com");
        Person person2 = new Person("user2", "password456", "user2@example.com");
        when(personRepo.findAll()).thenReturn(List.of(person1, person2));

        // Act
        List<Person> allPersons = personService.getAllPersons();

        // Assert
        assertEquals(2, allPersons.size());
        assertEquals("user1", allPersons.get(0).getUsername());
        assertEquals("user2", allPersons.get(1).getUsername());
        verify(personRepo, times(1)).findAll();
    }

    @Test
    void testCreatePerson_InvalidEmailFormat() {
        // Arrange
        String username = "testuser";
        String password = "password123";
        String email = "invalid-email"; // Invalid email format

        // Act & Assert
        Exception exception = assertThrows(GameStoreException.class, () -> 
            personService.createPerson(username, password, email)
        );
        assertEquals("Invalid email format.", exception.getMessage());
        verify(personRepo, never()).save(any(Person.class));
}

@Test
void testGetAllPersons_MultipleUsers() {
    // Arrange
    Person person1 = new Person("user1", "password123", "user1@example.com");
    Person person2 = new Person("user2", "password456", "user2@example.com");
    Person person3 = new Person("user3", "password789", "user3@example.com");
    when(personRepo.findAll()).thenReturn(List.of(person1, person2, person3));

    // Act
    List<Person> allPersons = personService.getAllPersons();

    // Assert
    assertEquals(3, allPersons.size());
    assertEquals("user1", allPersons.get(0).getUsername());
    assertEquals("user2", allPersons.get(1).getUsername());
    assertEquals("user3", allPersons.get(2).getUsername());
    verify(personRepo, times(1)).findAll();
}

}

