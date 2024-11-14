package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Wishlist;
import group15.gameStore.repository.CustomerRepository;
import group15.gameStore.repository.GameRepository;
import group15.gameStore.repository.WishlistRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {
    
        @Mock
        private WishlistRepository wishlistRepository;

        @Mock
        private CustomerRepository customerRepository;

        @Mock
        private GameRepository gameRepository;

        @InjectMocks
        private GameService gameService;
    
        @InjectMocks
        private WishlistService wishlistService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }
    
        // Test for creating a wishlist
        @Test
        public void testGetAllWishlist() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);
            Wishlist w2 = new Wishlist("Wishlist2", c1);
            Wishlist w3 = new Wishlist("Wishlist3", c1);

            //Act
            List<Wishlist> wishlist = new ArrayList<>();
            wishlist.add(w1);
            wishlist.add(w2);
            wishlist.add(w3);
            when(wishlistRepository.findAll()).thenReturn(List.of(w1, w2, w3));

            //Assert
            List<Wishlist> allWishlist = wishlistService.getAllWishlists();
            assertEquals(3, allWishlist.size());
            assertEquals(w1, allWishlist.get(0));
            assertEquals(w2, allWishlist.get(1));
            assertEquals(w3, allWishlist.get(2));

        }

        // Test for getting a wishlist with valid id
        @Test
        public void testGetWishlistById_Success() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);
            when(wishlistRepository.findByWishListId(w1.getWishListId())).thenReturn(w1);

            //Act
            Wishlist result = wishlistService.getWishlistByWishlistId(w1.getWishListId());

            //Assert
            assertNotNull(result);
            assertEquals(w1, result);
        }

        //Test for getting a wishlist with invalid id
        @Test
        public void testGetWishlistById_InvalidId() {
            //Arrange

            //Act
            GameStoreException exception = assertThrows(GameStoreException.class, () -> {
                wishlistService.getWishlistByWishlistId(2);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }
        
        // Test for adding a game to a wishlist
        @Test
        public void testAddGameToWishlist_Success() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);
            Game g1 = new Game();
            g1.setGameID(1);
            when(gameRepository.findGameByGameID(g1.getGameID())).thenReturn(g1);
            when(wishlistRepository.findByWishListId(w1.getWishListId())).thenReturn(w1);

            //Act
            Wishlist result = wishlistService.addGameToWishlist(w1.getWishListId(), g1.getGameID(), c1);

            //Assert
            assertNotNull(result);
            assertEquals(w1, result);
        }

        // Test for adding a game to a wishlist with invalid wishlist id
        @Test
        public void testAddGameToWishlist_InvalidWishlistId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Game g1 = new Game();
            g1.setGameID(1);

            //Act
            GameStoreException exception = assertThrows(GameStoreException.class, () -> {
                wishlistService.addGameToWishlist(2, g1.getGameID(), c1);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }

        // Test for adding a game to a wishlist with invalid game id
        @Test
        public void testAddGameToWishlist_InvalidGameId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);

            //Act
            GameStoreException exception = assertThrows(GameStoreException.class, () -> {
                wishlistService.addGameToWishlist(w1.getWishListId(), 2, c1);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }

        // Test for deleting a wishlist with invalid id
        @Test
        public void testDeleteWishlist_InvalidId() {
            //Arrange

            //Act
            GameStoreException exception = assertThrows(GameStoreException.class, () -> {
                wishlistService.deleteWishlist(2, new Customer());
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }





}