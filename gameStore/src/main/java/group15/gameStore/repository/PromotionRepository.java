package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;

import group15.gameStore.model.Game;
import group15.gameStore.model.Promotion;

import java.sql.Date;
import java.util.List;

public interface PromotionRepository extends CrudRepository<Promotion, Integer> {
    // Find promotions by ID
    Promotion findPromotionByPromotionID(int id);
    // Promotion findById(int Id);

    // Find promotions by game
    Promotion findByGame(Game game);

    // Find promotions by promotion code
    Promotion findByPromotionCode(String promotionCode);

    // Find all promotions that have a valid date greater than or equal to today
    List<Promotion> findByValidUntilAfter(Date today);

    // Delete a promotion by promotion code
    void deleteByPromotionCode(String promotionCode);



    // Get all promotions
    @SuppressWarnings("null")
    List<Promotion> findAll();
}
