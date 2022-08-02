package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleId(Long id);

    @Query("select r from Role r where r.roleName = ?1")
    Role checkRoleExist(String roleName);
}
