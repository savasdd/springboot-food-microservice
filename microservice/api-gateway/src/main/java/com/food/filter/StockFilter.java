package com.food.filter;

import com.food.service.jwt.JwtService;
import org.springframework.stereotype.Component;

@Component
public class StockFilter extends FilterFactory {

    public StockFilter(JwtService service) {
        super(service, "STOCK");
    }


}