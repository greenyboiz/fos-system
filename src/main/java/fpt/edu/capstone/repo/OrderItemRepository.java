package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
