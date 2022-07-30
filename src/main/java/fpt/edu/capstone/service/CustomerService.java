package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.implementService.ICustomerService;
import fpt.edu.capstone.repo.CustomerRepository;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        if(customer != null){
            Customer customer1 = customerRepository.findByCustomerId(customer.getCustomerId());
            if(customer1 != null){
                customer1.setFullName(customer.getFullName());
                customer1.setContact(customer.getContact());
                customer1.setEmail(customer.getEmail());
                customer1.setAddress(customer.getAddress());

                return customerRepository.save(customer1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteCustomer(Long id) {
        Customer customer = customerRepository.findByCustomerId(id);
        if(customer != null){
            customerRepository.delete(customer);
            return true;
        }
        return false;
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findByCustomerId(id);
    }

    @Override
    public boolean checkCustomerExist(Customer customer) {
        Customer customerNew = customerRepository.checkContactExist(customer.getContact());
        if(customerNew != null){
            return true;
        }
        return false;
    }

    @Override
    public Customer getCustomerByContact(String contact) {
        return customerRepository.checkContactExist(contact);
    }
}
