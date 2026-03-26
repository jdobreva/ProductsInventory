package com.adesso.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class ProductsInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsInventoryApplication.class, args);
	}

}
