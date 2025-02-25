package group15.gearUp.repository;

import org.springframework.data.repository.CrudRepository;

import group15.gearUp.model.Wishlist;
import java.util.List;

public interface WishlistRepository extends CrudRepository<Wishlist, Integer> {

    // Find Wishlist by wishListId
    Wishlist findByWishListId(Integer wishListId);

    // Find Wishlist by name (case-insensitive)
    List<Wishlist> findByWishListNameContainingIgnoreCase(String wishListName);

    // Find all Wishlists associated with a specific Equipment
    List<Wishlist> findByEquipments_EquipmentID(Integer EquipmentID);

    // Delete Wishlist by wishListId
    void deleteByWishListId(Integer wishListId);

    // Get all Wishlists
    @SuppressWarnings("null")
    List<Wishlist> findAll();

}
