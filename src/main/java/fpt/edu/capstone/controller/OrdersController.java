package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.OrderDTO;
import fpt.edu.capstone.dto.PaymentUserDTO;
import fpt.edu.capstone.entities.*;
import fpt.edu.capstone.implementService.*;
import fpt.edu.capstone.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Slf4j
public class OrdersController {

    @Autowired
    private IOrdersService iOrdersService;

    @Autowired
    private IOrderItemService iOrderItemService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IPaymentService iPaymentService;

    @Autowired
    private IPaymentTypeService iPaymentTypeService;
    @Autowired
    private ITablesService iTablesService;

    @Autowired
    private IFOSUserService ifosUserService;

    @GetMapping("/orders")
    public ResponseEntity<?> getAllOrders(){
        List<OrderDTO> orders = iOrdersService.getAllListOrders();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "succsessfully",true, orders)
        );
    }

    @PostMapping("/orders")
    public ResponseEntity<?> saveOrders(@RequestBody Orders orders){
        Orders orders1 = iOrdersService.checkOrderExist(orders);
        if (orders1 == null){
            Tables tables = iTablesService.getTableByQRCodeId(orders.getQrCode().getQRCodeId());
            if(tables != null){
                //check table empty
                if (tables.getStatus()==false){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(
                            new ResponseObject("fail", "Table is not empty",false, null)
                    );
                }
                //Nếu table còn trống thì chuyển trạng thái đã có người ngồi
                tables.setStatus(false);
                Customer customerSystem = iCustomerService.getCustomerByContact(orders.getCustomer().getContact());
                //Check customer theo contact chưa tồn tại thì add customer
                if(customerSystem == null){
                    Customer customer = iCustomerService.addCustomer(orders.getCustomer());
                    orders.setCustomer(customer);
                }
                //Customer đã tồn tại thì k add chỉ set customerId vào order
                if(customerSystem != null) {
                    orders.setCustomer(customerSystem);
                }
                iOrdersService.addOrder(orders);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "add order succsessfully",true, iOrdersService.addOrder(orders))
                );
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Table is not exist or in-active",false, null)
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Order is exist, you can order continue",true,orders1)
            );
        }
    }

    @PutMapping("/orders/update")
    public Orders updateOrders(@RequestBody Orders orders){
        return iOrdersService.updateOrder(orders);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @PutMapping("/orders/confirm/{orderId}")
    public ResponseEntity<?> confirmOrders(@PathVariable("orderId") Long orderId, @RequestBody PaymentUserDTO paymentUserDTO){
        try {
            Orders orders = iOrdersService.getOrderById(orderId);
            if (orders == null){
                throw new Exception();
            }
            //set order bill paid
            orders.setStatus(1);
            iOrdersService.addOrder(orders);
            //set table is Empty
            Tables tables = iTablesService.getTableByQRCodeId(orders.getQrCode().getQRCodeId());
            tables.setStatus(true);
            iTablesService.addTable(tables);
            ///------------
            BigDecimal totalMoney = iPaymentService.getTotalAmountByOrder(orderId);
            Long paymentTypeId = iPaymentTypeService.getPaymentTypeNameByPaymentTypeId(paymentUserDTO.getPaymentType());
            FOSUser fosUser = ifosUserService.getFOSUserById(paymentUserDTO.getFosUserId());
            Orders orders1 = iOrdersService.getOrderById(orderId);
            PaymentType paymentType = iPaymentTypeService.getPaymentTypeByPaymentTypeId(paymentTypeId);
            Payment payment = new Payment();
            payment.setFosUser(fosUser);
            payment.setOrders(orders1);
            payment.setPaymentType(paymentType);
            payment.setTotalMoney(totalMoney);
            iPaymentService.savePayment(payment);

            //--------------------
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Order " + orderId + " bill paid",true, payment)
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", e.getMessage(),false, null)
            );
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_STAFF')")
    @DeleteMapping("/orders/delete/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Long id){
        try {
            Orders orders = iOrdersService.getOrderById(id);
            if(orders==null){
                throw new Exception();
            }
            Tables tables = iTablesService.getTableByQRCodeId(orders.getQrCode().getQRCodeId());
            tables.setStatus(true);
            iTablesService.addTable(tables);
            iOrderItemService.deleteOrderItemByOrderId(orders);
            Payment payment = iPaymentService.getPaymentByOrderId(orders.getOrderId());
            iPaymentService.deletePaymentById(payment.getPaymentId());
            boolean orderDelete = iOrdersService.deleteOrder(orders.getOrderId());
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
