package fpt.edu.capstone.repo;

import fpt.edu.capstone.dto.DashboardDTO;
import fpt.edu.capstone.dto.OrderDTO;
import fpt.edu.capstone.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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

}
