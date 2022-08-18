package fpt.edu.capstone.repo;

import fpt.edu.capstone.entities.Dishes;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dishes,Long> {
    @Query("select c from Dishes c where c.dishesId = ?1")
    Dishes findDishesById(Long dishesId);

    @Query("select c from Dishes c where c.dishesName = ?1")
    Dishes findDishesByDishesName(String dishesName);

    @Query("select d from Dishes d where d.status = 1")
    List<Dishes> getAllDishesForClient();
}
