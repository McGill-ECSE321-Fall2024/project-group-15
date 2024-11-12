package group15.gameStore.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Order;
import group15.gameStore.model.Status;

public class OrderDto {

    //Order Attributes
    private int orderID;
    private String orderNumber;
    private Status orderStatus;
    private double price;

    private List<GameDto> games;
    private CustomerDto customer;

    @SuppressWarnings("unused")
    private OrderDto(){
    }

    //Constructor
    public OrderDto(Order order){
        this.orderID = order.getOrderID();
        this.orderNumber = order.getOrderNumber();
        this.orderStatus = order.getOrderStatus();
        this.price = order.getPrice();
        this.games = order.getGames().stream().map(GameDto::new).collect(Collectors.toList());
        this.customer = new CustomerDto(order.getCustomer());
    }
    
    public OrderDto(String aOrderNumber, Status aOrderStatus, double aPrice, Customer aCustomer){
        orderNumber = aOrderNumber;
        orderStatus = aOrderStatus;
        price = aPrice;
        customer = new CustomerDto(aCustomer);
        games = new ArrayList<GameDto>();
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

    public List<GameDto> getGames() {
        return games;
    }

    public void setGames(List<GameDto> games) {
        this.games = games;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    
}
