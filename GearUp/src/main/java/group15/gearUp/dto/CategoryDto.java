package group15.gearUp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group15.gearUp.model.Category;

public class CategoryDto {

    //Category Attributes
    private int categoryID;
    private String name;

    private List<EquipmentDto> Equipments;

    @SuppressWarnings("unused")
    public CategoryDto(){
    }

    //Constructor
    public CategoryDto(Category categoryDto){
        this.categoryID = categoryDto.getCategoryID();
        this.name = categoryDto.getName();
        this.Equipments = categoryDto.getEquipments().stream().map(EquipmentDto::new).collect(Collectors.toList());
    }

    public CategoryDto(String name){
        this.name = name;
        this.Equipments = new ArrayList<EquipmentDto>();;
    }

    //Generated Getters and Setters
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EquipmentDto> getEquipments() {
        return Equipments;
    }

    public void setEquipments(List<EquipmentDto> Equipments) {
        this.Equipments = Equipments;
    }

    
}
