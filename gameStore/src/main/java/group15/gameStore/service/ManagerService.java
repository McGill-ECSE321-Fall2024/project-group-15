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

    public Manager getManagerByID(int managerID) {
        Manager manager = managerRepo.findManagerByUserID(managerID);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with ID %d", managerID));
        }
        return manager;
    }

    public Manager getManagerByEmail(String email) {
        Manager manager = managerRepo.findManagerByEmail(email);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with email %s", email));
        }
        return manager;
    }

    public List<Manager> getManagersByUsername(String username) {
        List<Manager> managers = managerRepo.findManagersByUsername(username);
        if (managers.size() == 0) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no manager with username %s", username));
        }
        return managers;
    }

    public List<Manager> getAllManagers() {
        List<Manager> managers = managerRepo.findAll();
        if (managers.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no managers in the system");
        }
        return managers;
    }

    @Transactional
    public Manager createManager(String username, String password, String email, boolean isActive, Employee employee) {
        if (username.isBlank() || password.isBlank() || email.isBlank() || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        if (!employee.isIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can create a manager account");
        }
        Manager manager = new Manager(username, password, email, isActive, true);
        return managerRepo.save(manager);
    }

    @Transactional
    public void deleteManager(Manager managerToDelete, Employee employee) {
        if (managerToDelete == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
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

    @Transactional
    public Manager updateManager(int managerID, Manager updatedManager, Employee employee) {
        if (updatedManager == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        if (!employee.getIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "Only a manager can update the type of account");
        }
        Manager manager = managerRepo.findManagerByUserID(managerID);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Cannot update the manager since they do not exist");
        }
        manager.setUsername(updatedManager.getUsername());
        manager.setPassword(manager.getPassword());
        manager.setEmail(updatedManager.getEmail());
        manager.setIsActive(updatedManager.getIsActive());
        manager.setIsManager(updatedManager.getIsManager());
        return managerRepo.save(manager);
    }

}
