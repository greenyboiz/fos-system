package com.example.demo.repo;

import com.example.demo.entities.FOSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FOSUserRepository extends JpaRepository<FOSUser, Long> {

}
