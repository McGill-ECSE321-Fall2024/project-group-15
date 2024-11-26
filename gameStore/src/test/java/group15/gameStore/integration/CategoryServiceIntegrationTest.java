package group15.gameStore.integration;

import group15.gameStore.dto.*;
import group15.gameStore.repository.CategoryRepository;
import group15.gameStore.service.CategoryService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryServiceIntegrationTest {

    @SuppressWarnings("unused")
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepo;

    private final String VALID_NAME = "Action";
    private int validId;
    private CategoryDto categoryRequestDto;
    private CategoryDto categoryRequestDto2;
    

    @BeforeEach
    public void setUp() {
        categoryRepo.deleteAll();

        categoryRequestDto = new CategoryDto();
        categoryRequestDto2 = new CategoryDto();
        categoryRequestDto.setName(VALID_NAME);
        categoryRequestDto.setCategoryID(validId);
        categoryRequestDto2.setName("Adventure");
        //categoryRepo.save(any(categoryRequestDto));
    }
    
    /* 
    @Test
    @Order(0)
    public void testGetAllEmptyCategories() {
        ResponseEntity<String> response = client.getForEntity("/categories", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No categories found in the system.", response.getBody());
    }
    */

    @Test
    @Order(1)
    public void testCreateValidCategory() {
        // Arrange
        CategoryDto request = new CategoryDto(VALID_NAME);

        // Act
        ResponseEntity<CategoryDto> response = client.postForEntity("/category", request, CategoryDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CategoryDto createdCategory = response.getBody();
        assertNotNull(createdCategory);
        assertEquals(VALID_NAME, createdCategory.getName());
        assertNotNull(createdCategory.getCategoryID());
        assertTrue(createdCategory.getCategoryID() > 0, "Response should have a positive ID.");
        this.validId = createdCategory.getCategoryID();

        System.out.println("Valid ID written:" + this.validId);
    }

    @Test
    @Order(2)
    public void testGetCategoryByValidId() {
        // Arrange: Create the category first
        String createUrl = "/category";
        CategoryDto createRequest = new CategoryDto("Action");
        ResponseEntity<CategoryDto> createResponse = client.postForEntity(createUrl, createRequest, CategoryDto.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        CategoryDto createdCategory = createResponse.getBody();
        assertNotNull(createdCategory);
        this.validId = createdCategory.getCategoryID();       

        // Arrange
        String url = "/category/" + this.validId;

        // Act
        ResponseEntity<CategoryDto> response = client.getForEntity(url, CategoryDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CategoryDto categoryDto = response.getBody();
        assertNotNull(categoryDto);
        assertEquals(VALID_NAME, categoryDto.getName());
        assertEquals(this.validId, categoryDto.getCategoryID());
    }

    /* 
    @Test
    @Order(3)
    public void testGetCategoryByName() {
        // Arrange: Create the category first
        String createUrl = "/category";
        CategoryDto createRequest = new CategoryDto("Action");
        ResponseEntity<CategoryDto> createResponse = client.postForEntity(createUrl, createRequest, CategoryDto.class);

        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        CategoryDto createdCategory = createResponse.getBody();
        assertNotNull(createdCategory);
        this.validId = createdCategory.getCategoryID();

        // Arrange
        String url = "/category/name/Action";

        // Act
        ResponseEntity<CategoryDto> response = client.getForEntity(url, CategoryDto.class);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CategoryDto categoryDto = response.getBody();
        assertNotNull(categoryDto);
        assertEquals(VALID_NAME, categoryDto.getName());
        assertEquals(this.validId, categoryDto.getCategoryID());
    }
    */


    @Test
    @Order(3)
    public void testUpdateValidCategory() {

        System.out.println("Valid ID read:" + this.validId);

        // Arrange: Create the existing category
        String createUrl = "/category";
        CategoryDto createRequest = new CategoryDto("Action");
        ResponseEntity<CategoryDto> createResponse = client.postForEntity(createUrl, createRequest, CategoryDto.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        CategoryDto createdCategory = createResponse.getBody();
        assertNotNull(createdCategory);
        this.validId = createdCategory.getCategoryID();

        // Arrange: Create the category with updated info
        String createUrl2 = "/category";
        CategoryDto createRequest2 = new CategoryDto("Updated Action");
        ResponseEntity<CategoryDto> createResponse2 = client.postForEntity(createUrl2, createRequest2, CategoryDto.class);
        assertEquals(HttpStatus.CREATED, createResponse2.getStatusCode());
        CategoryDto createdCategory2 = createResponse.getBody();
        assertNotNull(createdCategory2);
        this.validId = createdCategory2.getCategoryID();

        // Arrange
        String url = "/category/" + this.validId;
        String updatedName = "Action Updated";
        createdCategory.setName(updatedName);
        CategoryDto updateRequest = createdCategory;
        updateRequest.setCategoryID(this.validId);

        // Act
        client.put(url, updateRequest);
        
        // Assert
        ResponseEntity<CategoryDto> categoryResponse = client.getForEntity(url,CategoryDto.class);

        assertNotNull(categoryResponse);
        assertEquals(HttpStatus.OK, categoryResponse.getStatusCode());
        CategoryDto updatedCategory = categoryResponse.getBody();
        assertNotNull(updatedCategory);
        assertEquals("Updated Action", updatedCategory.getName());
        assertNotNull(updatedCategory.getCategoryID());
        assertTrue(updatedCategory.getCategoryID() > 0, "Response should have a positive ID.");
        this.validId = updatedCategory.getCategoryID();
    }


    @Test
    @Order(4)
    public void testGetAllCategories() {
        client.postForEntity("/category", categoryRequestDto, CategoryDto.class);
        client.postForEntity("/category", categoryRequestDto2, CategoryDto.class);

        @SuppressWarnings("rawtypes")
        ResponseEntity<List> response = client.getForEntity("/categories", List.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() > 0);
        
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> categories = response.getBody();
        assertEquals(categoryRequestDto.getName(), categories.get(0).get("name"));
        assertEquals(categoryRequestDto2.getName(), categories.get(1).get("name"));
    }

    @Test
    @Order(5)
    public void testDeleteCategory() {
        // Arrange: Create the category first
        String createUrl = "/category";
        CategoryDto createRequest = new CategoryDto("Action");
        ResponseEntity<CategoryDto> createResponse = client.postForEntity(createUrl, createRequest, CategoryDto.class);
        assertEquals(HttpStatus.CREATED, createResponse.getStatusCode());
        CategoryDto createdCategory = createResponse.getBody();
        assertNotNull(createdCategory);
        this.validId = createdCategory.getCategoryID();

        String url = "/category/" + this.validId;
        ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCategory_NotFound() {
        String url = "/category/" + 0;
        ResponseEntity<String> response = client.exchange(url, HttpMethod.DELETE, null, String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("{\"errors\":[\"Category with the specified ID does not exist.\"]}", response.getBody());
    }

    private boolean equals(CategoryDto categoryResponseDto, CategoryDto categoryRequestDto) {
        return categoryResponseDto.getName().equals(categoryRequestDto.getName());
    }
}
