package com.food.filter;

import com.food.enums.ERole;
import com.food.service.jwt.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public abstract class FilterFactory extends AbstractGatewayFilterFactory {

    private String base;
    private JwtService service;

    public FilterFactory(JwtService service, String base) {
        this.service = service;
        this.base = base;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();
            String path = request.getPath().toString();
            var authorization = request.getHeaders().containsKey("Authorization") ? request.getHeaders().get("Authorization").toString() : null;
            var token = authorization.substring(7, authorization.length());
            var user = service.getAuthority(token);

            if ((StringUtils.contains(path, "/all") ||
                    StringUtils.contains(path, "/getAll") ||
                    StringUtils.contains(path, "/getOne") ||
                    StringUtils.contains(path, "/custom")) &&
                    validate(user.getRoles(), base, ERole.SEARCH.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/save") && validate(user.getRoles(), base, ERole.CREATE.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/update") && validate(user.getRoles(), base, ERole.UPDATE.name()))
                return chain.filter(exchange);
            else if (StringUtils.contains(path, "/delete") && validate(user.getRoles(), base, ERole.DELETE.name()))
                return chain.filter(exchange);

            var response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        };

    }

    private Boolean validate(List<SimpleGrantedAuthority> roles, String base, String contain) {
        return roles.stream().anyMatch(str -> str.getAuthority().contains(base + "_" + contain));
    }

}
