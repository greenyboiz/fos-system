package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.ListDto;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.OrderItem;
import fpt.edu.capstone.implementService.IOrderItemService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrderItemController {

    @Autowired
    private IOrderItemService iOrderItemService;

    @GetMapping("/orderItem")
    public List<OrderItem> getAllOrderItems(){
        return iOrderItemService.listOrderItems();
    }

    @GetMapping("/orderItem/order/{oderId}")
    public ResponseEntity<?> showOrderItemByOrderId(@PathVariable(name = "oderId") Long id){
        List<OrderItem> orderItem = iOrderItemService.getOrderItemByOrderId(id);
        if(orderItem != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, orderItem)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find OrderItemID: "+id,false,"null")
            );
        }
    }

    @GetMapping("/orderItem/{id}")
    public ResponseEntity<?> getOrderItemById(@PathVariable(name = "id") Long id){
        OrderItem orderItem = iOrderItemService.getOrderItemById(id);
        if(orderItem != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, orderItem)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find OrderItemID: "+id,false,"null")
            );
        }
    }

    @PostMapping("/orderItem")
    public List<OrderItem> addDishesToOrderItem(@RequestBody ListDto listOrderItem){
        return iOrderItemService.addDishesToOrderItem(listOrderItem.getList());
    }

    @PutMapping("/orderItem")
    public OrderItem updateDishesToOrderItem(@RequestBody OrderItem orderItem){
        return iOrderItemService.updateDishesToOrderItem(orderItem);
    }
}
