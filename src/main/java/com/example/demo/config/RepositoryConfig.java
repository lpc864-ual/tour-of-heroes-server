package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.example.demo.entity.Hero;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
    
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        // Deshabilitar TODOS los métodos HTTP para la entidad Hero
        HttpMethod[] allMethods = {
            HttpMethod.GET, 
            HttpMethod.POST, 
            HttpMethod.PUT, 
            HttpMethod.PATCH, 
            HttpMethod.DELETE
        };
        
        config.getExposureConfiguration()
            .forDomainType(Hero.class)
            .withItemExposure((metadata, methods) -> methods.disable(allMethods))
            .withCollectionExposure((metadata, methods) -> methods.disable(allMethods));
    }
}