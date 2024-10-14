package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Review;
import group15.gameStore.model.Rating;
import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    //Find a review by Id
    Review findById(int id);

    // Find reviews by rating
    List<Review> findByRating(Rating rating);

    // Find reviews by description containing a keyword (case-insensitive)
    List<Review> findByDescriptionContainingIgnoreCase(String keyword);

    // Find reviews for a specific game
    List<Review> findByGameID(Integer gameID);

    // Delete review by reviewID
    void deleteByReviewID(Integer reviewID);

    // Get all reviews
    List<Review> findAll();
}
