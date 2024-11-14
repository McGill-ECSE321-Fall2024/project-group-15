package group15.gameStore.integration;

import group15.gameStore.dto.*;
import group15.gameStore.model.Customer;
import group15.gameStore.model.PaymentInfo;
import group15.gameStore.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    @Autowired
    private TestRestTemplate client;

    private Customer customer;
    private PaymentInfo paymentInfo;
    private PaymentInfoDto paymentInfoRequestDto;
    private PaymentInfoDto paymentInfoRequestDto2;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUserID(1);
        customer.setUsername("John Doe");
        customer.setPhoneNumber("123456789");
        customerRepository.save(customer);

        paymentInfo = new PaymentInfo("1234567812345678", Date.valueOf("2025-12-31"), 123, "123 Test St", customer);
        paymentInfo.setPaymentInfoID(1);
        paymentInfoRequestDto = new PaymentInfoDto(paymentInfo);
    }

    @Test
    @Order(1)
    public void testGetAllEmptyPaymentInfo() {
        ResponseEntity<String> response = client.getForEntity("/paymentInfo", String.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(2)
    public void testCreateValidPaymentInfo() {
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.postForEntity("/paymentInfo", paymentInfoRequestDto, PaymentInfoDto.class);
        ResponseEntity<PaymentInfoDto> paymentInfoResponse2 = client.postForEntity("/paymentInfo", paymentInfoRequestDto2, PaymentInfoDto.class);

        assertEquals(HttpStatus.CREATED, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
        assertNotNull(paymentInfoResponse2.getBody());
        assertTrue(equals(paymentInfoResponse.getBody(), paymentInfoRequestDto));
    }

    @Test
    @Order(3)
    public void testGetValidPaymentInfoByCardNumber() {
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.getForEntity("/paymentInfo/card/1234567812345678", PaymentInfoDto.class);
        assertEquals(HttpStatus.OK, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
        assertTrue(equals(paymentInfoResponse.getBody(), paymentInfoRequestDto));
    }

    @Test
    @Order(4)
    public void testUpdateValidPaymentInfo() {
        PaymentInfoDto updatedPaymentInfo = new PaymentInfoDto();
        HttpEntity<PaymentInfoDto> requestEntity = new HttpEntity<>(updatedPaymentInfo);
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.exchange("/paymentInfo/1", HttpMethod.PUT, requestEntity, PaymentInfoDto.class);
        assertEquals(HttpStatus.OK, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
        assertTrue(equals(paymentInfoResponse.getBody(), updatedPaymentInfo));
    }

    @SuppressWarnings("null")
    @Test
    @Order(5)
    public void testGetAllPaymentInfo() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<List> response = client.getForEntity("/paymentinfo", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> paymentInfos = response.getBody();
        assertEquals(paymentInfoRequestDto.getCardNumber(), paymentInfos.get(0).get("cardNumber"));
        assertEquals(paymentInfoRequestDto.getBillingAddress(), paymentInfos.get(0).get("billingAddress"));
        assertEquals(paymentInfoRequestDto2.getCardNumber(), paymentInfos.get(1).get("cardNumber"));
        assertEquals(paymentInfoRequestDto2.getBillingAddress(), paymentInfos.get(1).get("billingAddress"));
    }

    @Test
    @Order(6)
    public void testDeletePaymentInfo() {
        CustomerDto customerDto = new CustomerDto(customer);
        HttpEntity<CustomerDto> requestEntity = new HttpEntity<>(customerDto);
        ResponseEntity<Void> response = client.exchange("/paymentInfo/1234567812345678", HttpMethod.DELETE, requestEntity, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(7)
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
