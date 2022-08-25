package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.OrderDTO;
import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.QRCode;
import fpt.edu.capstone.implementService.IOrdersService;
import fpt.edu.capstone.mapper.OrderRowMapper;
import fpt.edu.capstone.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrdersService implements IOrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private QRCodeRepository qrCodeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public Orders addOrder(Orders orders) {
        return ordersRepository.save(orders);
    }

    @Override
    public Orders updateOrder(Orders orders) {
        QRCode qrCode = qrCodeRepository.findByQRCodeId(orders.getQrCode().getQRCodeId());
        Customer customer = customerRepository.findByCustomerId(orders.getCustomer().getCustomerId());

        if(orders !=null){
            Orders orders1 = ordersRepository.getById(orders.getOrderId());
            if(orders1 != null){
                orders1.setCustomer(customer);
                orders1.setQrCode(qrCode);
//                orders1.setSubmitTime(orders.getSubmitTime());
                orders1.setCloseTime(orders.getCloseTime());
                orders1.setOtherFee(orders.getOtherFee());
                orders1.setStatus(orders.getStatus());
                orders1.setDescription(orders.getDescription());
                orders1.setOrderDiscount(orders.getOrderDiscount());

                return ordersRepository.save(orders1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteOrder(Long id) {
        Orders orders = ordersRepository.findOrdersById(id);
        if(orders != null){
//            orderItemRepository.deleteOrderItemByOrderId(id);
            ordersRepository.delete(orders);
            return true;
        }
        return false;
    }

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.getAllOrdersOrderByTime();
    }

    @Override
    public Orders getOrderById(Long id) {
       Orders orders = ordersRepository.findOrdersById(id);
       if (orders != null){
           return orders;
       }
       return null;
    }

    @Override
    public Orders checkOrderExist(Orders orders) {
        return ordersRepository.findByCustomerIdAndQrCodeAndOrderStatus(orders.getCustomer().getContact(), orders.getQrCode().getQRCodeId());
    }

    @Override
    public boolean checkCustomerExistInOrder(Orders orders) {
        Orders orders1 = ordersRepository.findCustomerByContactInOrder(orders.getCustomer().getContact());
        return orders1 == null ? false : true;
    }

    @Override
    public Orders getOrderIdByQRCodeId(Long qrCodeId) {
        Orders orders = ordersRepository.findOrderIdByQRCodeId(qrCodeId);
        if(orders != null){
            return orders;
        }
        return null;
    }

    @Override
    public Long getQRCodeIdByOrderId(Long orderId) {
        Long qrCodeId = ordersRepository.findQRCodeIdByOrderId(orderId);
        if(qrCodeId != null){
            return qrCodeId;
        }
        return null;
    }

    @Override
    public Page<Orders> listOrders(int pageNum, int pageSize) {
        log.info("(listOrders)pageNum: {}, pageSize: {}", pageNum, pageSize);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return ordersRepository.findAll(pageable);
    }

    @Override
    public List<OrderDTO> getAllListOrders() {
        String query = "WITH TEMP AS (SELECT o.order_id, c.full_name ,c.contact, o.submit_time, (SUM(d.sale_price*oi.quantity)*1.1) AS total_money_of_order\n" +
                "\t\tFROM railway.customer c INNER JOIN railway.orders o\n" +
                "                ON o.customer_id = c.customer_id INNER JOIN railway.order_item oi\n" +
                "                ON o.order_id = oi.order_id INNER JOIN railway.dishes d \n" +
                "                ON d.dishes_id = oi.dishes_id \n" +
                "                where o.status = 1\n" +
                "                group by o.order_id)\n" +
                "SELECT t.order_id, t.full_name ,t.contact, t.submit_time, fu.full_name as employee, pt.payment_type_name ,t.total_money_of_order\n" +
                "      FROM TEMP t INNER JOIN railway.payment p \n" +
                "      ON t.order_id = p.order_id INNER JOIN railway.payment_type pt\n" +
                "                ON p.payment_type_id = pt.payment_type_id INNER JOIN railway.fosuser fu\n" +
                "                ON p.user_id = fu.user_id\n" +
                "                order by t.submit_time desc;";
        List<OrderDTO> list = template.query(query, new OrderRowMapper());
        return list;
    }

//    @Override
//    public List<OrderDTO> getAllListOrders() {
//        return ordersRepository.getAllOrders();
//    }
}
