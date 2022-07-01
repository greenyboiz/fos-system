package com.example.demo.implementService;

import com.example.demo.entities.FOSUser;
import com.example.demo.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IFOSUserService {
    public FOSUser addFOSUser(FOSUser fosUser);

    public FOSUser updateFOSUser(FOSUser fosUser);

    public boolean deleteFOSUser(Long id);

    public List<FOSUser> getAllFOSUser();

    public ResponseEntity<ResponseObject> getFOSUserById(Long id);
}
