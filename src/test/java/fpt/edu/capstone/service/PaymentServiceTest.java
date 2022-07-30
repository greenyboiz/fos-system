package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.*;
import fpt.edu.capstone.repo.OrderItemRepository;
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

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Test
    public void getTotalAmountByOrder(){
        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);
        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        QRCode actualQRCode = new QRCode(1l,null);
        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);
        OrderItem actual = new OrderItem(1l,actualOrders,actualDishes,2,null,null,null);

        Mockito.when(orderItemRepository.findPaymentByOrderId(actual.getOrderItemId())).thenReturn(actual.getDishes().getSalePrice());

        BigDecimal result = paymentService.getTotalAmountByOrder(actual.getOrderItemId());

        Assert.assertEquals(result,actual.getDishes().getSalePrice());
    }

}
