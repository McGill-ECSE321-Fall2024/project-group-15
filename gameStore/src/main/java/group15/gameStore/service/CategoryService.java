package group15.gameStore.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Category;
import group15.gameStore.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    /**
     * CreateCategory: creates a new category with a name
     * @param name the name of the category
     * @return the newly created Category
     * @throws GameStoreException if the name is missing or invalid
     */
    @Transactional 
    public Category createCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Category name is required.");
        }

        Category category = new Category(name);
        categoryRepo.save(category);
        return category;
    }

    /**
     * UpdateCategory: updates an existing category's information
     * @param categoryId the ID of the category to update
     * @param updatedCategory the new category information to update to
     * @return the updated Category object
     * @throws GameStoreException if update request is invalid
     */
    @Transactional
    public Category updateCategory(int categoryId, Category updatedCategory) {
        Category existingCategory = categoryRepo.findByCategoryID(categoryId);
        if (existingCategory == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Category with the specified ID does not exist.");
        }
        if (updatedCategory == null || updatedCategory.getName() == null || updatedCategory.getName().trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Category name is required.");
        }

        existingCategory.setName(updatedCategory.getName());
        categoryRepo.save(existingCategory);
        return existingCategory;
    }

    /**
     * GetCategoryById: retrieves a category by its ID
     * @param id the ID of the category
     * @return the Category with the specified ID
     * @throws GameStoreException if the category with the given ID is not found
     */
    @Transactional
    public Category getCategoryById(int id) {
        Category category = categoryRepo.findByCategoryID(id);
        if (category == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Category not found.");
        }
        return category;
    }

    /**
     * GetCategoryByName: retrieves categories by a name substring (case-insensitive)
     * @param name the substring to search for within category names
     * @return a list of Category objects whose names contain the specified substring
     * @throws GameStoreException if no categories are found
     */
    @Transactional
    public List<Category> getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Search term cannot be empty.");
        }

        List<Category> categories = categoryRepo.findByNameContainingIgnoreCase(name);
        if (categories.isEmpty()) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "No categories found containing the specified name.");
        }

        return categories;
    }

    /**
     * GetAllCategories: retrieves all category records in the system
     * @return a list of all Category objects
     * @throws GameStoreException if no category records are found
     */
    @Transactional
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            throw new GameStoreException(HttpStatus.NO_CONTENT, "No category records found in the system.");
        }
        return categories;
    }

    /**
     * DeleteCategoryByCategoryID: deletes a category by its ID
     * @param categoryId the ID of the category to delete
     * @throws GameStoreException if the category with the given ID is not found
     */
    @Transactional
    public void deleteCategoryByCategoryID(int categoryId) {
        Category category = categoryRepo.findByCategoryID(categoryId);
        if (category == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Category with the specified ID does not exist.");
        }

        categoryRepo.deleteByCategoryID(categoryId);
    }
}
