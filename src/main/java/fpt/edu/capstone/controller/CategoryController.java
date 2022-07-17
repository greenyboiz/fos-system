package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.ICategoryService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

//    @PostMapping("/category/add")
//    private Category saveCategory(@RequestBody Category category){
//        return iCategoryService.addCategory(category);
//    }

    @PostMapping( value = "/category/add", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    private ResponseEntity<?> addCategory(@RequestParam("file") MultipartFile file, @RequestPart("category") String category){
        ObjectMapper objectMapper = new ObjectMapper();
        Category category1 = new Category();
        try {
            category1 = objectMapper.readValue(category, Category.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(file.getSize() == 0){
            iCategoryService.addCategory(category1);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "file image null",true, iCategoryService.addCategory(category1))
            );
        }
        iCategoryService.uploadImageCategory(file,category1);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, iCategoryService.uploadImageCategory(file,category1))
        );
    }

    @PutMapping(value = "/category/update1", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    private Category updateCategory(@RequestParam("file") MultipartFile file, @RequestPart("category") String category){
        ObjectMapper objectMapper = new ObjectMapper();
        Category category1 = new Category();
        try {
            category1 = objectMapper.readValue(category, Category.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return iCategoryService.uploadUpdateCategory(file,category1);
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
