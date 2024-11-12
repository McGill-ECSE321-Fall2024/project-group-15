package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.GameRequestDto;
import group15.gameStore.dto.GameResponseDto;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Game;
import group15.gameStore.service.GameService;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    /**
     * 
     * @param gameToCreate
     * @return
     */
    @PostMapping("/game/create")
    public ResponseEntity<GameResponseDto> createGame(@RequestBody GameRequestDto gameToCreate) {
        try {
            Game createdGame = gameService.createGame(gameToCreate.getTitle(), gameToCreate.getDescription(), gameToCreate.getPrice(), gameToCreate.getStock(), gameToCreate.getImage(), gameToCreate.getIsApproved(), gameToCreate.getManager(), null);
            return new ResponseEntity<>(new GameResponseDto(createdGame), HttpStatus.CREATED);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    /**
     * 
     * @param gameID
     * @param gameToUpdate
     * @return
     */
    @PutMapping("/game/{gameID}/update")
    public ResponseEntity<GameResponseDto> updateGame(@PathVariable("gameID") int gameID, GameRequestDto gameToUpdate) {
        try {
            Game updatedGame = gameService.updateGame(gameID, gameToUpdate.getGame(), gameToUpdate.getEmployee());
            return new ResponseEntity<>(new GameResponseDto(updatedGame), HttpStatus.OK); 
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        } 
    }

    @GetMapping("/game/{gameID}")
    public ResponseEntity<GameResponseDto> getGameByID(@PathVariable("gameID") int gameID) {
        try {
            Game game = gameService.getGameByID(gameID);
            GameResponseDto gameResponse = new GameResponseDto(game);
            return new ResponseEntity<>(gameResponse, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @GetMapping("/game/{gameTitle}")
    public ResponseEntity<GameResponseDto> getGamesByTitle(@PathVariable("gameTitle") String gameTitle) {
        try {
            Game game = gameService.getGameByTitle(gameTitle);
            GameResponseDto gameResponse = new GameResponseDto(game);
            return new ResponseEntity<>(gameResponse, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @GetMapping("/game")
    public ResponseEntity<List<GameResponseDto>> getAllGames() {
        try {
            List<Game> gameList = gameService.getAllGames();
            if (gameList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<GameResponseDto> gameResponseDtos = gameList.stream()
                    .map(GameResponseDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(gameResponseDtos, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @DeleteMapping("/game/{gameID}/delete")
    public ResponseEntity<Void> deleteGameByID(@PathVariable("gameID") int gameID, @RequestBody GameRequestDto gameToDelete) {
        try {
            Game deletedGame = gameService.getGameByID(gameID);
            gameService.deleteGame(deletedGame, gameToDelete.getEmployee());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }
    
    @DeleteMapping("/game/{gameTitle}/delete")
    public ResponseEntity<Void> deleteGameByTitle(@PathVariable("gameTitle") String gameTitle, @RequestBody GameRequestDto gameToDelete) {
        try {
            Game deletedGame = gameService.getGameByTitle(gameTitle);
            gameService.deleteGame(deletedGame, gameToDelete.getEmployee());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

}