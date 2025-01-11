package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${spring.web.cors.allowed-origins}")
    private String websiteUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:uploads/images/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                    websiteUrl,
                    "http://localhost:5173",
                    "https://vedantselectrosoft-demo.vercel.app/"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
