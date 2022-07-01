package com.example.demo.controller;

import com.example.demo.entities.FOSUser;
import com.example.demo.implementService.IFOSUserService;
import com.example.demo.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    private List<FOSUser> getAllFOSUser(){
        return ifosUserService.getAllFOSUser();
    }

    @PostMapping("/users/add")
    private FOSUser saveFOSUser(@RequestBody FOSUser fosUser){
        return ifosUserService.addFOSUser(fosUser);
    }

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
