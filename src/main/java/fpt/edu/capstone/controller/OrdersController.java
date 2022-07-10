package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.ICustomerService;
import fpt.edu.capstone.implementService.IOrdersService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private IOrdersService iOrdersService;

    @Autowired
    private ICustomerService iCustomerService;

    @GetMapping("/orders")
    private List<Orders> getAllOrders(){
        return iOrdersService.getAllOrders();
    }

    @PostMapping("/orders")
    private Orders saveOrders(@RequestBody Orders orders){
        boolean checkOrderExist = iOrdersService.checkOrderExist(orders);
        if (!checkOrderExist){
            Customer customer = iCustomerService.addCustomer(orders.getCustomer());
            orders.setCustomer(customer);
            return iOrdersService.addOrder(orders);
        }
        return null;
    }

    @PutMapping("/orders/update")
    private Orders updateOrders(@RequestBody Orders orders){
        return iOrdersService.updateOrder(orders);
    }

    @DeleteMapping("/orders/delete/{id}")
    public boolean deleteOrder(@PathVariable("id") Long id){
        return iOrdersService.deleteOrder(id);
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
