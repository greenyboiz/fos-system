package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
//    @Query("select c from ")
//    BigDecimal findPaymentByOrderId(Long orderId);
}
