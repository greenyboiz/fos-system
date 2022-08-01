package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Category;
import fpt.edu.capstone.repo.CategoryRepository;
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
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void addCategoryTest(){
        Category expect = new Category(1l,"ga");
        Category actual = new Category(2l,"lon");

        Mockito.when(categoryRepository.save(actual)).thenReturn(expect);
        Category result = categoryService.addCategory(actual);
        Assert.assertEquals(result,expect);
    }

    @Test
    public void updateCategoryTest(){
        Category expect = new Category(1l,"ga");
        Category actual = new Category(1l,"lon");
        Mockito.when(categoryRepository.findByCategoryId(actual.getCategoryId())).thenReturn(expect);
        Mockito.when(categoryRepository.save(actual)).thenReturn(expect);
        Category result = categoryService.updateCategory(actual);
        Assert.assertEquals(result,expect);
    }

    @Test
    public void deleteCategoryTest(){
        Category expect = new Category(1l,"ga");
        Category actual = new Category(1l,"lon");

        Mockito.when(categoryRepository.findByCategoryId(actual.getCategoryId())).thenReturn(expect);
        categoryRepository.delete(actual);

        Mockito.verify(categoryRepository,Mockito.timeout(1)).delete(actual);
        boolean result = categoryService.deleteCategory(actual.getCategoryId());
        Assert.assertEquals(result,true);
    }

    @Test
    public void getAllCategoryTest(){
        List<Category> expect = new ArrayList<>();
        expect.add(new Category(1l,"ga"));
        List<Category> actual = new ArrayList<>();
        actual.add(new Category(1l,"lon")) ;

        Mockito.when(categoryRepository.findAll()).thenReturn(expect);
        List<Category> results = categoryService.getAllCategory();
        Assert.assertEquals(results,expect);
    }

    @Test
    public void getCategoryByIdTest(){
        Category expect = new Category(1l,"ga");
        Category actual = new Category(1l,"lon");
        Mockito.when(categoryRepository.findByCategoryId(actual.getCategoryId())).thenReturn(expect);
        Category result = categoryService.getCategoryById(actual.getCategoryId());
        Assert.assertEquals(result,expect);
    }

    @Test
    public void checkCategoryExistTest(){
        Category expect = new Category(1l,"ga");
        Category actual = new Category(1l,"lon");
        Mockito.when(categoryRepository.findByCategoryName(actual.getCategoryName())).thenReturn(expect);
        boolean result = categoryService.checkCategoryExist(actual.getCategoryName());
        Assert.assertEquals(result,true);
    }
}
