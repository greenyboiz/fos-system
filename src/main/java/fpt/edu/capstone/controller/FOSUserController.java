package fpt.edu.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FOSUserController {
    @Autowired
    private IFOSUserService ifosUserService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllFOSUser(){
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
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "Account exist",false, null)
        );
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping( value = "/users/add", consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<?> addFOSUser(@RequestParam("file") MultipartFile file, @RequestPart("users") String users){
//        ObjectMapper objectMapper = new ObjectMapper();
//        FOSUser fosUser = new FOSUser();
//        try {
//            fosUser = objectMapper.readValue(users, FOSUser.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        boolean checkExistUser = ifosUserService.checkExistUserByUserNameAndContactAndEmail(fosUser);
//        if(file.getSize() == 0){
//            if(!checkExistUser){
//                ifosUserService.addFOSUser(fosUser);
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("ok", "file image null",true, ifosUserService.addFOSUser(fosUser))
//                );
//            }
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(
//                    new ResponseObject("fail", "Account exist",false, null)
//            );
//        }else {
//            if(!checkExistUser){
//                FOSUser fosUser1 = ifosUserService.addFOSUserImage(file,fosUser);
//                return ResponseEntity.status(HttpStatus.OK).body(
//                        new ResponseObject("ok", "successfull",true, fosUser1)
//                );
//            }
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(
//                    new ResponseObject("fail", "Account exist",false, null)
//            );
//        }
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/update")
    public FOSUser updateFOSUser(@RequestBody FOSUser fosUser){
        return ifosUserService.updateFOSUser(fosUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/delete/{id}")
    public boolean deleteFOSUser(@PathVariable("id") Long id){
        return ifosUserService.deleteFOSUser(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseObject> findFOSUserById(@PathVariable Long id){
        return ifosUserService.getFOSUserById(id);
    }
}
