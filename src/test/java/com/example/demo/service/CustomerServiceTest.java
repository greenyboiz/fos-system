package com.example.demo.service;

import com.example.demo.entities.Category;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Dishes;
import com.example.demo.repo.CustomerRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void getAllCustomerTest(){
        List<Customer> expect = new ArrayList<>();
        expect.add(new Customer(1l,"minh","nguyet","065623266","mn@gmail.com","Ha Noi"));

        List<Customer> actual = new ArrayList<>();
        actual.add(new Customer(1l,"minh","nguyet","065623266","mn@gmail.com","Ha Noi"));

        Mockito.when(customerRepository.findAll()).thenReturn(actual);
        List<Customer> result = customerService.getAllCustomer();
        Assert.assertEquals(result,actual);
    }

    @Test
    public void addCustomerTest(){
        Customer expect = new Customer(1l,"minh","nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer("tang","nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.save(actual)).thenReturn(expect);
        Customer result = customerService.addCustomer(actual);

        Assert.assertEquals(result,expect);
    }

    @Test
    public void updateCustomerTest(){
        Customer expect = new Customer(1l,"minh","nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang","nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.getById(actual.getCustomerId())).thenReturn(expect);
        Mockito.when(customerRepository.save(actual)).thenReturn(expect);

        Customer result = customerService.updateCustomer(actual);
        Assert.assertEquals(result,actual);
    }

    @Test
    public void  deleteCustomerByIdTest(){

        Customer expect = new Customer(1l,"minh","nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang","nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.getById(expect.getCustomerId())).thenReturn(actual);
//        Mockito.when(dishesRepository.delete(actual);)
        boolean result = customerService.deleteCustomer(actual.getCustomerId());
        Assert.assertEquals(result,true);
    }
}
