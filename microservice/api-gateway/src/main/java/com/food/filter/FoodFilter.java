package com.food.filter;

import com.food.service.jwt.JwtService;
import org.springframework.stereotype.Component;

@Component
public class FoodFilter extends FilterFactory {

    public FoodFilter(JwtService service) {
        super(service, "FOOD");
    }
}