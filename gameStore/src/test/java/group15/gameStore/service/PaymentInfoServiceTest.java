package group15.gameStore.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Customer;
import group15.gameStore.model.PaymentInfo;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.PaymentInfoRepository;

@SpringBootTest
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
    public void testCreatePaymentInfo_InvalidCardNumber() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            paymentInfoService.createPaymentInfo(
                "12345", 
                Date.valueOf("2025-12-31"), 
                123, 
                "123 Test St", 
                mockCustomer);
        });
        assertEquals("Invalid card number. Must be 16 digits.", thrown.getMessage());
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
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            paymentInfoService.getPaymentInfoById(1);
        });
        
        assertEquals("Payment Information not found", thrown.getMessage());
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
        
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            paymentInfoService.deletePaymentInfo("1234567812345678", mockCustomer);
        });
        
        assertEquals("Payment info with the specified ID does not exist.", thrown.getMessage());
    }

    @Test
    public void testDeletePaymentInfo_Unauthorized() {
        Customer unauthorizedCustomer = new Customer();
        unauthorizedCustomer.setPhoneNumber("0987654321");

        when(paymentInfoRepo.findByCardNumber("1234567812345678")).thenReturn(mockPaymentInfo);
        
        SecurityException thrown = assertThrows(SecurityException.class, () -> {
            paymentInfoService.deletePaymentInfo("1234567812345678", unauthorizedCustomer);
        });
        
        assertEquals("Unauthorized access. Only the owner can delete this payment info.", thrown.getMessage());
    }
}
