package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.IOrdersService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrdersController {

    @Autowired
    private IOrdersService iOrdersService;

    @GetMapping("/orders")
    private List<Orders> getAllOrders(){
        return iOrdersService.getAllOrders();
    }

    @PostMapping("/orders/add")
    private Orders saveOrders(@RequestBody Orders orders){
        return iOrdersService.addOrder(orders);
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
    ResponseEntity<ResponseObject> findOrderById(@PathVariable Long id){
        return iOrdersService.getOrderById(id);
    }

}
