package group15.gearUp.controller;

import java.sql.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import group15.gearUp.dto.EquipmentDto;
import group15.gearUp.model.Equipment;
import group15.gearUp.service.EmployeeService;
import group15.gearUp.service.EquipmentService;
import group15.gearUp.service.ManagerService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
@RestController
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * CreateEquipment: creates a new Equipment record
     * @param equipmentDto the EquipmentDto containing the Equipment details
     * @param managerDto the ManagerDto associated with the Equipment
     * @param employeeDto the EmployeeDto who creates the Equipment
     * @return the created Equipment and HTTP Status "CREATED"
     */
    @PostMapping("/Equipment")
    public ResponseEntity<EquipmentDto> createEquipment(@RequestBody EquipmentDto equipmentDto) {
        Equipment createdEquipment = equipmentService.createEquipment(equipmentDto.getTitle(), equipmentDto.getDescription(), equipmentDto.getPrice(),
            equipmentDto.getStock(), equipmentDto.getImage(), equipmentDto.getIsApproved(), equipmentDto.getManagerId());
        
            return new ResponseEntity<>(new EquipmentDto(createdEquipment), HttpStatus.CREATED);
    }

    /**
     * UpdateEquipment: updates an existing Equipment
     * @param EquipmentId the ID of the Equipment to update
     * @param equipmentDto the EquipmentDto containing updated Equipment details
     * @param employeeDto the EmployeeDto who updates the Equipment
     * @return the updated Equipment and HTTP Status "OK"
     */
    @PutMapping("/Equipment/{EquipmentId}")
    public ResponseEntity<EquipmentDto> updateEquipment(@PathVariable int EquipmentId, @RequestBody EquipmentDto equipmentDto) {
        Equipment equipmentToUpdate = equipmentService.getEquipmentByID(EquipmentId);
        equipmentToUpdate.setTitle(equipmentDto.getTitle());
        equipmentToUpdate.setDescription(equipmentDto.getDescription());
        equipmentToUpdate.setPrice(equipmentDto.getPrice());
        equipmentToUpdate.setStock(equipmentDto.getStock());
        equipmentToUpdate.setImage(equipmentDto.getImage());
        if (equipmentToUpdate.getArchivedDate() != null) {
            equipmentToUpdate.setArchivedDate(Date.valueOf(equipmentDto.getArchivedDate()));
        }
        equipmentToUpdate.setIsApproved(equipmentDto.getIsApproved());
        Equipment updatedEquipment = equipmentService.updateEquipment(EquipmentId, equipmentToUpdate);
        return new ResponseEntity<>(new EquipmentDto(updatedEquipment), HttpStatus.OK);
    }

    /**
    * UpdateEquipmentApproval: updates the approval status of a Equipment
    * @param EquipmentId the ID of the Equipment to update
    * @param newIsApproved the new approval status for the Equipment
    * @param employeeDto the EmployeeDto who requests the approval update
    * @return the updated Equipment with the new approval status and HTTP Status "OK"
    */
    @PutMapping("/Equipment/{EquipmentId}/approval")
    public ResponseEntity<EquipmentDto> updateEquipmentApproval(@PathVariable int EquipmentId, @RequestParam Boolean newIsApproved) {
        Equipment equipmentToUpdate = equipmentService.getEquipmentByID(EquipmentId);
        
        Equipment updatedEquipment = equipmentService.updateEquipmentApproval(equipmentToUpdate, newIsApproved);
        return new ResponseEntity<>(new EquipmentDto(updatedEquipment), HttpStatus.OK);
    }

    /**
     * GetEquipmentById: retrieves a Equipment by ID
     * @param EquipmentId the ID of the Equipment to retrieve
     * @return the Equipment information and HTTP Status "OK"
     */
    @GetMapping("/Equipment/{EquipmentId}")
    public ResponseEntity<EquipmentDto> getEquipmentById(@PathVariable int EquipmentId) {
        Equipment equipment = equipmentService.getEquipmentByID(EquipmentId);
        return new ResponseEntity<>(new EquipmentDto(equipment), HttpStatus.OK);
    }

    /**
     * GetEquipmentByTitle: retrieves a Equipment by title
     * @param title the title of the Equipment to retrieve
     * @return the Equipment information and HTTP Status "OK"
     */
    @GetMapping("/Equipment/title/{title}")
    public ResponseEntity<EquipmentDto> getEquipmentByTitle(@PathVariable String title) {
        Equipment equipment = equipmentService.getEquipmentByTitle(title);
        return new ResponseEntity<>(new EquipmentDto(equipment), HttpStatus.OK);
    }

    /**
     * GetEquipmentsByPrice: retrieves Equipments by price
     * @param price the price to filter Equipments by
     * @return a list of Equipments with the specified price and HTTP Status "OK"
     */
    @GetMapping("/Equipments/price/{price}")
    public ResponseEntity<List<EquipmentDto>> getEquipmentsByPrice(@PathVariable double price) {
        List<Equipment> equipment = equipmentService.getEquipmentsByPrice(price);
        List<EquipmentDto> equipmentDtos = equipment.stream().map(EquipmentDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(equipmentDtos, HttpStatus.OK);
    }

    /**
     * GetAllEquipments: retrieves all Equipments in the system
     * @return a list of all Equipments and HTTP Status "OK"
     */
    @GetMapping("/Equipments")
    public ResponseEntity<List<EquipmentDto>> getAllEquipments() {
        List<Equipment> equipment = equipmentService.getAllEquipments();
        List<EquipmentDto> equipmentDtos = equipment.stream().map(EquipmentDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(equipmentDtos, HttpStatus.OK);
    }

    /**
     * ArchiveEquipment: archives a Equipment
     * @param EquipmentId the ID of the Equipment to archive
     * @param employeeDto the EmployeeDto who archives the Equipment
     * @return the archived Equipment and HTTP Status "OK"
     */
    @PutMapping("/Equipment/archive/{EquipmentId}")
    public ResponseEntity<EquipmentDto> archiveEquipment(@PathVariable int EquipmentId) {
        Equipment equipmentToArchive = equipmentService.getEquipmentByID(EquipmentId);
        Equipment archivedEquipment = equipmentService.archiveEquipment(equipmentToArchive);
        return new ResponseEntity<>(new EquipmentDto(archivedEquipment), HttpStatus.OK);
    }

    /**
     * UnarchiveEquipment: unarchives a Equipment
     * @param EquipmentId the ID of the Equipment to unarchive
     * @param employeeDto the EmployeeDto who unarchives the Equipment
     * @return the unarchived Equipment and HTTP Status "OK"
     */
    @PutMapping("/Equipment/unarchive/{EquipmentId}")
    public ResponseEntity<EquipmentDto> unarchiveEquipment(@PathVariable int EquipmentId) {
        Equipment equipmentToUnarchive = equipmentService.getEquipmentByID(EquipmentId);
        Equipment unarchivedEquipment = equipmentService.unarchiveEquipment(equipmentToUnarchive);
        return new ResponseEntity<>(new EquipmentDto(unarchivedEquipment), HttpStatus.OK);
    }

    /**
     * DeleteEquipment: deletes a Equipment by ID
     * @param EquipmentId the ID of the Equipment to delete
     * @param employeeDto the EmployeeDto who deletes the Equipment
     * @return HTTP Status "NO CONTENT" if the deletion is successful
     */
    @DeleteMapping("/Equipment/{EquipmentId}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable int EquipmentId) {
        Equipment equipmentToDelete = equipmentService.getEquipmentByID(EquipmentId);
        equipmentService.deleteEquipment(equipmentToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}