package group15.gameStore.integration;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
import group15.gameStore.dto.ErrorDto;
import group15.gameStore.dto.GameDto;
import group15.gameStore.dto.ManagerDto;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.EmployeeRepository;
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
    @Autowired
    private EmployeeRepository employeeRepo;

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
    private Employee employee;

    private Game game = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER);

    @BeforeAll
    public void setDatabase() {
		manager = managerRepo.save(VALID_MANAGER);
	}
    @AfterAll
	public void clearDatabase() {
		gameRepo.deleteAll();
        managerRepo.deleteAll();
        employeeRepo.deleteAll();
	}

	@Test
	@Order(1)
    public void testCreateValidGame() {
        ManagerDto managerDto = new ManagerDto(VALID_MANAGER);
        EmployeeDto employeeDto = new EmployeeDto(VALID_MANAGER);
        GameDto game1 = new GameDto(game);

        ResponseEntity<GameDto> response = client.postForEntity("/game", game1, GameDto.class);

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
        ResponseEntity<GameDto> response = client.getForEntity(String.format("/game/title/%s", gameTitle), GameDto.class);

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

        ResponseEntity<GameDto> response = client.exchange(String.format("/game/archive/%d", gameID), HttpMethod.PUT, null, GameDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getArchivedDate());
        assertEquals(Date.valueOf(LocalDate.now()), response.getBody().getArchivedDate());
        }

    @Test
	@Order(7)
    public void testUnarchiveValidGame() {
        ResponseEntity<GameDto> response = client.exchange(String.format("/game/unarchive/%d", gameID), HttpMethod.PUT, null, GameDto.class);
        
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

        HttpEntity<GameDto> requestEntity = new HttpEntity<>(gameDto, null);

        ResponseEntity<GameDto> response = client.exchange(String.format("/game/%d", gameID), HttpMethod.PUT, requestEntity, GameDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newImage", response.getBody().getImage());    

    }

    @Test
	@Order(9)
    public void testUpdateValidGameApproval() {

        ResponseEntity<GameDto> response = client.exchange(String.format("/game/%d/approval?newIsApproved=%b", gameID, false), HttpMethod.PUT, null, GameDto.class);
        
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getIsApproved());    

    }

    @Test
	@Order(10)
    public void testDeleteValidGames() {

        client.delete(String.format("/game/%d", gameID));
        
        ResponseEntity<ErrorDto> response = client.exchange(String.format("/game/title/%s", gameTitle), HttpMethod.GET, null, ErrorDto.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(String.format("There is no game with title %s", gameTitle), response.getBody().getErrors().get(0));
    }

}
