package main.java.group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.model.Customer;
import group15.gameStore.model.PaymentInfo;
import group15.gameStore.service.CustomerService;
import group15.gameStore.service.PaymentInfoService;
import main.java.group15.gameStore.RequestDto.CustomerRequestDto;
import main.java.group15.gameStore.RequestDto.PaymentInfoRequestDto;
import main.java.group15.gameStore.ResponseDto.PaymentInfoResponseDto;



@RestController
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private CustomerService customerService;

     /**
     * CreatePaymentInfo: creates a new payment information record
     * @param paymentInfo the PaymentInfoRequestDto containing the payment details
     * @return the created payment information
     */
    @PostMapping("/paymentInfo")
    public ResponseEntity<PaymentInfoResponseDto> createPaymentInfo(@RequestBody PaymentInfoRequestDto paymentInfoDto) {
        try {
            PaymentInfo createdPaymentInfo = paymentInfoService.createPaymentInfo(
                paymentInfoDto.getCardNumber(),paymentInfoDto.getExpiryDate(),paymentInfoDto.getCvv(),
                paymentInfoDto.getBillingAddress(),paymentInfoDto.getCustomer());
            
            PaymentInfoResponseDto responseDto = new PaymentInfoResponseDto(createdPaymentInfo);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

     /**
     * UpdatePaymentInfo: updates an existing payment information record
     * @param paymentInfoId the ID of the payment information to update
     * @param paymentInfoDto the PaymentInfoRequestDto containing updated payment details
     * @return the updated payment information
     */
    @PutMapping("/paymentInfo/{paymentInfoId}")
    public ResponseEntity<PaymentInfoResponseDto> updatePaymentInfo(
            @PathVariable("paymentInfoId") int paymentInfoId,
            @RequestBody PaymentInfoRequestDto paymentInfoDto) {

        try {
            PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoById(paymentInfoId);
            
            PaymentInfo updatedPaymentInfo = paymentInfoService.updatePaymentInfo(
                    paymentInfoId,paymentInfo,paymentInfoDto.getCustomer());

            return new ResponseEntity<>(new PaymentInfoResponseDto(updatedPaymentInfo), HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * GetPaymentInfoById: retrieves payment information by ID
     * @param paymentInfoId the ID of the payment information to retrieve
     * @return desired payment information
     */
    @GetMapping("/paymentInfo/{paymentInfoId}")
    public ResponseEntity<PaymentInfoResponseDto> getPaymentInfoById(@PathVariable int paymentInfoId) {
        try {
            PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoById(paymentInfoId);
            PaymentInfoResponseDto responseDto = new PaymentInfoResponseDto(paymentInfo);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GetPaymentInfoByCardNumber: retrieves payment information by card number
     * @param cardNumber the card number of the payment information to retrieve
     * @return desired payment information
     */
    @GetMapping("/paymentInfo/card/{cardNumber}")
    public ResponseEntity<PaymentInfoResponseDto> getPaymentInfoByCardNumber(@PathVariable String cardNumber) {
        try {
            PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoByCardNumber(cardNumber);
            PaymentInfoResponseDto responseDto = new PaymentInfoResponseDto(paymentInfo);
            
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
            
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GetAllPaymentInfo: retrieves all payment information records in the system
     * @return all payment infromation in the system
     */
    @GetMapping("/paymentInfo")
    public ResponseEntity<List<PaymentInfoResponseDto>> getAllPaymentInfo() {
        List<PaymentInfo> paymentInfoList = paymentInfoService.GetAllPaymentInfo();

        if (paymentInfoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }

        List<PaymentInfoResponseDto> responseDtoList = paymentInfoList.stream()
                .map(PaymentInfoResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * DeletePaymentInfoByCardNumber: deletes payment information by card number
     * @param cardNumber the card number of the payment information to delete
     * @return HTTP status
     */
     @DeleteMapping("/paymentInfo/{cardNumber}")
    public ResponseEntity<Void> deletePaymentInfo(@PathVariable String cardNumber,
            @RequestBody CustomerRequestDto customerDto) {
        try {
            // Retrieve the Customer from the database 
            Customer customer = customerService.getCustomerByEmail(customerDto.getEmail());  

            paymentInfoService.deletePaymentInfo(cardNumber, customer);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        } 
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        } 
        catch (UnauthorizedAccessException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  
        }
    }
    

}