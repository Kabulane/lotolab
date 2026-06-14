package com.lotolab.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class OpenApiConfiguration {

    @Bean
    OpenAPI lotoLabOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("LotoLab API")
                        .version("v1")
                        .description("""
                Statistical analysis and generation of
                Lotto grids based on historical FDJ draws.
                This API does not provide predictions
                or increase winning probabilities.
            """));
    }
}

