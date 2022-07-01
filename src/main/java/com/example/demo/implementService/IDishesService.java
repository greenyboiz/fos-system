package com.example.demo.implementService;

import com.example.demo.entities.Dishes;
import com.example.demo.response.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IDishesService {
    public Dishes addDishes(Dishes dishes);

    public Dishes updateDishes(Dishes dishes);

    public boolean deleteDishes(Long id);

    public List<Dishes> getAllDishes();

    public ResponseEntity<ResponseObject> getDishesById(Long id);
}
