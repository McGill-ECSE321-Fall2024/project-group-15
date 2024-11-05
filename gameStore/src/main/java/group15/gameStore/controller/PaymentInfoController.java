package main.java.group15.gameStore.controller;

import group15.gameStore.model.PaymentInfo;
import group15.gameStore.service.PaymentInfoService;
import main.java.group15.gameStore.dto.PaymentInfoDto;

@RestController
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

     /**
     * CreatePaymentInfo: creates new payment information record
     * @param
     * @return the new PaymentInfo
     * @throws IllegalArgumentException if creation request is invalid or customer is not found
     */
    public PaymentInfoDto createPaymentInfo(@RequestBody PaymentInfoRequestDto paymentInfo){
        PaymentInfo createdPaymentInfo = PaymentInfoService.createPaymentInfo(paymentInfo.getCardNumber,paymentInfo.getExpiryDate,paymentInfo.getCvv,paymentInfo.getBillingAddress,paymentInfo.getCustomer);
        return new PaymentInfoDto(createdPaymentInfo);
    }
}