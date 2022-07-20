package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.FOSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FOSUserRepository extends JpaRepository<FOSUser, Long> {

    @Query("select c from FOSUser c where c.userName = ?1 or c.contact = ?2 or c.email = ?3")
    FOSUser checkExistUserByUserNameAndContactAndEmail(String userName,String contact, String email);


    FOSUser findByUserName(String username);

    @Query("select c from FOSUser c where c.userId = ?1")
    FOSUser findFOSUserById(Long id);

}
