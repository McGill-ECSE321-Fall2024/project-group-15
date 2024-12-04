package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ManagerRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GameServiceTest {
    @Mock
    private GameRepository mockGameRepo;
    @Mock
    private ManagerRepository mockManagerRepo;
    @Mock
    private EmployeeRepository mockEmployeeRepo;
    @InjectMocks
    private GameService gameService;
    @InjectMocks
    private ManagerService managerService;
    @Mock
    private Manager manager;

    private static final String VALID_TITLE = "game1";
    private static final String VALID_DESC = "description";
    private static final double VALID_PRICE = 49.99;
    private static final int VALID_STOCK = 1;
    private static final String VALID_IMAGE = "imagelink";
    private static final boolean VALID_ISAPPROVED = true;
    private static final Manager VALID_MANAGER = new Manager("SmithManager", "Smith123", "smith@mail.com", true, true);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void clearDatabase() {
        mockGameRepo.deleteAll();
        mockManagerRepo.deleteAll();
    }

    @Test
    public void testCreateValidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        when(mockManagerRepo.findManagerByUserID(0)).thenReturn(manager);
        
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Game createdGame = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);

        assertNotNull(createdGame);
		    assertEquals(VALID_TITLE, createdGame.getTitle());
        assertEquals(VALID_DESC, createdGame.getDescription());
        assertEquals(VALID_PRICE, createdGame.getPrice());
        assertEquals(VALID_STOCK, createdGame.getStock());
        assertEquals(VALID_IMAGE, createdGame.getImage());
        assertEquals(VALID_ISAPPROVED, createdGame.getIsApproved());
        assertEquals(manager, createdGame.getManager());

        verify(mockGameRepo, times(1)).save(createdGame);

    }

    @Test
    public void testCreateInvalidGame() {
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.createGame("", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid game creation request: missing attributes", e.getMessage());
    }

    @Test
    public void testCreateInvalidGameNegativeStock() {
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, -5, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The price or stock of a game cannot be negative", e.getMessage());
    }

    @Test
    public void testCreateGameInvalidEmployee() {
      Manager invalidEmployee = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
      invalidEmployee.setUserID(0);

      GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, invalidEmployee));
		  assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		  assertEquals("The manager does not exist", e.getMessage());
    }

    @Test
    public void testUpdateValidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);

        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Game gameToUpdate = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        Game gameUpdated = new Game("newGame1", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToUpdate.setGameID(0);
        gameUpdated.setGameID(0);

        when(mockGameRepo.findGameByGameID(0)).thenReturn(gameUpdated);

        Game updatedGame = gameService.updateGame(gameToUpdate.getGameID(), gameUpdated);
        
        assertNotNull(updatedGame);
		    assertEquals("newGame1", updatedGame.getTitle());
        assertEquals(VALID_DESC, updatedGame.getDescription());
        assertEquals(VALID_PRICE, updatedGame.getPrice());
        assertEquals(VALID_STOCK, updatedGame.getStock());
        assertEquals(VALID_IMAGE, updatedGame.getImage());
        assertEquals(VALID_ISAPPROVED, updatedGame.getIsApproved());
        assertEquals(manager, updatedGame.getManager());
    }

    @Test
    public void testUpdateInvalidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        when(mockManagerRepo.findManagerByUserID(0)).thenReturn(manager);
        
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Game gameToUpdate = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);

        gameToUpdate.setTitle("");

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.updateGame(gameToUpdate.getGameID(), gameToUpdate));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid game creation request: missing attributes", e.getMessage());

    }

    @Test
    public void testDeleteValidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Game gameToDelete = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToDelete.setGameID(0);

        when(mockGameRepo.findGameByGameID(0)).thenReturn(gameToDelete);

        gameService.deleteGame(gameToDelete);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getAllGames());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no games in the system", e.getMessage());
    }

    @Test
    public void testDeleteInvalidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Game gameToDelete = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToDelete.setGameID(0);

        when(mockGameRepo.findGameByGameID(0)).thenReturn(null);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.deleteGame(gameToDelete));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("The game to delete does not exist", e.getMessage());

    }

    @Test
    public void testDeleteInvalidGameAttributes() {
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.deleteGame(null));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid game creation request: missing attributes", e.getMessage());

    }


    @Test
    public void testGetGameByValidID() {
        Game gameToGet = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToGet.setGameID(0);

        when(mockGameRepo.findGameByGameID(0)).thenReturn(gameToGet);

        Game gottenGame = gameService.getGameByID(gameToGet.getGameID());
        assertNotNull(gottenGame);
		assertEquals(VALID_TITLE, gottenGame.getTitle());
        assertEquals(VALID_DESC, gottenGame.getDescription());
        assertEquals(VALID_PRICE, gottenGame.getPrice());
        assertEquals(VALID_STOCK, gottenGame.getStock());
        assertEquals(VALID_IMAGE, gottenGame.getImage());
        assertEquals(VALID_ISAPPROVED, gottenGame.getIsApproved());
        assertEquals(manager, gottenGame.getManager());

    }

    @Test
    public void testGetGameByInvalidID() {
        when(mockGameRepo.findGameByGameID(2)).thenReturn(null);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getGameByID(2));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no game with ID %d", 2), e.getMessage());
    }

    @Test
    public void testGetGameByValidTitle() {
        Game gameToGet = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToGet.setGameID(1);

        when(mockGameRepo.findGameByTitle(VALID_TITLE)).thenReturn(gameToGet);

        Game gottenGame = gameService.getGameByTitle(gameToGet.getTitle());
        assertNotNull(gottenGame);
		assertEquals(VALID_TITLE, gottenGame.getTitle());
        assertEquals(VALID_DESC, gottenGame.getDescription());
        assertEquals(VALID_PRICE, gottenGame.getPrice());
        assertEquals(VALID_STOCK, gottenGame.getStock());
        assertEquals(VALID_IMAGE, gottenGame.getImage());
        assertEquals(VALID_ISAPPROVED, gottenGame.getIsApproved());
        assertEquals(manager, gottenGame.getManager());
    }

    @Test
    public void testGetGameByInvalidTitle() {
        when(mockGameRepo.findGameByTitle("invalidTitle")).thenReturn(null);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getGameByTitle("invalidTitle"));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no game with title %s", "invalidTitle"), e.getMessage());

    }

    @Test
    public void testGetGamesByValidPrice() {
        Game gameToGet1 = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        Game gameToGet2 = new Game("game2", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);

        when(mockGameRepo.findGamesByPrice(gameToGet1.getPrice())).thenAnswer((InvocationOnMock invocation) ->{
            List<Game> gameList = new ArrayList<>();
            gameList.add(gameToGet1);
            gameList.add(gameToGet2);
            return gameList;
        });

        List<Game> gottenGames = gameService.getGamesByPrice(gameToGet1.getPrice());

        assertNotNull(gottenGames);
		assertEquals(2, gottenGames.size());

    }

    @Test
    public void testGetGamesByInvalidPrice() {
        when(mockGameRepo.findGamesByPrice(59.99)).thenReturn(new ArrayList<>());

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getGamesByPrice(59.99));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no game with price %f", 59.99), e.getMessage());
    }

    @Test
    public void testGetAllGamesValid() {
        Game gameToGet1 = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        Game gameToGet2 = new Game("game2", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);

        when(mockGameRepo.findAll()).thenAnswer((InvocationOnMock invocation) ->{
            List<Game> gameList = new ArrayList<>();
            gameList.add(gameToGet1);
            gameList.add(gameToGet2);
            return gameList;
        });

        List<Game> gottenGames = gameService.getAllGames();

        assertNotNull(gottenGames);
		assertEquals(2, gottenGames.size());
    }

    @Test
    public void testGetAllGamesInvalid() {
        when(mockGameRepo.findAll()).thenReturn(new ArrayList<>());

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getAllGames());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no games in the system", e.getMessage());

    }

    @Test
    public void testArchiveValidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
       
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Game gameToArchive = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToArchive.setGameID(0);

        when(mockGameRepo.findGameByGameID(0)).thenReturn(gameToArchive);

        Game archivedGame = gameService.archiveGame(gameToArchive);

        assertNotNull(archivedGame);
		assertEquals(Date.valueOf(LocalDate.now()), archivedGame.getArchivedDate());
		assertEquals(false, archivedGame.getIsApproved());
    }
    
    @Test
    public void testArchiveInvalidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Game archivedGame = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        archivedGame.setGameID(0);
        archivedGame.setArchivedDate(Date.valueOf(LocalDate.now()));

        when(mockGameRepo.findGameByGameID(0)).thenReturn(archivedGame);
        
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.archiveGame(archivedGame));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The game is already archived", e.getMessage());

    }

    @Test
    public void testUnarchiveValidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Game gameToUnarchive = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToUnarchive.setGameID(0);
        gameToUnarchive.setArchivedDate(Date.valueOf(LocalDate.now()));

        when(mockGameRepo.findGameByGameID(0)).thenReturn(gameToUnarchive);

        Game unarchivedGame = gameService.unarchiveGame(gameToUnarchive);

        assertNotNull(unarchivedGame);
		assertEquals(null, unarchivedGame.getArchivedDate());

    }
    
    @Test
    public void testUnarchiveInvalidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
        
        Game gameToUnarchive = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        
        when(mockGameRepo.findGameByGameID(0)).thenReturn(gameToUnarchive);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.unarchiveGame(gameToUnarchive));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The game is already unarchived", e.getMessage());

    }

    @Test
    public void testUpdateApprovalValidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
       
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Game gameToUpdateApproval = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToUpdateApproval.setGameID(0);

        when(mockGameRepo.findGameByGameID(0)).thenReturn(gameToUpdateApproval);

        Game updatedGame = gameService.updateGameApproval(gameToUpdateApproval, true);

        assertNotNull(updatedGame);
		assertEquals(true, updatedGame.getIsApproved());
    }

    @Test
    public void testUpdateApprovalInvalidGame() {
        Manager manager = new Manager(VALID_MANAGER.getUsername(), VALID_MANAGER.getPassword(), VALID_MANAGER.getEmail(), VALID_MANAGER.getIsActive(), VALID_MANAGER.getIsManager());
        manager.setUserID(0);
    
        Game gameToUpdateApproval = new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager);
        gameToUpdateApproval.setTitle("");

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.updateGameApproval(gameToUpdateApproval, false));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid game approval change request: missing attributes", e.getMessage());

    }
}
