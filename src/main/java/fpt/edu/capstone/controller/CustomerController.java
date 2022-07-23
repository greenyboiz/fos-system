package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.implementService.ICustomerService;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomer(){
        List<Customer> customers = iCustomerService.getAllCustomer();
        if(customers !=null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, customers)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Can not find any customer",false,"null")
        );
    }

    @PostMapping("/customers/add")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        boolean checkCustomerExist = iCustomerService.checkCustomerExist(customer);

        if(!checkCustomerExist){
            Customer customerNew = iCustomerService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, customerNew)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail", "Customer is exist ",false,"null")
            );
        }
    }

    @PutMapping("/customers/update")
    public Customer updateFOSUser(@RequestBody Customer customer){
        return iCustomerService.updateCustomer(customer);
    }

    @DeleteMapping("/customers/delete/{id}")
    public ResponseEntity<?> deletecustomers(@PathVariable("id") Long id){
        boolean deleteCustomer = iCustomerService.deleteCustomer(id);
        if(deleteCustomer){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, deleteCustomer)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Customer is not exist ",false,"null")
        );
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ResponseObject> findCustomerById(@PathVariable Long id){
        Customer customer = iCustomerService.getCustomerById(id);
        if(customer != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "succsessfully",true, customer)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Customer is not exist ",false,"null")
        );
    }


}
