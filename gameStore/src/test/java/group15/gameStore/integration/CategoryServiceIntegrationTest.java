package group15.gameStore.integration;

import group15.gameStore.dto.*;
import group15.gameStore.repository.CategoryRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceIntegrationTest {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private TestRestTemplate client;

    private CategoryDto categoryRequestDto;
    private CategoryDto categoryRequestDto2;

    @BeforeEach
    public void setUp() {
        categoryRequestDto = new CategoryDto();
        categoryRequestDto.setName("Action");
        categoryRequestDto2.setName("Adventure");

        categoryRepo.deleteAll();
    }

    @Test
    @Order(0)
    public void testGetAllEmptyCategories() {
        ResponseEntity<String> response = client.getForEntity("/categories", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No categories found in the system.", response.getBody());
    }

    @Test
    @Order(1)
    public void testCreateValidCategory() {
        // Create first category
        ResponseEntity<CategoryDto> categoryResponse1 = client.postForEntity("/categories/create", categoryRequestDto, CategoryDto.class);
        assertEquals(HttpStatus.CREATED, categoryResponse1.getStatusCode());
        assertNotNull(categoryResponse1.getBody());
        assertTrue(equals(categoryResponse1.getBody(), categoryRequestDto));

        // Create second category
        ResponseEntity<CategoryDto> categoryResponse2 = client.postForEntity("/categories/create", categoryRequestDto2, CategoryDto.class);
        assertEquals(HttpStatus.CREATED, categoryResponse2.getStatusCode());
        assertNotNull(categoryResponse2.getBody());
        assertTrue(equals(categoryResponse2.getBody(), categoryRequestDto2));
    }

    @Test
    @Order(2)
    public void testGetCategoryByName() {
        client.postForEntity("/categories/create", categoryRequestDto, CategoryDto.class);
        ResponseEntity<CategoryDto> response = client.getForEntity("/categories/name/Action", CategoryDto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), categoryRequestDto));
    }

    @Test
    @Order(3)
    public void testUpdateValidCategory() {
        //ResponseEntity<CategoryDto> categoryResponse = client.postForEntity("/categories/create", categoryRequestDto, CategoryDto.class);
        CategoryDto updatedCategoryDto = new CategoryDto();
        updatedCategoryDto.setName("Action Updated");

        HttpEntity<CategoryDto> requestEntity = new HttpEntity<>(updatedCategoryDto);
        ResponseEntity<CategoryDto> response = client.exchange("/categories/update/Action", HttpMethod.PUT, requestEntity, CategoryDto.class);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(equals(response.getBody(), updatedCategoryDto));
    }


    @Test
    @Order(4)
    public void testGetAllCategories() {
        client.postForEntity("/categories/create", categoryRequestDto, CategoryDto.class);
        client.postForEntity("/categories/create", categoryRequestDto2, CategoryDto.class);

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
        client.postForEntity("/categories/create", categoryRequestDto, CategoryDto.class);

        ResponseEntity<Void> response = client.exchange("/categories/delete/Action", HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testDeleteCategory_NotFound() {
        ResponseEntity<String> response = client.exchange("/categories/delete/NonExistentCategory", HttpMethod.DELETE, null, String.class);
        
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Category with name NonExistentCategory not found.", response.getBody());
    }

    private boolean equals(CategoryDto categoryResponseDto, CategoryDto categoryRequestDto) {
        return categoryResponseDto.getName().equals(categoryRequestDto.getName());
    }
}
