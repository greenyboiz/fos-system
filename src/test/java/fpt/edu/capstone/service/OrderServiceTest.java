package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.repo.CustomerRepository;
import fpt.edu.capstone.repo.OrdersRepository;
import fpt.edu.capstone.repo.QRCodeRepository;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {
    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private QRCodeRepository qrCodeRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void getAllOrdersTest(){
        List<Orders> expect = new ArrayList<>();
        Customer expectCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode expectQRCode = new QRCode(1l,null);
        expect.add(new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10));

        List<Orders> actual = new ArrayList<>();
        Customer actualCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode actualQRCode = new QRCode(1l,null);
        actual.add(new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10));

        Mockito.when(ordersRepository.findAll()).thenReturn(actual);

        List<Orders> results = ordersService.getAllOrders();

        Assert.assertEquals(results,actual);
    }

    @Test
    public void addOrderTest(){
        Customer expectCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode expectQRCode = new QRCode(1l,null);
        Orders expect = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);

        Customer actualCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode actualQRCode = new QRCode(1l,null);
        Orders actual = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);

        Mockito.when(ordersRepository.save(actual)).thenReturn(expect);
        Orders result = ordersService.addOrder(actual);

        Assert.assertEquals(result,actual);
    }

    @Test
    public void updateOrderTest(){
        Customer expectCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode expectQRCode = new QRCode(1l,null);
        Orders expect = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);

        Customer actualCustomer = new Customer(null,null,"0964697688",null,null);
        QRCode actualQRCode = new QRCode(2l,null);
        Orders actual = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"rất tuyệt vời",10);

        Mockito.when(ordersRepository.getById(actual.getOrderId())).thenReturn(expect);
        Mockito.when(ordersRepository.save(expect)).thenReturn(actual);

        Orders result = ordersService.updateOrder(actual);
        Assert.assertEquals(result,actual);
    }

    @Test
    public void deleteOrderTest(){
        Customer expectCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode expectQRCode = new QRCode(1l,null);
        Orders expect = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);

        Customer actualCustomer = new Customer(null,null,"0964697688",null,null);
        QRCode actualQRCode = new QRCode(2l,null);
        Orders actual = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"rất tuyệt vời",10);

        Mockito.when(ordersRepository.findOrdersById(actual.getOrderId())).thenReturn(expect);
//        Mockito.when(ordersRepository.delete(actual);)
        boolean result = ordersService.deleteOrder(actual.getOrderId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void checkOrderExistTest(){
        Customer actualCustomer = new Customer(null,null,"0964697688",null,null);
        QRCode actualQRCode = new QRCode(2l,null);
        Orders actual = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"rất tuyệt vời",10);

        Mockito.when(ordersRepository.findByCustomerIdAndQrCodeAndOrderStatus(actual.getCustomer().getContact(),actual.getQrCode().getQRCodeId())).thenReturn(actual);
        boolean result = ordersService.checkOrderExist(actual);

        Assert.assertEquals(result,true);
    }

    @Test
    public void checkCustomerExistInOrderTest(){
        Customer expectCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode expectQRCode = new QRCode(1l,null);
        Orders expect = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);

        Customer actualCustomer = new Customer(null,null,"0964697688",null,null);
        QRCode actualQRCode = new QRCode(2l,null);
        Orders actual = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"rất tuyệt vời",10);

        Mockito.when(ordersRepository.findCustomerByContactInOrder(actual.getCustomer().getContact())).thenReturn(expect);

        boolean result = ordersService.checkCustomerExistInOrder(actual);
        Assert.assertEquals(result,true);
    }

    @Test
    public void getOrderIdByQRCodeIdTest(){
        Customer expectCustomer = new Customer(null,null,"0964697689",null,null);
        QRCode expectQRCode = new QRCode(1l,null);
        Orders expect = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);

        Customer actualCustomer = new Customer(null,null,"0964697688",null,null);
        QRCode actualQRCode = new QRCode(2l,null);
        Orders actual = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"rất tuyệt vời",10);

        Mockito.when(ordersRepository.findQRCodeIdByOrderId(actual.getOrderId())).thenReturn(expect.getOrderId());
        Long result = ordersService.getQRCodeIdByOrderId(actual.getOrderId());
        Assert.assertEquals(result,expect.getOrderId());
    }
}
