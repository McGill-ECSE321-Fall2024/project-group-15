package group15.gearUp.repository;

import org.springframework.data.repository.CrudRepository;
import group15.gearUp.model.Category;
import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    // Find Category by categoryId
    Category findByCategoryID(Integer categoryID);

    // Find Category by name (case-insensitive)
    List<Category> findByNameContainingIgnoreCase(String name);

    // Delete Category by categoryId
    void deleteByCategoryID(Integer categoryID);

    // Get all Categories
    @SuppressWarnings("null")
    List<Category> findAll();
}
