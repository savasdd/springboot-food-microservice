package com.food.filter;

import com.food.service.jwt.JwtService;
import org.springframework.stereotype.Component;

@Component
public class UserFilter extends FilterFactory {

    public UserFilter(JwtService service) {
        super(service, "USER");
    }


}