package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.PaymentDTO;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.implementService.*;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private IPaymentService iPaymentService;

    @Autowired
    private IOrdersService iOrdersService;

    @Autowired
    private IQRCodeService iqrCodeService;

    @Autowired
    private ITablesService iTablesService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @GetMapping("/payment/{orderId}")
    public BigDecimal getTotal(@PathVariable(name = "orderId") Long orderId){
        return iPaymentService.getTotalAmountByOrder(orderId);
    }

    @GetMapping("/paymentOfTable/{orderId}")
    public ResponseEntity<?> getPaymentOfTable(@PathVariable(name = "orderId") Long orderId){
        Orders orders = iOrdersService.getOrderById(orderId);
        if(orders != null){
            PaymentDTO paymentDTO = iPaymentService.getPaymentOfTable(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, paymentDTO)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Order is not exist",false, null)
        );
    }

    @DeleteMapping("/payment/{paymentId}")
    public ResponseEntity<?> deletePayment(@PathVariable(name = "paymentId") Long paymentId){
        boolean payment = iPaymentService.deletePaymentById(paymentId);
        if(payment){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Payment successfull",true, payment)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Payment is not exist",false, null)
        );
    }
}
