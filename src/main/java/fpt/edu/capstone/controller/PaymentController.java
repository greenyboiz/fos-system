package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/payment/{orderId}")
    public BigDecimal getTotal(@PathVariable(name = "orderId") Long orderId){
        return paymentService.getTotalAmountByOrder(orderId);
    }
}
