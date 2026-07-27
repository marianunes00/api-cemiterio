package br.com.ifsertao.apicemiterio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// Classe responsável por configurar o CORS da aplicação,
// permitindo que a interface (front-end) faça requisições HTTP
// para a API Spring Boot, mesmo estando em outra origem (porta ou domínio)
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");

            }

        };

    }

}