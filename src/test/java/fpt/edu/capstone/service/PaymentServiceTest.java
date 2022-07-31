package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.*;
import fpt.edu.capstone.repo.OrderItemRepository;
import fpt.edu.capstone.repo.PaymentRepository;
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
    private PaymentRepository paymentRepository;

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

    @Test
    public void savePaymentTest(){
        Role roleExpect = new Role(1l,"ADMIN");
        Role roleUpdate = new Role(2l,"STAFF");

        FOSUser expectUser = new FOSUser(1l, "hoang tien dat111111","dat1", "12345",true,"0966564666","dat1@gmail.com",true,"image",roleExpect);
        FOSUser actualUser = new FOSUser(1l, "hoang tien dat","dat1", "12345",true,"0966564666","dat1@gmail.com",true,"image",roleUpdate);

        PaymentType expectPaymentType = new PaymentType(1l,"banking");
        Dishes expectDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);
        Customer expectCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        QRCode expectQRCode = new QRCode(1l,"abc");
        Orders expectOrders = new Orders(1l,expectCustomer,expectQRCode,null,null,null,0,"tuyệt vời",10);

        PaymentType actualPaymentType = new PaymentType(2l,"cash");
        Dishes actualDishes = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, null);
        Customer actualCustomer = new Customer(1l,"minh nguyet","065623266","mn@gmail.com","Ha Noi");
        QRCode actualQRCode = new QRCode(1l,"abc");
        Orders actualOrders = new Orders(1l,actualCustomer,actualQRCode,null,null,null,0,"tuyệt vời",10);

        Payment expect = new Payment(1l,expectOrders,expectUser,expectPaymentType,BigDecimal.valueOf(150000));
        Payment actual = new Payment(1l,actualOrders,actualUser,actualPaymentType,BigDecimal.valueOf(180000));

        Mockito.when(paymentRepository.save(actual)).thenReturn(expect);
        Payment result = paymentService.savePayment(actual);
        Assert.assertEquals(result,expect);
    }

}
