package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.PaymentDTO;
import fpt.edu.capstone.entities.OrderItem;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Payment;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.implementService.IPaymentService;
import fpt.edu.capstone.repo.OrderItemRepository;
import fpt.edu.capstone.repo.OrdersRepository;
import fpt.edu.capstone.repo.PaymentRepository;
import fpt.edu.capstone.repo.TableRepository;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private TableRepository tableRepository;

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

    @Override
    public boolean deletePaymentById(Long paymentId) {
        Payment payment = paymentRepository.findPaymentById(paymentId);
        if(payment != null){
            paymentRepository.delete(payment);
            return true;
        }
        return false;
    }

    @Override
    public Payment getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findPaymentByOrderId(orderId);
        if(payment != null){
            return payment;
        }
        return null;
    }

    @Override
    public PaymentDTO getPaymentOfTable(Long orderId) {
        PaymentDTO paymentDTO = new PaymentDTO();
        Orders orders = ordersRepository.getOrdersById(orderId);
        if(orders != null){
            Tables tables = tableRepository.findTableByQRCodeId(orders.getQrCode().getQRCodeId());
            BigDecimal payment = orderItemRepository.findPaymentByOrderId(orderId);
            List<OrderItem> itemList = orderItemRepository.getOrderItemByOrderId(orderId);

            paymentDTO.setPayment(payment);
            paymentDTO.setTableId(tables.getTableId());
            paymentDTO.setOrderItemList(itemList);
        }
        return paymentDTO;
    }

}
