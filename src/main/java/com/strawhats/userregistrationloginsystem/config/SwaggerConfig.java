package com.strawhats.userregistrationloginsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("User Registration & Login System")
                                .description("A secure user registration and login system built with Spring Boot, featuring authentication, password encryption, and session management for seamless user access control.")
                                .version("1.0")
                );
    }
}
