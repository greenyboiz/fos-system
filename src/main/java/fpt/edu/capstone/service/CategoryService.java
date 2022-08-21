package fpt.edu.capstone.service;

import com.cloudinary.Cloudinary;
import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.implementService.ICategoryService;
import fpt.edu.capstone.repo.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Cloudinary cloudinary;
    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        if(category != null){
            Category category1 = categoryRepository.findByCategoryId(category.getCategoryId());
            if(category1 != null){
                category1.setCategoryName(category.getCategoryName());
                return categoryRepository.save(category1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Category category = categoryRepository.findByCategoryId(id);
        if(category != null){
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    @Override
    public List<Category> getAllCategory() {
        List<Category> list = categoryRepository.findAll();
        return list;
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findByCategoryId(id);
        if(category != null){
            return category;
        }
        return null;
    }

    @Override
    public boolean checkCategoryExist(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if(category != null){
            return true;
        }
        return false;
    }

//    @Override
//    public Category uploadImageCategory(MultipartFile file, Category category) {
//        try {
//            Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
//            String img = (String) r.get("secure_url");
//            category.setCategoryImage(img);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return categoryRepository.save(category);
//    }

//    @Override
//    public Category uploadUpdateCategory(MultipartFile file, Category category) {
//        if(category != null){
//            Category category1 = categoryRepository.findByCategoryId(category.getCategoryId());
//            if(category1 != null){
//                try {
//                    Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
//                    String img = (String) r.get("secure_url");
//                    category1.setCategoryImage(img);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                category1.setCategoryName(category.getCategoryName());
//
//                return categoryRepository.save(category1);
//            }
//        }
//        return null;
//    }


}
