package com.food.filter;

import com.food.enums.ERole;
import com.food.service.jwt.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockFilter extends AbstractGatewayFilterFactory {

    private final JwtService service;

    public StockFilter(JwtService service) {
        this.service = service;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            String path = request.getPath().toString();
            var authorization = request.getHeaders().containsKey("Authorization") ? request.getHeaders().get("Authorization").toString() : null;
            var token = authorization.substring(7, authorization.length());
            var user = service.getRoles(token);


            if (StringUtils.contains(path, "/all") && validate(user.getRoles(), ERole.SEARCH.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/save") && validate(user.getRoles(), ERole.CREATE.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/update") && validate(user.getRoles(), ERole.UPDATE.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/delete") && validate(user.getRoles(), ERole.DELETE.name()))
                return chain.filter(exchange);


            var response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        };

    }


    private Boolean validate(List<SimpleGrantedAuthority> roles, String contain) {
        return roles.stream().anyMatch(str -> str.getAuthority().contains("STOCK_" + contain));
    }


}