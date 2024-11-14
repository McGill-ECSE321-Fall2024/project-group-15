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
     * CreateWishlist: creates a new wishlist for a customer
     * @param userId the ID of the user
     * @param wishlistName the name of the wishlist
     * @param customerDto the CustomerDto containing the customer details
     * @return the created wishlist and HTTP Status "CREATED"
     */
    @PostMapping("/wishlist/create/{userId}/{wishlistName}")
    public ResponseEntity<WishlistDto> createWishlist(@PathVariable int userId, @PathVariable String wishlistName,@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        Wishlist createdWishlist = wishlistService.createWishlist(userId, wishlistName, customer);
        return new ResponseEntity<>(new WishlistDto(createdWishlist), HttpStatus.CREATED);
    }

    /**
     * DeleteWishlist: deletes a wishlist for a specified customer
     * @param wishlistId the ID of the wishlist to delete
     * @param customerDto the CustomerDto containing the customer's details
     * @return HTTP status "NO CONTENT" if the deletion is successful
     */
    @DeleteMapping("/wishlist/delete/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable int wishlistId, @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        wishlistService.deleteWishlist(wishlistId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * addGameToWishlist: add a game to a wishlist
     * @param wishlistId the ID of the wishlist
     * @param gameId the ID of the game
     * @param customerDto the customer details for whom the game is added to the wishlist
     * @return the updated wishlist in WishlistDto format and HTTP Status "OK"
     */
    @PutMapping("/wishlist/addgame/{wishlistId}/{gameId}")
    public ResponseEntity<WishlistDto> addGameToWishlist(@PathVariable int wishlistId, @PathVariable int gameId, @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        Wishlist wishlist = wishlistService.addGameToWishlist(wishlistId, gameId, customer);
        return new ResponseEntity<>(new WishlistDto(wishlist), HttpStatus.OK);
    }

    /**
     * removeGameFromWishlist: remove a game from a wishlist
     * @param wishlistId the ID of the wishlist
     * @param gameId the ID of the game
     * @param customerDto the customer details to validate ownership of the wishlist
     * @return the updated wishlist in WishlistDto format and HTTP Status "OK"
     */
    @DeleteMapping("/wishlist/removegame/{wishlistId}/{gameId}")
    public ResponseEntity<WishlistDto> removeGameFromWishlist(@PathVariable int wishlistId, @PathVariable int gameId, @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        Wishlist wishlist = wishlistService.removeGameFromWishlist(wishlistId, gameId, customer);
        return new ResponseEntity<>(new WishlistDto(wishlist), HttpStatus.OK);
    }

    /**
     * Get a wishlist by its ID
     * @param wishlistId the ID of the wishlist
     * @return the wishlist with the given ID in WishlistDto format
     */
    @GetMapping("/wishlist/byId/{wishlistId}")
    public ResponseEntity<WishlistDto> getWishlistByWishlistId(@PathVariable int wishlistId) {
        WishlistDto wishlistDto = new WishlistDto(wishlistService.getWishlistByWishlistId(wishlistId));
        return new ResponseEntity<>(wishlistDto, HttpStatus.OK);
    }

}