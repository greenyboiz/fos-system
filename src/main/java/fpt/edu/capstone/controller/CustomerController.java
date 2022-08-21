package fpt.edu.capstone.controller;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.implementService.ICustomerService;
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
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomer(){
        List<Customer> customers = iCustomerService.getAllCustomer();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "succsessfully",true, customers)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/customers/add")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer){
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/customers/update")
    public ResponseEntity<?> updateFOSUser(@RequestBody Customer customer){
        Customer customer1 = iCustomerService.updateCustomer(customer);
        if(customer1 != null){
            iCustomerService.updateCustomer(customer);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update Customer succsessfully",true, customer1)
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("fail", "Customer is not exist ",false,"null")
        );
    }

    @DeleteMapping("/customers/delete/{id}")
    public ResponseEntity<?> deleteCustomers(@PathVariable("id") Long id){
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
    public ResponseEntity<ResponseObject> getCustomerById(@PathVariable Long id){
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
