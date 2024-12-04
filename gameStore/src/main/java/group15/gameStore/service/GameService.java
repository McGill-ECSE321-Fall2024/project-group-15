package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Employee;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;
import group15.gameStore.repository.EmployeeRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.ManagerRepository;
import group15.gameStore.exception.GameStoreException;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepo;
    @Autowired
    private ManagerRepository managerRepo;
    @Autowired
    private EmployeeRepository employeeRepo;

    /**
     * GetGameByID: retrieves a game by its unique ID
     * @param gameID the unique identifier of the game
     * @return the Game object with the specified ID
     * @throws GameStoreException if no game is found with the given ID
     */
    public Game getGameByID(int gameID) {
        Game game = gameRepo.findGameByGameID(gameID);
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with ID %d", gameID));
        }
        return game;
    }
    /**
     * GetGameByTitle: retrieves a game by its title
     * @param title the title of the game
     * @return the Game object with the specified title
     * @throws GameStoreException if no game is found with the given title
     */
    public Game getGameByTitle(String title) {
        Game game = gameRepo.findGameByTitle(title);
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with title %s", title));
        }
        return game;
    }
    /**
     * GetGamesByPrice: retrieves all games with the specified price
     * @param price the price of the games to retrieve
     * @return a list of Game objects with the specified price
     * @throws GameStoreException if no games are found with the specified price
     */
    public List<Game> getGamesByPrice(double price) {
        List<Game> games = gameRepo.findGamesByPrice(price);
        if (games.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with price %f", price));
        }
        return games;
    }
    /**
     * GetAllGames: retrieves all games in the system
     * @return a list of all Game objects
     * @throws GameStoreException if there are no games in the system
     */
    public List<Game> getAllGames() {
        List<Game> games = gameRepo.findAll();
        if (games.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no games in the system");
        }
        return games;
    }

    /**
     * CreateGame: creates a new game with specified details
     * @param title the title of the game
     * @param description the description of the game
     * @param price the price of the game
     * @param stock the stock quantity of the game
     * @param image the image URL or path of the game
     * @param isApproved the approval status of the game
     * @param manager the manager overseeing the game creation
     * @param employee the employee creating the game
     * @return the newly created Game object
     * @throws GameStoreException if any required attribute is missing or invalid
     */
    @Transactional
    public Game createGame(String title, String description, double price, int stock, String image, boolean isApproved, int managerId) {
        if (title.isBlank() || description.isBlank() || image.isBlank()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (price < 0 || stock < 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The price or stock of a game cannot be negative");
        }
        
        if (managerRepo.findManagerByUserID(managerId) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND,"The manager does not exist");
        }
        Game game = new Game(title, description, price, stock, image, isApproved, managerRepo.findManagerByUserID(managerId));
        return gameRepo.save(game);
    }
    
    /**
     * DeleteGame: deletes a game from the system
     * @param gameToDelete the Game object to delete
     * @param employee the Employee performing the deletion
     * @throws GameStoreException if game or employee is missing, or employee is unauthorized
     */
    @Transactional
    public void deleteGame(Game gameToDelete) {
        if (gameToDelete == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToDelete.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "The game to delete does not exist");
        }
        gameRepo.delete(game);
    }

    /**
     * ArchiveGame: archives a game in the system
     * @param gameToArchive the Game object to archive
     * @param employee the Employee performing the archival
     * @return the updated archived Game object
     * @throws GameStoreException if game or employee is missing, or employee is unauthorized
     */
    @Transactional
    public Game archiveGame(Game gameToArchive) {
        if (gameToArchive == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game archival request: missing attributes");
        }
        if (!gameToArchive.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The archival of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToArchive.getGameID());
        if (game.getArchivedDate() != null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The game is already archived");
        }
        game.setArchivedDate(Date.valueOf(LocalDate.now()));
        game.setIsApproved(false);
        return gameRepo.save(game);
    }

    /**
     * UnarchiveGame: unarchives a game in the system
     * @param gameToUnarchive the Game object to unarchive
     * @param employee the Employee performing the unarchival
     * @return the updated unarchived Game object
     * @throws GameStoreException if game or employee is missing, employee is unauthorized, or game does not exist
     */
    @Transactional
    public Game unarchiveGame(Game gameToUnarchive) {
        if (gameToUnarchive == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game unarchival request: missing attributes");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUnarchive.getGameID());
        if (game.getArchivedDate() == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The game is already unarchived");
        }
        game.setArchivedDate(null);
        return gameRepo.save(game);
    }

    /**
     * UpdateGame: updates an existing game's information
     * @param gameID the ID of the game to update
     * @param updatedGame the new game information to update to
     * @param employee the Employee requesting the update
     * @return the updated Game object
     * @throws GameStoreException if the update request is invalid
     */
    @Transactional
    public Game updateGame(int gameID, Game updatedGame) {
        if (updatedGame == null || updatedGame.getTitle().isBlank() || updatedGame.getDescription().isBlank() || updatedGame.getImage().isBlank() || updatedGame.getManager() == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        Game existingGame = gameRepo.findGameByGameID(gameID);
        if (existingGame == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Game with the specified ID does not exist.");
        }
        String title = updatedGame.getTitle();
        String description = updatedGame.getDescription();
        double price = updatedGame.getPrice();
        int stock = updatedGame.getStock();
        String image = updatedGame.getImage();
        if (title == null || title.trim().isEmpty() || description == null || description.trim().isEmpty() || image == null || image.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Game Attribute is required");
        }
        if (price < 0 || stock < 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The price or stock of a game cannot be negative.");
        }
        existingGame.setTitle(title);
        existingGame.setDescription(description);
        existingGame.setPrice(price);
        existingGame.setStock(stock);
        existingGame.setImage(image);

        return gameRepo.save(existingGame);
    }

    /**
     * UpdateGameApproval: updates the approval status of a game
     * @param gameToUpdate the Game object to update approval for
     * @param newIsApproved the new approval status
     * @param employee the Employee performing the approval update
     * @return the updated Game object with new approval status
     * @throws GameStoreException if game or employee is missing, employee is unauthorized, or game does not exist
     */
    @Transactional
    public Game updateGameApproval(Game gameToUpdate, Boolean newIsApproved) {
        if (gameToUpdate == null || gameToUpdate.getTitle().isBlank() || gameToUpdate.getDescription().isBlank() || gameToUpdate.getImage().isBlank() || gameToUpdate.getManager() == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game approval change request: missing attributes");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        game.setIsApproved(newIsApproved);
        return gameRepo.save(game);
    }

}