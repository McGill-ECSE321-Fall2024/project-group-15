package group15.gameStore.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.dto.ManagerDto;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Manager;
import group15.gameStore.service.EmployeeService;
import group15.gameStore.service.ManagerService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ManagerController{

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * CreateManager: creates a new manager
     * @param managerDto the ManagerDto containing the manager details
     * @param employeeDto the EmployeeDto of the employee requesting the creation
     * @return the created manager and HTTP Status "CREATED"
     */
    @PostMapping("/manager")
    public ResponseEntity<ManagerDto> createManager(@RequestBody ManagerDto managerDto) {
        Manager createdManager = managerService.createManager(
                managerDto.getUsername(), managerDto.getPassword(), managerDto.getEmail(), 
                managerDto.isActive(), managerDto.isManager());
        return new ResponseEntity<>(new ManagerDto(createdManager), HttpStatus.CREATED);
    }

    /**
     * UpdateManager: updates an existing manager
     * @param managerId the ID of the manager to update
     * @param managerDto the ManagerDto containing updated manager details
     * @param employeeDto the EmployeeDto of the employee requesting the update
     * @return the updated manager and the HTTP status "OK"
     */
    @PutMapping("/manager/{managerId}")
    public ResponseEntity<ManagerDto> updateManager(@PathVariable int managerId, @RequestBody ManagerDto managerDto) {
        Manager managerToUpdate = managerService.getManagerByID(managerId);
        managerToUpdate.setUsername(managerDto.getUsername());
        managerToUpdate.setEmail(managerDto.getEmail());
        managerToUpdate.setPassword(managerDto.getPassword());
        managerToUpdate.setIsActive(managerDto.isActive());
        managerToUpdate.setIsManager(managerDto.isManager());
        Manager updatedManager = managerService.updateManager(managerId, managerToUpdate);
        return new ResponseEntity<>(new ManagerDto(updatedManager), HttpStatus.OK);
    }

    /**
     * GetManagerById: retrieves a manager by ID
     * @param managerId the ID of the manager to retrieve
     * @return the desired manager information and the HTTP status "OK"
     */
    @GetMapping("/manager/{managerId}")
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable int managerId) {
        Manager manager = managerService.getManagerByID(managerId);
        return new ResponseEntity<>(new ManagerDto(manager), HttpStatus.OK);
    }

    /**
     * GetManagerByEmail: retrieves a manager by email
     * @param email the email of the manager to retrieve
     * @return the desired manager information and the HTTP status "OK"
     */
    @GetMapping("/manager/email/{email}")
    public ResponseEntity<ManagerDto> getManagerByEmail(@PathVariable String email) {
        Manager manager = managerService.getManagerByEmail(email);
        return new ResponseEntity<>(new ManagerDto(manager), HttpStatus.OK);
    }

    /**
     * GetManagersByUsername: retrieves managers by username
     * @param username the username of the managers to retrieve
     * @return a list of managers with the specified username and the HTTP status "OK"
     */
    @GetMapping("/manager/username/{username}")
    public ResponseEntity<List<ManagerDto>> getManagersByUsername(@PathVariable String username) {
        List<Manager> managers = managerService.getManagersByUsername(username);
        List<ManagerDto> responseDtoList = managers.stream().map(ManagerDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * GetAllManagers: retrieves all manager records in the system
     * @return a list of all manager information and the HTTP status "OK"
     */
    @GetMapping("/managers")
    public ResponseEntity<List<ManagerDto>> getAllManagers() {
        List<Manager> managers = managerService.getAllManagers();
        List<ManagerDto> responseDtoList = managers.stream().map(ManagerDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * DeleteManager: deletes a manager by ID
     * @param managerId the ID of the manager to delete
     * @param employeeDto the EmployeeDto of the employee requesting the deletion
     * @return HTTP status "NO CONTENT" if the deletion is successful
     */
    @DeleteMapping("/manager/{managerId}")
    public ResponseEntity<Void> deleteManager(@PathVariable int managerId) {
        Manager managerToDelete = managerService.getManagerByID(managerId);
        managerService.deleteManager(managerToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}