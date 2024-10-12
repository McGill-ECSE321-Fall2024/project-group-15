package group15.gameStore.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gameStore.model.Category;
import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    // Find Category by categoryId
    Category findByCategoryId(Integer categoryId);

    // Find Category by name (case-insensitive)
    List<Category> findByCategoryNameContainingIgnoreCase(String categoryName);

    // Delete Category by categoryId
    void deleteByCategoryId(Integer categoryId);

    // Get all Categories
    List<Category> findAll();
}
