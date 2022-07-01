package com.example.demo.implementService;

import com.example.demo.entities.Category;
import com.example.demo.entities.Customer;
import com.example.demo.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ICustomerService {
    public Customer addCustomer(Customer customer);

    public Customer updateCustomer(Customer customer);

    public boolean deleteCustomer(Long id);

    public List<Customer> getAllCustomer();

    public ResponseEntity<ResponseObject> getCustomerById(Long id);
}
