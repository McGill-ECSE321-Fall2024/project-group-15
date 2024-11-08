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
import group15.gameStore.RequestDto.PersonRequestDto;
import group15.gameStore.ResponseDto.PersonResponseDto;
import group15.gameStore.service.PersonService;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * CreatePerson: creates a new user record
     * @param personDto the PersonRequestDto containing the person details
     * @return the created user 
     */
    @PostMapping("/person")
    public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonRequestDto personRequestDto){
        try{
            Person createdPerson = personService.createPerson(personRequestDto.getUsername(), personRequestDto.getPassword(),
            personRequestDto.getEmail());

            PersonResponseDto responseDto = new PersonResponseDto(createdPerson);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    /**
     * UpdatePerson: updates an existing user record
     * @param personId the ID of the person to update
     * @return the updated user information
     */
    @PutMapping("/person/{personId}")
    public ResponseEntity<PersonResponseDto> updatePerson(@PathVariable("personId") int personId) {
        try{
            Person person = personService.getPersonById(personId);

            Person updatedPerson = personService.updatePersonInfo(personId, person);
            return new ResponseEntity<>(new PersonResponseDto(updatedPerson), HttpStatus.OK);
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GetPersonById: retrieves user information by ID
     * @param personId the ID of the person to retrieve
     * @return return desired user information
     */
    @GetMapping("/person/{personId}")
    public ResponseEntity<PersonResponseDto> getPersonById(@PathVariable int personId) {
        try {
            Person person = personService.getPersonById(personId);
            PersonResponseDto responseDto = new PersonResponseDto(person);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK); 
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GetPersonByUsername: retrieves user information by username
     * @param username the username of the person to retrieve
     * @return the desired user information
     */
    @GetMapping("/person/username/{username}")
    public ResponseEntity<PersonResponseDto> getPersonByUsername(@PathVariable String username) {
        try {
            Person person = personService.getPersonByUsername(username);
            PersonResponseDto responseDto = new PersonResponseDto(person);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK);    
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

     /**
     * GetAllPersons: retrieves all user records in the system
     * @return all users information in the system
     */
    @GetMapping("/person")
    public ResponseEntity<List<PersonResponseDto>> getAllPersons() {
        List<Person> personList = personService.getAllPersons();
        if (personList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<PersonResponseDto> responseDtoList = personList.stream()
                .map(PersonResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * DeletePersonByUsername: deletes a person by their username
     * @param username the username of the person to delete
     * @return HTTP status
     */
    @DeleteMapping("/person/{username}")
    public ResponseEntity<Void> deletePersonByUsername(@PathVariable String username,@RequestBody PersonRequestDto personRequestDto) {
        try {
            Person personToDelete = personService.getPersonByUsername(username);
            
            // Check authorization using the request DTO
            if (!personToDelete.getUsername().equals(personRequestDto.getUsername())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
            }
            personService.deletePersonByUsername(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
        }
    }
    
}