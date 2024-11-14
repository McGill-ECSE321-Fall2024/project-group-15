package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Customer;
import group15.gameStore.model.Game;
import group15.gameStore.model.Wishlist;
import group15.gameStore.repository.WishlistRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {
    
        @Mock
        private WishlistRepository wishlistRepository;
    
        @InjectMocks
        private WishlistService wishlistService;
    
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
            when(wishlistRepository.findById(1)).thenReturn(java.util.Optional.of(w1));

            //Act
            Wishlist result = wishlistService.getWishlistByWishlistId(1);

            //Assert
            assertNotNull(result);
            assertEquals(w1, result);
        }

        //Test for getting a wishlist with invalid id
        @Test
        public void testGetWishlistById_InvalidId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);
            when(wishlistRepository.findById(1)).thenReturn(java.util.Optional.of(w1));

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
            when(wishlistRepository.findById(1)).thenReturn(java.util.Optional.of(w1));

            //Act
            Wishlist result = wishlistService.addGameToWishlist(1, 1, c1);

            //Assert
            assertNotNull(result);
            assertEquals(w1, result);
        }



}