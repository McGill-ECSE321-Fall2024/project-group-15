package group15.gameStore.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import group15.gameStore.model.PaymentInfo;
import group15.gameStore.repository.PaymentInfoRepository;
import group15.gameStore.repository.customerRepository;


@Service 
public class PaymentInfoService {
    
    @Autowired
    PaymentInfoRepository PaymentInfoRepo;

    /**
     * CreatePaymentInfo: creates new payment information record
     * @param aCardNumber: the card number for payment
     * @param aExpiryDate: the expiry date of the card
     * @param aCvv: the CVV code of the card
     * @param aBillingAddress: the billing address associated with the card
     * @param aCustomer: the customer for whom the payment info is created
     * @return the new PaymentInfo
     * @throws IllegalArgumentException if creation request is invalid or customer is not found
     */
    @Transactional
    public PaymentInfo createPaymentInfo(String aCardNumber, Date aExpiryDate, int aCvv, String aBillingAdress, Customer aCustomer){
        if (aCardNumber == null || aCardNumber.length() != 16 || !aCardNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid card number. Must be 16 digits.");
        }
        if (aExpiryDate == null || aExpiryDate.before(new Date(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("Invalid expiry date. Must be a future date.");
        }
        if (String.valueOf(aCvv).length() != 3) {
            throw new IllegalArgumentException("Invalid CVV. Must be a 3-digit number.");
        }
        if (aBillingAddress == null || aBillingAddress.isEmpty()) {
            throw new IllegalArgumentException("Billing address cannot be empty.");
        }
        if (aCustomer == null || customerRepository.findById(aCustomer.getId()).isEmpty()) {
            throw new IllegalArgumentException("Customer must be valid and registered.");
        }
        PaymentInfo paymentInfo = new PaymentInfo(aCardNumber, aExpiryDate, aCvv, aBillingAdress, aCustomer);
        PaymentInfoRepository.save(paymentInfo);
        return paymentInfo;
    }

     /**
     * UpdatePaymentInfo: updates an existing payment information record
     * @param paymentInfoId: the ID of the payment information to update
     * @param updatedPaymentInfo: the new payment information to update to
     * @param customer: the customer for whom the payment info is being updated
     * @return the updated PaymentInfo object
     * @throws IllegalArgumentException if update request is invalid
     */
    @Transactional
    public PaymentInfo updatePaymentInfo(int paymentInfoId, PaymentInfo updatedPaymentInfo, Customer customer) {
        PaymentInfo existingPaymentInfo = paymentInfoRepository.findById(paymentInfoId).orElse(null);
        if (existingPaymentInfo == null) {
            throw new IllegalArgumentException("Payment info with the specified ID does not exist.");
        }
        if (updatedPaymentInfo == null 
                || updatedPaymentInfo.getCardNumber() == null 
                || updatedPaymentInfo.getExpiryDate() == null 
                || updatedPaymentInfo.getCvv() <= 0 
                || updatedPaymentInfo.getBillingAddress() == null 
                || customer == null) {
            throw new IllegalArgumentException("Missing required information.");
        }
        if (!existingPaymentInfo.getCustomer().getId().equals(customer.getId())) {
            throw new IllegalArgumentException("This payment info does not belong to the specified customer.");
        }
        String cardNumber = updatedPaymentInfo.getCardNumber();
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            throw new IllegalArgumentException("Invalid card number. Must be 16 digits.");
        }
        Date expiryDate = updatedPaymentInfo.getExpiryDate();
        if (expiryDate.before(new Date(System.currentTimeMillis()))) {
            throw new IllegalArgumentException("Invalid expiry date. Must be a future date.");
        }
        int cvv = updatedPaymentInfo.getCvv();
        if (String.valueOf(cvv).length() != 3) {
            throw new IllegalArgumentException("Invalid CVV. Must be a 3-digit number.");
        }
        String billingAddress = updatedPaymentInfo.getBillingAddress();
        if (billingAddress.isEmpty()) {
            throw new IllegalArgumentException("Billing address cannot be empty.");
        }
        existingPaymentInfo.setCardNumber(cardNumber);
        existingPaymentInfo.setExpiryDate(expiryDate);
        existingPaymentInfo.setCvv(cvv);
        existingPaymentInfo.setBillingAddress(billingAddress);
        paymentInfoRepository.save(existingPaymentInfo);
        return existingPaymentInfo;
    }






}