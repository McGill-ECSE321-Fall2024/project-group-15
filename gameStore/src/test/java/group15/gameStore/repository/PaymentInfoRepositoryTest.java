package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.PaymentInfo;

@SpringBootTest
public class PaymentInfoRepositoryTest {
    @Autowired
    private PaymentInfoRepository repo;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        repo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadPaymentInfo() {
        //Create PaymentInfo
        String cardNumber = "1234 5678 9011 1213";
        Date expiryDate = Date.valueOf("2025-12-31");
        int cvv = 123;
        String billingAddress = "123 Sesame Street";

        PaymentInfo paymentInfo = new PaymentInfo(cardNumber, expiryDate, cvv, billingAddress);

        // Save in the database
        paymentInfo = repo.save(paymentInfo);
        int paymentinfoId = paymentInfo.getPaymentInfoID();

        // Read back from the database
        PaymentInfo paymentInfoFromDb = repo.findByPaymentInfoID(paymentinfoId);

        // Assertions
        assertNotNull(paymentInfoFromDb);
        assertEquals(paymentinfoId, paymentInfoFromDb.getPaymentInfoID());
        assertEquals(cardNumber, paymentInfoFromDb.getCardNumber());
        assertEquals(expiryDate, paymentInfoFromDb.getExpiryDate());
        assertEquals(cvv, paymentInfoFromDb.getCvv());
        assertEquals(billingAddress, paymentInfoFromDb.getBillingAddress());
    }
}
