package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT P FROM Payment P WHERE P.paymentId = ?1")
    Payment findPaymentById(Long paymentId);

    @Query("select p from Payment p where p.orders.orderId = ?1")
    Payment findPaymentByOrderId(Long orderId);
//    @Query("select c from ")
//    BigDecimal findPaymentByOrderId(Long orderId);
}
