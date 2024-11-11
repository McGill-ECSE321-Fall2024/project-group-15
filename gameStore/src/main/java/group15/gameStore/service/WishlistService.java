package group15.gameStore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Wishlist;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.WishlistRepository;
import jakarta.transaction.Transactional;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepo;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    GameRepository gameRepository;

    /**
     * CreateWishlist: creates a wishlist for a customer
     * @param userId: the ID of the user
     * @param wishlistName: the name of the wishlist
     * @param customer: the customer to create the wishlist for
     * @return the created wishlist
     * @throws IllegalArgumentException if customer ID is invalid
     */
     @Transactional  
     public Wishlist createWishlist(int userId, String wishlistName, Customer customer) {
         Wishlist wishlist = new Wishlist(wishlistName, customer);        
         if (wishlist.getCustomer().getUserID() != customer.getUserID()) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
         }
         wishlistRepo.save(wishlist);
         return wishlist;
     } 

    /**
     * DeleteWishlist: deletes a wishlist
     * @param wishlistId the ID of the wishlist
     * @param customer the customer to delete the wishlist for
     * @throws IllegalArgumentException if customer ID is invalid
     */
    @Transactional
    public void deleteWishlist(int wishlistId, Customer customer) {
        Wishlist wishlist = wishlistRepo.findById(wishlistId).orElse(null);
        if (wishlist.getCustomer().getUserID() != customer.getUserID()) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }
        wishlistRepo.delete(wishlist);
    }
    

    /**
     * AddGameToWishlist: adds a game to a wishlist
     * @param wishlistId: the ID of the wishlist
     * @param gameId: the ID of the game to add
     * @return the updated wishlist
     * @throws IllegalArgumentException if wishlist ID or game ID is invalid
     */
    @Transactional
    public Wishlist addGameToWishlist(int wishlistId, int gameId, Customer customer) {
        Wishlist wishlist = wishlistRepo.findById(wishlistId).orElse(null);
        if (wishlist.getCustomer().getUserID() != customer.getUserID()) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }
        Game game = gameRepository.findById(String.valueOf(gameId)).orElse(null);
        if (game == null) {
            throw new IllegalArgumentException("Game with the specified ID does not exist.");
        }

        wishlist.addGame(game);
        wishlistRepo.save(wishlist);
        return wishlist;
    }

    /**
     * RemoveGameFromWishlist: removes a game from a wishlist
     * @param wishlistId: the ID of the wishlist
     * @param gameId: the ID of the game to remove
     * @return the updated wishlist
     * @throws IllegalArgumentException if wishlist ID or game ID is invalid
     */
    @Transactional
    public Wishlist removeGameFromWishlist(int wishlistId, int gameId, Customer customer) {
        Wishlist wishlist = wishlistRepo.findById(wishlistId).orElse(null);
        if (wishlist.getCustomer().getUserID() != customer.getUserID()) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }

        Game game = gameRepository.findById(String.valueOf(gameId)).orElse(null);
        if (game == null) {
            throw new IllegalArgumentException("Game with the specified ID does not exist.");
        }

        wishlist.removeGame(game);
        wishlistRepo.save(wishlist);
        return wishlist;
    }


    


    @Transactional
    public List<Wishlist> getWishlistByUserId(int userId) {
        Customer customer = customerRepository.findById(userId).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }
        List<Wishlist> wishlist = wishlistRepo.findByUserID(userId);
        return wishlist;
    }

    /**
     * GetWishlistByWishlistId: retrieves a wishlist by its wishlistId
     * @param wishlistId: the ID of the wishlist
     * @return the wishlist with the specified wishlistId
     * @throws IllegalArgumentException if wishlistId is invalid
     */
    @Transactional
    public Wishlist getWishlistByWishlistId(int wishlistId) {
        Wishlist wishlist = wishlistRepo.findByWishListId(wishlistId);
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist with the specified ID does not exist.");
        }

        return wishlist;
    }


}
