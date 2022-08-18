package fpt.edu.capstone.implementService;

import fpt.edu.capstone.dto.DashboardDTO;
import fpt.edu.capstone.dto.OrderDTO;
import fpt.edu.capstone.entities.Customer;
import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrdersService {
    public Orders addOrder(Orders orders);

    public Orders updateOrder(Orders orders);

    public boolean deleteOrder(Long id);

    public List<Orders> getAllOrders();
    public Orders getOrderById(Long id);

    public Orders checkOrderExist(Orders orders);

    boolean checkCustomerExistInOrder(Orders orders);

    Orders getOrderIdByQRCodeId(Long qrCodeId);

    Long getQRCodeIdByOrderId(Long orderId);

    Page<Orders> listOrders(int pageNum, int pageSize);

    List<OrderDTO> getAllListOrders();
}
