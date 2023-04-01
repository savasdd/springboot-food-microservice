import com.food.dto.CategoryDto;
import com.food.model.Category;
import com.food.repository.CategoryRepository;
import com.food.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryServiceImpl service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCategoryAllTest(){
        List<Category> list=new ArrayList<>();
        list.add(Category.builder().categoryId(UUID.randomUUID()).categoryName("category").description("category test").build());
        Mockito.when(repository.findAll()).thenReturn(list);
        List<CategoryDto> dtoList=service.getAll();
        Assertions.assertNotNull(dtoList);
        assertEquals(list.size(),1);
        assertEquals(list.get(0).getCategoryName(),"category");
        log.info("Test GetAll Category");
    }

    @Test
    void createCategoryTest(){
        CategoryDto dto=CategoryDto.builder().categoryId(UUID.randomUUID()).categoryName("category").description("category test").build();
        Category category=Category.builder().categoryId(UUID.randomUUID()).categoryName("category").description("category test").build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(category);
        CategoryDto response=service.create(dto);
        Assertions.assertNotNull(response);
        assertEquals(response.getDescription(),dto.getDescription());
        log.info("Test Create Category");
    }

    @Test
    void updateCategoryTest(){
        UUID uuid=UUID.randomUUID();
        CategoryDto dto=CategoryDto.builder().categoryId(uuid).categoryName("category").description("category test").build();
        Optional<Category> account=Optional.of(Category.builder().categoryId(uuid).categoryName("category1").description("category test1").build());

        Mockito.when(repository.findById(uuid)).thenReturn(account);
        Mockito.when(repository.save(Mockito.any())).thenReturn(Category.builder().categoryId(dto.getCategoryId()).categoryName(dto.getCategoryName()).description(dto.getDescription()).build());

        CategoryDto response=service.update(uuid,dto);
        Assertions.assertNotNull(response);
        assertEquals(response.getCategoryName(),dto.getCategoryName());
        log.info("Test Update Category");

    }

    @Test
    void deleteExistCategoryTest(){
        UUID uuid=UUID.randomUUID();
        Category category=Category.builder().categoryId(uuid).categoryName("category").description("category test").build();

        when(repository.findById(uuid)).thenReturn(Optional.of(category));
        CategoryDto response=service.delete(uuid);
        verify(repository,times(1)).delete(category);
        assertNotNull(response);
        assertEquals(uuid,response.getCategoryId());
        assertEquals(category.getCategoryName(),response.getCategoryName());
        log.info("Test Delete Category");

    }

    @Test
    void deleteNonExistCategoryTest() {
        final UUID uuid = UUID.randomUUID();
        when(repository.findById(uuid)).thenReturn(Optional.empty());

        CategoryDto response = service.delete(uuid);
        verify(repository, times(0)).delete(any(Category.class));
        assertNull(response);
    }


}
