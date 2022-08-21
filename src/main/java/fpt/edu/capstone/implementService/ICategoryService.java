package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Category;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategory();
    Category addCategory(Category category);

    Category updateCategory(Category category);

    boolean deleteCategory(Long id);

    Category getCategoryById(Long id);

    boolean checkCategoryExist(String categoryName);
}
