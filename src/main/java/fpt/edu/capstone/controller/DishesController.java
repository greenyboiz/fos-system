package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.implementService.IDishesService;
import fpt.edu.capstone.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/dishesClient")
    public ResponseEntity<?> getAllDishesForClient(){
        List<Dishes> dishes = iDishesService.getAllDishesForClient();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, dishes)
        );
    }

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
                new ResponseObject("fail", "this dishes is not exist",false, null)
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/dishes/changeStatus/{dishesId}")
    public ResponseEntity<?> changeStatusOfDishesById(@PathVariable(name = "dishesId") Long dishesId){
        Dishes dishes = iDishesService.getDishesById(dishesId);
        if(dishes != null){
            iDishesService.changeStatusOfDishesById(dishes);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, dishes)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Can not find dishesId: "+ dishesId,false,"null")
        );
    }

}
