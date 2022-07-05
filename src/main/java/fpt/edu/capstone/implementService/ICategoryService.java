package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICategoryService {
    public Category addCategory(Category category);

    public Category uploadImageCategory(MultipartFile file, Category category);

    public Category uploadUpdateCategory(MultipartFile file,Category category);
    public Category updateCategory(Category category);

    public boolean deleteCategory(Long id);

    public List<Category> getAllCategory();

    public ResponseEntity<ResponseObject> getCategoryById(Long id);
}
