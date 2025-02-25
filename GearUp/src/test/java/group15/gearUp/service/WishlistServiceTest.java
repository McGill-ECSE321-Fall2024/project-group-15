package group15.gearUp.service;

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

import group15.gearUp.exception.GearUpException;
import group15.gearUp.model.Customer;
import group15.gearUp.model.Equipment;
import group15.gearUp.model.Wishlist;
import group15.gearUp.repository.CustomerRepository;
import group15.gearUp.repository.EquipmentRepository;
import group15.gearUp.repository.WishlistRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class WishlistServiceTest {
    
        @Mock
        private WishlistRepository wishlistRepository;

        @Mock
        private CustomerRepository customerRepository;

        @Mock
        private EquipmentRepository equipmentRepository;

        @InjectMocks
        private EquipmentService equipmentService;
    
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
            GearUpException exception = assertThrows(GearUpException.class, () -> {
                wishlistService.getWishlistByWishlistId(2);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }
        
        // Test for adding a equipment to a wishlist
        @Test
        public void testAddEquipmentToWishlist_Success() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);
            Equipment g1 = new Equipment();
            g1.setEquipmentID(1);
            when(equipmentRepository.findEquipmentByEquipmentID(g1.getEquipmentID())).thenReturn(g1);
            when(wishlistRepository.findByWishListId(w1.getWishListId())).thenReturn(w1);

            //Act
            Wishlist result = wishlistService.addEquipmentToWishlist(w1.getWishListId(), g1.getEquipmentID(), c1);

            //Assert
            assertNotNull(result);
            assertEquals(w1, result);
        }

        // Test for adding a equipment to a wishlist with invalid wishlist id
        @Test
        public void testAddEquipmentToWishlist_InvalidWishlistId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Equipment g1 = new Equipment();
            g1.setEquipmentID(1);

            //Act
            GearUpException exception = assertThrows(GearUpException.class, () -> {
                wishlistService.addEquipmentToWishlist(2, g1.getEquipmentID(), c1);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }

        // Test for adding a equipment to a wishlist with invalid equipment id
        @Test
        public void testAddEquipmentToWishlist_InvalidEquipmentId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);

            //Act
            GearUpException exception = assertThrows(GearUpException.class, () -> {
                wishlistService.addEquipmentToWishlist(w1.getWishListId(), 2, c1);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }
        //remove equipment from wishlist
        @Test
        public void testRemoveEquipmentFromWishlist_Success() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);
            Equipment g1 = new Equipment();
            g1.setEquipmentID(1);
            w1.addEquipment(g1);
            when(equipmentRepository.findEquipmentByEquipmentID(g1.getEquipmentID())).thenReturn(g1);
            when(wishlistRepository.findByWishListId(w1.getWishListId())).thenReturn(w1);

            //Act
            Wishlist result = wishlistService.removeEquipmentFromWishlist(w1.getWishListId(), g1.getEquipmentID(), c1);

            //Assert
            assertNotNull(result);
            assertEquals(w1, result);
        }

        //remove equipment from wishlist with invalid wishlist id
        @Test
        public void testRemoveEquipmentFromWishlist_InvalidWishlistId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Equipment g1 = new Equipment();
            g1.setEquipmentID(1);

            //Act
            GearUpException exception = assertThrows(GearUpException.class, () -> {
                wishlistService.removeEquipmentFromWishlist(2, g1.getEquipmentID(), c1);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }

        //remove equipment from wishlsit with invalid equipmentid
        @Test
        public void testRemoveEquipmentFromWishlist_InvalidEquipmentId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);

            Wishlist w1 = new Wishlist("Wishlist1", c1);

            //Act
            GearUpException exception = assertThrows(GearUpException.class, () -> {
                wishlistService.removeEquipmentFromWishlist(w1.getWishListId(), 2, c1);
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
            GearUpException exception = assertThrows(GearUpException.class, () -> {
                wishlistService.deleteWishlist(2, new Customer());
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Wishlist with the specified ID does not exist.", exception.getMessage());
        }

        // Test for creating a wishlist
        @Test
        public void testCreateWishlist_Success() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);
            Wishlist w1 = new Wishlist("Wishlist1", c1);
            when(customerRepository.findByUserID(c1.getUserID())).thenReturn(c1);

            //Act
            Wishlist result = wishlistService.createWishlist(c1.getUserID(), w1.getWishListName(), c1);

            //Assert
            assertNotNull(result);
            assertEquals(w1.getWishListName(), result.getWishListName());
        }

        // Test for creating a wishlist with invalid id
        @Test
        public void testCreateWishlist_InvalidId() {
            //Arrange
            String name = "Dana White";
            String password = "password1234";
            String email = "dana@gmail.com";
            String address = "1234 Main St";
            String phoneNumber = "123-456-7890";
            Customer c1 = new Customer(name, password, email, address, phoneNumber);

            //Act
            GearUpException exception = assertThrows(GearUpException.class, () -> {
                wishlistService.createWishlist(2, "Wishlist1", c1);
            });

            //Assert
            assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("Customer with the specified ID does not exist.", exception.getMessage());
        }



}