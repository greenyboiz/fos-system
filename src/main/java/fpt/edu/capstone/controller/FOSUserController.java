package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.response.ResponseObject;
import fpt.edu.capstone.validation.ValidateFOSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FOSUserController {
    @Autowired
    private IFOSUserService ifosUserService;

    @Autowired
    private ValidateFOSUser validateFOSUser;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllFOSUser(){
        List<FOSUser> fosUserList = ifosUserService.getAllFOSUser();
        if (fosUserList != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, fosUserList)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Does not exist any account",true, fosUserList)
            );
        }
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping("/users/add")
//    public ResponseEntity<?> saveFOSUser(@RequestBody FOSUser fosUser){
//        try {
//            ResponseEntity<?> responseEntity = validateFOSUser.validateFOSUser(fosUser);
//            if(responseEntity.getStatusCode() == HttpStatus.OK){
//                FOSUser fosUserAdd = ifosUserService.addFOSUser(fosUser);
//                return ResponseEntity.status(HttpStatus.OK).body(
//                            new ResponseObject("ok", "successfull",true, fosUserAdd)
//                    );
//            }
//            return  responseEntity;
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("fail", e.getMessage(),false, null)
//            );
//        }
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/add")
    public ResponseEntity<?> saveFOSUser(@RequestBody FOSUser fosUser){
        boolean checkExistUser = ifosUserService.checkExistUserByUserNameAndContactAndEmail(fosUser);
        if(!checkExistUser){
            FOSUser fosUserAdd = ifosUserService.addFOSUser(fosUser);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, fosUserAdd)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail", "User is exist",false, null)
        );
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PutMapping("/users/update")
//    public ResponseEntity<?> updateFOSUser(@RequestBody FOSUser fosUser){
//        try {
//            ResponseEntity<?> responseEntity = validateFOSUser.validateUpdateFOSUser(fosUser);
//            if(responseEntity.getStatusCode() == HttpStatus.OK){
//                ifosUserService.updateFOSUser(fosUser);
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("ok", "Update FOSUser succsessfully",true, ifosUserService.updateFOSUser(fosUser))
//                );
//            }
//            return responseEntity;
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("fail", e.getMessage(),false, null)
//            );
//        }
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/update")
    public ResponseEntity<?> updateFOSUser(@RequestBody FOSUser fosUser){
        FOSUser fosUser1 = ifosUserService.getFOSUserById(fosUser.getUserId());
        if(fosUser1 != null){
            ifosUserService.updateFOSUser(fosUser);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update FOSUser succsessfully",true, ifosUserService.updateFOSUser(fosUser))
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject("fail", "User is not exist",false, null)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteFOSUser(@PathVariable("id") Long id){
        boolean deleteFOSUser =  ifosUserService.deleteFOSUser(id);
        if (deleteFOSUser){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete FOSUser succsessfully",true, deleteFOSUser)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Can not find FOSUserID: "+id,false,"null")
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> findFOSUserById(@PathVariable Long id){
        FOSUser fosUser = ifosUserService.getFOSUserById(id);
        if(fosUser!=null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, fosUser)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Can not find FOSUserID: "+id,false,"null")
        );
    }

}
