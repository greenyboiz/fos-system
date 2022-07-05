package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Orders;
import fpt.edu.capstone.entities.Tables;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrdersService {
    public Orders addOrder(Orders orders);

    public Orders updateOrder(Orders orders);

    public boolean deleteOrder(Long id);

    public List<Orders> getAllOrders();
    public ResponseEntity<ResponseObject> getOrderById(Long id);

}
