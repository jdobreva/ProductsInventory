package com.adesso.products.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.adesso.products.converters.ProductConverter;
import com.adesso.products.converters.ProductDtoConverter;
import com.adesso.products.converters.UserDetailsConverter;

@Configuration
public class ProductsApiConfiguration implements WebMvcConfigurer {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new UserDetailsConverter());
		registry.addConverter(new ProductDtoConverter());
		registry.addConverter(new ProductConverter());
	}

}
