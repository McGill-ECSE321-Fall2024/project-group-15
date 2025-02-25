 package group15.gearUp.repository;

 import static org.junit.jupiter.api.Assertions.assertEquals;
 import static org.junit.jupiter.api.Assertions.assertNotNull;

 import org.junit.jupiter.api.AfterEach;
 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;

 import group15.gearUp.model.Review;
 import group15.gearUp.model.Wishlist;
 import jakarta.transaction.Transactional;
 import group15.gearUp.model.Customer;
 import group15.gearUp.model.Equipment;
 import group15.gearUp.model.Manager;
 import group15.gearUp.model.Rating;

 import java.util.List;

 @SpringBootTest
 public class ReviewRepositoryTest {
    
     @Autowired
     private ReviewRepository repo;
    
     @Autowired
     private EquipmentRepository equipmentRepository;

     @Autowired
     private WishlistRepository wishlistRepo;
    
     @Autowired
     private CustomerRepository customerRepo;

     @Autowired
     private ManagerRepository managerRepo;

     private Manager testManager = new Manager("ChadTheManager", "00password", "manager@mail.mcgill.ca", true, true);
    
     private Equipment testequipment = new Equipment("Test equipment", "A description of the equipment", 59.99, 0, null, false, testManager);
     //private equipment testequipment2 = new equipment("Test equipment", "A description of the equipment", 59.99, 0, null, false);
     private Customer testCustomer = new Customer("ChadTheequipmentr", "00password", "chad@gmail.com","123SesameStreet", "012345678");

     private Wishlist testWishlist = new Wishlist("My Wishlist", testCustomer);

     @BeforeEach
     public void setUp() {
         // Clear the database before each test
         repo.deleteAll();
         wishlistRepo.deleteAll();
         equipmentRepository.deleteAll();
         customerRepo.deleteAll();
         managerRepo.deleteAll();

         // Create and save a equipment to be used in the tests
         testManager = managerRepo.save(testManager);
         testCustomer = customerRepo.save(testCustomer);
         testWishlist = wishlistRepo.save(testWishlist);
         testequipment = equipmentRepository.save(testequipment);
     }

     @AfterEach
     public void tearDown() {
         // Clear the database after each test
         repo.deleteAll();
         wishlistRepo.deleteAll();
         equipmentRepository.deleteAll();
         customerRepo.deleteAll();
         managerRepo.deleteAll();
     }

     @Test
     public void testCreateSaveAndReadReview() {
         // Create a new Review
         Rating rating = Rating.FIVE_STAR;
         String description = "Amazing equipment!";
         Review review = new Review(rating, description, testequipment, testCustomer);

         // Save the Review
         review = repo.save(review);
         int reviewId = review.getReviewID();

         // Read the Review back from the database
         Review reviewFromDb = repo.findByReviewID(reviewId);

         // Assertions
         assertNotNull(reviewFromDb);
         assertEquals(reviewId, reviewFromDb.getReviewID());
         assertEquals(rating, reviewFromDb.getRating());
         assertEquals(description, reviewFromDb.getDescription());
         assertEquals(testequipment.getEquipmentID(), reviewFromDb.getEquipment().getEquipmentID());
     }

     @Test
     @Transactional
     public void testFindReviewsByRating() {
         // Create and save reviews with different ratings
         Review review1 = new Review(Rating.FIVE_STAR, "Great equipment!", testequipment,testCustomer);
         Review review2 = new Review(Rating.THREE_STAR, "It’s okay", testequipment,testCustomer);
         repo.save(review1);
         repo.save(review2);

         // Find reviews by rating
         List<Review> fiveStarReviews = repo.findByRating(Rating.FIVE_STAR);

         // Assertions
         assertNotNull(fiveStarReviews);
         assertEquals(1, fiveStarReviews.size());
         assertEquals(Rating.FIVE_STAR, fiveStarReviews.get(0).getRating());
     }

     @Test
     public void testFindReviewsByDescription() {
         // Create and save reviews
         Review review1 = new Review(Rating.FOUR_STAR, "Incredible equipmentplay!", testequipment,testCustomer);
         Review review2 = new Review(Rating.FIVE_STAR, "Best. equipment. Ever.", testequipment,testCustomer);
         repo.save(review1);
         repo.save(review2);

         // Find review by keyword in description (case-insensitive)
         List<Review> reviewsWithKeyword = repo.findByDescriptionContainingIgnoreCase("incredible");

         // Assertions
         assertNotNull(reviewsWithKeyword);
         assertEquals(1, reviewsWithKeyword.size());
         assertEquals("Incredible equipmentplay!", reviewsWithKeyword.get(0).getDescription());
     }

     @Test
     public void testFindReviewsByequipmentID() {
         // Create and save a review for the equipment
         Review review = new Review(Rating.FOUR_STAR, "Challenging and fun!", testequipment,testCustomer);
         repo.save(review);

         // Find reviews for the specific equipment by equipment ID
         List<Review> reviewsForequipment = repo.findByEquipment(testequipment);

         // Assertions
         assertNotNull(reviewsForequipment);
         assertEquals(1, reviewsForequipment.size());
         assertEquals(testequipment.getEquipmentID(), reviewsForequipment.get(0).getEquipment().getEquipmentID());
     }

     @Test
     @Transactional
     public void testDeleteReviewByReviewID() {
         // Create and save a review
         Review review = new Review(Rating.THREE_STAR, "Average equipment", testequipment,testCustomer);
         review = repo.save(review);
         int reviewId = review.getReviewID();

         // Delete the review by review ID
         repo.deleteByReviewID(reviewId);

         // Verify that the review was deleted
         //Review deletedReview = repo.findById(reviewId).orElse(null);
         Review deletedReview = repo.findByReviewID(reviewId);
         assertEquals(null, deletedReview);
     }
 }
