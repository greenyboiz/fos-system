package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryId(Long id);

    @Query("select c from Category c where c.categoryName = ?1")
    Category findByCategoryName(String categoryName);
}
