package com.example.demo.service;

import com.example.demo.entities.Category;
import com.example.demo.entities.Tables;
import com.example.demo.implementService.ICategoryService;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        if(category != null){
            Category category1 = categoryRepository.getById(category.getCategoryId());
            if(category1 != null){
                category1.setCategoryImage(category.getCategoryName());
                category1.setCategoryImage(category.getCategoryImage());

                return categoryRepository.save(category1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Category category = categoryRepository.getById(id);
        if(category != null){
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public ResponseEntity<ResponseObject> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, category)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find CategoryID: "+id,false,"null")
            );
        }
    }
}
