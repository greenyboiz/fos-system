package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.entities.Dishes;
import fpt.edu.capstone.repo.CategoryRepository;
import fpt.edu.capstone.repo.DishesRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        expect.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(50000), null, 10l, true, null));
        List<Dishes> actual = new ArrayList<>();
        actual.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(60000), null, 10l, true, null));
        Mockito.when(dishesRepository.findAll()).thenReturn(actual);
        List<Dishes> results = dishesService.getAllDishes();
        Assert.assertEquals(results,actual);
    }

    @Test
    public void getAllDishesTestFail(){
        List<Dishes> expect = new ArrayList<>();
        expect.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(50000), null, 10l, true, null));
        List<Dishes> actual = new ArrayList<>();
        actual.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(60000), null, 10l, true, null));
        Mockito.when(dishesRepository.findAll()).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> dishesService.getAllDishes());
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void addDishesTest(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, expectCategory);
        Dishes actual = new Dishes( "ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, actualCategory);

        Mockito.when(dishesRepository.save(actual)).thenReturn(expect);
        Dishes result = dishesService.addDishes(actual);

        Assert.assertEquals(result,expect);
    }

    @Test
    public void addDishesTestFail(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, expectCategory);
        Dishes actual = new Dishes( "ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, actualCategory);

        Mockito.when(dishesRepository.save(actual)).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> dishesService.addDishes(actual));

        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void updateDishesTest(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");
        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, expectCategory);
        Dishes actual = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, actualCategory);
        Mockito.when(categoryRepository.findByCategoryId(actual.getCategory().getCategoryId())).thenReturn(actualCategory);
        Mockito.when(dishesRepository.findDishesById(actual.getDishesId())).thenReturn(expect);
        Mockito.when(dishesRepository.save(actual)).thenReturn(expect);

        Dishes result = dishesService.updateDishes(actual);
        Assert.assertEquals(result,actual);
    }

    @Test
    public void updateDishesTestFail(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");
        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, expectCategory);
        Dishes actual = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, actualCategory);
        Mockito.when(categoryRepository.findByCategoryId(actual.getCategory().getCategoryId())).thenReturn(actualCategory);
        Mockito.when(dishesRepository.findDishesById(actual.getDishesId())).thenReturn(expect);
        Mockito.when(dishesRepository.save(actual)).thenThrow(new NullPointerException(""));

        NullPointerException result = assertThrows(NullPointerException.class, () -> dishesService.updateDishes(actual));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void  deleteDishesByIdTest(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, expectCategory);
        Dishes actual = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, actualCategory);

        Mockito.when(dishesRepository.findDishesById(expect.getDishesId())).thenReturn(actual);
        dishesRepository.delete(actual);
        Mockito.verify(dishesRepository,Mockito.timeout(1)).delete(actual);
        boolean result = dishesService.deleteDishes(actual.getDishesId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void  deleteDishesByIdTestFail(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", null, null, 10l, true, expectCategory);
        Dishes actual = new Dishes( 1l,"ga tan cao cap", "ngon ngon", "image1", null, null, 10l, true, actualCategory);

        Mockito.when(dishesRepository.findDishesById(expect.getDishesId())).thenThrow(new NullPointerException(""));
        dishesRepository.delete(actual);
        Mockito.verify(dishesRepository,Mockito.timeout(1)).delete(actual);
        NullPointerException result = assertThrows(NullPointerException.class, () -> dishesService.deleteDishes(actual.getDishesId()));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void getDishesByIdTest(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(80000), BigDecimal.valueOf(50000), 10l, true, expectCategory);
        Dishes actual = new Dishes( "ga tan cao cap", "ngon ngon", "image1", BigDecimal.valueOf(70000), BigDecimal.valueOf(50000), 10l, true, actualCategory);

        Mockito.when(dishesRepository.findDishesById(actual.getDishesId())).thenReturn(expect);
        Dishes result = dishesService.getDishesById(actual.getDishesId());
        Assert.assertEquals(result,expect);
    }

    @Test
    public void getDishesByIdTestFail(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(80000), BigDecimal.valueOf(50000), 10l, true, expectCategory);
        Dishes actual = new Dishes( "ga tan cao cap", "ngon ngon", "image1", BigDecimal.valueOf(70000), BigDecimal.valueOf(50000), 10l, true, actualCategory);

        Mockito.when(dishesRepository.findDishesById(actual.getDishesId())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> dishesService.getDishesById(actual.getDishesId()));
        Assert.assertEquals("",result.getMessage());
    }

    @Test
    public void getDishesExistTest(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(80000), BigDecimal.valueOf(50000), 10l, true, expectCategory);
        Dishes actual = new Dishes( "ga tan cao cap", "ngon ngon", "image1", BigDecimal.valueOf(70000), BigDecimal.valueOf(50000), 10l, true, actualCategory);

        Mockito.when(dishesRepository.findDishesByDishesName(actual.getDishesName())).thenReturn(expect);
        boolean result = dishesService.getDishesExist(actual.getDishesName());
        Assert.assertEquals(true,result);
    }

    @Test
    public void getDishesExistTestFail(){
        Category expectCategory = new Category(1l,"ga");
        Category actualCategory = new Category(2l,"lon");

        Dishes expect = new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(80000), BigDecimal.valueOf(50000), 10l, true, expectCategory);
        Dishes actual = new Dishes( "ga tan cao cap", "ngon ngon", "image1", BigDecimal.valueOf(70000), BigDecimal.valueOf(50000), 10l, true, actualCategory);

        Mockito.when(dishesRepository.findDishesByDishesName(actual.getDishesName())).thenThrow(new NullPointerException(""));
        NullPointerException result = assertThrows(NullPointerException.class, () -> dishesService.getDishesExist(actual.getDishesName()));
        Assert.assertEquals("",result.getMessage());
    }

