package com.example.demo.implementService;

import com.example.demo.entities.Category;
import com.example.demo.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    public Category addCategory(Category category);

    public Category updateCategory(Category category);

    public boolean deleteCategory(Long id);

    public List<Category> getAllCategory();

    public ResponseEntity<ResponseObject> getCategoryById(Long id);
}
