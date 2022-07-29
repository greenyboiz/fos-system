package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Payment;
import fpt.edu.capstone.implementService.IPaymentService;
import fpt.edu.capstone.repo.OrderItemRepository;
import fpt.edu.capstone.repo.OrdersRepository;
import fpt.edu.capstone.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public BigDecimal getTotalAmountByOrder(Long orderId){
        BigDecimal total = orderItemRepository.findPaymentByOrderId(orderId);
        return total;
    }

    @Override
    public Payment getTotalMoneyOrder(Long orderId){
        Payment payment = new Payment();
        BigDecimal total = orderItemRepository.findPaymentByOrderId(orderId);
        Orders orders = ordersRepository.findOrdersById(orderId);
        payment.setTotalMoney(total);
        payment.setOrders(orders);
//        payment.setPaymentType();
        return payment;
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

}
