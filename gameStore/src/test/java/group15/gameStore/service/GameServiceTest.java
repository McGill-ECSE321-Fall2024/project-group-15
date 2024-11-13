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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ManagerRepository;
import group15.gameStore.service.GameService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GameServiceTest {
    @Mock
    private GameRepository mockGameRepo;
    @Mock
    private ManagerRepository mockManagerRepo;
    @InjectMocks
    private GameService gameService;
    @InjectMocks
    private ManagerService managerService;

    private static final String VALID_TITLE = "game1";
    private static final String VALID_DESC = "description";
    private static final double VALID_PRICE = 49.99;
    private static final int VALID_STOCK = 1;
    private static final String VALID_IMAGE = "imagelink";
    private static final boolean VALID_ISAPPROVED = true;
    private static final Manager VALID_MANAGER = new Manager("SmithManager", "Smith123", "smith@mail.com", true, true);
    private Manager manager;

    @BeforeEach
    public void setDatabase() {
        manager = mockManagerRepo.save(VALID_MANAGER);
    }

    @AfterEach
    public void clearDatabase() {
        mockGameRepo.deleteAll();
        mockManagerRepo.deleteAll();
    }

    @Test
    public void testCreateValidGame() {
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Game createdGame = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

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
				() -> gameService.createGame("", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER, VALID_MANAGER));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid game creation request: missing attributes", e.getMessage());
    }

    @Test
    public void testUpdateValidGame() {
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Game gameToUpdate = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        gameToUpdate.setTitle("newGame1");

        Game updatedGame = gameService.updateGame(gameToUpdate.getGameID(), gameToUpdate, manager);
        
        assertNotNull(updatedGame);
		assertEquals("newGame1", updatedGame.getTitle());
        assertEquals(VALID_DESC, updatedGame.getDescription());
        assertEquals(VALID_PRICE, updatedGame.getPrice());
        assertEquals(VALID_STOCK, updatedGame.getStock());
        assertEquals(VALID_IMAGE, updatedGame.getImage());
        assertEquals(VALID_ISAPPROVED, updatedGame.getIsApproved());
        assertEquals(manager, updatedGame.getManager());

        verify(mockGameRepo, times(1)).save(gameToUpdate);
        verify(mockGameRepo, times(1)).save(updatedGame);

    }

    @Test
    public void testUpdateInvalidGame() {
        Game gameToUpdate = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        gameToUpdate.setTitle("");

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.updateGame(gameToUpdate.getGameID(), gameToUpdate, manager));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid game creation request: missing attributes", e.getMessage());

    }

    @Test
    public void testDeleteValidGame() {
        Game gameToDelete = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        List<Game> gameList = gameService.getAllGames();
        assertEquals(1, gameList.size());
        verify(mockGameRepo, times(1)).save(gameToDelete);

        gameService.deleteGame(gameToDelete, manager);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getAllGames());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no games in the system", e.getMessage());
    }

    @Test
    public void testDeleteInvalidGame() {
        Game gameToDelete = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        gameToDelete.setGameID(gameToDelete.getGameID() + 1);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.deleteGame(gameToDelete, manager));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("The game to delete does not exist", e.getMessage());

    }

    @Test
    public void testGetGameByValidID() {
        Game gameToGet = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        when(mockGameRepo.findGameByGameID(gameToGet.getGameID())).thenReturn(new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER));

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
        Game gameToGet = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        gameToGet.setGameID(gameToGet.getGameID() + 1);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getGameByID(gameToGet.getGameID()));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no game with ID %d", gameToGet.getGameID()), e.getMessage());
    }

    @Test
    public void testGetGameByValidTitle() {
        Game gameToGet = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        when(mockGameRepo.findGameByTitle(gameToGet.getTitle())).thenReturn(new Game(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, VALID_MANAGER));

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
        Game gameToGet = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        gameToGet.setTitle("invalidGame");

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getGameByTitle(gameToGet.getTitle()));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no game with title %s", gameToGet.getTitle()), e.getMessage());

    }

    @Test
    public void testGetGamesByValidPrice() {
        Game gameToGet1 = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);
        Game gameToGet2 = gameService.createGame("game2", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        when(mockGameRepo.findGamesByPrice(gameToGet1.getPrice())).thenAnswer((InvocationOnMock invocation) ->{
            List<Game> gameList = new ArrayList<>();
            gameList.add(gameToGet1);
            gameList.add(gameToGet2);
            return gameList;
        });

        List<Game> gottenGames = gameService.getGamesByPrice(gameToGet1.getPrice());

        assertNotNull(gottenGames);
		assertEquals(2, gottenGames.size());
        verify(mockGameRepo, times(1)).save(gameToGet1);
        verify(mockGameRepo, times(1)).save(gameToGet2);

    }

    @Test
    public void testGetGamesByInvalidPrice() {
        Game gameToGet = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        double invalidPrice = gameToGet.getPrice() + 1.00;

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getGamesByPrice(invalidPrice));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals(String.format("There is no game with price %f", invalidPrice), e.getMessage());
    }

    @Test
    public void testGetAllGamesValid() {
        Game gameToGet1 = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);
        Game gameToGet2 = gameService.createGame("game2", VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        when(mockGameRepo.findAll()).thenAnswer((InvocationOnMock invocation) ->{
            List<Game> gameList = new ArrayList<>();
            gameList.add(gameToGet1);
            gameList.add(gameToGet2);
            return gameList;
        });

        List<Game> gottenGames = gameService.getAllGames();

        assertNotNull(gottenGames);
		assertEquals(2, gottenGames.size());
        verify(mockGameRepo, times(1)).save(gameToGet1);
        verify(mockGameRepo, times(1)).save(gameToGet2);
    }

    @Test
    public void testGetAllGamesInvalid() {
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.getAllGames());
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There are no games in the system", e.getMessage());

    }

    @Test
    public void testArchiveValidGame() {
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        Game gameToArchive = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        Game archivedGame = gameService.archiveGame(gameToArchive, VALID_MANAGER);

        assertNotNull(archivedGame);
		assertEquals(Date.valueOf(LocalDate.now()), archivedGame.getArchivedDate());
		assertEquals(false, archivedGame.getIsApproved());
    }
    
    @Test
    public void testArchiveInvalidGame() {
        Game gameToArchive = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);
        gameToArchive.setArchivedDate(Date.valueOf(LocalDate.now()));
        Game archivedGame = gameService.updateGame(gameToArchive.getGameID(), gameToArchive, manager);

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.archiveGame(archivedGame, manager));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("The game is already archived", e.getMessage());

    }

    @Test
    public void testUnarchiveValidGame() {
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Game gameToUnarchive = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);
        gameToUnarchive.setArchivedDate(Date.valueOf(LocalDate.now()));
        Game archivedGame = gameService.updateGame(gameToUnarchive.getGameID(), gameToUnarchive, manager);

        Game unarchivedGame = gameService.unarchiveGame(archivedGame, VALID_MANAGER);

        assertNotNull(unarchivedGame);
		assertEquals(null, unarchivedGame.getArchivedDate());

    }
    
    @Test
    public void testUnarchiveInvalidGame() {
        Game gameToUnarchive = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);
        
        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.unarchiveGame(gameToUnarchive, manager));
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("The game is already unarchived", e.getMessage());

    }

    @Test
    public void testUpdateApprovalValidGame() {
        when(mockGameRepo.save(any(Game.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        Game gameToUpdateApproval = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        Game updatedGame = gameService.updateGameApproval(gameToUpdateApproval, true, manager);

        assertNotNull(updatedGame);
		assertEquals(true, updatedGame.getIsApproved());
    }

    @Test
    public void testUpdateApprovalInvalidGame() {
        Game gameToUpdateApproval = gameService.createGame(VALID_TITLE, VALID_DESC, VALID_PRICE, VALID_STOCK, VALID_IMAGE, VALID_ISAPPROVED, manager, manager);

        gameToUpdateApproval.setTitle("");

        GameStoreException e = assertThrows(GameStoreException.class,
				() -> gameService.updateGameApproval(gameToUpdateApproval, false, manager));
		assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
		assertEquals("Invalid game approval change request: missing attributes", e.getMessage());

    }
}
