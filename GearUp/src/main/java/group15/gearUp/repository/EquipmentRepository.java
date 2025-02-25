package group15.gearUp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import group15.gearUp.model.Equipment;

public interface EquipmentRepository extends CrudRepository<Equipment, String>{

    Equipment findEquipmentByEquipmentID(int EquipmentID);

    Equipment findEquipmentByTitle(String title);
    // Equipment findEquipmentByType(String type);
  
    List<Equipment> findEquipmentsByPrice(double price);

    @SuppressWarnings("null")
    List<Equipment> findAll();

    
}
