package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import group15.gameStore.dto.CustomerDto;
import group15.gameStore.dto.PaymentInfoDto;



@RestController
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private CustomerService customerService;

     /**
     * CreatePaymentInfo: creates a new payment information record
     * @param paymentInfo the PaymentInfoDto containing the payment details
     * @param customerDto the customer for whom the payment info is created
     * @return the created payment information and HTTP Status "CREATED"
     */
    @PostMapping("/paymentInfo")
    public ResponseEntity<PaymentInfoDto> createPaymentInfo(@RequestBody PaymentInfoDto paymentInfoDto) {
            CustomerDto customerDto = paymentInfoDto.getCustomer();
            Customer customer = customerService.getCustomerByID(customerDto.getUserId());
            PaymentInfo createdPaymentInfo = paymentInfoService.createPaymentInfo(
                paymentInfoDto.getCardNumber(),paymentInfoDto.getExpiryDate(),paymentInfoDto.getCvv(),
                paymentInfoDto.getBillingAddress(),customer);
            
            PaymentInfoDto responseDto = new PaymentInfoDto(createdPaymentInfo);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

     /**
     * UpdatePaymentInfo: updates an existing payment information record
     * @param paymentInfoId the ID of the payment information to update
     * @param paymentInfoDto the PaymentInfoDto containing updated payment details
     * @return the updated payment information and the HTTP status "OK"
     */
    @PutMapping("/paymentInfo/{paymentInfoId}")
    public ResponseEntity<PaymentInfoDto> updatePaymentInfo(@PathVariable int paymentInfoId,
            @RequestBody PaymentInfoDto paymentInfoDto) {
            CustomerDto customerDto = paymentInfoDto.getCustomer();
            Customer customer = customerService.getCustomerByID(customerDto.getUserId());
            PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoById(paymentInfoDto.getPaymentInfoID());
            PaymentInfo updatedPaymentInfo = paymentInfoService.updatePaymentInfo(paymentInfoId,paymentInfo,customer);
            return new ResponseEntity<>(new PaymentInfoDto(updatedPaymentInfo), HttpStatus.OK);
    }

    /**
     * GetPaymentInfoById: retrieves payment information by ID
     * @param paymentInfoId the ID of the payment information to retrieve
     * @return desired payment information and the HTTP status "OK"
     */
    @GetMapping("/paymentInfo/id/{paymentInfoId}")
    public ResponseEntity<PaymentInfoDto> getPaymentInfoById(@PathVariable int paymentInfoId) {
        PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoById(paymentInfoId);
        return new ResponseEntity<>(new PaymentInfoDto(paymentInfo), HttpStatus.OK);
    }

    /**
     * GetPaymentInfoByCardNumber: retrieves payment information by card number
     * @param cardNumber the card number of the payment information to retrieve
     * @return desired payment information and the HTTP status "OK"
     */
    @GetMapping("/paymentInfo/card/{cardNumber}")
    public ResponseEntity<PaymentInfoDto> getPaymentInfoByCardNumber(@PathVariable String cardNumber) {
        PaymentInfo paymentInfo = paymentInfoService.getPaymentInfoByCardNumber(cardNumber);
        return new ResponseEntity<>(new PaymentInfoDto(paymentInfo), HttpStatus.OK);
    }

    /**
     * GetAllPaymentInfo: retrieves all payment information records in the system
     * @return all payment information in the system and the HTTP status "OK"
     */
    @GetMapping("/paymentInfo")
    public ResponseEntity<List<PaymentInfoDto>> getAllPaymentInfo() {
        List<PaymentInfo> paymentInfoList = paymentInfoService.GetAllPaymentInfo();

        if (paymentInfoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
        }

        List<PaymentInfoDto> responseDtoList = paymentInfoList.stream()
                .map(PaymentInfoDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * DeletePaymentInfo: deletes payment information by card number
     * @param cardNumber the card number of the payment information to delete
     * @return HTTP status "NO CONTENT"
     */
     @DeleteMapping("/paymentInfo/{cardNumber}")
     public ResponseEntity<Void> deletePaymentInfo(@PathVariable String cardNumber,@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());  
        paymentInfoService.deletePaymentInfo(cardNumber, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
    

}