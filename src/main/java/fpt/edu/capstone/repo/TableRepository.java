package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Tables, Long> {
}
