package br.com.henrique.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}
    @Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
				.routes()
				.route(r -> r.path("/cliente/**", "/cliente-service/v3/api-docs").uri("lb://cliente-service"))
				.route(r -> r.path("/cartao/**","/cartao-service/v3/api-docs").uri("lb://cartao-service"))
				.route(r -> r.path("/avaliacao-credito/**","/avaliador-credito-service/v3/api-docs").uri("lb://avaliador-credito-service"))
				.build();
	}
 
}
