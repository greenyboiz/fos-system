//package fpt.edu.capstone.controller;
//
//import fpt.edu.capstone.entities.CartItem;
//import fpt.edu.capstone.entities.Customer;
//import fpt.edu.capstone.entities.Dishes;
//import fpt.edu.capstone.entities.QRCode;
//import fpt.edu.capstone.response.ResponseObject;
//import fpt.edu.capstone.service.CartItemService;
//import fpt.edu.capstone.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@CrossOrigin(origins = "*", maxAge = 3600)
//@RestController
//@RequestMapping("/api")
//public class CartController {
//    @Autowired
//    private CartItemService cartItemService;
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping("/cart2")
//    private List<CartItem> showShoppingCart2(@RequestBody Customer customer){
//        return cartItemService.listCartItemByCustomer(customer);
//    }
//
//    @GetMapping("/cart")
//    private List<CartItem> showShoppingCart(@RequestBody QRCode qrCode){
//        return cartItemService.listCartItemByQRCode(qrCode);
//    }
//
//    @GetMapping("/cart3")
//    private List<CartItem> showShoppingCart3(@RequestBody Dishes dishes){
//        return cartItemService.listCartItemByDishes(dishes);
//    }
//
////    @PostMapping("cart/add")
////    private CartItem addToCart(@RequestBody CartItem cartItem){
////        Customer customer = customerService.getCustomerById2(cartItem.getCustomer().getCustomerId());
////        return cartItemService.addDishes(cartItem,customer);
////    }
//
//    @PostMapping("cart/add")
//    private ResponseEntity<?> addToCart(@RequestBody CartItem cartItem){
//        CartItem cartItem1 = cartItemService.addDishesToCart(cartItem);
//        if (cartItem1 == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponseObject("fail", "file null",false, null)
//            );
//        }else {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "successfull",true, cartItem1)
//            );
//        }
//    }
//
//    @DeleteMapping("cart/update")
//    private ResponseEntity<?> updateToCart(@RequestBody CartItem cartItem){
//        CartItem cartItem1 = cartItemService.updateDishesToCart(cartItem);
//        if (cartItem1.getQuantity() == null){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponseObject("fail", "file null",false, null)
//            );
//        }else {
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "successfull",true, cartItem1)
//            );
//        }
//    }
//}
