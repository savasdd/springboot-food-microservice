import com.food.dto.FoodDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Food;
import com.food.repository.FoodRepository;
import com.food.service.impl.FoodServiceImpl;
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
public class FoodServiceTest {

    @Mock
    private FoodRepository repository;

    @InjectMocks
    private FoodServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTest() throws GeneralException, GeneralWarning {
        List<Food> foodList = new ArrayList<>();
        foodList.add(Food.builder().id(UUID.randomUUID()).foodName("Pizza").description("İtalyan Pizza").category(null).build());
        Mockito.when(repository.findAll()).thenReturn(foodList);
        List<FoodDto> foodDtoList = service.getAll();
        Assertions.assertNotNull(foodDtoList);
        Assertions.assertEquals(foodDtoList.size(), 1);
        Assertions.assertEquals(foodDtoList.get(0).getFoodName(), "Pizza");
        log.info("Test GetAll Food");
    }

    @Test
    void createTest() throws GeneralException, GeneralWarning {
        Food foodDto = new Food();
        foodDto.setFoodName("Pizza");
        foodDto.setCategory(null);
        foodDto.setDescription("Italian pizza");
        Food food = Food.builder().foodId(UUID.randomUUID()).foodName(foodDto.getFoodName()).category(null).description(foodDto.getDescription()).build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(food);
        Food foodDtoResponse = service.create(foodDto);
        Assertions.assertNotNull(foodDtoResponse);
        Assertions.assertEquals(foodDtoResponse.getFoodName(), foodDto.getFoodName());
        log.info("Test Create Food");
    }

    @Test
    void updateTest() throws GeneralException, GeneralWarning {
        UUID id = UUID.randomUUID();
        Food foodDto = new Food();
        foodDto.setFoodName("Pizza");
        foodDto.setCategory(null);
        foodDto.setDescription("Italian pizza");
        Optional<Food> optionalFood = Optional.of(Food.builder().foodId(id).foodName("Burger").category(null).description("American burger").build());
        Mockito.when(repository.findById(id)).thenReturn(optionalFood);
        Mockito.when(repository.save(Mockito.any())).thenReturn(Food.builder().foodId(id).foodName(foodDto.getFoodName()).category(null).description(foodDto.getDescription()).build());
        FoodDto foodDtoResponse = service.update(id.toString(), foodDto);
        Assertions.assertNotNull(foodDtoResponse);
        Assertions.assertEquals(foodDtoResponse.getFoodName(), foodDto.getFoodName());
        log.info("Test Update Food");
    }

    @Test
    void testDeleteExistingFood() throws GeneralException, GeneralWarning {
        final UUID FOOD_ID = UUID.randomUUID();
        Food food = Food.builder()
                .id(FOOD_ID)
                .foodName("Pizza")
                .category(null)
                .description("A delicious pizza")
                .build();
        when(repository.findById(FOOD_ID)).thenReturn(Optional.of(food));

        Food deletedFoodDto = service.delete(FOOD_ID.toString());
        verify(repository, times(1)).delete(food);
        assertNotNull(deletedFoodDto);
        assertEquals(FOOD_ID, deletedFoodDto.getFoodId());
        assertEquals(food.getFoodName(), deletedFoodDto.getFoodName());
        assertEquals(food.getDescription(), deletedFoodDto.getDescription());
        log.info("Test Delete Food");
    }

    @Test
    void testDeleteNonExistingFood() throws GeneralException, GeneralWarning {
        final UUID FOOD_ID = UUID.randomUUID();
        when(repository.findById(FOOD_ID)).thenReturn(Optional.empty());

        Food deletedFoodDto = service.delete(FOOD_ID.toString());
        verify(repository, times(0)).delete(any(Food.class));
        assertNull(deletedFoodDto);
    }


}
