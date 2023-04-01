import com.food.dto.StockDto;
import com.food.model.Stock;
import com.food.repository.StockRepository;
import com.food.service.impl.StockServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
public class StockServiceTest {

    @Mock
    private StockRepository repository;

    @InjectMocks
    private StockServiceImpl service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getStockAllTest(){
        List<Stock> list=new ArrayList<>();
        list.add(Stock.builder().stockId(UUID.randomUUID()).foodId(UUID.randomUUID()).count(10.0).price(BigDecimal.ZERO).description("stock test").build());
        Mockito.when(repository.findAll()).thenReturn(list);
        List<StockDto> dtoList=service.getAll();
        Assertions.assertNotNull(dtoList);
        assertEquals(list.size(),1);
        assertEquals(list.get(0).getDescription(),"stock test");
        log.info("Test GetAll Stock");
    }

    @Test
    void createStockTest(){
        UUID uuid=UUID.randomUUID();
        StockDto dto=StockDto.builder().stockId(UUID.randomUUID()).foodId(uuid).count(10.0).price(BigDecimal.ZERO).description("stock test").build();
        Stock category=Stock.builder().stockId(UUID.randomUUID()).foodId(uuid).count(10.0).price(BigDecimal.ZERO).description("stock test").build();
        Mockito.when(repository.save(Mockito.any())).thenReturn(category);
        StockDto response=service.create(dto);
        Assertions.assertNotNull(response);
        assertEquals(response.getDescription(),dto.getDescription());
        log.info("Test Create Stock");
    }

    @Test
    void updateStockTest(){
        UUID uuid=UUID.randomUUID();
        StockDto dto=StockDto.builder().stockId(uuid).foodId(uuid).count(5.0).price(BigDecimal.ZERO).description("stock test").build();
        Optional<Stock> account=Optional.of(Stock.builder().stockId(uuid).foodId(uuid).count(6.0).price(BigDecimal.ONE).description("stock test1").build());

        Mockito.when(repository.findById(uuid)).thenReturn(account);
        Mockito.when(repository.save(Mockito.any())).thenReturn(Stock.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).count(dto.getCount()).price(dto.getPrice()).description(dto.getDescription()).build());

        StockDto response=service.update(uuid,dto);
        Assertions.assertNotNull(response);
        assertEquals(response.getFoodId(),dto.getFoodId());
        assertEquals(response.getDescription(),dto.getDescription());
        log.info("Test Update Stock");

    }

    @Test
    void deleteExistStockTest(){
        UUID uuid=UUID.randomUUID();
        Stock category=Stock.builder().stockId(uuid).foodId(uuid).count(2.0).price(BigDecimal.ZERO).description("stock test").build();

        when(repository.findById(uuid)).thenReturn(Optional.of(category));
        StockDto response=service.delete(uuid);
        verify(repository,times(1)).delete(category);
        assertNotNull(response);
        assertEquals(uuid,response.getStockId());
        assertEquals(category.getDescription(),response.getDescription());
        log.info("Test Delete Stock");

    }

    @Test
    void deleteNonExistStockTest() {
        final UUID uuid = UUID.randomUUID();
        when(repository.findById(uuid)).thenReturn(Optional.empty());

        StockDto response = service.delete(uuid);
        verify(repository, times(0)).delete(any(Stock.class));
        assertNull(response);
    }


}
