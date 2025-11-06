package com.example.petcare.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //Marca a classe como um arquivo de configuração do Spring

//Marca a classe como um arquivo de configuração do Spring
public class CorsConfig implements WebMvcConfigurer {

    //Metodo que configura as regras de CORS
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica as regras CORS a TODAS as rotas que começam com /api/
                .allowedOrigins("*") //Permite requisições de QUALQUER origem/dominio * = curinga
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Location")
                .allowCredentials(false)
                .maxAge(3600);
    }
}

