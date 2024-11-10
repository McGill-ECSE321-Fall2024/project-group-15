package group15.gameStore.integration;

import group15.gameStore.dto.*;
import group15.gameStore.model.Customer;
import group15.gameStore.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    private PaymentInfoDto paymentInfoRequestDto;
    private PaymentInfoDto paymentInfoRequestDto2;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setUserID(1);
        customer.setUsername("John Doe");
        customer.setPhoneNumber("123456789");
        customerRepository.save(customer);

        paymentInfoRequestDto = new PaymentInfoDto();
        paymentInfoRequestDto2 = new PaymentInfoDto();
    }

    @Test
    @Order(0)
    public void testGetAllEmptyPaymentInfo() {
        ResponseEntity<String> response = client.getForEntity("/paymentinfo", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), "There are no payment information in the system.");
    }

    @Test
    @Order(1)
    public void testCreateValidPaymentInfo() {
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.postForEntity("/paymentinfo/create", paymentInfoRequestDto, PaymentInfoDto.class);
        ResponseEntity<PaymentInfoDto> paymentInfoResponse2 = client.postForEntity("/paymentinfo/create", paymentInfoRequestDto2, PaymentInfoDto.class);

        assertEquals(HttpStatus.CREATED, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
        assertNotNull(paymentInfoResponse2.getBody());
        assertTrue(equals(paymentInfoResponse.getBody(), paymentInfoRequestDto));
    }

    @Test
    @Order(2)
    public void testGetValidPaymentInfoByCardNumber() {
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.getForEntity("/paymentinfo/1234567812345678", PaymentInfoDto.class);
        assertEquals(HttpStatus.OK, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
        assertTrue(equals(paymentInfoResponse.getBody(), paymentInfoRequestDto));
    }

    @Test
    @Order(3)
    public void testUpdateValidPaymentInfo() {
        PaymentInfoDto updatedPaymentInfo = new PaymentInfoDto();
        HttpEntity<PaymentInfoDto> requestEntity = new HttpEntity<>(updatedPaymentInfo);
        ResponseEntity<PaymentInfoDto> paymentInfoResponse = client.exchange("/paymentinfo/update", HttpMethod.PUT, requestEntity, PaymentInfoDto.class);
        assertEquals(HttpStatus.OK, paymentInfoResponse.getStatusCode());
        assertNotNull(paymentInfoResponse.getBody());
        assertTrue(equals(paymentInfoResponse.getBody(), updatedPaymentInfo));
    }

    @SuppressWarnings("null")
    @Test
    @Order(4)
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
    @Order(5)
    public void testDeletePaymentInfo() {
        ResponseEntity<Void> response = client.exchange("/paymentinfo/delete/1234567812345678", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeletePaymentInfo_NotFound() {
        ResponseEntity<String> response = client.exchange("/paymentinfo/delete/0000000000000000", HttpMethod.DELETE, null, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(response.getBody(), "Payment Info with card number 0000000000000000 not found.");
    }

    private boolean equals(PaymentInfoDto paymentInfoResponseDto, PaymentInfoDto paymentInfoRequestDto) {
        return paymentInfoResponseDto.getCardNumber().equals(paymentInfoRequestDto.getCardNumber()) &&
               paymentInfoResponseDto.getBillingAddress().equals(paymentInfoRequestDto.getBillingAddress()) &&
               paymentInfoResponseDto.getCvv() == paymentInfoRequestDto.getCvv();
    }
}
