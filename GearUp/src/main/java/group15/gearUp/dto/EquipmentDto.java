package group15.gearUp.dto;

import java.time.LocalDate;

import group15.gearUp.model.Equipment;

public class EquipmentDto {

    //Equipment Attributes
    private int EquipmentID;
    private String title;
    private String description;
    private double price;
    private int stock;
    private String image;
    private LocalDate archivedDate;
    private boolean isApproved;
    private int managerId;

//    private ManagerDto manager;

    @SuppressWarnings("unused")
    public EquipmentDto(){
    }

    //Constructor
    public EquipmentDto(Equipment equipment){
        this.EquipmentID = equipment.getEquipmentID();
        this.title = equipment.getTitle();
        this.description = equipment.getDescription();
        this.price = equipment.getPrice();
        this.stock = equipment.getStock();
        this.image = equipment.getImage();
        if (equipment.getArchivedDate() != null) {
            this.archivedDate = equipment.getArchivedDate().toLocalDate();
        }
        this.isApproved = equipment.isIsApproved();
        this.managerId = equipment.getManager().getUserID();
//        this.manager = new ManagerDto(Equipment.getManager());
    }

    //Generated Getters and Setters
    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int EquipmentID) {
        this.EquipmentID = EquipmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(LocalDate archivedDate) {
        this.archivedDate = archivedDate;
    }

    public boolean getIsApproved() {
        return isApproved;
    }

    public void setApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    
}
