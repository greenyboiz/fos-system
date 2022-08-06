package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.dto.PagingRequest;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IDishesService;
import fpt.edu.capstone.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@Slf4j
public class DishesController {
    @Autowired
    public IDishesService iDishesService;

    @GetMapping("/dishes")
    public ResponseEntity<?> getAllDishes(){
        List<Dishes> dishes = iDishesService.getAllDishes();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, dishes)
        );
    }

//    @GetMapping("/dishes")
//    public Page<Dishes> listAll(
////            PagingRequest pagingRequest
//            @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
//            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
//    ){
//        log.info("(listAll)pageNum: {}, pageSize: {}", pageNum, pageSize);
//        return iDishesService.listDishes(pageNum, pageSize);
//    }


    @PostMapping("/dishes/add")
    public ResponseEntity<?> saveDishes(@RequestBody Dishes dishes){
        boolean checkDishesExist = iDishesService.getDishesExist(dishes.getDishesName());
        if(!checkDishesExist){
            iDishesService.addDishes(dishes);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, iDishesService.addDishes(dishes))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "this dishes is exist",false, null)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/dishes/update")
    public  ResponseEntity<?> updateDishes(@RequestBody Dishes dishes){
        Dishes dishes1 = iDishesService.getDishesById(dishes.getDishesId());
        if(dishes1 != null){
            iDishesService.updateDishes(dishes);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update dishes successfull",true, iDishesService.updateDishes(dishes1))
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "this dishes is exist",false, null)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/dishes/delete/{id}")
    public ResponseEntity<?> deleteDishes(@PathVariable("id") Long id){
        boolean dishesDelete = iDishesService.deleteDishes(id);
        if(dishesDelete){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete dishes succsessfully",true, dishesDelete)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Can not find dishesId: "+id,false,"null")
        );
    }

    @GetMapping("/dishes/{id}")
    public ResponseEntity<?> findDishesById(@PathVariable Long id){
        Dishes dishes = iDishesService.getDishesById(id);
        if(dishes != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, dishes)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Can not find dishesId: "+id,false,"null")
        );
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping( value = "/dishes/add", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<?> addDishes(@RequestParam("file")MultipartFile file, @RequestPart("dishes") String dishes){
//        ObjectMapper objectMapper = new ObjectMapper();
//        Dishes dishes1 = new Dishes();
//        try {
//            dishes1 = objectMapper.readValue(dishes, Dishes.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        if(file.getSize() == 0){
//            iDishesService.addDishes(dishes1);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "file imange of dishes null",true, iDishesService.addDishes(dishes1))
//            );
//        }else {
//
//            Dishes dishes2 = iDishesService.uploadDishesImage(file,dishes1);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "successfull",true, dishes2)
//            );
//        }
//
//    }

}
