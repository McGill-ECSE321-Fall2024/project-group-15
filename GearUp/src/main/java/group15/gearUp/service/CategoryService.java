package group15.gearUp.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import group15.gearUp.exception.GearUpException;
import group15.gearUp.model.Category;
import group15.gearUp.model.Equipment;
import group15.gearUp.repository.CategoryRepository;
import group15.gearUp.repository.EquipmentRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    @Autowired
    private EquipmentRepository equipmentRepository;

    /**
     * CreateCategory: creates a new category with a name
     * @param name the name of the category
     * @return the newly created Category
     * @throws GearUpException if the name is missing or invalid
     */
    @Transactional 
    public Category createCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Category name is required.");
        }

        Category category = new Category(name);
        categoryRepo.save(category);
        return category;
    }

    @Transactional
    public void assignEquipmentToCategory(int EquipmentId, int categoryId) {
        //
        if (equipmentRepository.findEquipmentByEquipmentID(EquipmentId) == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Equipment not found with ID: " + EquipmentId);
        }
        Equipment equipment = equipmentRepository.findEquipmentByEquipmentID(EquipmentId);
        if (equipment == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Equipment not found with ID: " + EquipmentId);
        }

        Category category = categoryRepo.findByCategoryID(categoryId);

        if (!category.addEquipment(equipment)) {
            throw new GearUpException(HttpStatus.CONFLICT, "Equipment is already assigned to this category.");
            }
        categoryRepo.save(category);
    }

    /**
     * UpdateCategory: updates an existing category's information
     * @param categoryId the ID of the category to update
     * @param updatedCategory the new category information to update to
     * @return the updated Category object
     * @throws GearUpException if update request is invalid
     */
    @Transactional
    public Category updateCategory(int categoryId, Category updatedCategory) {
        Category existingCategory = categoryRepo.findByCategoryID(categoryId);
        if (existingCategory == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Category with the specified ID does not exist.");
        }
        if (updatedCategory == null || updatedCategory.getName() == null || updatedCategory.getName().trim().isEmpty()) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Category name is required.");
        }

        existingCategory.setName(updatedCategory.getName());
        categoryRepo.save(existingCategory);
        return existingCategory;
    }

    /**
     * GetCategoryById: retrieves a category by its ID
     * @param id the ID of the category
     * @return the Category with the specified ID
     * @throws GearUpException if the category with the given ID is not found
     */
    @Transactional
    public Category getCategoryById(int id) {
        Category category = categoryRepo.findByCategoryID(id);
        if (category == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Category not found.");
        }
        return category;
    }

    /**
     * GetCategoryByName: retrieves categories by a name substring (case-insensitive)
     * @param name the substring to search for within category names
     * @return a list of Category objects whose names contain the specified substring
     * @throws GearUpException if no categories are found
     */
    @Transactional
    public List<Category> getCategoryByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new GearUpException(HttpStatus.BAD_REQUEST, "Search term cannot be empty.");
        }

        List<Category> categories = categoryRepo.findByNameContainingIgnoreCase(name);
        if (categories.isEmpty()) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "No categories found containing the specified name.");
        }

        return categories;
    }

    /**
     * GetAllCategories: retrieves all category records in the system
     * @return a list of all Category objects
     * @throws GearUpException if no category records are found
     */
    @Transactional
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        if (categories.isEmpty()) {
            throw new GearUpException(HttpStatus.NO_CONTENT, "No category records found in the system.");
        }
        return categories;
    }

    /**
     * DeleteCategoryByCategoryID: deletes a category by its ID
     * @param categoryId the ID of the category to delete
     * @throws GearUpException if the category with the given ID is not found
     */
    @Transactional
    public void deleteCategoryByCategoryID(int categoryId) {
        Category category = categoryRepo.findByCategoryID(categoryId);
        if (category == null) {
            throw new GearUpException(HttpStatus.NOT_FOUND, "Category with the specified ID does not exist.");
        }

        categoryRepo.deleteByCategoryID(categoryId);
    }
}
