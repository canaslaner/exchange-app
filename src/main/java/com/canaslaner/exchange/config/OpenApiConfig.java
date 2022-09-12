package com.canaslaner.exchange.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exchange Rate Main Application")
                        .description("APIs for Application")
                        .version("v1.0.0")
                        .license(new License().name("MIT License").url("https://api.github.com/licenses/mit")));
    }
}
