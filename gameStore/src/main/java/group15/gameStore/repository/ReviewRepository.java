package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Review;
import group15.gameStore.model.Game;
import group15.gameStore.model.Rating;
import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    //Find a review by Id
    Review findByReviewID(int reviewID);

    // Find reviews by rating
    List<Review> findByRating(Rating rating);

    // Find reviews by description containing a keyword (case-insensitive)
    List<Review> findByDescriptionContainingIgnoreCase(String keyword);

    // Find reviews for a specific game
    List<Review> findByGame(Game game);

    // Delete review by reviewID
    void deleteByReviewID(Integer reviewID);

    // Get all reviews
    @SuppressWarnings("null")
    List<Review> findAll();
}
