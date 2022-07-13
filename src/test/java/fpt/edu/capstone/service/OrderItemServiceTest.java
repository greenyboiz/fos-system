package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.*;
import fpt.edu.capstone.repo.OrderItemRepository;
import fpt.edu.capstone.repo.OrdersRepository;
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
public class OrderItemServiceTest {
    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    public void getAllOrderItem(){
        Dishes expectDishes = new Dishes(1l, "ga tan", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, null);
        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, null);

        Customer expectCustomer = new Customer(1l,"minh","nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actualCustomer = new Customer(1l,"minh","nguyet","065623266","mn@gmail.com","Ha Noi");

        QRCode expectQRCode = new QRCode(1l,null,null);
        QRCode actualQRCode = new QRCode(1l,null,null);

        Orders expectOrders = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);
        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);


        List<OrderItem> expect = new ArrayList<>();
        expect.add(new OrderItem(1l,expectOrders,expectDishes,2,null,null,null));
        List<OrderItem> actual = new ArrayList<>();
        actual.add(new OrderItem(1l,actualOrders,actualDishes,2,null,null,null));

        Mockito.when(orderItemRepository.findAll()).thenReturn(actual);

        List<OrderItem> result = orderItemService.listOrderItems();

        Assert.assertEquals(result,actual);

    }

    @Test
    public void getOrderItemById(){
//        OrderItem actual = new OrderItem(1l, null,null);
    }
}
