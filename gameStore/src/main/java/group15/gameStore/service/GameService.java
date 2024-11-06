package group15.gameStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Game;
import group15.gameStore.repository.GameRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepo;

    public Game findGameByID(int gameID) {
        Game game = gameRepo.findGameByGameID(gameID);
        if (game == null) {
            //Raise Error
        }
        return game;
    }

    public Game findGameByTitle(String title) {
        Game game = gameRepo.findGameByTitle(title);
        if (game == null) {
            //Raise Error
        }
        return game;
    }

    public List<Game> findGamesByPrice(double price) {
        List<Game> games = gameRepo.findGamesByPrice(price);
        if (games.isEmpty()) {
            //Raise Error
        }
        return games;
    }

    public List<Game> findAllGames() {
        List<Game> games = gameRepo.findAll();
        if (games.isEmpty()) {
            //Raise Error
        }
        return games;
    }

    @Transactional
    public Game createGame() {
        return ;
    }

    @Transactional
    public Game deleteGame() {
        return ;
    }

    @Transactional
    public Game updateGame() {
        return ;
    }
    
}