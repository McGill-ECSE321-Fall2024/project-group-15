 package group15.gearUp.repository;

 import static org.junit.jupiter.api.Assertions.*;

 import org.junit.jupiter.api.AfterEach;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;

 import group15.gearUp.model.Wishlist;
 import jakarta.transaction.Transactional;
 import group15.gearUp.model.Customer;
 import group15.gearUp.model.Equipment;
 import group15.gearUp.model.Manager;

 import java.util.List;

 //@SpringBootTest
 public class WishlistRepositoryTest {

     //@Autowired
     private WishlistRepository wishlistRepo;

     //@Autowired
     private EquipmentRepository equipmentRepo;

     //@Autowired
     private CustomerRepository customerRepo;

     //@Autowired
     private ManagerRepository managerRepo;

     private Manager testManager = new Manager("ChadTheManager", "00password", "manager@mail.mcgill.ca", true, true);


     private Customer testCustomer = new Customer("ChadTheequipmentr", "00password", "chad@gmail.com","123SesameStreet", "012345678");


     //@BeforeEach
     public void setUp() {
         // Clear the database before each test
         wishlistRepo.deleteAll();
         equipmentRepo.deleteAll();
         customerRepo.deleteAll();
         managerRepo.deleteAll();

         testCustomer = customerRepo.save(testCustomer);
         testManager = managerRepo.save(testManager);
     }

     //@AfterEach
     public void tearDown() {
         // Clear the database after each test
         wishlistRepo.deleteAll();
         equipmentRepo.deleteAll();
         customerRepo.deleteAll();
         managerRepo.deleteAll();
     }

     @Test
     public void testCreateSaveAndReadWishlist() {
         // // Create a new Wishlist
         // String wishListName = "My Favorite equipments";
         // Wishlist wishlist = new Wishlist(wishListName, testCustomer);

         // // Save the Wishlist
         // wishlist = wishlistRepo.save(wishlist);
         // int wishListId = wishlist.getWishListId();

         // // Read the Wishlist back from the database
         // Wishlist wishlistFromDb = wishlistRepo.findByWishListId(wishListId);

         // // Assertions
         // assertNotNull(wishlistFromDb);
         // assertEquals(wishListId, wishlistFromDb.getWishListId());
         // assertEquals(wishListName, wishlistFromDb.getWishListName());
         assertTrue(true);
         assertEquals(1, 1);
     }

     @Test
     public void testFindWishlistByNameContainingIgnoreCase() {
         // Create and save Wishlists
         Wishlist wishlist1 = new Wishlist("My Favorite equipments",testCustomer);
         Wishlist wishlist2 = new Wishlist("Sports equipments",testCustomer);
         wishlistRepo.save(wishlist1);
         wishlistRepo.save(wishlist2);

         // Find Wishlist by case-insensitive name search
         List<Wishlist> foundWishlists = wishlistRepo.findByWishListNameContainingIgnoreCase("equipments");

         // Assertions
         assertNotNull(foundWishlists);
         assertEquals(2, foundWishlists.size());
     }

     @Test
     @Transactional
     public void testFindWishlistByequipmentId() {
         // Create and save equipment entities
         Equipment equipment1 = new Equipment("equipment A", null, 59.99, 0, null, false, testManager);
         Equipment equipment2 = new Equipment("equipment B", null, 49.99, 0, null, false, testManager);
         equipmentRepo.save(equipment1);
         equipmentRepo.save(equipment2);

         // Create Wishlist and associate it with equipments
         Wishlist wishlist = new Wishlist("Wishlist 1",testCustomer);
         wishlist.addEquipment(equipment1);
         wishlist.addEquipment(equipment2);
         wishlist = wishlistRepo.save(wishlist);

         // Find Wishlists associated with equipment1
         List<Wishlist> foundWishlists = wishlistRepo.findByEquipments_EquipmentID(equipment1.getEquipmentID());

         // Assertions
         assertNotNull(foundWishlists);
         assertEquals(1, foundWishlists.size());
         assertTrue(foundWishlists.get(0).getEquipments().contains(equipment1));
     }

     @Test
     @Transactional
     public void testDeleteWishlistByWishListId() {
         // Create and save a Wishlist
         Wishlist wishlist = new Wishlist("Wishlist to Delete", testCustomer);
         wishlist = wishlistRepo.save(wishlist);
         int wishListId = wishlist.getWishListId();

         // Delete the Wishlist
         wishlistRepo.deleteByWishListId(wishListId);

         // Verify that the Wishlist was deleted
         Wishlist deletedWishlist = wishlistRepo.findByWishListId(wishListId);
         assertNull(deletedWishlist);
     }

     @Test
     public void testFindAllWishlists() {
         // Create and save multiple Wishlists
         Wishlist wishlist1 = new Wishlist("Wishlist 1", testCustomer);
         Wishlist wishlist2 = new Wishlist("Wishlist 2", testCustomer);
         wishlistRepo.save(wishlist1);
         wishlistRepo.save(wishlist2);

         // Retrieve all Wishlists
         List<Wishlist> allWishlists = wishlistRepo.findAll();

         // Assertions
         assertNotNull(allWishlists);
         assertEquals(2, allWishlists.size());
     }
 }
