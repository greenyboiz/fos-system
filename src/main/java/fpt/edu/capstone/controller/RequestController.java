package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Request;
import fpt.edu.capstone.service.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class RequestController {
    @Autowired
    private RequestService requestService;

//    @GetMapping("/requestByTable/{requestId}")
//    public ResponseEntity<?> getRequestByTable(@PathVariable("requestId") Long requestId){
//
//    }

//    @PostMapping()
}
