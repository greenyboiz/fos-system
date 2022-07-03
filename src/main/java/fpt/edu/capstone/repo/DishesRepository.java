package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DishesRepository extends JpaRepository<Dishes,Long> {
}
