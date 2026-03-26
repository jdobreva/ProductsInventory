package com.adesso.products.converters;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import com.adesso.products.dto.ProductDto;
import com.adesso.products.entity.Product;
import com.adesso.products.entity.ProductAvailability;

public class ProductDtoConverter implements Converter<ProductDto, Product>{
	
	public Product convert(@Nullable ProductDto prodDto) {
		if (prodDto == null) {
			return null;
		}
		
		Product product = new Product();
		product.setName(prodDto.getName());
		product.setDescription(prodDto.getDescription());
		product.setAvailability(convertAvailability(prodDto, product));
		product.setCreated(java.sql.Date.valueOf(LocalDate.now()));
		product.setUpdated(java.sql.Date.valueOf(LocalDate.now()));
		return product;
	}
	
	private ProductAvailability convertAvailability(ProductDto prodDto, Product p) {
		ProductAvailability available = new ProductAvailability();
		available.setAvailable(prodDto.isAvailable());
		available.setPrice((prodDto.getPrice() <= 0)? new BigDecimal(0): BigDecimal.valueOf(prodDto.getPrice()));
		available.setProduct(p);
		return available;
	}
}
