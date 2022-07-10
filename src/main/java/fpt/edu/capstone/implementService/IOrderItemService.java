package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.OrderItem;

import java.util.List;

public interface IOrderItemService {

//    public List<OrderItem> listOrderItemByQRCode(QRCode qrCode);

    public List<OrderItem> listOrderItems();

    public OrderItem getOrderItemById(Long id);

    public List<OrderItem> addDishesToOrderItem(List<OrderItem> list);

    public OrderItem updateDishesToOrderItem(OrderItem orderItem);


    List<OrderItem> getOrderItemByOrderId(Long id);
}
