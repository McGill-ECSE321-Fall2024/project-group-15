package group15.gameStore.dto;

import group15.gameStore.model.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDto {
    @NotBlank(message = "Order number is required")
    private String orderNumber;
    @NotNull(message = "Order status is required")
    private Status orderStatus;
    @NotNull(message = "Order price is required")
    @Min(value = 0, message = "The price must be non-negative")
    private double price;

    public OrderRequestDto(String orderNumber, Status orderStatus, double price) {
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.price = price;
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

}
