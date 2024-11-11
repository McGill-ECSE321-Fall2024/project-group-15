package group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Person;
import group15.gameStore.repository.PersonRepository;


@Service
public class PersonService {

    @Autowired
    PersonRepository personRepo;

    /**
     * CreatePerson: creates a new person with username, password, and email
     * @param username: the username of the person
     * @param password: the password of the person
     * @param email: the email of the person
     * @return the new created Person 
     * @throws GameStoreException if any field is missing or invalid
     */
    @Transactional 
    public Person createPerson(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
           throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        if (password == null || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }

        Person person = new Person(username, password, email);
        personRepo.save(person);
        return person;
    }

    /**
     * UpdatePersonInfo: updates an existing person's information
     * @param personId: ID of the person to update
     * @param updatedPersonInfo: the new person information to update to
     * @return the updated Person object
     * @throws GameStoreException if update request is invalid
     */
    @Transactional
    public Person updatePersonInfo(int personId, Person updatedPersonInfo) {
        Person existingPerson = personRepo.findByUserID(personId);
        if (existingPerson == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "User with the specified ID does not exist.");
        }
        if (updatedPersonInfo == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid update request: no information provided.");
        }
        String username = updatedPersonInfo.getUsername();
        if (username == null || username.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        String password = updatedPersonInfo.getPassword();
        if (password == null || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        String email = updatedPersonInfo.getEmail();
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }
    
        existingPerson.setUsername(username);
        existingPerson.setPassword(password);
        existingPerson.setEmail(email);
    
        return personRepo.save(existingPerson);
    }

    /**
     * GetPersonById: retrieves a person by their ID
     * @param id: the ID of the person
     * @return the Person with the specified ID
     * @throws GameStoreException if the person with the given ID is not found
     */
    @Transactional
    public Person getPersonById(int id){
        Person person = personRepo.findByUserID(id);
        if(person == null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return person;
    }

    /**
     * GetPersonByUsername: retrieves a person by their username
     * @param username: the username of the person
     * @return the Person with the specified username
     * @throws GameStoreException if the person with the given username is not found
     */
    @Transactional
    public Person getPersonByUsername(String username){
        Person person = personRepo.findByUsername(username);
        if(person == null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "User not found.");
        }
        return person;
    }

    /**
     * GetAllPersons: retrieves all person records in the system
     * @return a list of all Person objects
     * @throws GameStoreException if no person records are found
     */
    @Transactional
    public List<Person> getAllPersons() {
        List<Person> persons = personRepo.findAll();
        if (persons.isEmpty()) {
            throw new GameStoreException(HttpStatus.NO_CONTENT, "No user records found in the system.");
        }
        return persons;
    }

    /**
     * DeletePersonByUsername: deletes a person by their username
     * @param username: the username of the person to delete
     * @throws GameStoreException if the person with the given username is not found
     */
    @Transactional
    public void deletePersonByUsername(String username) {
        Person person = personRepo.findByUsername(username);
        if (person == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Person with the specified username does not exist.");
        }

        personRepo.deleteByUsername(username);
    }   

}

