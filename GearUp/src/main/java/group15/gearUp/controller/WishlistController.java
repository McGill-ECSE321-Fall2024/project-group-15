package group15.gearUp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gearUp.model.Customer;
import group15.gearUp.model.Wishlist;
import group15.gearUp.service.CustomerService;
import group15.gearUp.dto.CustomerDto;
import group15.gearUp.dto.WishlistDto;
import group15.gearUp.service.WishlistService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
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
     * addEquipmentToWishlist: add a Equipment to a wishlist
     * @param wishlistId the ID of the wishlist
     * @param EquipmentId the ID of the Equipment
     * @param customerDto the customer details for whom the Equipment is added to the wishlist
     * @return the updated wishlist in WishlistDto format and HTTP Status "OK"
     */
    @PutMapping("/wishlist/addEquipment/{wishlistId}/{EquipmentId}")
    public ResponseEntity<WishlistDto> addEquipmentToWishlist(@PathVariable int wishlistId, @PathVariable int EquipmentId, @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        System.out.println("Customer: " + customer);
        Wishlist wishlist = wishlistService.addEquipmentToWishlist(wishlistId, EquipmentId, customer);
        return new ResponseEntity<>(new WishlistDto(wishlist), HttpStatus.OK);
    }

    /**
     * removeEquipmentFromWishlist: remove a Equipment from a wishlist
     * @param wishlistId the ID of the wishlist
     * @param EquipmentId the ID of the Equipment
     * @param customerDto the customer details to validate ownership of the wishlist
     * @return the updated wishlist in WishlistDto format and HTTP Status "OK"
     */
    @DeleteMapping("/wishlist/removeEquipment/{wishlistId}/{EquipmentId}")
    public ResponseEntity<WishlistDto> removeEquipmentFromWishlist(@PathVariable int wishlistId, @PathVariable int EquipmentId, @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.getCustomerByID(customerDto.getUserId());
        Wishlist wishlist = wishlistService.removeEquipmentFromWishlist(wishlistId, EquipmentId, customer);
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