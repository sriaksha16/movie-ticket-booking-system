package com.example.movieticketbookingsystem.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	  @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        // Serve files from the uploads directory
	        registry.addResourceHandler("/uploads/**")
//	                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
	        .addResourceLocations("file:uploads/");
	    }
}
