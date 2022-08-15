package fpt.edu.capstone.implementService;

import fpt.edu.capstone.dto.BestSellerDishesDTO;
import fpt.edu.capstone.dto.DashboardDTO;
import fpt.edu.capstone.entities.OrderItem;
import fpt.edu.capstone.entities.Orders;

import java.util.List;

public interface IOrderItemService {

//    public List<OrderItem> listOrderItemByQRCode(QRCode qrCode);

    public List<OrderItem> listOrderItems();

    public OrderItem getOrderItemById(Long id);

    public List<OrderItem> addDishesToOrderItem(List<OrderItem> list);

    public OrderItem updateDishesToOrderItem(OrderItem orderItem);


    List<OrderItem> getOrderItemByOrderId(Long id);

    boolean deleteDishesInOrederItem(Long orderItemId);

    void deleteOrderItemByOrderId(Orders orders);

    List<DashboardDTO> getListDashboard();

    List<BestSellerDishesDTO> getListDashboardBestseller();
}
