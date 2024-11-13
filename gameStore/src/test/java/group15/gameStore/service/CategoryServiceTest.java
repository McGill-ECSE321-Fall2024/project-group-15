package group15.gameStore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Category;
import group15.gameStore.repository.CategoryRepository;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepo;

    @InjectMocks
    private CategoryService categoryService;

    private Category mockCategory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockCategory = new Category("Adventure");
        mockCategory.setCategoryID(1);
    }

    @Test
    public void testCreateCategory_Success() {
        when(categoryRepo.save(any(Category.class))).thenReturn(mockCategory);

        Category result = categoryService.createCategory("Adventure");

        assertNotNull(result);
        assertEquals("Adventure", result.getName());
        verify(categoryRepo, times(1)).save(any(Category.class));
    }

    @Test
    public void testCreateCategory_EmptyName() {
        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            categoryService.createCategory(" ");
        });
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        assertEquals("Category name is required.", thrown.getMessage());
    }

    @Test
    public void testUpdateCategory_Success() {
        Category updatedCategory = new Category("Action");
        when(categoryRepo.findByCategoryID(mockCategory.getCategoryID())).thenReturn(mockCategory);
        when(categoryRepo.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(mockCategory.getCategoryID(), updatedCategory);

        assertNotNull(result);
        assertEquals("Action", result.getName());
        verify(categoryRepo, times(1)).save(mockCategory);
    }

    @Test
    public void testUpdateCategory_NotFound() {
        when(categoryRepo.findByCategoryID(mockCategory.getCategoryID())).thenReturn(null);

        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            categoryService.updateCategory(mockCategory.getCategoryID(), mockCategory);
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
        assertEquals("Category with the specified ID does not exist.", thrown.getMessage());
    }

    @Test
    public void testUpdateCategory_EmptyName() {
        Category updatedCategory = new Category(" ");
        when(categoryRepo.findByCategoryID(mockCategory.getCategoryID())).thenReturn(mockCategory);

        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            categoryService.updateCategory(mockCategory.getCategoryID(), updatedCategory);
        });

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        assertEquals("Category name is required.", thrown.getMessage());
    }

    @Test
    public void testGetCategoryById_Success() {
        when(categoryRepo.findByCategoryID(mockCategory.getCategoryID())).thenReturn(mockCategory);

        Category result = categoryService.getCategoryById(mockCategory.getCategoryID());

        assertNotNull(result);
        assertEquals(mockCategory.getCategoryID(), result.getCategoryID());
        assertEquals(mockCategory.getName(), result.getName());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        when(categoryRepo.findByCategoryID(mockCategory.getCategoryID())).thenReturn(null);

        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            categoryService.getCategoryById(mockCategory.getCategoryID());
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
        assertEquals("Category not found.", thrown.getMessage());
    }

    @Test
    public void testGetCategoryByName_Success() {
        List<Category> categories = new ArrayList<>();
        categories.add(mockCategory);
        when(categoryRepo.findByNameContainingIgnoreCase("Adventure")).thenReturn(categories);

        List<Category> result = categoryService.getCategoryByName("Adventure");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Adventure", result.get(0).getName());
    }

    @Test
    public void testGetCategoryByName_NotFound() {
        when(categoryRepo.findByNameContainingIgnoreCase("Nonexistent")).thenReturn(new ArrayList<>());

        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            categoryService.getCategoryByName("Nonexistent");
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
        assertEquals("No categories found containing the specified name.", thrown.getMessage());
    }

    @Test
    public void testGetAllCategories_Success() {
        List<Category> categories = new ArrayList<>();
        categories.add(mockCategory);
        when(categoryRepo.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Adventure", result.get(0).getName());
    }

    @Test
    public void testGetAllCategories_NoContent() {
        when(categoryRepo.findAll()).thenReturn(new ArrayList<>());

        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            categoryService.getAllCategories();
        });

        assertEquals(HttpStatus.NO_CONTENT, thrown.getStatus());
        assertEquals("There are no category records found in the system.", thrown.getMessage());
    }

    @Test
    public void testDeleteCategoryByCategoryID_Success() {
        when(categoryRepo.findByCategoryID(mockCategory.getCategoryID())).thenReturn(mockCategory);

        categoryService.deleteCategoryByCategoryID(mockCategory.getCategoryID());

        verify(categoryRepo, times(1)).deleteByCategoryID(mockCategory.getCategoryID());
    }

    @Test
    public void testDeleteCategoryByCategoryID_NotFound() {
        when(categoryRepo.findByCategoryID(mockCategory.getCategoryID())).thenReturn(null);

        GameStoreException thrown = assertThrows(GameStoreException.class, () -> {
            categoryService.deleteCategoryByCategoryID(mockCategory.getCategoryID());
        });

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
        assertEquals("Category with the specified ID does not exist.", thrown.getMessage());
    }
}
