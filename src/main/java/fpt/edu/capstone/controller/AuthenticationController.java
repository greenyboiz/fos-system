//package fpt.edu.capstone.controller;
//
//import fpt.edu.capstone.entities.FOSUser;
//import fpt.edu.capstone.entities.FOSUserPrincipal;
//import fpt.edu.capstone.repo.FOSUserRepository;
//import fpt.edu.capstone.response.JwtResponse;
//import fpt.edu.capstone.response.ResponseObject;
//import fpt.edu.capstone.sercurity.JwtTokenUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api")
//public class AuthenticationController {
//
//    @Autowired
//    private FOSUserRepository fosUserRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestBody FOSUser fosUser){
//        Authentication authentication = new UsernamePasswordAuthenticationToken(fosUser.getUserName(),fosUser.getPassword());
//        FOSUser user = fosUserRepository.findByUserName(fosUser.getUserName());
//
//        if (user == null || !passwordEncoder.matches(fosUser.getPassword(), user.getPassword())
//                || !user.getUserName().equals(fosUser.getUserName())) {
////            logger.error("username or pasword wrong!");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                    .body(ResponseObject.builder().status("401").message("sign in request: username or pasword wrong!")
//                            .success(false).build());
//        }
////        if (!user.isActive()) {
////            logger.error("Account inactive!");
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseObject.builder().code("401")
////                    .message("Authenticate request: account lock!").messageCode("INACTIVE_ACCOUNT").build());
////        }
//        JwtResponse jwtResponse = new JwtResponse(jwtTokenUtil.generateToken(new FOSUserPrincipal(user)));
//
//        return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().status("200")
//                .message("Get token signin: successfully!").data(jwtResponse).build());
//    }
//}