//    @Test
//    public void listDishesTest(){
//        List<Dishes> expect = new ArrayList<>();
//        expect.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(50000), null, 10l, true, null));
//
//        List<Dishes> actual = new ArrayList<>();
//        actual.add(new Dishes(1l, "ga tan", "ngon ngon", "image1", BigDecimal.valueOf(60000), null, 10l, true, null));
//
//        int pageNum = 1;
//        int pageSize = 1;
//        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
//        Mockito.when(dishesRepository.findAll(pageable)).thenReturn((Page<Dishes>) expect);
//
//        Page<Dishes> result = dishesService.listDishes(pageNum,pageSize);
//
//        Assert.assertEquals(result,(Page<Dishes>) expect);
//    }

//    @Test
//    public void updateDishesTest1() {
//        // Step 1: Prepare data
//        Category category = Category.builder()
//                .categoryName("Test demo")
//                .build();
//        category = categoryRepository.save(category);
//
//        Dishes dishes = Dishes.builder()
//                .dishImage("/str/image.jpg")
//                .costPrice(BigDecimal.valueOf(5000000))
//                .discount(10000L)
//                .description("default description")
//                .dishesName("Name")
//                .category(category)
//                .build();
//        dishes = dishesRepository.save(dishes); // data mau
//        System.out.println(dishes.getDishesId());
//
//        // Step 2: test update field dishImage
//        Dishes updateDishes = dishesRepository.findById(dishes.getDishesId()).orElse(null);
//        Assertions.assertEquals(dishes, updateDishes);
//
//        String updateDishImage = "/1/image.jpg";
//        updateDishes.setDishImage(updateDishImage);
//        updateDishes = dishesService.updateDishes(updateDishes);
//
//        Assertions.assertEquals(updateDishImage, updateDishes.getDishImage());
//
//        //Step 3: Rollback data
//        dishesRepository.deleteById(updateDishes.getDishesId());
//        categoryRepository.delete(category);
//    }

}
