package group15.gearUp.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import group15.gearUp.dto.PromotionDto;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Manager;
import group15.gearUp.model.Promotion;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.PromotionRepository;
import group15.gearUp.repository.ManagerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PromotionServiceIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private ManagerRepository managerRepository;

    private Equipment equipment;
    private Promotion promo2024;
    private Promotion promo2026;

    private Manager manager;
    private int validEquipmentId;
    private int valid2024PromotionId;
    private int valid2026PromotionId;
    private PromotionDto promo2024Dto;
    private PromotionDto promo2026Dto;

    @BeforeAll
    public void setUp() {
        manager = new Manager("ChadTheManager", "manager123","chad@mail.mcgill.ca", true, true);
        managerRepository.save(manager);


        equipment = new Equipment("Minecraft", "3d sandbox equipment", 10, 10,"image.jpg", true, manager);
        equipmentRepository.save(equipment);
        this.validEquipmentId = equipment.getEquipmentID();

        equipment = equipmentRepository.findEquipmentByEquipmentID(equipment.getEquipmentID());

//        promo2024 = new Promotion("PROMO2024", 20.0, Date.valueOf("2024-12-31"), equipment);
//        promotionRepository.save(promo2024);
//        this.valid2024PromotionId = promo2024.getPromotionID();
//
//        promo2026 = new Promotion("PROMO2026", 18.0, Date.valueOf("2026-12-31"), equipment);
//        promotionRepository.save(promo2026);
//        this.valid2026PromotionId = promo2026.getPromotionID();

        promo2024Dto = new PromotionDto("PROMO2024", 20.0,Date.valueOf("2024-12-31"), validEquipmentId);
        promo2026Dto = new PromotionDto("PROMO2026", 18.0, Date.valueOf("2026-12-31"), validEquipmentId);
    }

    @AfterAll
    public void clearDatabase() {
        promotionRepository.deleteAll();
        equipmentRepository.deleteAll();
    }

    // Test to create a valid promotion
    @Test
    @Order(1)
    public void testCreatePromotion() {
        // Act
        ResponseEntity<PromotionDto> response = client.postForEntity("/promotion", promo2024Dto, PromotionDto.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        System.out.println(response.getBody());
    }

    // Test to get a promotion by ID
    @Test
    @Order(2)
    public void testGetPromotionByEquipmentId() {
        // Act
        ResponseEntity<PromotionDto> response = client.getForEntity("/promotion/equipment/" + validEquipmentId, PromotionDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("PROMO2024", response.getBody().getPromotionCode());
    }


    // Test to delete a promotion
    @Test
    @Order(3)
    public void testDeletePromotion() {
        // Act
        ResponseEntity<Void> response = client.exchange("/promotion/" + valid2024PromotionId, HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);

        // Assert
        assertNotNull(response.getStatusCode());
    }

    // Test to get all promotions
    @Test
    @Order(4)
    public void testGetAllPromotions() {
        // Act
        ResponseEntity<PromotionDto[]> response = client.getForEntity("/promotions", PromotionDto[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("PROMO2024", response.getBody()[0].getPromotionCode());
    }
}
