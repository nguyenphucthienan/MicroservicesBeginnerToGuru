package com.nguyenphucthienan.msscbrewerygateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

// @Profile("google")
// @Configuration
public class GoogleConfig {

    @Bean
    public RouteLocator googleRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(route -> route.path("/webhp")
                        .filters(filter -> filter.rewritePath("/webhp(?<segment>/?.*)", "/${segment}"))
                        .uri("https://google.com")
                        .id("google"))
                .build();
    }
}
