package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {
    @Query("select p.paymentTypeId from PaymentType p where p.paymentTypeName = ?1")
    public Long findPaymentTypeNameByPaymentTypeId(String paymentType);

    @Query("select p from PaymentType p where p.paymentTypeId = ?1")
    PaymentType findPaymentTypeByPaymentTypeId(Long paymentTypeId);
}
