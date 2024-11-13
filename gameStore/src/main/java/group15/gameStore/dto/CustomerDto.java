package group15.gameStore.dto;

import group15.gameStore.model.Customer;

public class CustomerDto extends PersonDto {

    //Customer attributes
    private String address;
    private String phoneNumber;

    // Constructor
    public CustomerDto(Customer customer) {
        this.userID = customer.getUserID();
        this.username = customer.getUsername();
        this.password = customer.getPassword();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.phoneNumber = customer.getPhoneNumber();
    }

    public CustomerDto(String aUsername, String aPassword, String aEmail, String aAddress, String aPhoneNumber){
        super(aUsername, aPassword, aEmail);
        address = aAddress;
        phoneNumber = aPhoneNumber;
    }



    // Getters and Setters
    public int getUserId() {
        return userID;
    }

    public void setUserId(int userId) {
        this.userID = userId;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public CustomerDto(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }



}
