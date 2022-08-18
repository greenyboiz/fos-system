package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDishesService {
    public Dishes addDishes(Dishes dishes);

    public Dishes uploadDishesImage(MultipartFile file, Dishes dishes);

    public Dishes updateDishes(Dishes dishes);

    public boolean deleteDishes(Long id);

    public List<Dishes> getAllDishes();

    public Dishes getDishesById(Long id);

    boolean getDishesExist(String dishesName);

    /**
     * Function list dishes with pagination: pageNum, pageSize
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Dishes> listDishes(int pageNum, int pageSize);

    List<Dishes> getAllDishesForClient();

    Dishes changeStatusOfDishesById(Dishes dishes);
}
