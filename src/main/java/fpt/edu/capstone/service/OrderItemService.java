package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.OrderItem;
import fpt.edu.capstone.implementService.IOrderItemService;
import fpt.edu.capstone.repo.OrderItemRepository;
import fpt.edu.capstone.repo.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

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
            orderItem.setPrice(orderItem.getDishes().getSalePrice());
            if (orderItem != null){
                orderItem.setQuantity(order.getQuantity() + orderItem.getQuantity());
                orderItemList.add(orderItemRepository.save(orderItem));
            }else{
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

}
