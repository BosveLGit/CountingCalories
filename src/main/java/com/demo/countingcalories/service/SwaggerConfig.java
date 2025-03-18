package com.demo.countingcalories.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API \"Счетчик калорий\"",
                version = "1.0.0",
                description = "API сервиса для отслеживания дневной нормы калорий."
        )
)
public class SwaggerConfig {
}
