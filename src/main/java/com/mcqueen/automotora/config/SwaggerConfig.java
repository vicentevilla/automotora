package com.mcqueen.automotora.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API Automotora - Vehículos")
                .version("1.0")
                .description("Documentación de la API para el sistema de gestión de vehículos"))
            .servers(List.of(new Server().url("/")));
    }
}
