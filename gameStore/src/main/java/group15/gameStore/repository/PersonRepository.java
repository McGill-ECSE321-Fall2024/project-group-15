package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;

import group15.gameStore.model.Category;
import group15.gameStore.model.Person;
import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    
    // Find a Person by user ID
    Person findByUserID(Integer userID);

    // Find a Person by username
    Person findByUsername(String username);

    // Find a Person by email
    Person findByEmail(String email);

    // Delete a Person by username
    void deleteByUsername(String username);

    // Get all Person records
    List<Person> findAll();
}
