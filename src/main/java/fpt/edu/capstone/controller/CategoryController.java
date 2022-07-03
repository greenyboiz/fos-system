package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.implementService.ICategoryService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/category")
    private List<Category> getAlllCategories(){
        return iCategoryService.getAllCategory();
    }

    @PostMapping("/category/add")
    private Category saveCategory(@RequestBody Category category){
        return iCategoryService.addCategory(category);
    }

    @PutMapping("/category/update")
    private Category updateCategory(@RequestBody Category category){
        return iCategoryService.updateCategory(category);
    }

    @DeleteMapping("/category/delete/{id}")
    public boolean deleteCategory(@PathVariable("id") Long id){
        return iCategoryService.deleteCategory(id);
    }

    @GetMapping("/category/{id}")
    ResponseEntity<ResponseObject> findTableById(@PathVariable Long id){
        return iCategoryService.getCategoryById(id);
    }

}
