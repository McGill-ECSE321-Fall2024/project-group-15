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

import group15.gameStore.dto.ManagerRequestDto;
import group15.gameStore.dto.ManagerResponseDto;
import group15.gameStore.dto.OrderRequestDto;
import group15.gameStore.dto.OrderResponseDto;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Manager;
import group15.gameStore.model.Order;
import group15.gameStore.service.ManagerService;

@RestController
public class ManagerController {
    @Autowired
    private ManagerService managerService;

    @PostMapping("/manager/create")
    public ResponseEntity<ManagerResponseDto> createManager(@RequestBody ManagerRequestDto managerToCreate) {
        try {
            Manager createdManager = managerService.createManager(managerToCreate.getUsername(), managerToCreate.getPassword(), managerToCreate.getEmail(), managerToCreate.getIsActive(), managerToCreate.getEmployee());
            return new ResponseEntity<>(new ManagerResponseDto(createdManager), HttpStatus.CREATED);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @PutMapping("/manager/{managerID}/update")
    public ResponseEntity<ManagerResponseDto> updateManager(@PathVariable("managerID") int managerID, ManagerRequestDto managerToUpdate) {
        try {
            Manager updatedManager = managerService.updateManager(managerID,managerToUpdate.getManager(), managerToUpdate.getEmployee());
            return new ResponseEntity<>(new ManagerResponseDto(updatedManager), HttpStatus.OK); 
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        } 
    }

    @GetMapping("/manager/{managerID}")
    public ResponseEntity<ManagerResponseDto> getManagerByID(@PathVariable("managerID") int managerID) {
        try {
            Manager manager = managerService.getManagerByID(managerID);
            ManagerResponseDto managerResponse = new ManagerResponseDto(manager);
            return new ResponseEntity<>(managerResponse, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @GetMapping("/manager/{managerEmail}")
    public ResponseEntity<ManagerResponseDto> getManagerByEmail(@PathVariable("managerEmail") String managerEmail) {
        try {
            Manager manager = managerService.getManagerByEmail(managerEmail);
            ManagerResponseDto managerResponse = new ManagerResponseDto(manager);
            return new ResponseEntity<>(managerResponse, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @GetMapping("/manager/{managerUsername}")
    public ResponseEntity<List<ManagerResponseDto>> getManagersByUsername(@PathVariable("managerUsername") String managerUsername) {
        try {
            List<Manager> managerList = managerService.getManagersByUsername(managerUsername);
            if (managerList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<ManagerResponseDto> managerResponseDtos = managerList.stream()
                    .map(ManagerResponseDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(managerResponseDtos, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @GetMapping("/manager")
    public ResponseEntity<List<ManagerResponseDto>> getAllManagers() {
        try {
            List<Manager> managerList = managerService.getAllManagers();
            if (managerList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<ManagerResponseDto> managerResponseDtos = managerList.stream()
                    .map(ManagerResponseDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(managerResponseDtos, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @DeleteMapping("/manager/{managerID}/delete")
    public ResponseEntity<Void> deleteManagerByID(@PathVariable("managerID") int managerID, @RequestBody ManagerRequestDto managerToDelete) {
        try {
            Manager deletedManager = managerService.getManagerByID(managerID);
            managerService.deleteManager(deletedManager, managerToDelete.getEmployee());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }
    
    @DeleteMapping("/manager/{managerEmail}/delete")
    public ResponseEntity<Void> deleteManagerByEmail(@PathVariable("managerEmail") String managerEmail, @RequestBody ManagerRequestDto managerToDelete) {
        try {
            Manager deletedManager = managerService.getManagerByEmail(managerEmail);
            managerService.deleteManager(deletedManager, managerToDelete.getEmployee());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }
    
}