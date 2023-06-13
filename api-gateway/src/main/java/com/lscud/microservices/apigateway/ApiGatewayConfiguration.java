package com.lscud.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p ->p.path("/get")
                        .filters(f ->f
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyValue"))
                        .uri("http://httpbin.org:80"))
                //se a requisição surgir com /currency-exchange quero que redirecione para naming-server e faça loadbalance(lb://nome_do_serviço_resgitrado_no_eureka_server)
                .route(p ->p.path("/currency-exchange/**").uri("lb://currency-exchange"))
                .route(p ->p.path("/currency-conversion/**").uri("lb://currency-conversion"))
                .route(p ->p.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
                //Quero copiar o que vem depois do ...new/ Aí utilizom(?<segment>.*) e colar no ...feign/ Aí para colar uso ${segment}
                .route(p ->p.path("/currency-conversion-new/**").filters(f ->f.rewritePath("/currency-conversion-new/(?<segment>.*)", "/currency-conversion-feign/${segment}")).uri("lb://currency-conversion"))
                .build();
    }
}
