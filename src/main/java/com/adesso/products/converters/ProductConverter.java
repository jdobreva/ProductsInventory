package com.adesso.products.converters;

import org.springframework.core.convert.converter.Converter;

import com.adesso.products.dto.ProductDto;
import com.adesso.products.entity.Product;
import com.adesso.products.entity.ProductAvailability;

public class ProductConverter implements Converter<Product, ProductDto> {

	public ProductDto convert(final Product product) {
		if (product == null) {
			return null;
		}

		return ProductDto.builder().id(product.getId()).name(product.getName()).description(product.getDescription())
				.available(isProductAvailable(product.getAvailability()))
				.price(getProductPrice(product.getAvailability())).updated(product.getUpdated().toLocalDate())
				.created(product.getUpdated().toLocalDate()).build();
	}

	private boolean isProductAvailable(ProductAvailability prodAvailability) {
		return ((prodAvailability != null) && (prodAvailability.isAvailable()));
	}

	private double getProductPrice(ProductAvailability prodAvailability) {
		if (prodAvailability == null) {
			return 0.0;
		}
		return prodAvailability.getPrice().doubleValue();
	}

}
