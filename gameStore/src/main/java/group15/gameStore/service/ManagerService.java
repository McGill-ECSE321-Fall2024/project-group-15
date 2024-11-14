package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Employee;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.ManagerRepository;

import group15.gameStore.exception.GameStoreException;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * GetManagerByID: retrieves a manager by their ID
     * @param managerID the ID of the manager to retrieve
     * @return the Manager with the specified ID
     * @throws GameStoreException if the manager with the given ID is not found
     */
    public Manager getManagerByID(int managerID) {
        Manager manager = managerRepo.findManagerByUserID(managerID);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with ID %d", managerID));
        }
        return manager;
    }

    /**
     * GetManagerByEmail: retrieves a manager by their email
     * @param email the email of the manager to retrieve
     * @return the Manager with the specified email
     * @throws GameStoreException if the manager with the given email is not found
     */
    public Manager getManagerByEmail(String email) {
        Manager manager = managerRepo.findManagerByEmail(email);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with email %s", email));
        }
        return manager;
    }

    /**
     * GetManagersByUsername: retrieves a list of managers by their username
     * @param username the username of the managers to retrieve
     * @return a list of Managers with the specified username
     * @throws GameStoreException if no managers with the given username are found
     */
    public List<Manager> getManagersByUsername(String username) {
        List<Manager> managers = managerRepo.findManagersByUsername(username);
        if (managers.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with username %s", username));
        }
        return managers;
    }

    /**
     * GetAllManagers: retrieves all managers in the system
     * @return a list of all Managers in the system
     * @throws GameStoreException if no managers are found in the system
     */
    public List<Manager> getAllManagers() {
        List<Manager> managers = managerRepo.findAll();
        if (managers.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no managers in the system");
        }
        return managers;
    }

    /**
     * CreateManager: creates a new manager with username, password, email, and active status
     * @param username the username of the manager
     * @param password the password of the manager
     * @param email the email of the manager
     * @param isActive the active status of the manager
     * @param employee the employee who is creating the manager account
     * @return the newly created Manager object
     * @throws GameStoreException if any field is missing or invalid
     */
    @Transactional
    public Manager createManager(String username, String password, String email, boolean isActive, Employee employee) {
        if (username.isBlank() || password.isBlank() || email.isBlank() || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid manager creation request: missing attributes");
        if (username == null || username.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        if (password == null || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }
        if (employee == null || employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "The employee making the request does not exist.");
        }
        if (!employee.isIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can create a manager account.");
        }
        Manager manager = new Manager(username, password, email, isActive, true);
        managerRepo.save(manager);
        return manager;
    }

    /**
     * DeleteManager: deletes a manager account from the system
     * @param managerToDelete the Manager object representing the manager to delete
     * @param employee the Employee object representing the employee making the deletion request
     * @throws GameStoreException if the manager to delete or employee is invalid, or employee is unauthorized
     */
    @Transactional
    public void deleteManager(Manager managerToDelete, Employee employee) {
        if (managerToDelete == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid manager creation request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        if (!employee.isIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can delete a manager account");
        }
        if (managerRepo.findManagerByUserID(managerToDelete.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The manager to delete does not exist");
        }
        managerRepo.delete(managerToDelete);
    }

    /**
     * updateManager: Updates an existing manager's information.
     * @param managerID the ID of the manager to update
     * @param updatedManager the new manager information to update to
     * @param employee the employee requesting the update
     * @return the updated Manager object
     * @throws GameStoreException if the update request is invalid or unauthorized
     */
    @Transactional
    public Manager updateManager(int managerID, Manager updatedManager, Employee employee) {
        if (updatedManager == null || updatedManager.getUsername().isBlank() || updatedManager.getPassword().isBlank() || updatedManager.getEmail().isBlank() || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid manager creation request: missing attributes");
        if (updatedManager == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid update request: missing manager or employee information.");
        }
                Employee existingEmployee = employeeRepo.findByUserID(employee.getUserID());
        if (existingEmployee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' does not exist", employee.getUsername()));
        }
        if (!existingEmployee.getIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can update manager accounts.");
        }
        Manager existingManager = managerRepo.findManagerByUserID(managerID);
        if (existingManager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Cannot update manager: manager with the specified ID does not exist.");
        }
        String username = updatedManager.getUsername();
        if (username == null || username.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Username is required.");
        }
        String password = updatedManager.getPassword();
        if (password == null || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
        String email = updatedManager.getEmail();
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }

        existingManager.setUsername(username);
        existingManager.setPassword(password); 
        existingManager.setEmail(email);
        existingManager.setIsActive(updatedManager.getIsActive());
        existingManager.setIsManager(updatedManager.getIsManager());

        return managerRepo.save(existingManager);
    }

}
