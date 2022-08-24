package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    @Query("select c from Orders c where c.customer.contact = ?1 and c.qrCode.QRCodeId = ?2 and c.status = 0")
    Orders findByCustomerIdAndQrCodeAndOrderStatus(String contact, Long qrCodeId);

    @Query("select c from Orders c where c.orderId = ?1 and c.status = 0")
    Orders getOrdersById(Long id);

    @Query("select c from Orders c where c.customer.contact = ?1")
    Orders findCustomerByContactInOrder(String contact);

    @Query("select c from Orders c where c.qrCode.QRCodeId = ?1 and c.status = 0")
    Orders findOrderIdByQRCodeId(Long qrCodeId);

    @Query("select c.qrCode.QRCodeId from Orders c where c.orderId = ?1")
    Long findQRCodeIdByOrderId(Long orderId);


    @Query("select c from Orders c where c.orderId = ?1")
    Orders findOrdersById(Long id);

    @Query("select o from Orders o order by o.submitTime desc")
    List<Orders> getAllOrdersOrderByTime();

//    @Query(nativeQuery = true,
//            value = "SELECT o.order_id, c.full_name ,c.contact, o.submit_time, (SUM(d.sale_price*oi.quantity)*1.1) AS total_money_of_order\n" +
//                    "FROM railway.customer c INNER JOIN railway.orders o\n" +
//                    "                ON o.customer_id = c.customer_id INNER JOIN railway.order_item oi\n" +
//                    "                ON o.order_id = oi.order_id INNER JOIN railway.dishes d ON d.dishes_id = oi.dishes_id\n" +
//                    "                where o.status = 1\n" +
//                    "                group by o.order_id\n" +
//                    "                order by o.submit_time desc;")
//    List<OrderDTO> getAllOrders();

//    @Query("SELECT o.orderId, c.fullName ,c.contact, o.submitTime, (SUM(d.salePrice*oi.quantity)*1.1) AS total_money_of_order\n" +
//            "FROM Customer c INNER JOIN Orders o\n" +
//            "                ON o.customer.customerId = c.customerId INNER JOIN OrderItem oi\n" +
//            "                ON o.orderId = oi.orders.orderId INNER JOIN Dishes d ON d.dishesId = oi.dishes.dishesId \n" +
//            "                where o.status = 1\n" +
//            "                group by o.orderId\n" +
//            "                order by o.submitTime desc")
//    List<OrderDTO> getAllOrders();
}
