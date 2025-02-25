package group15.gearUp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gearUp.exception.GearUpException;
import group15.gearUp.model.Customer;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Wishlist;
import group15.gearUp.repository.CustomerRepository;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.WishlistRepository;
import jakarta.transaction.Transactional;

@Service
public class WishlistService {

    @Autowired
    WishlistRepository wishlistRepo;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

   /**
     * CreateWishlist: creates a wishlist for a customer
     * @param userId the ID of the user
     * @param wishlistName the name of the wishlist
     * @param customer the customer to create the wishlist for
     * @return the created wishlist
     * @throws GearUpException if customer ID is invalid
     */
    @Transactional  
    public Wishlist createWishlist(int userId, String wishlistName, Customer customer) {
        if (customerRepository.findByUserID(userId) == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Customer with the specified ID does not exist.");
        }
        
        Wishlist wishlist = new Wishlist(wishlistName, customer);
        wishlistRepo.save(wishlist);
        return wishlist;
    }

    /**
     * DeleteWishlist: deletes a wishlist
     * @param wishlistId the ID of the wishlist
     * @param customer the customer to delete the wishlist for
     * @throws GearUpException if customer ID or wishlist ID is invalid
     */
    @Transactional
    public void deleteWishlist(int wishlistId, Customer customer) {
        Wishlist wishlist = wishlistRepo.findByWishListId(wishlistId);
        if (wishlist == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Wishlist with the specified ID does not exist.");
        }
        if (wishlist.getCustomer().getUserID() != customer.getUserID()) {
            throw new GearUpException(HttpStatus.UNAUTHORIZED, "Customer is not authorized to delete this wishlist.");
        }
        
        wishlistRepo.delete(wishlist);
    }
    

    /**
     * AddEquipmentToWishlist: adds a Equipment to a wishlist
     * @param wishlistId the ID of the wishlist
     * @param EquipmentId the ID of the Equipment to add
     * @param customer the customer to validate access
     * @return the updated wishlist
     * @throws GearUpException if wishlist ID, Equipment ID, or customer ID is invalid
     */
    @Transactional
    public Wishlist addEquipmentToWishlist(int wishlistId, int EquipmentId, Customer customer) {
        Wishlist wishlist = wishlistRepo.findByWishListId(wishlistId);
        if (wishlist == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Wishlist with the specified ID does not exist.");
        }
        if (wishlist.getCustomer().getUserID() != customer.getUserID()) {
            throw new GearUpException(HttpStatus.UNAUTHORIZED, "Customer is not authorized to modify this wishlist.");
        }
        Equipment equipment = equipmentRepository.findEquipmentByEquipmentID(EquipmentId);
        if (equipment == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Equipment with the specified ID does not exist.");
        }

        wishlist.addEquipment(equipment);
        
        return wishlistRepo.save(wishlist);
    }

    /**
     * RemoveEquipmentFromWishlist: removes a Equipment from a wishlist
     * @param wishlistId the ID of the wishlist
     * @param EquipmentId the ID of the Equipment to remove
     * @param customer the customer to validate access
     * @return the updated wishlist
     * @throws GearUpException if wishlist ID, Equipment ID, or customer ID is invalid
     */
    @Transactional
    public Wishlist removeEquipmentFromWishlist(int wishlistId, int EquipmentId, Customer customer) {
        Wishlist wishlist = wishlistRepo.findByWishListId(wishlistId);
        if (wishlist == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Wishlist with the specified ID does not exist.");
        }
        if (wishlist.getCustomer().getUserID() != customer.getUserID()) {
            throw new GearUpException(HttpStatus.UNAUTHORIZED, "Customer is not authorized to modify this wishlist.");
        }

        Equipment equipment = equipmentRepository.findEquipmentByEquipmentID(EquipmentId);
        if (equipment == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Equipment with the specified ID does not exist.");
        }

        wishlist.removeEquipment(equipment);
        return wishlistRepo.save(wishlist);
    }

    /**
     * GetWishlistByUserId: retrieves a wishlist by its userId
     * @return the wishlist with the specified userId
     * @throws GearUpException if userId is invalid
     */
    @Transactional
    public List<Wishlist> getAllWishlists() {
        return wishlistRepo.findAll();
    }

    /**
     * GetWishlistByWishlistId: retrieves a wishlist by its wishlistId
     * @param wishlistId the ID of the wishlist
     * @return the wishlist with the specified wishlistId
     * @throws GearUpException if wishlistId is invalid
     */
    @Transactional
    public Wishlist getWishlistByWishlistId(int wishlistId) {
        Wishlist wishlist = wishlistRepo.findByWishListId(wishlistId);
        if (wishlist == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Wishlist with the specified ID does not exist.");
        }
        return wishlist;
    }
}
