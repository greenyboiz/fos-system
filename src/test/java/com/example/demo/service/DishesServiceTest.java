package com.example.demo.service;

import com.example.demo.entities.Category;
import com.example.demo.entities.Dishes;
import com.example.demo.repo.CategoryRepository;
import com.example.demo.repo.DishesRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DishesServiceTest {
    @InjectMocks
    private  DishesService dishesService;

    @Mock
    private DishesRepository dishesRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void getAllDishesTest(){
        List<Dishes> expect = new ArrayList<>();
        expect.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, null));

        List<Dishes> actual = new ArrayList<>();
        actual.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, null));

        Mockito.when(dishesRepository.findAll()).thenReturn(actual);
        List<Dishes> results = dishesService.getAllDishes();

        Assert.assertEquals(results,actual);
    }

    @Test
    public void addDishesTest(){
        Category expectCategory = new Category(1l,"ga","img ga 1");
        Category actualCategory = new Category(2l,"lon","img lon 1");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, expectCategory);
        Dishes actual = new Dishes( "ga tan cao cap", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, actualCategory);

        Mockito.when(dishesRepository.save(actual)).thenReturn(expect);
        Dishes result = dishesService.addDishes(actual);

        Assert.assertEquals(result,expect);
    }

    @Test
    public void updateDishesTest(){
        Category expectCategory = new Category(1l,"ga","img ga 1");
        Category actualCategory = new Category(2l,"lon","img lon 1");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, expectCategory);
        Dishes actual = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, actualCategory);

        Mockito.when(categoryRepository.findByCategoryId(actual.getCategory().getCategoryId())).thenReturn(actualCategory);
        Mockito.when(dishesRepository.getById(actual.getDishesId())).thenReturn(expect);
        Mockito.when(dishesRepository.save(actual)).thenReturn(expect);

        Dishes result = dishesService.updateDishes(actual);
        Assert.assertEquals(result,actual);
    }

    @Test
    public void  deleteDishesByIdTest(){
        Category expectCategory = new Category(1l,"ga","img ga 1");
        Category actualCategory = new Category(2l,"lon","img lon 1");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, expectCategory);
        Dishes actual = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", 250000l, 120000l, 10l, 1, actualCategory);

        Mockito.when(dishesRepository.getById(expect.getDishesId())).thenReturn(actual);
//        Mockito.when(dishesRepository.delete(actual);)
        boolean result = dishesService.deleteDishes(actual.getDishesId());
        Assert.assertEquals(result,true);
    }

}
