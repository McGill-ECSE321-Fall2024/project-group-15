package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.PaymentInfo;
import java.util.List;

public interface PaymentInfoRepository extends CrudRepository<PaymentInfo, Integer> {
    // Find PaymentInfo by ID
    PaymentInfo findPaymentInfoById(int Id);

    // Find PaymentInfo by card number
    PaymentInfo findByCardNumber(String cardNumber);

    // Find PaymentInfo by CVV
    List<PaymentInfo> findByCvv(int cvv);

    // Find PaymentInfo by billing address
    List<PaymentInfo> findByBillingAddress(String billingAddress);

    // Delete PaymentInfo by card number
    void deleteByCardNumber(String cardNumber);

    // Get all PaymentInfo records
    List<PaymentInfo> findAll();
}
