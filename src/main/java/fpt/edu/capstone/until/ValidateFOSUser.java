package fpt.edu.capstone.until;

import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidateFOSUser {
    @Autowired
    private IFOSUserService ifosUserService;

    private final String PHONE_NUMBER = "^(84|0[3|5|7|8|9])+([0-9]{8})$";

    private final String EMAIL = "^(.+)@gmail.com$";

    public ResponseEntity<?> validateFOSUser(FOSUser fosUser) {
        //validate exist user
        boolean checkExistUser = ifosUserService.checkExistUserByUserNameAndContactAndEmail(fosUser);
        if(checkExistUser){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "User is exist",false, null)
            );
        }
        //validate contact of user
        String contact = fosUser.getContact();
        if(contact.trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Contact of is not null",false, null)
            );
        }
        if (!contact.matches(PHONE_NUMBER)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Contact of is incorrect format of phone number VietNamese",false, null)
            );
        }
        //validate email of user
        String email = fosUser.getEmail();
        if(email.trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Email of user must not null",false, null)
            );
        }
        if (!email.matches(EMAIL)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Email of is incorrect format of email with domain @gmail.com",false, null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Validate successfull",true, null)
        );
    }

    public ResponseEntity<?> validateAddFOSUser(FOSUser fosUser) {
        boolean checkExistUserByUserName = ifosUserService.checkExistUserByUserName(fosUser);
        boolean checkExistUserByContact = ifosUserService.checkExistUserByContact(fosUser);
        boolean checkExistUserByEmail = ifosUserService.checkExistUserByEmail(fosUser);
        if(checkExistUserByUserName && checkExistUserByContact && checkExistUserByEmail){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Username and Email and Contact is exist",false, null)
            );
        }
        if(checkExistUserByUserName && checkExistUserByEmail){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Username and Email is exist",false, null)
            );
        }
        if(checkExistUserByUserName && checkExistUserByContact){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Username and Contact is exist",false, null)
            );
        }
        if(checkExistUserByEmail && checkExistUserByContact){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Email and Contact is exist",false, null)
            );
        }
        if(checkExistUserByUserName){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Username is exist",false, null)
            );
        }
        if(checkExistUserByContact){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Contact is exist",false, null)
            );
        }
        if(checkExistUserByEmail){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Email is exist",false, null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Validate successfull",true, null)
        );
    }

    public ResponseEntity<?> validateUpdateFOSUser(FOSUser fosUser) {
        FOSUser fosUser1 = ifosUserService.getFOSUserById(fosUser.getUserId());
        if(fosUser1 == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "User is not exist",false, null)
            );
        }
        //validate contact of user
        String contact = fosUser.getContact();
        if(contact.trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Contact of is not null",false, null)
            );
        }
        if (!contact.matches(PHONE_NUMBER)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Contact of is incorrect format of phone number VietNamese",false, null)
            );
        }
        //validate email of user
        String email = fosUser.getEmail();
        if(email.trim().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Email of user must not null",false, null)
            );
        }
        if (!email.matches(EMAIL)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("fail", "Email of is incorrect format of email with domain @gmail.com",false, null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Validate successfull",true, null)
        );
    }
}
