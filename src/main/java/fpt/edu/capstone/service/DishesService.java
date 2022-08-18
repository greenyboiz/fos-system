package fpt.edu.capstone.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.implementService.IDishesService;
import fpt.edu.capstone.repo.CategoryRepository;
import fpt.edu.capstone.repo.DishesRepository;
import fpt.edu.capstone.response.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class DishesService implements IDishesService {

    @Autowired
    private DishesRepository dishesRepository;

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public Dishes addDishes(Dishes dishes) {
        dishes.setStatus(true);
        return dishesRepository.save(dishes);
    }

    @Override
    public Dishes uploadDishesImage(MultipartFile file, Dishes dishes) {
        try {
            Map r = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
            String img = (String) r.get("secure_url");
            dishes.setDishImage(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return dishesRepository.save(dishes);
    }

    @Override
    public Dishes updateDishes(Dishes dishes) {
        Category category = categoryRepository.findByCategoryId(dishes.getCategory().getCategoryId());
        if(dishes != null) {
            Dishes dishes1 = dishesRepository.findDishesById(dishes.getDishesId());
            if(dishes1 != null) {
                dishes1.setDishesName(dishes.getDishesName());
                dishes1.setDescription(dishes.getDescription());
                dishes1.setDishImage(dishes.getDishImage());
                dishes1.setDiscount(dishes.getDiscount());
                dishes1.setCategory(category);
                dishes1.setCostPrice(dishes.getCostPrice());
                dishes1.setSalePrice(dishes.getSalePrice());
                dishes1.setStatus(dishes.getStatus());

                return dishesRepository.save(dishes1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteDishes(Long id) {
        Dishes dishes = dishesRepository.findDishesById(id);
        if(dishes != null){
            dishesRepository.delete(dishes);
            return true;
        }
        return false;
    }

    @Override
    public List<Dishes> getAllDishes() {
        return dishesRepository.findAll();
    }

    @Override
    public Dishes getDishesById(Long id) {
        Dishes dishes = dishesRepository.findDishesById(id);
        if(dishes != null){
            return dishes;
        }
        return null;
    }

    @Override
    public boolean getDishesExist(String dishesName) {
        Dishes dishes = dishesRepository.findDishesByDishesName(dishesName);
        if (dishes != null){
            return true;
        }
        return false;
    }

    @Override
    public Page<Dishes> listDishes(int pageNum, int pageSize) {
        log.info("(listDishes)pageNum: {}, pageSize: {}", pageNum, pageSize);
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return dishesRepository.findAll(pageable);
    }

    @Override
    public List<Dishes> getAllDishesForClient() {
        return dishesRepository.getAllDishesForClient();
    }

    @Override
    public Dishes changeStatusOfDishesById(Dishes dishes) {
        if(dishes.getStatus() == true){
            dishes.setStatus(false);
        } else {
            dishes.setStatus(true);
        }
        return dishesRepository.save(dishes);
    }
}
