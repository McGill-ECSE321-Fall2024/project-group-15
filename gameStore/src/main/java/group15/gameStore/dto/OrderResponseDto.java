package group15.gameStore.dto;

import group15.gameStore.model.Order;
import group15.gameStore.model.Status;

public class OrderResponseDto {
    private int orderID;
    private String orderNumber;
    private Status orderStatus;
    private double price;
    
    //Default constructor
    protected OrderResponseDto() {}

    public OrderResponseDto(Order order) {
        this.orderID = order.getOrderID();
        this.orderNumber = order.getOrderNumber();
        this.orderStatus = order.getOrderStatus();
        this.price = order.getPrice();
    }

    public int getOrderID() {
        return orderID;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public double getPrice() {
        return price;
    }
}
