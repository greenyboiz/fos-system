package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.repo.CustomerRepository;
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

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        expect.add(new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi"));

        List<Customer> actual = new ArrayList<>();
        actual.add(new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi"));

        Mockito.when(customerRepository.findAll()).thenReturn(actual);
        List<Customer> result = customerService.getAllCustomer();
        Assert.assertEquals(result,actual);
    }

    @Test
    public void getAllCustomerTestFail(){
        List<Customer> expect = new ArrayList<>();
        expect.add(new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi"));

        List<Customer> actual = new ArrayList<>();
        actual.add(new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi"));

        Mockito.when(customerRepository.findAll()).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> customerService.getAllCustomer());

//        List<Customer> result = customerService.getAllCustomer();
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void addCustomerTest(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer("tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.save(actual)).thenReturn(expect);
        Customer result = customerService.addCustomer(actual);

        Assert.assertEquals(result,expect);
    }

    @Test
    public void addCustomerTestFail(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer("tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.save(actual)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> customerService.addCustomer(actual));

        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void updateCustomerTest(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.findByCustomerId(actual.getCustomerId())).thenReturn(expect);
        Mockito.when(customerRepository.save(actual)).thenReturn(expect);

        Customer result = customerService.updateCustomer(actual);
        Assert.assertEquals(result,expect);
    }

    @Test
    public void updateCustomerTestFail(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.findByCustomerId(actual.getCustomerId())).thenReturn(expect);

        Mockito.when(customerRepository.save(actual)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> customerService.updateCustomer(actual));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void  deleteCustomerByIdTest(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");
        Mockito.when(customerRepository.findByCustomerId(expect.getCustomerId())).thenReturn(actual);
        customerRepository.delete(actual);
        Mockito.verify(customerRepository,Mockito.timeout(1)).delete(actual);
        boolean result = customerService.deleteCustomer(actual.getCustomerId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void  deleteCustomerByIdTestFail(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");
        Mockito.when(customerRepository.findByCustomerId(expect.getCustomerId())).thenThrow(new NullPointerException(""));
        customerRepository.delete(actual);
        Mockito.verify(customerRepository,Mockito.timeout(1)).delete(actual);
        NullPointerException result = assertThrows(NullPointerException.class, () -> customerService.deleteCustomer(actual.getCustomerId()));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void getCustomerByIdTest(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.findByCustomerId(actual.getCustomerId())).thenReturn(expect);
        Customer result = customerService.getCustomerById(actual.getCustomerId());

        Assert.assertEquals(result,expect);
    }

    @Test
    public void getCustomerByIdTestFail(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.findByCustomerId(actual.getCustomerId())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> customerService.getCustomerById(actual.getCustomerId()));

        Assert.assertEquals("",result.getMessage());
    }
    @Test
    public void checkCustomerExistTest(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.checkContactExist(actual.getContact())).thenReturn(expect);
        boolean result = customerService.checkCustomerExist(actual);

        Assert.assertEquals(true,result);
    }

    @Test
    public void checkCustomerExistTestFail(){
        Customer expect = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actual = new Customer(1l,"tang nguyet","065623266","mn@gmail.com","Ha Noi");

        Mockito.when(customerRepository.checkContactExist(actual.getContact())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> customerService.checkCustomerExist(actual));

        Assert.assertEquals("",result.getMessage());
    }

}
