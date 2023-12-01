package com.food.filter;

import com.food.dto.AuthorityDto;
import com.food.enums.ELogType;
import com.food.event.LogEvent;
import com.food.service.LogService;
import com.food.service.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class GlobalsFilter implements GlobalFilter, WebFilter, Ordered {

    private final JwtService jwtService;
    private final LogService logService;
    private static String serviceId = null;

    public GlobalsFilter(JwtService jwtService, LogService logService) {
        this.jwtService = jwtService;
        this.logService = logService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = getDecoratedResponse(path, response, request, dataBufferFactory);
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        serviceId = route != null ? route.getId() : null;
        return chain.filter(exchange.mutate().build());
    }


    private ServerHttpResponseDecorator getDecoratedResponse(String path, ServerHttpResponse response, ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
        return new ServerHttpResponseDecorator(response) {

            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux) {

                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody.buffer().handle((dataBuffers, sink) -> {

                        String authorization = request.getHeaders().containsKey("Authorization") ? request.getHeaders().get("Authorization").toString() : null;
                        String token = authorization != null ? authorization.substring(7, authorization.length()) : null;
                        AuthorityDto user = jwtService.getAuthority(token);

                        DefaultDataBuffer joinedBuffers = new DefaultDataBufferFactory().join(dataBuffers);
                        byte[] content = new byte[joinedBuffers.readableByteCount()];
                        joinedBuffers.read(content);
                        String responseBody = new String(content, StandardCharsets.UTF_8);//MODIFY RESPONSE and Return the Modified response

                        LogEvent event = LogEvent.builder()
                                .username(user.getPreferred_username())
                                .requestId(request.getId())
                                .method(request.getMethodValue())
                                .url(request.getURI().toString())
                                .path(path)
                                .status(200)
                                .body(List.of(responseBody))
                                .build();

                        switch (serviceId) {
                            case "foods":
                                event.setLogType(ELogType.FOOD);
                                break;
                            case "users":
                                event.setLogType(ELogType.USER);
                                break;
                            case "stocks":
                                event.setLogType(ELogType.STOCK);
                                break;
                            case "payments":
                                event.setLogType(ELogType.PAYMENT);
                                break;
                        }
                        logService.eventLog(event);

                        sink.next(dataBufferFactory.wrap(responseBody.getBytes()));
                    })).onErrorResume(err -> {

                        System.out.println("error while decorating Response: {}" + err.getMessage());
                        return Mono.empty();
                    });

                }
                return super.writeWith(body);
            }
        };
    }

    @Override
    public int getOrder() {
        return 0;
    }


}