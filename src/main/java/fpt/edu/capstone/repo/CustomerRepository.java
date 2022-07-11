package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerId(Long id);

    @Query("select c from Customer c where c.contact = ?1")
    Customer checkContactExist(String contact);
}
