package com.proyecto.usuarios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI 3 / Swagger para documentar y probar interactivamente la API REST.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI usuariosOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST para la Administración de Usuarios")
                        .description("API REST desarrollada con Spring Boot 3 y Spring Data JPA bajo restricciones de Bean Validation, manejo global de excepciones y consultas dinámicas con Specification.\n\n" +
                                "**Carrera:** Desarrollo de Software Multiplataforma  \n" +
                                "**Alumno:** Gerardo Manzano Villafaña  \n" +
                                "**Maestro:** Francisco Hasani Barrera  \n" +
                                "**Grupo:** GIDS6102")
                        .version("1.0.0")
                        .contact(new Contact()
                                .email("gerardo8.manzano@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
