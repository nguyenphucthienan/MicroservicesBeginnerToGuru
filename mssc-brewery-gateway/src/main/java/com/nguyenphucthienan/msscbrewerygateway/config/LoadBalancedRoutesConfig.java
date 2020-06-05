package com.nguyenphucthienan.msscbrewerygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutesConfig {

    @Bean
    public RouteLocator loadBalancedRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(route -> route.path("/api/v1/beers*", "/api/v1/beers/*", "/api/v1/beerUpc/*")
                        .uri("lb://beer-service")
                        .id("beer-service"))
                .route(route -> route.path("/api/v1/customers/**")
                        .uri("lb://order-service")
                        .id("order-service"))
                .route(route -> route.path("/api/v1/beers/*/inventory")
                        .uri("lb://inventory-service")
                        .id("inventory-service"))
                .build();
    }
}
