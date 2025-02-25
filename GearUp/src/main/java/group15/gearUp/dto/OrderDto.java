package group15.gearUp.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group15.gearUp.model.Customer;
import group15.gearUp.model.Order;
import group15.gearUp.model.Status;

public class OrderDto {

    //Order Attributes
    private int orderID;
    private String orderNumber;
    private Status orderStatus;
    private double price;

    private List<Integer> Equipments;
    private int customerId;

    @SuppressWarnings("unused")
    private OrderDto(){
    }

    //Constructor
    public OrderDto(Order order){
        this.orderID = order.getOrderID();
        this.orderNumber = order.getOrderNumber();
        this.orderStatus = order.getOrderStatus();
        this.price = order.getPrice();
        this.Equipments = order.getEquipments().stream().map(Equipment -> Equipment.getEquipmentID()).collect(Collectors.toList());
        this.customerId = order.getCustomer().getUserID();
    }
    
    public OrderDto(String orderNumber, Status orderStatus, double price, int customerId){
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.price = price;
        this.customerId = customerId;
        this.Equipments = new ArrayList<Integer>();
    }

    //Generated Getters and Setters
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Integer> getEquipments() {
        return Equipments;
    }

    public void setEquipments(List<Integer> Equipments) {
        this.Equipments = Equipments;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
}
