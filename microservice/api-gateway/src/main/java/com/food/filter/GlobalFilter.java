package com.food.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.dto.AuthorityDto;
import com.food.enums.ELogType;
import com.food.event.LogEvent;
import com.food.service.LogService;
import com.food.service.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
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
public class GlobalFilter implements WebFilter {

    private final JwtService jwtService;
    private final LogService logService;

    public GlobalFilter(JwtService jwtService, LogService logService) {
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

    private ServerHttpResponseDecorator getDecoratedResponse(String path, ServerHttpResponse response, ServerHttpRequest request, DataBufferFactory dataBufferFactory) {
        return new ServerHttpResponseDecorator(response) {

            @Override
            public Mono<Void> writeWith(final Publisher<? extends DataBuffer> body) {

                if (body instanceof Flux) {

                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

                    return super.writeWith(fluxBody.buffer().handle((dataBuffers, sink) -> {

                        String authorization = request.getHeaders().containsKey("Authorization") ? request.getHeaders().get("Authorization").toString() : null;
                        String token = authorization != null ? authorization.substring(7, authorization.length()) : null;
                        AuthorityDto user = jwtService.getRoles(token);

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
                                .logType(ELogType.FOOD)
                                .body(List.of(responseBody))
                                .build();
                        logService.eventLog(event);


                        try {
                            if (request.getURI().getPath().equals("/first") && request.getMethodValue().equals("GET")) {
                                List<Object> student = new ObjectMapper().readValue(responseBody, List.class);
                                System.out.println("student:" + student);
                            } else if (request.getURI().getPath().equals("/second") && request.getMethodValue().equals("GET")) {
                                List<Object> companies = new ObjectMapper().readValue(responseBody, List.class);
                                System.out.println("companies:" + companies);
                            }
                        } catch (JsonProcessingException e) {
                            sink.error(new RuntimeException(e));
                            return;
                        }
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

}