package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Employee;
import group15.gameStore.model.Manager;

import group15.gameStore.repository.ManagerRepository;

import group15.gameStore.exception.GameStoreException;

import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepo;

    public Manager findManagerByID(int managerID) {
        Manager manager = managerRepo.findManagerByUserID(managerID);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with ID %d", managerID));
        }
        return manager;
    }

    public Manager findManagerByEmail(String email) {
        Manager manager = managerRepo.findManagerByEmail(email);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with email %s", email));
        }
        return manager;
    }

    public List<Manager> findManagersByUsername(String username) {
        List<Manager> managers = managerRepo.findManagersByUsername(username);
        if (managers.size() == 0) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with username %s", username));
        }
        return managers;
    }

    public List<Manager> findAllManagers() {
        List<Manager> managers = managerRepo.findAll();
        if (managers.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no managers in the system");
        }
        return managers;
    }

    @Transactional
    public Manager createManager(String username, String password, String email, boolean isActive, Employee employee) {
        if (!employee.isIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can create a manager account");
        }
        Manager manager = new Manager(username, password, email, isActive, true);
        return managerRepo.save(manager);
    }

    @Transactional
    public void deleteManager(Manager managerToDelete, Employee employee) {
        if (!employee.isIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can delete a manager account");
        }
        if (managerRepo.findManagerByUserID(managerToDelete.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The manager to delete does not exist");
        }
        managerRepo.delete(managerToDelete);
    }

    @Transactional
    public Manager updateManagerUsername(Manager managerToUpdate, String newUsername) {
        Manager manager = managerRepo.findManagerByUserID(managerToUpdate.getUserID());
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the username since the manager does not exist");
        }
        manager.setUsername(newUsername);
        return managerRepo.save(manager);
    }
    
    @Transactional
    public Manager updateManagerPassword(Manager managerToUpdate, String newPassword) {
        Manager manager = managerRepo.findManagerByUserID(managerToUpdate.getUserID());
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the password since the manager does not exist");
        }
        manager.setPassword(newPassword);
        return managerRepo.save(manager);
    }

    @Transactional
    public Manager updateManagerEmail(Manager managerToUpdate, String newEmail) {
        Manager manager = managerRepo.findManagerByUserID(managerToUpdate.getUserID());
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the email since the manager does not exist");
        }
        manager.setEmail(newEmail);
        return managerRepo.save(manager);
    }

    @Transactional
    public Manager updateManagerIsActive(Manager managerToUpdate, boolean newIsActive, Employee employee) {
        if (!employee.getIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can update the activity of an account");
        }
        Manager manager = managerRepo.findManagerByUserID(managerToUpdate.getUserID());
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the activity since the manager does not exist");
        }
        manager.setIsActive(newIsActive);
        return managerRepo.save(manager);
    }

    @Transactional
    public Manager updateManagerIsManager(Manager managerToUpdate, boolean newIsManager, Employee employee) {
        if (!employee.getIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can update the type of account");
        }
        Manager manager = managerRepo.findManagerByUserID(managerToUpdate.getUserID());
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the type of account since the manager does not exist");
        }
        manager.setIsManager(newIsManager);
        return managerRepo.save(manager);
    }

}