package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    @Query("select c from Orders c where c.customer.customerId = ?1 and c.qrCode.QRCodeId = ?2 and c.status = ?3")
    Orders findByCustomerIdAndQrCodeAndOrderStatus(Long customerId, Long qrCodeId, Integer status);

    @Query("select c from Orders c where c.orderId = ?1")
    Orders getOrdersById(Long id);
}
