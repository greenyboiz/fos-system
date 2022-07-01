package com.example.demo.service;

import com.example.demo.entities.FOSUser;
import com.example.demo.entities.Role;
import com.example.demo.implementService.IFOSUserService;
import com.example.demo.repo.FOSUserRepository;
import com.example.demo.repo.RoleRepository;
import com.example.demo.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FOSUserService implements IFOSUserService {
    @Autowired
    public FOSUserRepository fosUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public FOSUser addFOSUser(FOSUser fosUser) {
        return fosUserRepository.save(fosUser);
    }

    @Override
    public FOSUser updateFOSUser(FOSUser fosUser) {
        Role role = roleRepository.findByRoleId(fosUser.getRole().getRoleId());
        if(fosUser != null){
            FOSUser fosUser1 = fosUserRepository.getById(fosUser.getUserId());
            if(fosUser1 != null){
                fosUser1.setFullName(fosUser.getFullName());
                fosUser1.setUserName(fosUser.getUserName());
                fosUser1.setPassword(fosUser.getPassword());
                fosUser1.setRole(role);
                fosUser1.setContact(fosUser.getContact());
                fosUser1.setEmail(fosUser.getEmail());
                fosUser1.setGender(fosUser.getGender());
                fosUser1.setStatus(fosUser.getStatus());
                fosUser1.setProfileImage(fosUser.getProfileImage());

                return  fosUserRepository.save(fosUser1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteFOSUser(Long id) {
//        FOSUser fosUser = fosUserRepository.findById(id).orElseThrow(()->new RuntimeException("Can not find FosUser with id: " + id));
        FOSUser fosUser = fosUserRepository.getById(id);
        if(fosUser != null){
            fosUserRepository.delete(fosUser);
            return true;
        }
        return false;
    }

    @Override
    public List<FOSUser> getAllFOSUser() {
        return fosUserRepository.findAll();
    }

    @Override
    public ResponseEntity<ResponseObject> getFOSUserById(Long id) {
        Optional<FOSUser> FOSUser = fosUserRepository.findById(id);
        if(FOSUser.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, FOSUser)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find FOSUserID: "+id,false,"null")
            );
        }
    }
}
