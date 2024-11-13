package group15.gameStore.integration;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.dto.GameDto;
import group15.gameStore.dto.ManagerDto;
import group15.gameStore.dto.PersonDto;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ManagerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {
    @Autowired
	private TestRestTemplate client;
	@Autowired
	private GameRepository gameRepo;
    @Autowired
	private ManagerRepository managerRepo;

    private static final String VALID_TITLE = "game1";
    private static final String VALID_DESC = "description";
    private static final double VALID_PRICE = 49.99;
    private static final int VALID_STOCK = 1;
    private static final String VALID_IMAGE = "imagelink";
    private static final boolean VALID_ISAPPROVED = true;
    private static final Manager VALID_MANAGER = new Manager("SmithManager", "Smith123", "smith@mail.com", true, true);
    private int gameID;
    private String gameTitle;
    private double gamePrice;
    private Manager manager;

    private Game game = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER);

    @BeforeAll
    public void setDatabase() {
		manager = managerRepo.save(VALID_MANAGER);
	}
    @AfterAll
	public void clearDatabase() {
		gameRepo.deleteAll();
	}

	@Test
	@Order(1)
    public void testCreateValidGame() {
        ManagerDto managerDto = new ManagerDto(VALID_MANAGER);
        EmployeeDto employeeDto = new EmployeeDto(VALID_MANAGER);
        GameDto game1 = new GameDto(game);

        List<Object> Dtos = new ArrayList<>();
        Dtos.add(game1);
        Dtos.add(managerDto);
        Dtos.add(employeeDto);

        ResponseEntity<GameDto> response = client.postForEntity("/game", Dtos, GameDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        gameID = response.getBody().getGameID();
        gameTitle = response.getBody().getTitle();
        gamePrice = response.getBody().getPrice();
    }

    @Test
	@Order(2)
    public void testGetValidGameByID() {
        ResponseEntity<GameDto> response = client.getForEntity(String.format("/game/%d", gameID), GameDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(gameID, response.getBody().getGameID());
        
    }

    @Test
	@Order(3)
    public void testGetValidGameByTitle() {
        ResponseEntity<GameDto> response = client.getForEntity(String.format("/game/title/%d", gameTitle), GameDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(gameTitle, response.getBody().getTitle());
    }

    @Test
	@Order(4)
    public void testGetValidGamesByPrice() {
        ResponseEntity<List<GameDto>> response = client.exchange(String.format("/games/price/%f", gamePrice), HttpMethod.GET, null, new ParameterizedTypeReference<List<GameDto>>() {});

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(gamePrice, response.getBody().get(0).getPrice());
    }

    @Test
	@Order(5)
    public void testGetAllValidGames() {
        ResponseEntity<List<GameDto>> response = client.exchange("/games", HttpMethod.GET, null, new ParameterizedTypeReference<List<GameDto>>() {});
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
	@Order(6)
    public void testArchiveValidGame() {
        EmployeeDto employeeDto = new EmployeeDto(VALID_MANAGER);
        
        HttpEntity<EmployeeDto> requestEntity = new HttpEntity<>(employeeDto, null);

        ResponseEntity<GameDto> response = client.exchange(String.format("/game/archive/%d", gameID), HttpMethod.PUT, requestEntity, GameDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getArchivedDate());
        assertEquals(Date.valueOf(LocalDate.now()), response.getBody().getArchivedDate());
        }

    @Test
	@Order(7)
    public void testUnarchiveValidGame() {
        EmployeeDto employeeDto = new EmployeeDto(VALID_MANAGER);
        
        HttpEntity<EmployeeDto> requestEntity = new HttpEntity<>(employeeDto, null);

        ResponseEntity<GameDto> response = client.exchange(String.format("/game/unarchive/%d", gameID), HttpMethod.PUT, requestEntity, GameDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNull(response.getBody().getArchivedDate());    
    }

    @Test
	@Order(8)
    public void testUpdateValidGame() {
        EmployeeDto employeeDto = new EmployeeDto(VALID_MANAGER);
        
        game.setImage("newImage");
        GameDto gameDto = new GameDto(game);
        gameDto.setGameID(gameID);

        List<Object> Dtos = new ArrayList<>();
        Dtos.add(gameDto);
        Dtos.add(employeeDto);

        HttpEntity<List<Object>> requestEntity = new HttpEntity<>(Dtos, null);

        ResponseEntity<GameDto> response = client.exchange(String.format("/game/%d", gameID), HttpMethod.PUT, requestEntity, GameDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newImage", response.getBody().getImage());    

    }

    @Test
	@Order(9)
    public void testUpdateValidGameApproval() {
        EmployeeDto employeeDto = new EmployeeDto(VALID_MANAGER);

        List<Object> Dtos = new ArrayList<>();
        Dtos.add(false);
        Dtos.add(employeeDto);

        HttpEntity<List<Object>> requestEntity = new HttpEntity<>(Dtos, null);

        ResponseEntity<GameDto> response = client.exchange(String.format("/game/%d/approval", gameID), HttpMethod.PUT, requestEntity, GameDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getIsApproved());    

    }

    @Test
	@Order(10)
    public void testDeleteValidGames() {
        EmployeeDto employeeDto = new EmployeeDto(VALID_MANAGER);

        HttpEntity<EmployeeDto> requestEntity = new HttpEntity<>(employeeDto, null);

        client.delete(String.format("/game/%d", gameID), requestEntity);

        GameStoreException e = assertThrows(GameStoreException.class,
		() -> client.getForEntity(String.format("/game/title/%d", gameTitle), GameDto.class));

    }

}
