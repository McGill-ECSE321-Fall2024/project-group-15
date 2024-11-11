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

    public Game getGameByID(int gameID) {
        Game game = gameRepo.findGameByGameID(gameID);
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with ID %d", gameID));
        }
        return game;
    }

    public Game getGameByTitle(String title) {
        Game game = gameRepo.findGameByTitle(title);
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with title %s", title));
        }
        return game;
    }

    public List<Game> getGamesByPrice(double price) {
        List<Game> games = gameRepo.findGamesByPrice(price);
        if (games.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with price %f", price));
        }
        return games;
    }

    public List<Game> getAllGames() {
        List<Game> games = gameRepo.findAll();
        if (games.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no games in the system");
        }
        return games;
    }

    @Transactional
    public Game createGame(String title, String description, double price, int stock, String image, boolean isApproved, Manager manager, Employee employee) {
        //TODO Handle date
        //Date today = Date.valueOf(LocalDate.now());
        if (title.isBlank() || description.isBlank() || image.isBlank() || manager == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (price < 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The price of a game cannot be negative");
        }
        if (stock < 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The stock of a game cannot be negative");
        }
        if (managerRepo.findManagerByUserID(manager.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The manager '%s' does not exist", manager.getUsername()));
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        Game game = new Game(title, description, price, stock, image, isApproved, manager);
        return gameRepo.save(game);
    }
    
    @Transactional
    public void deleteGame(Game gameToDelete, Employee employee) {
        if (gameToDelete == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        /*if (!employee.isIsManager() && !gameToDelete.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The deletion of the game has not been approved by the manager");
        }*/
        //Search game
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        Game game = gameRepo.findGameByGameID(gameToDelete.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "The game to delete does not exist");
        }
        gameRepo.delete(game);
    }

    @Transactional
    public Game archiveGame(Game gameToArchive, Employee employee) {
        if (gameToArchive == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        /*if (!employee.isIsManager() && !gameToArchive.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The archival of the game has not been approved by the manager");
        }*/
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToArchive.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "The game to archive does not exist");
        }
        game.setArchivedDate(Date.valueOf(LocalDate.now()));
        game.setIsApproved(false);
        return gameRepo.save(game);
    }

    @Transactional
    public Game unarchiveGame(Game gameToUnarchive, Employee employee) {
        /*if (!employee.isIsManager() && !gameToUnarchive.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The unarchival of the game has not been approved by the manager");
        }*/
        if (gameToUnarchive == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUnarchive.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "The game to unarchive does not exist");
        }
        game.setArchivedDate(null);
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGame(int gameID, Game updatedGame, Employee employee) {
        if (updatedGame == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameID);
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Cannot update the game since it does not exist");
        }
        game.setTitle(updatedGame.getTitle());
        game.setDescription(updatedGame.getDescription());
        game.setPrice(updatedGame.getPrice());
        game.setStock(updatedGame.getStock());
        game.setImage(updatedGame.getImage());
        if (employee.getIsManager()) {
            game.setArchivedDate(updatedGame.getArchivedDate());
            game.setIsApproved(updatedGame.getIsApproved());
        }
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGameApproval(Game gameToUpdate, boolean newIsApproved, Employee employee) {
        if (gameToUpdate == null || employee == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid game creation request: missing attributes");
        }
        if (employeeRepo.findByUserID(employee.getUserID()) == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("The employee '%s' that made the request does not exist", employee.getUsername()));
        }
        if (!employee.isIsManager()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The approval update of the game can only be done by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Cannot update the approval since the game does not exist");
        }
        game.setIsApproved(newIsApproved);
        return gameRepo.save(game);
    }

}
