package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Manager;
import java.util.List;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    // Find a manager by their email address
    Manager findManagerByEmail(String email);

    // Find managers by username
    List<Manager> findManagersByUsername(String username);

    // Find a manager by their user ID
    Manager findManagerByUserId(int id);

    // Delete a manager by email
    void deleteManagerByEmail(String email);

    // Get all managers
    List<Manager> findAll();
}
