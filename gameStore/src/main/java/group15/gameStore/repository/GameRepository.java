package group15.gameStore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import group15.gameStore.model.Game;

public interface GameRepository extends CrudRepository<Game, String>{

    Game findGameByGameID(int gameID);

    Game findGameByTitle(String title);
    // Game findGameByType(String type);
    
    List<Game> findAll();

    
}
