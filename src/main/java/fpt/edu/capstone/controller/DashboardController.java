package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.BestSellerDishesDTO;
import fpt.edu.capstone.dto.DashboardDTO;
import fpt.edu.capstone.implementService.IOrderItemService;
import fpt.edu.capstone.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class DashboardController {
    @Autowired
    private IOrderItemService iOrderItemService;
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard(){
        List<DashboardDTO> list = iOrderItemService.getListDashboard();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "succsessfully",true, list)
        );
    }

    @GetMapping("/dashboard/bestseller")
    public ResponseEntity<?> getDashboardBestseller(){
        List<BestSellerDishesDTO> list = iOrderItemService.getListDashboardBestseller();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "succsessfully",true, list)
        );
    }
}
