package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IDishesService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DishesController {
    @Autowired
    public IDishesService iDishesService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dishes")
    public List<Dishes> getAllDishes(){
        return iDishesService.getAllDishes();
    }


//    @PostMapping("/dishes/add")
//    private Dishes saveDishes(@RequestBody Dishes dishes){
//        return iDishesService.addDishes(dishes);
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping( value = "/dishes/add", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> addDishes(@RequestParam("file")MultipartFile file, @RequestPart("dishes") String dishes){
        ObjectMapper objectMapper = new ObjectMapper();
        Dishes dishes1 = new Dishes();
        try {
            dishes1 = objectMapper.readValue(dishes, Dishes.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        if(file.getSize() == 0){
            iDishesService.addDishes(dishes1);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "file imange of dishes null",true, iDishesService.addDishes(dishes1))
            );
        }else {

            Dishes dishes2 = iDishesService.uploadDishesImage(file,dishes1);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, dishes2)
            );
        }

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/dishes/update")
    private Dishes updateDishes(@RequestBody Dishes dishes){
        return iDishesService.updateDishes(dishes);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/dishes/delete/{id}")
    public boolean deleteDishes(@PathVariable("id") Long id){
        return iDishesService.deleteDishes(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dishes/{id}")
    ResponseEntity<ResponseObject> findDishesById(@PathVariable Long id){
        return iDishesService.getDishesById(id);
    }

}
