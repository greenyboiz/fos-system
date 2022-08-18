package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.FOSUser;
import fpt.edu.capstone.entities.FOSUserPrincipal;
import fpt.edu.capstone.implementService.IFOSUserService;
import fpt.edu.capstone.response.JwtResponse;
import fpt.edu.capstone.response.ResponseObject;
import fpt.edu.capstone.sercurity.JwtTokenUtil;
import fpt.edu.capstone.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {
    @Autowired
    private IFOSUserService ifosUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody FOSUser fosUser) throws Exception {
        boolean checkExistUser = ifosUserService.checkExistUserByUserNameAndContactAndEmail(fosUser);
        if(!checkExistUser) {
            FOSUser fosUserAdd = jwtUserDetailsService.save(fosUser);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, fosUserAdd)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("fail", "Account exist",false, null)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody FOSUser fosUser){
        FOSUser user = ifosUserService.getByUserName(fosUser.getUserName());

        if (user == null || !passwordEncoder.matches(fosUser.getPassword(), user.getPassword())
                || !user.getUserName().equals(fosUser.getUserName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseObject.builder().status("401").message("sign in request: username or pasword wrong!")
                            .success(false).build());
        }
        if (!user.getStatus()) {
            log.error("Account inactive!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().status("401")
                    .message("Authenticate request: account lock!").success(false).build());
        }
        final FOSUserPrincipal userPrincipal = (FOSUserPrincipal) jwtUserDetailsService
                .loadUserByUsername(user.getUserName());
        JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(userPrincipal),user.getRole().getRoleName());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().status("200")
                .message("Get token signin: successfully!").success(true).data(jwtResponse).build());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/userLogin")
    public ResponseEntity<?> getCurrentUser() {
        if (jwtUserDetailsService.getCurrentUser() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Does not exist any account",true, null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, jwtUserDetailsService.getCurrentUser()));
    }
}
