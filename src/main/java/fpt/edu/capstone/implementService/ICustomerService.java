package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerService {
    public Customer addCustomer(Customer customer);

    public Customer updateCustomer(Customer customer);

    public boolean deleteCustomer(Long id);

    public List<Customer> getAllCustomer();

    public Customer getCustomerById(Long id);

    boolean checkCustomerExist(Customer customer);

    Customer getCustomerByContact(String contact);
}
