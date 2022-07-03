package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.FOSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FOSUserRepository extends JpaRepository<FOSUser, Long> {

}
