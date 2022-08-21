package fpt.edu.capstone.controller;

import fpt.edu.capstone.dto.ListDto;
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
    public ResponseEntity<?> getAllOrderItems(){
        List<OrderItem> orderItemList = iOrderItemService.listOrderItems();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "successfull",true, orderItemList)
        );
    }

    @GetMapping("/orderItem/order/{orderId}")
    public ResponseEntity<?> showOrderItemByOrderId(@PathVariable(name = "orderId") Long orderId){
        List<OrderItem> orderItem = iOrderItemService.getOrderItemByOrderId(orderId);
        if(orderItem != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, iOrderItemService.getOrderItemByOrderId(orderId))
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Can not find OrderItemID: "+orderId,false,"null")
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

    @DeleteMapping("/orderItem/{orderItemId}")
    public ResponseEntity<?> deleteDishesInOrederItem(@PathVariable(name = "orderItemId") Long orderItemId){
        boolean deleteDishesInOrederItem = iOrderItemService.deleteDishesInOrederItem(orderItemId);
        if(deleteDishesInOrederItem == true){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "successfull",true, null)
            );
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "In OrderItem does not contain orderItemId: " + orderItemId,false, null)
            );
    }
}
