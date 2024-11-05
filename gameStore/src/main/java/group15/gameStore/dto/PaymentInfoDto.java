package main.java.group15.gameStore.dto;

import java.sql.Date;

import group15.gameStore.model.Customer;
import group15.gameStore.model.PaymentInfo;

public class PaymentInfoDto{
    private int paymentInfoID;
    private String cardNumber;
    private Date expiryDate;
    private int cvv;
    private String billingAddress; 

    private Customer customer;

    @SuppressWarnings("unused")
    private PaymentInfoDto(){
    }
    public PaymentInfoDto(PaymentInfo paymentInfoDto){
        this.paymentInfoID = paymentInfoDto.getPaymentInfoID();
        this.cardNumber = paymentInfoDto.getCardNumber();
        this.expiryDate = paymentInfoDto.getExpiryDate();
        this.cvv = paymentInfoDto.getCvv();
        this.billingAddress = paymentInfoDto.getBillingAddress();
        this.customer = paymentInfoDto.getCustomer();
    }

    public PaymentInfoDto(String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAddress, Customer aCustomer){
        this.cardNumber = aCardNumber;
        this.expiryDate = aExpiryDate;
        this.cvv = aCvv;
        this.billingAddress = aBillingAddress;
        this.customer = aCustomer;
    }
    
    //Generated Getters and Setters
    public int getPaymentInfoID() {
        return paymentInfoID;
    }
    public void setPaymentInfoID(int paymentInfoID) {
        this.paymentInfoID = paymentInfoID;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
    public String getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}