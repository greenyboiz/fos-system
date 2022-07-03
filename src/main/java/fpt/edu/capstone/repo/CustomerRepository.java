package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerId(Long id);
}
