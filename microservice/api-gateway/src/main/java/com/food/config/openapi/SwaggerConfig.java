package com.food.config.openapi;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

//    @Bean
//    public CommandLineRunner openApiGroups(
//            RouteDefinitionLocator locator,
//            SwaggerUiConfigParameters swaggerUiParameters) {
//
//        return args -> Objects.requireNonNull(locator
//                        .getRouteDefinitions().collectList().block())
//                .stream()
//                .map(RouteDefinition::getId)
//                .filter(id -> id.matches(".*-service"))
//                .map(id -> id.replace("-service", ""))
//                .forEach(swaggerUiParameters::addGroup);
//    }


}