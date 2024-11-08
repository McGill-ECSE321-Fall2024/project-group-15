package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Employee;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;

import group15.gameStore.repository.GameRepository;

import group15.gameStore.exception.GameStoreException;

import jakarta.transaction.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepo;

    public Game findGameByID(int gameID) {
        Game game = gameRepo.findGameByGameID(gameID);
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with ID %d", gameID));
        }
        return game;
    }

    public Game findGameByTitle(String title) {
        Game game = gameRepo.findGameByTitle(title);
        if (game == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with title %s", title));
        }
        return game;
    }

    public List<Game> findGamesByPrice(double price) {
        List<Game> games = gameRepo.findGamesByPrice(price);
        if (games.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, String.format("There is no game with price %f", price));
        }
        return games;
    }

    public List<Game> findAllGames() {
        List<Game> games = gameRepo.findAll();
        if (games.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There are no games in the system");
        }
        return games;
    }

    @Transactional
    public Game createGame(String title, String description, double price, int stock, String image, boolean isApproved, Manager manager, Employee employee) {
        if (!employee.isIsManager() && !isApproved) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The game has not been approved by the manager");
        }
        //Handle date
        Date today = Date.valueOf(LocalDate.now());
        Game game = new Game(title, description, price, stock, image, isApproved, manager);
        return gameRepo.save(game);
    }

    @Transactional
    public void deleteGame(Game gameToDelete, Employee employee) {
        Game game = gameRepo.findGameByGameID(gameToDelete.getGameID());
        if (!employee.isIsManager() && !gameToDelete.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The deletion of the game has not been approved by the manager");
        }
        //Search game
        if (gameRepo.findGameByGameID(game.getGameID()) == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The game to delete does not exist");
        }
        gameRepo.delete(game);
    }

    @Transactional
    public Game archiveGame(Game gameToArchive, Employee employee) {
        if (!employee.isIsManager() && !gameToArchive.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The archival of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToArchive.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The game to archive does not exist");
        }
        game.setArchivedDate(Date.valueOf(LocalDate.now()));
        return gameRepo.save(game);
    }

    @Transactional
    public Game unarchiveGame(Game gameToUnarchive, Employee employee) {
        if (!employee.isIsManager() && !gameToUnarchive.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The unarchival of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUnarchive.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The game to unarchive does not exist");
        }
        game.setArchivedDate(null);
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGameTitle(Game gameToUpdate, String newTitle, Employee employee) {
        if (!employee.isIsManager() && !gameToUpdate.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The title update of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the title since the game does not exist");
        }
        game.setTitle(newTitle);
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGameDescription(Game gameToUpdate, String newDescription, Employee employee) {
        if (!employee.isIsManager() && !gameToUpdate.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The description update of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the description since the game does not exist");
        }
        game.setDescription(newDescription);
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGamePrice(Game gameToUpdate, double newPrice, Employee employee) {
        if (!employee.isIsManager() && !gameToUpdate.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The price update of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the price since the game does not exist");
        }
        game.setPrice(newPrice);
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGameStock(Game gameToUpdate, int newStock, Employee employee) {
        if (!employee.isIsManager() && !gameToUpdate.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The stock update of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the stock since the game does not exist");
        }
        game.setStock(newStock);
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGameImage(Game gameToUpdate, String newImage, Employee employee) {
        if (!employee.isIsManager() && !gameToUpdate.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The image update of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the image since the game does not exist");
        }
        game.setImage(newImage);
        return gameRepo.save(game);
    }

    @Transactional
    public Game updateGameApproval(Game gameToUpdate, boolean newIsApproved, Employee employee) {
        if (!employee.isIsManager() && !gameToUpdate.isIsApproved()) {
            throw new GameStoreException(HttpStatus.UNAUTHORIZED, "The approval update of the game has not been approved by the manager");
        }
        //Search game
        Game game = gameRepo.findGameByGameID(gameToUpdate.getGameID());
        if (game == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Cannot update the approval since the game does not exist");
        }
        game.setIsApproved(newIsApproved);
        return gameRepo.save(game);
    }


}
