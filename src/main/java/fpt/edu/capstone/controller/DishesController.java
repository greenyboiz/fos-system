package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IDishesService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DishesController {
    @Autowired
    public IDishesService iDishesService;

    @GetMapping("/dishes")
    private List<Dishes> getAllDishes(){
        return iDishesService.getAllDishes();
    }


//    @PostMapping("/dishes/add")
//    private Dishes saveDishes(@RequestBody Dishes dishes){
//        return iDishesService.addDishes(dishes);
//    }

    @PostMapping( value = "/dishes/add", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    private Dishes uploadDishes(@RequestParam("file")MultipartFile file, @RequestPart("dishes") String dishes){
        ObjectMapper objectMapper = new ObjectMapper();
        Dishes dishes1 = new Dishes();
        try {
            dishes1 = objectMapper.readValue(dishes, Dishes.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return iDishesService.uploadDishesImage(file,dishes1);
    }
    @PutMapping("/dishes/update")
    private Dishes updateDishes(@RequestBody Dishes dishes){
        return iDishesService.updateDishes(dishes);
    }

    @DeleteMapping("/dishes/delete/{id}")
    public boolean deleteDishes(@PathVariable("id") Long id){
        return iDishesService.deleteDishes(id);
    }

    @GetMapping("/dishes/{id}")
    ResponseEntity<ResponseObject> findDishesById(@PathVariable Long id){
        return iDishesService.getDishesById(id);
    }

}
