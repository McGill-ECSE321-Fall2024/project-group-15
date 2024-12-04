package group15.gameStore.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.EmployeeDto;
import group15.gameStore.dto.GameDto;
import group15.gameStore.dto.ManagerDto;
import group15.gameStore.model.Employee;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;
import group15.gameStore.service.EmployeeService;
import group15.gameStore.service.GameService;
import group15.gameStore.service.ManagerService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
@RestController
public class GameController{

    @Autowired
    private GameService gameService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private EmployeeService employeeService;

    /**
     * CreateGame: creates a new game record
     * @param gameDto the GameDto containing the game details
     * @param managerDto the ManagerDto associated with the game
     * @param employeeDto the EmployeeDto who creates the game
     * @return the created game and HTTP Status "CREATED"
     */
    @PostMapping("/game")
    public ResponseEntity<GameDto> createGame(@RequestBody GameDto gameDto) {
        Game createdGame = gameService.createGame(gameDto.getTitle(), gameDto.getDescription(), gameDto.getPrice(),
            gameDto.getStock(), gameDto.getImage(), gameDto.getIsApproved(), gameDto.getManagerId());
        
            return new ResponseEntity<>(new GameDto(createdGame), HttpStatus.CREATED);
    }

    /**
     * UpdateGame: updates an existing game
     * @param gameId the ID of the game to update
     * @param gameDto the GameDto containing updated game details
     * @param employeeDto the EmployeeDto who updates the game
     * @return the updated game and HTTP Status "OK"
     */
    @PutMapping("/game/{gameId}")
    public ResponseEntity<GameDto> updateGame(@PathVariable int gameId, @RequestBody GameDto gameDto) {
        Game gameToUpdate = gameService.getGameByID(gameId);
        gameToUpdate.setTitle(gameDto.getTitle());
        gameToUpdate.setDescription(gameDto.getDescription());
        gameToUpdate.setPrice(gameDto.getPrice());
        gameToUpdate.setStock(gameDto.getStock());
        gameToUpdate.setImage(gameDto.getImage());
        if (gameToUpdate.getArchivedDate() != null) {
            gameToUpdate.setArchivedDate(Date.valueOf(gameDto.getArchivedDate()));
        }
        gameToUpdate.setIsApproved(gameDto.getIsApproved());
        Game updatedGame = gameService.updateGame(gameId, gameToUpdate);
        return new ResponseEntity<>(new GameDto(updatedGame), HttpStatus.OK);
    }

    /**
    * UpdateGameApproval: updates the approval status of a game
    * @param gameId the ID of the game to update
    * @param newIsApproved the new approval status for the game
    * @param employeeDto the EmployeeDto who requests the approval update
    * @return the updated game with the new approval status and HTTP Status "OK"
    */
    @PutMapping("/game/{gameId}/approval")
    public ResponseEntity<GameDto> updateGameApproval(@PathVariable int gameId, @RequestParam Boolean newIsApproved) {
        Game gameToUpdate = gameService.getGameByID(gameId);
        
        Game updatedGame = gameService.updateGameApproval(gameToUpdate, newIsApproved);
        return new ResponseEntity<>(new GameDto(updatedGame), HttpStatus.OK);
    }

    /**
     * GetGameById: retrieves a game by ID
     * @param gameId the ID of the game to retrieve
     * @return the game information and HTTP Status "OK"
     */
    @GetMapping("/game/{gameId}")
    public ResponseEntity<GameDto> getGameById(@PathVariable int gameId) {
        Game game = gameService.getGameByID(gameId);
        return new ResponseEntity<>(new GameDto(game), HttpStatus.OK);
    }

    /**
     * GetGameByTitle: retrieves a game by title
     * @param title the title of the game to retrieve
     * @return the game information and HTTP Status "OK"
     */
    @GetMapping("/game/title/{title}")
    public ResponseEntity<GameDto> getGameByTitle(@PathVariable String title) {
        Game game = gameService.getGameByTitle(title);
        return new ResponseEntity<>(new GameDto(game), HttpStatus.OK);
    }

    /**
     * GetGamesByPrice: retrieves games by price
     * @param price the price to filter games by
     * @return a list of games with the specified price and HTTP Status "OK"
     */
    @GetMapping("/games/price/{price}")
    public ResponseEntity<List<GameDto>> getGamesByPrice(@PathVariable double price) {
        List<Game> games = gameService.getGamesByPrice(price);
        List<GameDto> gameDtos = games.stream().map(GameDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    /**
     * GetAllGames: retrieves all games in the system
     * @return a list of all games and HTTP Status "OK"
     */
    @GetMapping("/games")
    public ResponseEntity<List<GameDto>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        List<GameDto> gameDtos = games.stream().map(GameDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(gameDtos, HttpStatus.OK);
    }

    /**
     * ArchiveGame: archives a game
     * @param gameId the ID of the game to archive
     * @param employeeDto the EmployeeDto who archives the game
     * @return the archived game and HTTP Status "OK"
     */
    @PutMapping("/game/archive/{gameId}")
    public ResponseEntity<GameDto> archiveGame(@PathVariable int gameId) {
        Game gameToArchive = gameService.getGameByID(gameId);
        Game archivedGame = gameService.archiveGame(gameToArchive);
        return new ResponseEntity<>(new GameDto(archivedGame), HttpStatus.OK);
    }

    /**
     * UnarchiveGame: unarchives a game
     * @param gameId the ID of the game to unarchive
     * @param employeeDto the EmployeeDto who unarchives the game
     * @return the unarchived game and HTTP Status "OK"
     */
    @PutMapping("/game/unarchive/{gameId}")
    public ResponseEntity<GameDto> unarchiveGame(@PathVariable int gameId) {
        Game gameToUnarchive = gameService.getGameByID(gameId);
        Game unarchivedGame = gameService.unarchiveGame(gameToUnarchive);
        return new ResponseEntity<>(new GameDto(unarchivedGame), HttpStatus.OK);
    }

    /**
     * DeleteGame: deletes a game by ID
     * @param gameId the ID of the game to delete
     * @param employeeDto the EmployeeDto who deletes the game
     * @return HTTP Status "NO CONTENT" if the deletion is successful
     */
    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable int gameId) {
        Game gameToDelete = gameService.getGameByID(gameId);
        gameService.deleteGame(gameToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}