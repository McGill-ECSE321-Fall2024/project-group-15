package group15.gameStore.integration;

import group15.gameStore.model.Person;
import group15.gameStore.dto.PersonDto;
import group15.gameStore.service.PersonService;
import group15.gameStore.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("unused")
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    private Person testPerson;

    @BeforeEach
    public void setUp() {
        // Create a test person to use in tests
        testPerson = new Person("john_doe", "password123", "john@example.com");
        personRepository.save(testPerson);
    }

    @Test
    public void testCreatePerson() throws Exception {
        PersonDto newPersonDto = new PersonDto("new_user", "password123", "newuser@example.com");

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"new_user\", \"password\": \"password123\", \"email\": \"newuser@example.com\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(newPersonDto.getUsername()))
                .andExpect(jsonPath("$.email").value(newPersonDto.getEmail()));
    }

    @Test
    public void testUpdatePersonInfo() throws Exception {
        PersonDto updatedPersonDto = new PersonDto("updated_john", "newpassword123", "updatedjohn@example.com");

        mockMvc.perform(put("/person/{personId}", testPerson.getUserID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"updated_john\", \"password\": \"newpassword123\", \"email\": \"updatedjohn@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(updatedPersonDto.getUsername()))
                .andExpect(jsonPath("$.email").value(updatedPersonDto.getEmail()));
    }

    @Test
    public void testGetPersonById() throws Exception {
        mockMvc.perform(get("/person/{personId}", testPerson.getUserID()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testPerson.getUsername()))
                .andExpect(jsonPath("$.email").value(testPerson.getEmail()));
    }

    @Test
    public void testGetPersonByUsername() throws Exception {
        mockMvc.perform(get("/person/username/{username}", testPerson.getUsername()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(testPerson.getUsername()))
                .andExpect(jsonPath("$.email").value(testPerson.getEmail()));
    }

    @Test
    public void testDeletePersonByUsername() throws Exception {
        mockMvc.perform(delete("/person/{username}", testPerson.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"" + testPerson.getUsername() + "\", \"password\": \"password123\", \"email\": \"john@example.com\" }"))
                .andExpect(status().isNoContent());

        // Verify person is deleted from the database
        mockMvc.perform(get("/person/{personId}", testPerson.getUserID()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePersonUnauthorized() throws Exception {
        // Attempt to delete with a different person details (should be forbidden)
        mockMvc.perform(delete("/person/{username}", testPerson.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"another_user\", \"password\": \"password123\", \"email\": \"another@example.com\" }"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreatePersonWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"new_user\", \"password\": \"password123\", \"email\": \"invalid-email\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreatePersonWithShortPassword() throws Exception {
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"new_user\", \"password\": \"short\", \"email\": \"valid@example.com\" }"))
                .andExpect(status().isBadRequest());
    }
}
