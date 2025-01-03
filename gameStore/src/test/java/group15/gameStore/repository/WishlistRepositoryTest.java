package group15.gameStore.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import group15.gameStore.model.Wishlist;
import jakarta.transaction.Transactional;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Manager;

import java.util.List;

@SpringBootTest
public class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private ManagerRepository managerRepo;

    private Manager testManager = new Manager("ChadTheManager", "00password", "manager@mail.mcgill.ca", true, true);


    private Customer testCustomer = new Customer("ChadTheGamer", "00password", "chad@gmail.com","123SesameStreet", "012345678");


    @BeforeEach
    public void setUp() {
        // Clear the database before each test
        wishlistRepo.deleteAll();
        gameRepo.deleteAll();
        customerRepo.deleteAll();
        managerRepo.deleteAll();

        testCustomer = customerRepo.save(testCustomer);
        testManager = managerRepo.save(testManager);
    }

    @AfterEach
    public void tearDown() {
        // Clear the database after each test
        wishlistRepo.deleteAll();
        gameRepo.deleteAll();
        customerRepo.deleteAll();
        managerRepo.deleteAll();
    }

    @Test
    public void testCreateSaveAndReadWishlist() {
        // Create a new Wishlist
        String wishListName = "My Favorite Games";
        Wishlist wishlist = new Wishlist(wishListName, testCustomer);

        // Save the Wishlist
        wishlist = wishlistRepo.save(wishlist);
        int wishListId = wishlist.getWishListId();

        // Read the Wishlist back from the database
        Wishlist wishlistFromDb = wishlistRepo.findByWishListId(wishListId);

        // Assertions
        assertNotNull(wishlistFromDb);
        assertEquals(wishListId, wishlistFromDb.getWishListId());
        assertEquals(wishListName, wishlistFromDb.getWishListName());
    }

    @Test
    public void testFindWishlistByNameContainingIgnoreCase() {
        // Create and save Wishlists
        Wishlist wishlist1 = new Wishlist("My Favorite Games",testCustomer);
        Wishlist wishlist2 = new Wishlist("Sports Games",testCustomer);
        wishlistRepo.save(wishlist1);
        wishlistRepo.save(wishlist2);

        // Find Wishlist by case-insensitive name search
        List<Wishlist> foundWishlists = wishlistRepo.findByWishListNameContainingIgnoreCase("games");

        // Assertions
        assertNotNull(foundWishlists);
        assertEquals(2, foundWishlists.size());
    }

    @Test
    @Transactional
    public void testFindWishlistByGameId() {
        // Create and save Game entities
        Game game1 = new Game("Game A", null, 59.99, 0, null, false, testManager);
        Game game2 = new Game("Game B", null, 49.99, 0, null, false, testManager);
        gameRepo.save(game1);
        gameRepo.save(game2);

        // Create Wishlist and associate it with games
        Wishlist wishlist = new Wishlist("Wishlist 1",testCustomer);
        wishlist.addGame(game1);
        wishlist.addGame(game2);
        wishlist = wishlistRepo.save(wishlist);

        // Find Wishlists associated with game1
        List<Wishlist> foundWishlists = wishlistRepo.findByGames_GameID(game1.getGameID());

        // Assertions
        assertNotNull(foundWishlists);
        assertEquals(1, foundWishlists.size());
        assertTrue(foundWishlists.get(0).getGames().contains(game1));
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
