package group15.gearUp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gearUp.model.Equipment;
import group15.gearUp.repository.EmployeeRepository;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.ManagerRepository;
import group15.gearUp.exception.GearUpException;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository EquipmentRepo;
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * GetEquipmentByID: retrieves a Equipment by its unique ID
     * @param EquipmentID the unique identifier of the Equipment
     * @return the Equipment object with the specified ID
     * @throws GearUpException if no Equipment is found with the given ID
     */
    public Equipment getEquipmentByID(int EquipmentID) {
        Equipment equipment = EquipmentRepo.findEquipmentByEquipmentID(EquipmentID);
        if (equipment == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, String.format("There is no Equipment with ID %d", EquipmentID));
        }
        return equipment;
    }
    /**
     * GetEquipmentByTitle: retrieves a Equipment by its title
     * @param title the title of the Equipment
     * @return the Equipment object with the specified title
     * @throws GearUpException if no Equipment is found with the given title
     */
    public Equipment getEquipmentByTitle(String title) {
        Equipment equipment = EquipmentRepo.findEquipmentByTitle(title);
        if (equipment == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, String.format("There is no Equipment with title %s", title));
        }
        return equipment;
    }
    /**
     * GetEquipmentsByPrice: retrieves all Equipments with the specified price
     * @param price the price of the Equipments to retrieve
     * @return a list of Equipment objects with the specified price
     * @throws GearUpException if no Equipments are found with the specified price
     */
    public List<Equipment> getEquipmentsByPrice(double price) {
        List<Equipment> equipment = EquipmentRepo.findEquipmentsByPrice(price);
        if (equipment.isEmpty()) {
            throw new GearUpException(HttpStatus.NOT_FOUND, String.format("There is no Equipment with price %f", price));
        }
        return equipment;
    }
    /**
     * GetAllEquipments: retrieves all Equipments in the system
     * @return a list of all Equipment objects
     * @throws GearUpException if there are no Equipments in the system
     */
    public List<Equipment> getAllEquipments() {
        List<Equipment> equipment = EquipmentRepo.findAll();
        if (equipment.isEmpty()) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "There are no Equipments in the system");
        }
        return equipment;
    }

    /**
     * CreateEquipment: creates a new Equipment with specified details
     * @param title the title of the Equipment
     * @param description the description of the Equipment
     * @param price the price of the Equipment
     * @param stock the stock quantity of the Equipment
     * @param image the image URL or path of the Equipment
     * @param isApproved the approval status of the Equipment
     * @param manager the manager overseeing the Equipment creation
     * @param employee the employee creating the Equipment
     * @return the newly created Equipment object
     * @throws GearUpException if any required attribute is missing or invalid
     */
    @Transactional
    public Equipment createEquipment(String title, String description, double price, int stock, String image, boolean isApproved, int managerId) {
        if (title.isBlank() || description.isBlank() || image.isBlank()) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Invalid Equipment creation request: missing attributes");
        }
        if (price < 0 || stock < 0) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "The price or stock of a Equipment cannot be negative");
        }
        
        if (managerRepo.findManagerByUserID(managerId) == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND,"The manager does not exist");
        }
        Equipment equipment = new Equipment(title, description, price, stock, image, isApproved, managerRepo.findManagerByUserID(managerId));
        return EquipmentRepo.save(equipment);
    }
    
    /**
     * DeleteEquipment: deletes a Equipment from the system
     * @param equipmentToDelete the Equipment object to delete
     * @param employee the Employee performing the deletion
     * @throws GearUpException if Equipment or employee is missing, or employee is unauthorized
     */
    @Transactional
    public void deleteEquipment(Equipment equipmentToDelete) {
        if (equipmentToDelete == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Invalid Equipment creation request: missing attributes");
        }
        //Search Equipment
        Equipment equipment = EquipmentRepo.findEquipmentByEquipmentID(equipmentToDelete.getEquipmentID());
        if (equipment == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "The Equipment to delete does not exist");
        }
        EquipmentRepo.delete(equipment);
    }

    /**
     * ArchiveEquipment: archives a Equipment in the system
     * @param equipmentToArchive the Equipment object to archive
     * @param employee the Employee performing the archival
     * @return the updated archived Equipment object
     * @throws GearUpException if Equipment or employee is missing, or employee is unauthorized
     */
    @Transactional
    public Equipment archiveEquipment(Equipment equipmentToArchive) {
        if (equipmentToArchive == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Invalid Equipment archival request: missing attributes");
        }
        if (!equipmentToArchive.isIsApproved()) {
            throw new GearUpException(HttpStatus.UNAUTHORIZED, "The archival of the Equipment has not been approved by the manager");
        }
        //Search Equipment
        Equipment equipment = EquipmentRepo.findEquipmentByEquipmentID(equipmentToArchive.getEquipmentID());
        if (equipment.getArchivedDate() != null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "The Equipment is already archived");
        }
        equipment.setArchivedDate(Date.valueOf(LocalDate.now()));
        equipment.setIsApproved(false);
        return EquipmentRepo.save(equipment);
    }

    /**
     * UnarchiveEquipment: unarchives a Equipment in the system
     * @param equipmentToUnarchive the Equipment object to unarchive
     * @param employee the Employee performing the unarchival
     * @return the updated unarchived Equipment object
     * @throws GearUpException if Equipment or employee is missing, employee is unauthorized, or Equipment does not exist
     */
    @Transactional
    public Equipment unarchiveEquipment(Equipment equipmentToUnarchive) {
        if (equipmentToUnarchive == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Invalid Equipment unarchival request: missing attributes");
        }
        //Search Equipment
        Equipment equipment = EquipmentRepo.findEquipmentByEquipmentID(equipmentToUnarchive.getEquipmentID());
        if (equipment.getArchivedDate() == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "The Equipment is already unarchived");
        }
        equipment.setArchivedDate(null);
        return EquipmentRepo.save(equipment);
    }

    /**
     * UpdateEquipment: updates an existing Equipment's information
     * @param EquipmentID the ID of the Equipment to update
     * @param updatedEquipment the new Equipment information to update to
     * @param employee the Employee requesting the update
     * @return the updated Equipment object
     * @throws GearUpException if the update request is invalid
     */
    @Transactional
    public Equipment updateEquipment(int EquipmentID, Equipment updatedEquipment) {
        if (updatedEquipment == null || updatedEquipment.getTitle().isBlank() || updatedEquipment.getDescription().isBlank() || updatedEquipment.getImage().isBlank() || updatedEquipment.getManager() == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Invalid Equipment creation request: missing attributes");
        }
        Equipment existingEquipment = EquipmentRepo.findEquipmentByEquipmentID(EquipmentID);
        if (existingEquipment == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Equipment with the specified ID does not exist.");
        }
        String title = updatedEquipment.getTitle();
        String description = updatedEquipment.getDescription();
        double price = updatedEquipment.getPrice();
        int stock = updatedEquipment.getStock();
        String image = updatedEquipment.getImage();
        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty() || image == null || image.trim().isEmpty()) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Equipment Attribute is required");
        }
        if (price < 0 || stock < 0) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "The price or stock of a Equipment cannot be negative.");
        }
        existingEquipment.setTitle(title);
        existingEquipment.setDescription(description);
        existingEquipment.setPrice(price);
        existingEquipment.setStock(stock);
        existingEquipment.setImage(image);

        return EquipmentRepo.save(existingEquipment);
    }

    /**
     * UpdateEquipmentApproval: updates the approval status of a Equipment
     * @param equipmentToUpdate the Equipment object to update approval for
     * @param newIsApproved the new approval status
     * @param employee the Employee performing the approval update
     * @return the updated Equipment object with new approval status
     * @throws GearUpException if Equipment or employee is missing, employee is unauthorized, or Equipment does not exist
     */
    @Transactional
    public Equipment updateEquipmentApproval(Equipment equipmentToUpdate, Boolean newIsApproved) {
        if (equipmentToUpdate == null || equipmentToUpdate.getTitle().isBlank() || equipmentToUpdate.getDescription().isBlank() || equipmentToUpdate.getImage().isBlank() || equipmentToUpdate.getManager() == null) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Invalid Equipment approval change request: missing attributes");
        }
        //Search Equipment
        Equipment equipment = EquipmentRepo.findEquipmentByEquipmentID(equipmentToUpdate.getEquipmentID());
        equipment.setIsApproved(newIsApproved);
        return EquipmentRepo.save(equipment);
    }

}