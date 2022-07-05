package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IDishesService {
    public Dishes addDishes(Dishes dishes);

    public Dishes uploadDishesImage(MultipartFile file, Dishes dishes);

    public Dishes updateDishes(Dishes dishes);

    public boolean deleteDishes(Long id);

    public List<Dishes> getAllDishes();

    public ResponseEntity<ResponseObject> getDishesById(Long id);
}
