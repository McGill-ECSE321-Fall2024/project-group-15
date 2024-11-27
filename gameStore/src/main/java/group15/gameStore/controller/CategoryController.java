package group15.gameStore.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.CategoryDto;
import group15.gameStore.model.Category;
import group15.gameStore.service.CategoryService;

@CrossOrigin(origins = "http://localhost:5173") // Frontend's base URL
@RestController
public class CategoryController{
    
    @Autowired
    private CategoryService categoryService;

    /**
    * CreateCategory: creates a new category
    * @param categoryDto the CategoryDto containing the category details
    * @return the created category and HTTP Status "CREATED"
    */
    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        //System.out.println("Request received to create category: " + categoryDto.getName());
        Category createdCategory = categoryService.createCategory(categoryDto.getName());
        return new ResponseEntity<>(new CategoryDto(createdCategory), HttpStatus.CREATED);
    }

    /**
     * GetCategoryById: retrieves a category by ID
     * @param categoryId the ID of the category to retrieve
     * @return the desired category information and the HTTP status "OK"
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") int categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(new CategoryDto(category), HttpStatus.OK);
    }

    /**
     * GetCategoryByName: retrieves a category by name
     * @param name the name of the category to retrieve
     * @return the desired category information and the HTTP status "OK"
     */
    @GetMapping("/category/name/{name}")
    public ResponseEntity<List<CategoryDto>> getCategoryByName(@PathVariable("categoryName") String name) {
        List<Category> categories = categoryService.getCategoryByName(name);
        List<CategoryDto> responseDtoList = categories.stream().map(CategoryDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    /**
     * GetAllCategories: retrieves all category records in the system
     * @return a list of all category information and the HTTP status "OK"
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<CategoryDto> responseDtoList = categories.stream().map(CategoryDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

        /**
    * UpdateCategory: updates an existing category
    * @param categoryId the ID of the category to update
    * @param categoryDto the CategoryDto containing updated category details
    * @return the updated category and the HTTP status "OK"
    */
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") int categoryId,
            @RequestBody CategoryDto categoryDto) {

            Category existingCategory = categoryService.getCategoryById(categoryDto.getCategoryID());
            Category updatedCategory = categoryService.updateCategory(categoryId, existingCategory);
            return new ResponseEntity<>(new CategoryDto(updatedCategory), HttpStatus.OK);

    }

    /**
    * DeleteCategory: deletes a category by category ID
    * @param categoryId the ID of the category to delete
    * @return HTTP status "NO CONTENT" if the deletion is successful
    */
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        
        categoryService.deleteCategoryByCategoryID(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}