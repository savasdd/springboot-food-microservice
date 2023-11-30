package com.food.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RoleFilter extends AbstractGatewayFilterFactory {

    private static final String[] roles = {"FOOD_SEARCH", "FOOD_DELETE", "FOOD_CREAT", "FOOD_UPDATE"};

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();

            if (StringUtils.contains(path, "/all") && validate(roles, ERole.SEARCH.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/save") && validate(roles, ERole.CREATE.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/update") && validate(roles, ERole.UPDATE.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/delete") && validate(roles, ERole.DELETE.name()))
                return chain.filter(exchange);


            var response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        };

    }


    private Boolean validate(String[] roles, String contain) {
        return Arrays.stream(roles).anyMatch(str -> str.contains("FOOD_" + contain));
    }

    public enum ERole {
        SEARCH,
        CREATE,
        UPDATE,
        DELETE
    }

}