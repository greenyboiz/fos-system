package fpt.edu.capstone.implementService;

import fpt.edu.capstone.dto.CategoryDTO;
import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService {

    List<CategoryDTO> getAllCategory();
    Category addCategory(Category category);

    Category updateCategory(Category category);

    boolean deleteCategory(Long id);

    Category getCategoryById(Long id);

    boolean checkCategoryExist(String categoryName);
}
