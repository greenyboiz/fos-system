package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.*;
import fpt.edu.capstone.repo.DishesRepository;
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

    @Mock
    private DishesRepository dishesRepository;

    @Test
    public void getAllOrderItem(){
        Dishes expectDishes = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, null);
        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);

        Customer expectCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");

        QRCode expectQRCode = new QRCode(1l,null);
        QRCode actualQRCode = new QRCode(1l,null);

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
        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);
        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        QRCode actualQRCode = new QRCode(1l,null);
        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);
        OrderItem actual =new OrderItem(1l,actualOrders,actualDishes,2,null,null,null);

        Mockito.when(orderItemRepository.getOrderItemById(actual.getOrderItemId())).thenReturn(actual);
        OrderItem result = orderItemService.getOrderItemById(actual.getOrderItemId());

        Assert.assertEquals(result,actual);
    }

//    @Test
//    public void addDishesToOrderItemTest(){
//        Dishes expectDishes = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, null);
//        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);
//
//        Customer expectCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
//        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
//
//        QRCode expectQRCode = new QRCode(1l,null);
//        QRCode actualQRCode = new QRCode(1l,null);
//
//        Orders expectOrders = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);
//        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);
//
//
//        List<OrderItem> expect = new ArrayList<>();
//        expect.add(new OrderItem(1l,expectOrders,expectDishes,2,null,null,null));
//        List<OrderItem> actual = new ArrayList<>();
//        actual.add(new OrderItem(1l,actualOrders,actualDishes,2,null,null,null));
//
//        for (OrderItem order: actual
//             ) {
//            Mockito.when(orderItemRepository.findOrderItemByOrderIdAndDishesId(order.getDishes().getDishesId(),order.getOrders().getOrderId())).thenReturn(expect.get(0));
//            Mockito.when(dishesRepository.findDishesById(order.getDishes().getDishesId())).thenReturn(expect.get(0).getDishes());
//            Mockito.when(actual.add(orderItemRepository.save(order))).thenReturn(true);
//        }
//        List<OrderItem> results = orderItemService.addDishesToOrderItem(actual);
//        Assert.assertEquals(results,expect);
//    }

    @Test
    public void updateDishesToOrderItem(){
        Dishes expectDishes = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, null);
        Dishes actualDishes = new Dishes( 1l,"ga tan", "ngon ngon", "image1", null, null, 10l, true, null);

        Customer expectCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");

        QRCode expectQRCode = new QRCode(1l,null);
        QRCode actualQRCode = new QRCode(1l,null);

        Orders expectOrders = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);
        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);


        OrderItem expect = new OrderItem(1l,expectOrders,expectDishes,2,null,null,null);
        OrderItem actual = new OrderItem(1l,actualOrders,actualDishes,3,null,null,null);

        Mockito.when(orderItemRepository.getById(expect.getOrderItemId())).thenReturn(actual);
//        actual.setQuantity(expect.getQuantity());
        Mockito.when(orderItemRepository.save(actual)).thenReturn(expect);

        OrderItem result = orderItemService.updateDishesToOrderItem(actual);
        Assert.assertEquals(result,expect);
    }

    @Test
    public void getOrderItemByOrderId(){
        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);
        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        QRCode actualQRCode = new QRCode(1l,null);
        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);
        List<OrderItem> actual = new ArrayList<>();
        actual.add(new OrderItem(1l,actualOrders,actualDishes,2,null,null,null));

        Mockito.when(orderItemRepository.getOrderItemByOrderId(actualOrders.getOrderId())).thenReturn(actual);

        List<OrderItem> results = orderItemService.getOrderItemByOrderId(actualOrders.getOrderId());

        Assert.assertEquals(results,actual);

    }

    @Test
    public void deleteDishesInOrederItem(){
        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);
        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        QRCode actualQRCode = new QRCode(1l,null);
        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);
        OrderItem actual = new OrderItem(1l,actualOrders,actualDishes,2,null,null,null);

        Mockito.when(orderItemRepository.getOrderItemById(actual.getOrderItemId())).thenReturn(actual);
//        Mockito.when(orderItemRepository.delete();)
        boolean result = orderItemService.deleteDishesInOrederItem(actual.getOrderItemId());

        Assert.assertEquals(result,true);
    }
}
