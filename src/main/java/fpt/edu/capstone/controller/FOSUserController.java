package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.FOSUserDTO;
import fpt.edu.capstone.entities.CurrentUserDetails;
import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.implementService.IEmailService;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.response.ResponseObject;
import fpt.edu.capstone.service.JwtUserDetailsService;
import fpt.edu.capstone.until.ValidateFOSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class FOSUserController {
    @Autowired
    private IFOSUserService ifosUserService;

    @Autowired
    private IEmailService iEmailService;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private ValidateFOSUser validateFOSUser;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllFOSUser(){
        List<FOSUser> fosUserList = ifosUserService.getAllFOSUser();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, fosUserList)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/users/add")
    public ResponseEntity<?> saveFOSUser(@RequestBody FOSUser fosUser){
        try {
            ResponseEntity<?> responseEntity = validateFOSUser.validateAddFOSUser(fosUser);
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                fosUser.setPassword(bcryptEncoder.encode(fosUser.getPassword()));
                FOSUser fosUserAdd = ifosUserService.addFOSUser(fosUser);
                return ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "successfull",true, fosUserAdd)
                    );
            }
            return  responseEntity;
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", e.getMessage(),false, null)
            );
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/update")
    public ResponseEntity<?> updateFOSUser(@RequestBody FOSUser fosUser){
        FOSUser fosUser1 = ifosUserService.getFOSUserById(fosUser.getUserId());
        CurrentUserDetails currentUserDetails = jwtUserDetailsService.getCurrentUser();
        if(fosUser1 == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "User is not exist",false, null)
            );
        }
        if(currentUserDetails.getUser().getRoleName() == fosUser1.getRole().getRoleName()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "User Same Role " + fosUser1.getRole().getRoleName() + " can not edit",false, null)
            );
        }
        ifosUserService.updateFOSUser(fosUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update FOSUser succsessfully",true, ifosUserService.updateFOSUser(fosUser))
        );
    }

    @Autowired
    private PasswordEncoder bcryptEncoder;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody FOSUserDTO fosUser){
        FOSUser fosUser1 = ifosUserService.getFOSUserById(fosUser.getUserId());
        if(!bcryptEncoder.matches(fosUser.getOldPassword(),fosUser1.getPassword())){
//        if(!bcryptEncoder.encode(fosUser.getOldPassword()).equals(fosUser1.getPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Old Password incorrect",false, null)
            );
        }
        if(fosUser.getNewPassword().equals(fosUser.getOldPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "New password must be not same old password",false, null)
            );
        }
        if(!fosUser.getNewPassword().equals(fosUser.getConfirmNewPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "New password must be equal confirm new password",false, null)
            );
        }
        fosUser1.setPassword(bcryptEncoder.encode(fosUser.getNewPassword()));
        ifosUserService.addFOSUser(fosUser1);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Change password of FOSUser succsessfully",true, fosUser1)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteFOSUser(@PathVariable("id") Long id){
        FOSUser fosUser1 = ifosUserService.getFOSUserById(id);
        CurrentUserDetails currentUserDetails = jwtUserDetailsService.getCurrentUser();
        if(currentUserDetails.getUser().getRoleName() == fosUser1.getRole().getRoleName()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "User Same Role " + fosUser1.getRole().getRoleName() + " can not edit",false, null)
            );
        }
        boolean deleteFOSUser =  ifosUserService.deleteFOSUser(id);
        if (deleteFOSUser){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Change status of FOSUser succsessfully",true, deleteFOSUser)
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

    @PostMapping("/forgetPassword")
    public ResponseEntity<?> sendPasswordToEmail(@RequestBody FOSUser fosUser){
        if(fosUser == null || fosUser.getEmail()==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Send mail fail",false,"null")
            );
        }
        try {
            FOSUser fosUser1 = ifosUserService.getFOSUserByEmail(fosUser.getEmail());
            if(fosUser1==null){
                throw new Exception();
            }
            iEmailService.sendMailForgetPass(fosUser1);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, fosUser1)
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail","mail not exist",false,"null")
            );
        }
    }
}
