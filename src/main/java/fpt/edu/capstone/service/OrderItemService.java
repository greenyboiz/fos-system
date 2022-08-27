package fpt.edu.capstone.service;

import fpt.edu.capstone.dto.BestSellerDishesDTO;
import fpt.edu.capstone.dto.DashboardDTO;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.entities.OrderItem;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.implementService.IOrderItemService;
import fpt.edu.capstone.mapper.BestSellerDishRowMapper;
import fpt.edu.capstone.mapper.DashboardRowMapper;
import fpt.edu.capstone.repo.DishesRepository;
import fpt.edu.capstone.repo.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private DishesRepository dishesRepository;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Override
    public List<OrderItem> listOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.getOrderItemById(id);
        if(orderItem != null){
            return orderItem;
        }
        return null;
    }

    @Override
    public List<OrderItem> addDishesToOrderItem(List<OrderItem> list) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItem order: list
             ) {
            OrderItem orderItem = orderItemRepository.findOrderItemByOrderIdAndDishesId(order.getDishes().getDishesId(), order.getOrders().getOrderId());
            Dishes dishes = dishesRepository.findDishesById(order.getDishes().getDishesId());
            if (orderItem != null){
                orderItem.setPrice(orderItem.getDishes().getSalePrice());
                orderItem.setQuantity(order.getQuantity() + orderItem.getQuantity());
                orderItemList.add(orderItemRepository.save(orderItem));
            }else{
                order.setPrice(dishes.getSalePrice());
                orderItemList.add(orderItemRepository.save(order));
            }
        }
        return orderItemList;
    }

    @Override
    public OrderItem updateDishesToOrderItem(OrderItem orderItem) {
        OrderItem orderItem1 = orderItemRepository.getById(orderItem.getOrderItemId());
        orderItem1.setQuantity(orderItem.getQuantity());
        return orderItemRepository.save(orderItem1);
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(Long id) {
        List<OrderItem> orderItem = orderItemRepository.getOrderItemByOrderId(id);
        if(orderItem != null){
            return orderItem;
        }
        return null;
    }

    @Override
    public boolean deleteDishesInOrederItem(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.getOrderItemById(orderItemId);
        if(orderItem != null){
            orderItemRepository.delete(orderItem);
            return true;
        }
        return false;
    }

    @Override
    public void deleteOrderItemByOrderId(Orders orders) {
        orderItemRepository.deleteOrderItemByOrderId(orders.getOrderId());
    }

    @Override
    public List<DashboardDTO> getListDashboard() {
        String query = "WITH TEMP AS (SELECT o.order_id , MONTH(o.submit_time) AS month_order, YEAR(o.submit_time) AS year_order\n" +
                "        , SUM(d.cost_price * oi.quantity) AS total_cost, SUM(d.sale_price * oi.quantity) AS total_sale\n" +
                "                FROM railway.orders o INNER JOIN railway.order_item oi\n" +
                "                ON o.order_id = oi.order_id INNER JOIN railway.dishes d ON d.dishes_id = oi.dishes_id\n" +
                "                where o.status = 1\n" +
                "                group by o.order_id)\n" +
                "                SELECT t.month_order, t.year_order, SUM(t.total_cost) as total_cost, (SUM(t.total_sale)*1.1) as total_sale, (SUM(t.total_sale)*1.1 - SUM(t.total_cost)) as profit\n" +
                "                FROM TEMP t\n" +
                "                group by t.month_order, t.year_order\n" +
                "                order by t.year_order, t.month_order";
        List<DashboardDTO> list = template.query(query, new DashboardRowMapper());
        return list;
    }

    @Override
    public List<BestSellerDishesDTO> getListDashboardBestseller() {
        String query = "WITH TEMP AS (SELECT d.dishes_id, d.dishes_name,d.dish_image, SUM(oi.quantity) as quantity, d.sale_price\n" +
                "FROM railway.orders o INNER JOIN railway.order_item oi\n" +
                "                ON o.order_id = oi.order_id INNER JOIN railway.dishes d ON d.dishes_id = oi.dishes_id\n" +
                "                group by  d.dishes_id\n" +
                "                order by quantity desc\n" +
                "                LIMIT 5)\n" +
                "SELECT t.dishes_name,t.dish_image, t.quantity, t.sale_price FROM TEMP t;";
        List<BestSellerDishesDTO> list = template.query(query, new BestSellerDishRowMapper());
        return list;
    }

}
