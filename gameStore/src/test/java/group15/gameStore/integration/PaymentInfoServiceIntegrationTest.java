package group15.gameStore.integration;

import group15.gameStore.dto.*;
import group15.gameStore.model.Customer;
import group15.gameStore.model.PaymentInfo;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.PaymentInfoRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentInfoServiceIntegrationTest {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate client;

    private Customer customer;
    private int customerId;
    private PaymentInfo paymentInfo;
    private PaymentInfo updatedPaymentInfo;
    private PaymentInfoDto paymentInfoRequestDto;
    private PaymentInfoDto paymentInfoRequestDto2;
    private PaymentInfoDto updatedPaymentInfoDto;
    private int paymentInfoId;

    @BeforeEach
    public void setUp() {
        // customerRepository.deleteAll();
        customer = new Customer("John Doe", "password123", "johndoe@mail.mcgill.ca", "Rue Mcgill", "123456789");
        customerRepository.save(customer);
        this.customerId = customer.getUserID();

        paymentInfo = new PaymentInfo("1234567812345678", Date.valueOf("2025-12-31"), 123, "123 Test St", customer);
        paymentInfoRepository.save(paymentInfo);
        this.paymentInfoId = paymentInfo.getPaymentInfoID();
        paymentInfoRequestDto = new PaymentInfoDto(paymentInfo);

        updatedPaymentInfo = new PaymentInfo("9999888877776666", Date.valueOf("2025-01-01"), 567, "123 Sesame St", customer);
        paymentInfoRepository.save(updatedPaymentInfo);
        updatedPaymentInfoDto = new PaymentInfoDto(updatedPaymentInfo);
    }

    @AfterAll
    public void clearDatabase() {
         paymentInfoRepository.deleteAll();
         customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testGetAllPaymentInfo() {
        ResponseEntity<String> response = client.getForEntity("/paymentInfo", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void testCreateValidPaymentInfo() {
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.postForEntity("/paymentInfo", paymentInfoRequestDto, PaymentInfoDto.class);

        assertEquals(HttpStatus.CREATED, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
        assertTrue(equals(paymentInfoResponse.getBody(), paymentInfoRequestDto));
    }

    @Test
    @Order(3)
    public void testGetValidPaymentInfoByID() {
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.getForEntity("/paymentInfo/id/" + paymentInfoId, PaymentInfoDto.class);
        assertEquals(HttpStatus.OK, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
    }

    @Test
    @Order(4)
    public void testDeletePaymentInfo() {
        CustomerDto customerDto = new CustomerDto(customer);
        HttpEntity<CustomerDto> requestEntity = new HttpEntity<>(customerDto);
        ResponseEntity<Void> response = client.exchange("/paymentInfo/1234567812345678", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    @Order(5)
    public void testUpdateValidPaymentInfo() {
        
        HttpEntity<PaymentInfoDto> requestEntity = new HttpEntity<>(updatedPaymentInfoDto);
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.exchange("/paymentInfo/" + paymentInfoId, HttpMethod.PUT, requestEntity, PaymentInfoDto.class);
        assertEquals(HttpStatus.OK, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
    }




    @Test
    @Order(6)
    public void testDeletePaymentInfo_NotFound() {
        CustomerDto customerDto = new CustomerDto(customer);
        HttpEntity<CustomerDto> requestEntity = new HttpEntity<>(customerDto);
        ResponseEntity<String> response = client.exchange("/paymentInfo/0000000000000000", HttpMethod.DELETE, requestEntity, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    private boolean equals(PaymentInfoDto paymentInfoResponseDto, PaymentInfoDto paymentInfoRequestDto) {
        return paymentInfoResponseDto.getCardNumber().equals(paymentInfoRequestDto.getCardNumber()) &&
               paymentInfoResponseDto.getBillingAddress().equals(paymentInfoRequestDto.getBillingAddress()) &&
               paymentInfoResponseDto.getCvv() == paymentInfoRequestDto.getCvv();
    }
}
