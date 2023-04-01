import com.food.dto.FoodDto;
import com.food.model.Food;
import com.food.repository.FoodRepository;
import com.food.service.impl.FoodServiceImpl;
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
    void getAllTest() {
        List<Food> foodList = new ArrayList<>();
        foodList.add(Food.builder().foodId(UUID.randomUUID()).foodName("Pizza").description("İtalyan Pizza").foodCategoryId(null).build());
        Mockito.when(repository.findAll()).thenReturn(foodList);
        List<FoodDto> foodDtoList = service.getAll();
        Assertions.assertNotNull(foodDtoList);
        Assertions.assertEquals(foodDtoList.size(), 1);
        Assertions.assertEquals(foodDtoList.get(0).getFoodName(), "Pizza");
    }

    @Test
    void createTest() {
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodName("Pizza");
        foodDto.setFoodCategoryId(UUID.randomUUID().toString());
        foodDto.setDescription("Italian pizza");
        Food food=Food.builder().foodId(UUID.randomUUID()).foodName(foodDto.getFoodName()).foodCategoryId(foodDto.getFoodCategoryId()).description(foodDto.getDescription()).build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(food);
        FoodDto foodDtoResponse = service.create(foodDto);
        Assertions.assertNotNull(foodDtoResponse);
        Assertions.assertEquals(foodDtoResponse.getFoodName(), foodDto.getFoodName());
    }

    @Test
    void updateTest() {
        UUID id = UUID.randomUUID();
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodName("Pizza");
        foodDto.setFoodCategoryId(UUID.randomUUID().toString());
        foodDto.setDescription("Italian pizza");
        Optional<Food> optionalFood = Optional.of(Food.builder().foodId(id).foodName("Burger").foodCategoryId(UUID.randomUUID().toString()).description("American burger").build());
        Mockito.when(repository.findById(id)).thenReturn(optionalFood);
        Mockito.when(repository.save(Mockito.any())).thenReturn(Food.builder().foodId(id).foodName(foodDto.getFoodName()).foodCategoryId(foodDto.getFoodCategoryId()).description(foodDto.getDescription()).build());
        FoodDto foodDtoResponse = service.update(id, foodDto);
        Assertions.assertNotNull(foodDtoResponse);
        Assertions.assertEquals(foodDtoResponse.getFoodName(), foodDto.getFoodName());
    }

    @Test
    void testDeleteExistingFood() {
        final UUID FOOD_ID = UUID.randomUUID();
        Food food = Food.builder()
                .foodId(FOOD_ID)
                .foodName("Pizza")
                .foodCategoryId(UUID.randomUUID().toString())
                .description("A delicious pizza")
                .build();
        when(repository.findById(FOOD_ID)).thenReturn(Optional.of(food));

        FoodDto deletedFoodDto = service.delete(FOOD_ID);
        verify(repository, times(1)).delete(food);
        assertNotNull(deletedFoodDto);
        assertEquals(FOOD_ID, deletedFoodDto.getFoodId());
        assertEquals(food.getFoodName(), deletedFoodDto.getFoodName());
        assertEquals(food.getFoodCategoryId(), deletedFoodDto.getFoodCategoryId());
        assertEquals(food.getDescription(), deletedFoodDto.getDescription());
    }

    @Test
    void testDeleteNonExistingFood() {
        final UUID FOOD_ID = UUID.randomUUID();
        when(repository.findById(FOOD_ID)).thenReturn(Optional.empty());

        FoodDto deletedFoodDto = service.delete(FOOD_ID);
        verify(repository, times(0)).delete(any(Food.class));
        assertNull(deletedFoodDto);
    }


}
