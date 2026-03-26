package com.adesso.products.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adesso.products.dto.ProductDto;
import com.adesso.products.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@ResponseBody
@AllArgsConstructor
public class ProductsController {

	private ProductService productService;

	@GetMapping(path = { "/", "" })
	public List<ProductDto> getAllProducts() {
		return productService.getAllProducts();
	}

	@PostMapping(path = { "/", "" })
	public ProductDto createNewProduct(@Valid @RequestBody ProductDto product) {
		return productService.createNewProduct(product);
	}

	@GetMapping(path = "/{id}")
	public ProductDto getProductById(@Pattern(regexp = "\\d{1,50}") @PathVariable("id") String id) {
		return productService.getProductById(id);
	}

	@PutMapping(path = "/{id}")
	public ProductDto updateProduct(@Pattern(regexp = "\\d{1,50}") @PathVariable("id") String id,
			@Valid @RequestBody ProductDto product) {
		return productService.updateProduct(id, product);
	}

	@DeleteMapping(path = "/{id}")
	public boolean deleteProductById(@Pattern(regexp = "\\d{1,50}") @PathVariable("id") String id) {
		return productService.deleteProduct(id);
	}

}
