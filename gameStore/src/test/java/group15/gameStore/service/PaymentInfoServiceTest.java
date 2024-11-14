package group15.gameStore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.model.PaymentInfo;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.PaymentInfoRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentInfoServiceTest {

    @Mock
    private PaymentInfoRepository paymentInfoRepo;
    
    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private PaymentInfoService paymentInfoService;

    private Customer mockCustomer;
    private PaymentInfo mockPaymentInfo;

    @BeforeEach
    public void setUp() {
        mockCustomer = new Customer();
        mockCustomer.setUserID(1);
        mockCustomer.setPhoneNumber("1234567890");
        
        mockPaymentInfo = new PaymentInfo();
        mockPaymentInfo.setCardNumber("1234567812345678");
        mockPaymentInfo.setExpiryDate(Date.valueOf("2025-12-31"));
        mockPaymentInfo.setCvv(123);
        mockPaymentInfo.setBillingAddress("123 Test St");
        mockPaymentInfo.setCustomer(mockCustomer);
    }

    @Test
    public void testCreatePaymentInfo_Success() {
        when(customerRepo.findByUserID(mockCustomer.getUserID())).thenReturn(mockCustomer);
        when(paymentInfoRepo.save(any(PaymentInfo.class))).thenReturn(mockPaymentInfo);
        
        PaymentInfo result = paymentInfoService.createPaymentInfo(
            "1234567812345678", 
            Date.valueOf("2025-12-31"), 
            123, 
            "123 Test St", 
            mockCustomer);
        
        assertNotNull(result);
        assertEquals("1234567812345678", result.getCardNumber());
        assertEquals(123, result.getCvv());
        verify(paymentInfoRepo, times(1)).save(any(PaymentInfo.class));
    }

    @Test
    public void testCreatePaymentInfo_InvalidPaymentInfoAttribute() {
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "12345", 
                Date.valueOf("2025-12-31"), 
                123, 
                "123 Test St", 
                mockCustomer);
        });
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testCreatePaymentInfo_NullCustomer() {
        Customer c2 = null;
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "1234567898765432", 
                Date.valueOf("2025-12-31"), 
                123, 
                "123 Test St", 
                c2);  
            });

        assertEquals("Customer must be valid and registered.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_Success() {
        // Arrange
        PaymentInfo updatedPaymentInfo = new PaymentInfo();
        updatedPaymentInfo.setCardNumber("8765432187654321");
        updatedPaymentInfo.setExpiryDate(Date.valueOf("2026-12-31"));
        updatedPaymentInfo.setCvv(321);
        updatedPaymentInfo.setBillingAddress("456 New Address St");
        updatedPaymentInfo.setCustomer(mockCustomer);

        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);
        when(paymentInfoRepo.save(any(PaymentInfo.class))).thenReturn(updatedPaymentInfo);

        // Act
        PaymentInfo result = paymentInfoService.updatePaymentInfo(1, updatedPaymentInfo, mockCustomer);

        // Assert
        assertNotNull(result);
        assertEquals("8765432187654321", result.getCardNumber());
        assertEquals(Date.valueOf("2026-12-31"), result.getExpiryDate());
        assertEquals(321, result.getCvv());
        assertEquals("456 New Address St", result.getBillingAddress());
        verify(paymentInfoRepo, times(1)).save(mockPaymentInfo);
    }

        @Test
    public void testUpdatePaymentInfo_UnauthorizedOrInvalidUpdate() {
        // Arrange
        PaymentInfo invalidUpdatedPaymentInfo = new PaymentInfo();
        invalidUpdatedPaymentInfo.setCardNumber("invalidcard");
        invalidUpdatedPaymentInfo.setExpiryDate(Date.valueOf("2020-12-31"));
        invalidUpdatedPaymentInfo.setCvv(-1); 
        invalidUpdatedPaymentInfo.setBillingAddress(""); 
        invalidUpdatedPaymentInfo.setCustomer(mockCustomer);

        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);

        // Act Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, invalidUpdatedPaymentInfo, mockCustomer);
        });

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testGetPaymentInfoById_Success() {
        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);
        
        PaymentInfo result = paymentInfoService.getPaymentInfoById(1);
        
        assertNotNull(result);
        assertEquals("1234567812345678", result.getCardNumber());
    }

    @Test
    public void testGetPaymentInfoById_NotFound() {
        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(null);
        
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.getPaymentInfoById(1);
        });
        
        assertEquals("Payment Information not found.", thrown.getMessage());
    }

    @Test
    public void testDeletePaymentInfo_Success() {
        when(paymentInfoRepo.findByCardNumber("1234567812345678")).thenReturn(mockPaymentInfo);
        
        paymentInfoService.deletePaymentInfo("1234567812345678", mockCustomer);
        
        verify(paymentInfoRepo, times(1)).deleteByCardNumber("1234567812345678");
    }

    @Test
    public void testDeletePaymentInfo_NotFound() {
        when(paymentInfoRepo.findByCardNumber("1234567812345678")).thenReturn(null);
        
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.deletePaymentInfo("1234567812345678", mockCustomer);
        });
        
        assertEquals("Payment info with the specified card number does not exist.", thrown.getMessage());
    }

    @Test
    public void testDeletePaymentInfo_Unauthorized() {
        Customer unauthorizedCustomer = new Customer();
        unauthorizedCustomer.setUserID(2);
        unauthorizedCustomer.setPhoneNumber("0987654321");

        when(paymentInfoRepo.findByCardNumber("1234567812345678")).thenReturn(mockPaymentInfo);
        
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.deletePaymentInfo("1234567812345678", unauthorizedCustomer);
        });
        
        assertEquals("Unauthorized access. Only the owner can delete this payment info.", thrown.getMessage());
    }

    @Test
    public void testCreatePaymentInfo_InvalidCVV() {
        // Arrange
        int invalidCvv = 9999; // CVV length exceeds the expected range
        
        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "1234567812345678", 
                Date.valueOf("2025-12-31"), 
                invalidCvv, 
                "123 Test St", 
                mockCustomer);
        });
        
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testCreatePaymentInfo_NullExpiryDate() {
        // Arrange
        Date nullExpiryDate = null;
        
        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "1234567812345678", 
                nullExpiryDate, 
                123, 
                "123 Test St", 
                mockCustomer);
        });
        
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_InvalidExpiryDate() {
        // Arrange
        PaymentInfo updatedPaymentInfo = new PaymentInfo();
        updatedPaymentInfo.setCardNumber("8765432187654321");
        updatedPaymentInfo.setExpiryDate(Date.valueOf("2020-01-01")); // Expiration date in the past
        updatedPaymentInfo.setCvv(321);
        updatedPaymentInfo.setBillingAddress("789 Past Date St");
        updatedPaymentInfo.setCustomer(mockCustomer);

        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);

        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, updatedPaymentInfo, mockCustomer);
        });
        
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_InvalidCardNumberFormat() {
        // Arrange
        PaymentInfo updatedPaymentInfo = new PaymentInfo();
        updatedPaymentInfo.setCardNumber("abc123xyz"); // Non-numeric card number
        updatedPaymentInfo.setExpiryDate(Date.valueOf("2026-12-31"));
        updatedPaymentInfo.setCvv(123);
        updatedPaymentInfo.setBillingAddress("456 New St");
        updatedPaymentInfo.setCustomer(mockCustomer);

        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);

        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, updatedPaymentInfo, mockCustomer);
        });
        
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_UnauthorizedCustomer() {
        // Arrange
        Customer unauthorizedCustomer = new Customer();
        unauthorizedCustomer.setUserID(2);
        unauthorizedCustomer.setPhoneNumber("9876543210");
        
        PaymentInfo updatedPaymentInfo = new PaymentInfo();
        updatedPaymentInfo.setCardNumber("8765432187654321");
        updatedPaymentInfo.setExpiryDate(Date.valueOf("2026-12-31"));
        updatedPaymentInfo.setCvv(321);
        updatedPaymentInfo.setBillingAddress("456 Unauthorized St");
        updatedPaymentInfo.setCustomer(unauthorizedCustomer);

        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);

        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, updatedPaymentInfo, unauthorizedCustomer);
        });
        
        assertEquals("Invalid update request or unauthorized customer.", thrown.getMessage());
    }

    @Test
    public void testCreatePaymentInfo_EmptyCardNumber() {
        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "",  // Empty card number
                Date.valueOf("2025-12-31"), 
                123, 
                "123 Test St", 
                mockCustomer);
        });
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_NullPaymentInfo() {
        // Simulate findByPaymentInfoID returning null to mimic PaymentInfo not found
        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(null);

        // Act & Assert
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, mockPaymentInfo, mockCustomer);
        });
        assertEquals("Payment info with the specified ID does not exist.", thrown.getMessage());
    }

    @Test
    public void testCreatePaymentInfo_ExpiredCardDate() {
        // Act & Assert: Expect exception when expiry date is in the past
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "1234567812345678", 
                Date.valueOf("2020-01-01"),  // Past expiry date
                123, 
                "123 Test St", 
                mockCustomer);
        });
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testCreatePaymentInfo_NullBillingAddress() {
        // Act & Assert: Expect exception when billing address is null
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "1234567812345678", 
                Date.valueOf("2025-12-31"), 
                123, 
                null,  // Null billing address
                mockCustomer);
        });
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_NullCustomerOnUpdate() {
        // Arrange
        PaymentInfo updatedPaymentInfo = new PaymentInfo();
        updatedPaymentInfo.setCardNumber("8765432187654321");
        updatedPaymentInfo.setExpiryDate(Date.valueOf("2026-12-31"));
        updatedPaymentInfo.setCvv(321);
        updatedPaymentInfo.setBillingAddress("456 New Address St");

        // Act & Assert: Attempt to update with null customer should throw an exception
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, updatedPaymentInfo, null);  // Null customer
        });
        assertEquals("Payment info with the specified ID does not exist.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_MismatchedCustomer() {
        // Arrange
        Customer otherCustomer = new Customer();
        otherCustomer.setUserID(99);  // Different customer ID

        PaymentInfo updatedPaymentInfo = new PaymentInfo();
        updatedPaymentInfo.setCardNumber("8765432187654321");
        updatedPaymentInfo.setExpiryDate(Date.valueOf("2026-12-31"));
        updatedPaymentInfo.setCvv(321);
        updatedPaymentInfo.setBillingAddress("456 New Address St");
        updatedPaymentInfo.setCustomer(otherCustomer);

        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);

        // Act & Assert: Updating with a different customer should throw an exception
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, updatedPaymentInfo, otherCustomer);
        });
        assertEquals("Invalid update request or unauthorized customer.", thrown.getMessage());
    }

    @Test
    public void testUpdatePaymentInfo_EmptyCardNumber() {
        // Arrange
        PaymentInfo updatedPaymentInfo = new PaymentInfo();
        updatedPaymentInfo.setCardNumber("");  // Empty card number
        updatedPaymentInfo.setExpiryDate(Date.valueOf("2026-12-31"));
        updatedPaymentInfo.setCvv(321);
        updatedPaymentInfo.setBillingAddress("456 New Address St");
        updatedPaymentInfo.setCustomer(mockCustomer);

        when(paymentInfoRepo.findByPaymentInfoID(1)).thenReturn(mockPaymentInfo);

        // Act & Assert: Updating with an empty card number should throw an exception
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            paymentInfoService.updatePaymentInfo(1, updatedPaymentInfo, mockCustomer);
        });
        assertEquals("Payment Card information is invalid.", thrown.getMessage());
    }


}
