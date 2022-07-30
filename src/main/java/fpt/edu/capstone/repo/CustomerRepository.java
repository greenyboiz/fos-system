package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.customerId = ?1")
    Customer findByCustomerId(Long id);

    @Transactional
    @Query("select c from Customer c where c.contact = ?1")
    Customer checkContactExist(String contact);

}
