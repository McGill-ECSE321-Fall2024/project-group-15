package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.model.Customer;
import group15.gameStore.model.Wishlist;
import group15.gameStore.service.CustomerService;
import group15.gameStore.dto.CustomerDto;
import group15.gameStore.dto.WishlistDto;
import group15.gameStore.service.WishlistService;

@RestController
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private CustomerService customerService;

    


    /**
     * Create a wishlist
     * @param userId the ID of the user
     * @param wishlistName the name of the wishlist
     * @param customerDto the customer to create the wishlist for
     * @return the created wishlist
     */
    @PutMapping("/wishlist/create{userId}/{wishlistName}")
    public WishlistDto createWishlist(@PathVariable int userId, @PathVariable String wishlistName, @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService.getCustomerByID(customerDto.getUserId());
            Wishlist wishlist = wishlistService.createWishlist(userId, wishlistName, customer);
            return new WishlistDto(wishlist);
        } catch (Exception e) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }
    }

    /**
     * Delete a wishlist
     * @param wishlistId the ID of the wishlist
     * @param customerDto the customer to delete the wishlist for
     */
    @DeleteMapping("/wishlist/delete/{wishlistId}")
    public void deleteWishlist(@PathVariable int wishlistId, @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService.getCustomerByID(customerDto.getUserId());
            wishlistService.deleteWishlist(wishlistId, customer);
        } catch (Exception e) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }
    }

    /**
     * Add a game to a wishlist
     * @param wishlistId the ID of the wishlist
     * @param gameId the ID of the game
     * @param customerDto the customer to add the game to the wishlist for
     * @return the updated wishlist
     */
    @PutMapping("/wishlist/addgame/{wishlistId}/{gameId}")
    public WishlistDto addGameToWishlist(@PathVariable int wishlistId, @PathVariable int gameId, @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService.getCustomerByID(customerDto.getUserId());
            Wishlist wishlist = wishlistService.addGameToWishlist(wishlistId, gameId, customer);
            return new WishlistDto(wishlist);
        } catch (Exception e) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }
    }

    /**
     * Remove a game from a wishlist
     * @param wishlistId the ID of the wishlist
     * @param gameId the ID of the game
     * @param customerDto the customer to remove the game from the wishlist for
     * @return the updated wishlist
     */
    @DeleteMapping("/wishlist/removegame/{wishlistId}/{gameId}")
    public WishlistDto removeGameFromWishlist(@PathVariable int wishlistId, @PathVariable int gameId, @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService.getCustomerByID(customerDto.getUserId());
            Wishlist wishlist = wishlistService.removeGameFromWishlist(wishlistId, gameId, customer);
            return new WishlistDto(wishlist);
        } catch (Exception e) {
            throw new IllegalArgumentException("Customer with the specified ID does not exist.");
        }
    }

    /**
     * Get a wishlist by its ID
     * @param wishlistId the ID of the wishlist
     * @return the wishlist with the given ID
     */
    @GetMapping("/wishlist/{wishlistId}")
    public WishlistDto getWishlistByWishlistId(@PathVariable int wishlistId) {
        WishlistDto wishlistDto = new WishlistDto(wishlistService.getWishlistByWishlistId(wishlistId));
        return wishlistDto;
    }

    /**
     * Get a wishlist by the user's ID
     * @param userId the ID of the user
     * @return the wishlist with the given user ID
     */
    @GetMapping("/wishlist/{userId}")
    public List<WishlistDto> getWishlistByUserId(@PathVariable int userId) {
        List<WishlistDto> wishlistDtos = wishlistService.getWishlistByUserId(userId).stream().map(wishlist -> new WishlistDto(wishlist)).collect(Collectors.toList());
        return wishlistDtos;
    }

}