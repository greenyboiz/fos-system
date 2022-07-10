package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.OrderItem;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Payment;
import fpt.edu.capstone.repo.OrderItemRepository;
import fpt.edu.capstone.repo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public BigDecimal getTotalAmountByOrder(Orders orders){
        BigDecimal total = orderItemRepository.findPaymentByOrderId(orders.getOrderId());
        return total;
    }

}
