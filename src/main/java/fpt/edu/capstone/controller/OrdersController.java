package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.ICustomerService;
import fpt.edu.capstone.implementService.IOrderItemService;
import fpt.edu.capstone.implementService.IOrdersService;
import fpt.edu.capstone.implementService.ITablesService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private IOrdersService iOrdersService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private ITablesService iTablesService;

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(){
        List<Orders> orders = iOrdersService.getAllOrders();
        if(orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, orders)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "List order is empty ",false,"null")
            );
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<?> saveOrders(@RequestBody Orders orders){
        boolean checkOrderExist = iOrdersService.checkOrderExist(orders);

//        boolean checkCustomerExistInOrder = iOrdersService.checkCustomerExistInOrder(orders);
//        boolean checkCustomerExist = iCustomerService.checkCustomerExist(orders.getCustomer());
        if (!checkOrderExist){
            Tables tables = iTablesService.getTableByQRCodeId(orders.getQrCode().getQRCodeId());
            tables.setStatus("0");
            Customer customer = iCustomerService.addCustomer(orders.getCustomer());
            orders.setCustomer(customer);
            // check qrCode đã có người ngồi và chưa thanh toán
            iOrdersService.addOrder(orders);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "add order succsessfully",true, iOrdersService.addOrder(orders))
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("fail", "Order is exist",false,"null")
            );
        }
    }

    @PutMapping("/orders/update")
    public Orders updateOrders(@RequestBody Orders orders){
        return iOrdersService.updateOrder(orders);
    }

    @DeleteMapping("/orders/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id){
        try {
            Orders orders = iOrdersService.getOrderById(id);
            if(orders==null){
                throw new Exception();
            }
            Tables tables = iTablesService.getTableByQRCodeId(orders.getQrCode().getQRCodeId());
            tables.setStatus("1");
            iTablesService.addTable(tables);
            iOrderItemService.deleteOrderItemByOrderId(orders);
            boolean orderDelete = iOrdersService.deleteOrder(id);
            if(orderDelete){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Delete Order succsessfully",true, orderDelete)
                );
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Delete Order : "+ id + " fail",false,"null")
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", e.getMessage(),false,"null")
            );
        }

    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> findOrderById(@PathVariable Long id){
        Orders orders = iOrdersService.getOrderById(id);
        if(orders != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, orders)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find OrderID: "+id,false,"null")
            );
        }

    }

}
