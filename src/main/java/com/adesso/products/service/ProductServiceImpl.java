package com.adesso.products.service;

import java.util.List;
import java.util.Objects;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.adesso.products.dto.ProductDto;
import com.adesso.products.entity.Product;
import com.adesso.products.exceptions.ProductsServiceException;
import com.adesso.products.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private ConversionService conversionService;

	@Override
	public ProductDto getProductById(String productId) {
		Long prodId = Long.parseLong(productId);
		Product product = productRepository.getReferenceById(prodId);
		return conversionService.convert(product, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<Product> products = productRepository.findAll();
		if (CollectionUtils.isEmpty(products)) {
			return List.of();
		}
		return products.stream().map(p -> conversionService.convert(p, ProductDto.class))
				.filter(Objects::nonNull)
				.toList();
	}

	@Override
	@Transactional
	public ProductDto createNewProduct(ProductDto product) {
		if (product == null) {
			throw new ProductsServiceException("Incorrect payload");
		}
		return conversionService.convert(productRepository.save(conversionService.convert(product, Product.class)),
				ProductDto.class);
	}

	@Override
	@Transactional
	public ProductDto updateProduct(String productId, ProductDto product) {
		if (productId == null) {
			throw new ProductsServiceException("Incorrect product id");
		}
		Product prod = conversionService.convert(product, Product.class);
		if (prod == null) {
			throw new ProductsServiceException("Incorrect product payload");
		}
		prod.setId(Long.valueOf(productId));
		return conversionService.convert(productRepository.save(prod), ProductDto.class);
	}

	@Override
	@Transactional
	public boolean deleteProduct(String productId) {
		final Long prodId = sanitize(productId);
		if (prodId == null) {
			throw new ProductsServiceException("Incorrect product id");
		}
		try {
			productRepository.deleteById(prodId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private Long sanitize(final String id) {
		if ((id == null) || id.isBlank()) {
			throw new ProductsServiceException("Incorrect identifier!");
		}
		String sanitized = id.replace("/[\s=a-z\\{\\}\\(\\)\\%]+/gi", "");
		if ((sanitized == null) || sanitized.isEmpty()) {
			throw new ProductsServiceException("Incorrect identifier!");
		}
		return Long.valueOf(sanitized);
	}

}