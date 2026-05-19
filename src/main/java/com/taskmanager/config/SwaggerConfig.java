package com.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI taskManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management REST API")
                        .description("A complete production-style REST API for managing user tasks, featuring pagination, filtering, global validation, and layered architecture.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@taskmanager.com")));
    }
}
