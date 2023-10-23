package com.food.service.impl;

import com.food.dto.PaymentDto;
import com.food.dto.StockDto;
import com.food.model.Orders;
import com.food.repository.FoodRepository;
import com.food.repository.OrdersRepository;
import com.food.service.FoodFileService;
import com.food.service.FoodOrdersService;
import com.food.spesification.Filter;
import com.food.spesification.enums.FilterOperator;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class FoodOrdersServiceImpl implements FoodOrdersService {
    private static final String STOCK_URL = "http://127.0.0.1:8087/api";
    private static final String PAYMENT_URL = "http://127.0.0.1:8088/api";
    private final OrdersRepository repository;
    private final FoodRepository foodRepository;
    private final FoodFileService fileService;
    private final RestTemplate restTemplate;

    public FoodOrdersServiceImpl(OrdersRepository repository, FoodRepository foodRepository, FoodFileService fileService, RestTemplate restTemplate) {
        this.repository = repository;
        this.foodRepository = foodRepository;
        this.fileService = fileService;
        this.restTemplate = restTemplate;
    }

    @Override
    public Orders getByOne(String id) {
        var model = repository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Not Found!"));
        return model;
    }

    @Override
    public LoadResult<Orders> getAll(DataSourceLoadOptions<Orders> loadOptions) {
        LoadResult<Orders> loadResult = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());

        list.stream().map(val -> {
            InputStream stream = fileService.getObjects(val.getFood().getFoodId().toString());
            val.setImage(stream != null ? IOUtils.toString(stream, StandardCharsets.UTF_8) : null);
            return val;
        }).toList();

        loadResult.setData(list.getContent());
        loadResult.setTotalCount(list.stream().count());
        log.info("list orders {} ", loadResult.totalCount);
        return loadResult;
    }

    //TODO control stock and payment
    @Override
    public Orders create(Orders dto) {
        var price = repository.getSumPrice(dto.getFood().getFoodId(), dto.getStatus());
        var count = repository.getCountPrice(dto.getFood().getFoodId(), dto.getStatus());
        var stock = getStockStatus(dto.getFood().getFoodId());

        if (stock != null && count < stock.getAvailableItems()) {
            var payment = getPaymentStatus(stock.getStockId());

            if (payment.doubleValue() > price) {
                var food = foodRepository.findById(dto.getFood().getFoodId()).orElseThrow(() -> new RuntimeException("Not Found!"));
                dto.setFood(food);
                dto.setCreateDate(new Date());
                var model = repository.save(dto);
                return model;
            }
        }

        return null;
    }

    private StockDto getStockStatus(UUID foodId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<StockDto[]> response = restTemplate.exchange(STOCK_URL + "/stocks/byFood/" + foodId, HttpMethod.GET, entity, StockDto[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().length > 0 ? StockDto.builder().availableItems(response.getBody()[0].getAvailableItems()).stockId(response.getBody()[0].getStockId()).build() : null;
        }

        DataSourceLoadOptions<StockDto> loadOptions = new DataSourceLoadOptions<>();
        loadOptions.setMandatoryFilter(Filter.build(StockDto.class).operation(FilterOperator.equal).with(w -> w.setFoodId(foodId)).get());
        HttpEntity<DataSourceLoadOptions> request = new HttpEntity<>(loadOptions);

        return null;
    }

    private BigDecimal getPaymentStatus(UUID stockId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<PaymentDto[]> response = restTemplate.exchange(PAYMENT_URL + "/payments/byStock/" + stockId, HttpMethod.GET, entity, PaymentDto[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().length > 0 ? response.getBody()[0].getAmountAvailable() : BigDecimal.ZERO;
        }
        return BigDecimal.ZERO;
    }

    @Override
    public Orders delete(String id) {
        var order = getByOne(id);
        repository.deleteById(UUID.fromString(id));
        return order;
    }
}
