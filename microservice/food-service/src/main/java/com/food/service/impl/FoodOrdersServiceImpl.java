package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.dto.StockDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Orders;
import com.food.repository.FoodRepository;
import com.food.repository.OrdersRepository;
import com.food.service.FoodFileService;
import com.food.service.FoodOrdersService;
import com.food.service.grpc.PaymentGrpcService;
import com.food.service.grpc.StockGrpcService;
import com.food.utils.JsonUtil;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class FoodOrdersServiceImpl implements FoodOrdersService {
    private static final String STOCK_URL = "http://127.0.0.1:8087/api";
    private final OrdersRepository repository;
    private final FoodRepository foodRepository;
    private final FoodFileService fileService;
    private final RestTemplate restTemplate;
    private final StockGrpcService stockGrpcService;
    private final PaymentGrpcService paymentGrpcService;

    public FoodOrdersServiceImpl(OrdersRepository repository, FoodRepository foodRepository, FoodFileService fileService, RestTemplate restTemplate, StockGrpcService stockGrpcService, PaymentGrpcService paymentGrpcService) {
        this.repository = repository;
        this.foodRepository = foodRepository;
        this.fileService = fileService;
        this.restTemplate = restTemplate;
        this.stockGrpcService = stockGrpcService;
        this.paymentGrpcService = paymentGrpcService;
    }

    @Override
    public Orders getByOne(Long id) throws GeneralException, GeneralWarning {
        var model = repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
        return model;
    }

    @Override
    public LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        LoadResult loadResult = new LoadResult();
        var result = repository.load(loadOptions);

        if (result.getTotalCount() > 0) {
            var list = JsonUtil.fromJsonList(result.getItems(), Orders.class);

            list.stream().map(val -> {
                InputStream stream = fileService.getObjects(val.getFood().getId().toString());
                val.setImage(stream != null ? IOUtils.toString(stream, StandardCharsets.UTF_8) : null);
                return val;
            }).toList();

            loadResult.setItems(list);
            loadResult.setTotalCount(list.stream().count());
        }

        log.info("list orders {} ", result.getTotalCount());
        return loadResult;
    }

    @Override
    public Orders create(Orders dto) throws GeneralException, GeneralWarning {
        var price = repository.getSumPrice(dto.getFood().getId(), dto.getStatus());
        var count = repository.getCountPrice(dto.getFood().getId(), dto.getStatus());
        var stock = stockGrpcService.getStock(dto.getFood().getId().toString());

        if (stock.getStatus() == 400 || count > stock.getAvailableItems())
            throw new GeneralException("Ürüne ait stok kaydı bulunamadı!");

        var payment = paymentGrpcService.getPayment(stock.getStockId());
        if (payment.getStatus() == 400 || payment.getAmountAvailable() < price)
            throw new GeneralException("Ürüne ait ödeme kaydı bulunamadı!");

        var food = foodRepository.findById(dto.getFood().getId()).orElseThrow(() -> new RuntimeException("Not Found!"));
        dto.setFood(food);
        dto.setStockId(Long.parseLong(stock.getStockId()));
        dto.setPaymentId(Long.parseLong(payment.getPaymentId()));
        dto.setCreateDate(new Date());
        var model = repository.save(dto);
        return model;
    }

    @Override
    public Orders delete(Long id) throws GeneralException, GeneralWarning {
        var order = getByOne(id);
        repository.deleteById(id);
        return order;
    }

    //TODO
    private StockDto getStockStatus(UUID foodId) throws GeneralException, GeneralWarning {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<StockDto[]> response = restTemplate.exchange(STOCK_URL + "/stocks/byFood/" + foodId, HttpMethod.GET, entity, StockDto[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().length > 0 ? StockDto.builder().availableItems(response.getBody()[0].getAvailableItems()).stockId(response.getBody()[0].getStockId()).build() : null;
        }

//        DataSourceLoadOptions<StockDto> loadOptions = new DataSourceLoadOptions<>();
//        loadOptions.setMandatoryFilter(Filter.build(StockDto.class).operation(FilterOperator.equal).with(w -> w.setFoodId(foodId)).get());
//        HttpEntity<DataSourceLoadOptions> request = new HttpEntity<>(loadOptions);

        return null;
    }
}
