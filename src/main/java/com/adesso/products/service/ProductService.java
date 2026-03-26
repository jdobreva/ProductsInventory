package com.adesso.products.service;

import java.util.List;

import com.adesso.products.dto.ProductDto;

public interface ProductService {
	ProductDto getProductById(String productId) ;
	List<ProductDto> getAllProducts();
	ProductDto createNewProduct(ProductDto product);
	ProductDto updateProduct(String productId, ProductDto product);
	boolean deleteProduct(String productId);
}
