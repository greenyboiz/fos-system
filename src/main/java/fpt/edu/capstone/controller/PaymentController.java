package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.PaymentDTO;
import fpt.edu.capstone.entities.OrderItem;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.IOrderItemService;
import fpt.edu.capstone.implementService.IOrdersService;
import fpt.edu.capstone.implementService.IQRCodeService;
import fpt.edu.capstone.implementService.ITablesService;
import fpt.edu.capstone.response.ResponseObject;
import fpt.edu.capstone.response.ResponsePayment;
import fpt.edu.capstone.service.PaymentService;
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
    private PaymentService paymentService;

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
        return paymentService.getTotalAmountByOrder(orderId);
    }

    @GetMapping("/paymentOfTable/{orderId}")
    public ResponseEntity<?> getPaymentOfTable(@PathVariable(name = "orderId") Long orderId){
        Orders orders = iOrdersService.getOrderById(orderId);
        if(orders != null){
            Tables tables = iTablesService.getTableByQRCodeId(orders.getQrCode().getQRCodeId());
            BigDecimal payment = paymentService.getTotalAmountByOrder(orderId);
            List<OrderItem> itemList = iOrderItemService.getOrderItemByOrderId(orderId);
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPayment(payment);
            paymentDTO.setTableId(tables.getTableId());
            paymentDTO.setOrderItemList(itemList);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, paymentDTO)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Order is not exist",false, null)
        );
    }
}
