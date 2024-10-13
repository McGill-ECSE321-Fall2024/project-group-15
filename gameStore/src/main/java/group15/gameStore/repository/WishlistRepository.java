package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Wishlist;
import java.util.List;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {

    // Find Wishlist by wishListId
    Wishlist findByWishListId(Integer wishListId);

    // Find Wishlist by name (case-insensitive)
    List<Wishlist> findByWishListNameContainingIgnoreCase(String wishListName);

    // Find all Wishlists associated with a specific game
    List<Wishlist> findByGames_GameId(Integer gameId);

    // Delete Wishlist by wishListId
    void deleteByWishListId(Integer wishListId);

    // Get all Wishlists
    List<Wishlist> findAll();
}
