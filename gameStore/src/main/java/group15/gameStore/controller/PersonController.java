package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.model.Person;
import group15.gameStore.dto.PersonDto;
import group15.gameStore.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * CreatePerson: creates a new user record
     * @param personDto the PersonDto containing the person details
     * @return the created user and the HTTP status "CREATED"
     */
    @PostMapping("/person")
    public ResponseEntity<PersonDto> createPerson(@RequestBody PersonDto PersonDto){
        Person createdPerson = personService.createPerson(PersonDto.getUsername(), PersonDto.getPassword(),PersonDto.getEmail());
        PersonDto responseDto = new PersonDto(createdPerson);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  
    }

    /**
     * UpdatePerson: updates an existing user record
     * @param personId the ID of the person to update
     * @param personDto the PersonDto containing the updated person details
     * @return the updated user information and the HTTP status "OK"
     */
    @PutMapping("/person/{personId}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable int personId,@RequestBody PersonDto personDto) {
        Person personToUpdate = new Person();
        personToUpdate.setUsername(personDto.getUsername());
        personToUpdate.setPassword(personDto.getPassword());
        personToUpdate.setEmail(personDto.getEmail());
        Person updatedPerson = personService.updatePerson(personId, personToUpdate);
        return new ResponseEntity<>(new PersonDto(updatedPerson), HttpStatus.OK);
    }

    /**
     * GetPersonById: retrieves user information by ID
     * @param personId the ID of the person to retrieve
     * @return return desired user information and the HTTP status "OK"
     */
    @GetMapping("/person/{personId}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable int personId) {
        Person person = personService.getPersonById(personId);
        PersonDto responseDto = new PersonDto(person);
        return new ResponseEntity<>(responseDto, HttpStatus.OK); 
    }

    /**
     * GetPersonByUsername: retrieves user information by username
     * @param username the username of the person to retrieve
     * @return the desired user information and the HTTP status "OK"
     */
    @GetMapping("/person/username/{username}")
    public ResponseEntity<PersonDto> getPersonByUsername(@PathVariable String username) {
        Person person = personService.getPersonByUsername(username);
        PersonDto responseDto = new PersonDto(person);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);    
    }

     /**
     * GetAllPersons: retrieves all user records in the system
     * @return all users information in the system and the HTTP status "OK"
     */
    @GetMapping("/person")
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        List<Person> personList = personService.getAllPersons();
        if (personList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<PersonDto> responseDtoList = personList.stream()
                .map(PersonDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * DeletePersonByUsername: deletes a person by their username
     * @param username the username of the person to delete
     * @param personDto the DTO containing the requesting user's information for authorization
     * @return HTTP status "NO CONTENT" if deletion is successful, "FORBIDDEN" if authorization fails
     */
    @DeleteMapping("/person/{username}")
    public ResponseEntity<Void> deletePersonByUsername(@PathVariable String username, @RequestBody PersonDto personDto) {
        Person personToDelete = personService.getPersonByUsername(username);

        // Check authorization using the request DTO
        if (!personToDelete.getUsername().equals(personDto.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        personService.deletePersonByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}