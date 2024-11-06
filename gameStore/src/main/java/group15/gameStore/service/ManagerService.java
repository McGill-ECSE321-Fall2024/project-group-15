package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Manager;
import group15.gameStore.repository.ManagerRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepo;

    public Manager findManagerByID(int managerID) {
        Manager manager = managerRepo.findManagerByUserID(managerID);
        if (manager == null) {
            //Raise Error
        }
        return manager;
    }

    public Manager findManagerByEmail(String email) {
        Manager manager = managerRepo.findManagerByEmail(email);
        if (manager == null) {
            //Raise Error
        }
        return manager;
    }

    public List<Manager> findManagersByUsername(String username) {
        List<Manager> managers = managerRepo.findManagersByUsername(username);
        if (managers.size() == 0) {
            //Raise Error
        }
        return managers;
    }

    public List<Manager> findAllManagers() {
        List<Manager> managers = managerRepo.findAll();
        if (managers.isEmpty()) {
            //Raise Error
        }
        return managers;
    }

    @Transactional
    public Manager createManager() {
        return ;
    }

    @Transactional
    public Manager deleteManager() {
        return ;
    }

    @Transactional
    public Manager updateManager() {
        return ;
    }
    
}