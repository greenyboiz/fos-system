package fpt.edu.capstone.repo;

import fpt.edu.capstone.dto.DashboardDTO;
import fpt.edu.capstone.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("select c from OrderItem c where c.dishes.dishesId = ?1 and  c.orders.orderId = ?2")
    OrderItem findOrderItemByOrderIdAndDishesId(Long dishesId, Long orderId);

    @Query("select sum (c.dishes.salePrice*c.quantity) from OrderItem c where c.orders.orderId = ?1 " +
            "group by c.orders.orderId")
    BigDecimal findPaymentByOrderId(Long orderId);


    @Query("select c from OrderItem c where c.orderItemId = ?1")
    OrderItem getOrderItemById(Long id);

    @Query("select c from OrderItem c where c.orders.orderId = ?1")
    List<OrderItem> getOrderItemByOrderId(Long id);

    @Modifying
    @Transactional
    @Query("delete from OrderItem o where o.orders.orderId = ?1")
    void deleteOrderItemByOrderId(Long orderId);


//    @Query(
//    value = "delete from OrderItem o where o.orders = ?1",
//    nativeQuery = true)
//    void deleteOrderItemByOrderId(Orders orders);

//    @Query(value="delete from OrderItem a where a.orders.orderId= :orderId", nativeQuery = true)
//    void deleteOrderItemByOrderId(@Param("orderId") Long orderId);
//
//    void deleteOrderItemsByOrders(Orders orders);
}
