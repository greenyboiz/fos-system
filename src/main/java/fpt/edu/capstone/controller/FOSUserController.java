package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FOSUserController {
    @Autowired
    private IFOSUserService ifosUserService;

    @GetMapping("/users")
    private ResponseEntity<?> getAllFOSUser(){
        List<FOSUser> fosUserList = ifosUserService.getAllFOSUser();
        if (fosUserList != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, fosUserList)
            );
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "Does not exist any account",true, fosUserList)
            );
        }
    }

    @PostMapping("/users/add1")
    private ResponseEntity<?> saveFOSUser(@RequestBody FOSUser fosUser){
        boolean checkExistUser = ifosUserService.checkExistUserByUserNameAndContactAndEmail(fosUser);
        if(!checkExistUser){
            FOSUser fosUserAdd = ifosUserService.addFOSUser(fosUser);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, fosUserAdd)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("fail", "Account exist",false, null)
            );
        }

    }

//    @PostMapping("/users/add")
//    private ResponseEntity<?> addFOSUser(@RequestBody FOSUser fosUser){
//        if(file.getSize() == 0){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("fail", "file image null",false, null)
//            );
//        }else {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Dishes dishes1 = new Dishes();
//            try {
//                dishes1 = objectMapper.readValue(dishes, Dishes.class);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            Dishes dishes2 = iDishesService.uploadDishesImage(file,dishes1);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "successfull",true, dishes2)
//            );
//        }
//
//    }

    @PutMapping("/users/update")
    private FOSUser updateFOSUser(@RequestBody FOSUser fosUser){
        return ifosUserService.updateFOSUser(fosUser);
    }

    @DeleteMapping("/users/delete/{id}")
    public boolean deleteFOSUser(@PathVariable("id") Long id){
        return ifosUserService.deleteFOSUser(id);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<ResponseObject> findFOSUserById(@PathVariable Long id){
        return ifosUserService.getFOSUserById(id);
    }
}
