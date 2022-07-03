package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    Request findByRequestId(Long id);
}
