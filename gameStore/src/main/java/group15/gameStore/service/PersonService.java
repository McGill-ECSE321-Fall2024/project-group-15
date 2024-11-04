package main.java.group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
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
     * @throws IllegalArgumentException if any field is missing or invalid
     */
    @Transactional 
    public Person createPerson(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required.");
        }
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
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
     * @throws IllegalArgumentException if update request is invalid
     */
    @Transactional
    public Person updatePersonInfo(int personId, Person updatedPersonInfo) {
        Person existingPerson = personRepository.findById(personId);
        if (existingPerson == null) {
            throw new IllegalArgumentException("User with the specified ID does not exist.");
        }
        if (updatedPersonInfo == null) {
            throw new IllegalArgumentException("Invalid update request: no information provided.");
        }
        String username = updatedPersonInfo.getUsername();
        if (username != null && !username.trim().isEmpty()) {
            existingPerson.setUsername(username);
        } else {
            throw new IllegalArgumentException("Username is required.");
        }
        String password = updatedPersonInfo.getPassword();
        if (password != null && password.length() >= 8) {
            existingPerson.setPassword(password);
        } else {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        String email = updatedPersonInfo.getEmail();
        if (email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            existingPerson.setEmail(email);
        } else {
            throw new IllegalArgumentException("Invalid email format.");
        }
        return personRepository.save(existingPerson);
    }

    /**
     * GetPersonById: retrieves a person by their ID
     * @param id: the ID of the person
     * @return the Person with the specified ID
     * @throws IllegalArgumentException if the person with the given ID is not found
     */
    @Transactional
    public Person getPersonById(int id){
        Person person = personRepo.findByUserID(id);
        if(person == null){
            throw new IllegalArgumentException("User not found");
        }
        return person;
    }

    /**
     * GetPersonByUsername: retrieves a person by their username
     * @param username: the username of the person
     * @return the Person with the specified username
     * @throws IllegalArgumentException if the person with the given username is not found
     */
    @Transactional
    public Person getPersonByUsername(String username){
        Person person = personRepo.findByUsername(username);
        if(person == null){
            throw new IllegalArgumentException("User not found");
        }
        return person;
    }

    /**
     * GetAllPersons: retrieves all person records in the system
     * @return a list of all Person objects
     * @throws IllegalArgumentException if no person records are found
     */
    @Transactional
    public List<Person> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        if (persons.isEmpty()) {
            throw new IllegalArgumentException("No User records found in the system.");
        }
        return persons;
    }

    /**
     * DeletePersonByUsername: deletes a person by their username
     * @param username: the username of the person to delete
     * @throws IllegalArgumentException if the person with the given username is not found
     */
    @Transactional
    public void deletePersonByUsername(String username) {
        Person person = personRepository.findByUsername(username);
        if (person == null) {
            throw new IllegalArgumentException("Person with the specified username does not exist.");
        }

        personRepo.deleteByUsername(username);
    }   

}
