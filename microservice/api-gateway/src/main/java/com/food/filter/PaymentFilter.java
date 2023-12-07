package com.food.filter;

import com.food.service.jwt.JwtService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFilter extends FilterFactory {

    public PaymentFilter(JwtService service) {
        super(service, "PAYMENT");
    }


}