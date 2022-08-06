package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.implementService.ICategoryService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getAlllCategories(){
        List<Category> categories = iCategoryService.getAllCategory();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, categories)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/category/add")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        boolean checkCategoryExist = iCategoryService.checkCategoryExist(category.getCategoryName());
        if(!checkCategoryExist){
            iCategoryService.addCategory(category);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, iCategoryService.addCategory(category))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "category is exist",false, null)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/category/update")
    public ResponseEntity<?> updateCategory(@RequestBody Category category){
        Category category1 = iCategoryService.updateCategory(category);
        if (category1 != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "update category successfull",true, category1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "category not exist",false, null)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/category/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id){
        boolean deleteCategory = iCategoryService.deleteCategory(id);
        if(deleteCategory){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete tableId = " + id + " successfull",true, deleteCategory)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "tableId = " + id + " not exist",false, null)
        );
    }

    @GetMapping("/category/{id}")
    public  ResponseEntity<?> findTableById(@PathVariable Long id){
        Category category = iCategoryService.getCategoryById(id);
        if(category != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, category)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find CategoryID: "+id,false,"null")
            );
        }
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping( value = "/category/add", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
//    private ResponseEntity<?> addCategory(@RequestParam("file") MultipartFile file, @RequestPart("category") String category){
//        ObjectMapper objectMapper = new ObjectMapper();
//        Category category1 = new Category();
//        try {
//            category1 = objectMapper.readValue(category, Category.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        if(file.getSize() == 0){
//            iCategoryService.addCategory(category1);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "file image null",true, iCategoryService.addCategory(category1))
//            );
//        }
//        iCategoryService.uploadImageCategory(file,category1);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("ok", "successfull",true, iCategoryService.uploadImageCategory(file,category1))
//        );
//    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping(value = "/category/update1", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
//    private Category updateCategory(@RequestParam("file") MultipartFile file, @RequestPart("category") String category){
//        ObjectMapper objectMapper = new ObjectMapper();
//        Category category1 = new Category();
//        try {
//            category1 = objectMapper.readValue(category, Category.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        return iCategoryService.uploadUpdateCategory(file,category1);
//    }



}
