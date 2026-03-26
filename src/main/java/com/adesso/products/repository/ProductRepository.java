package com.adesso.products.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adesso.products.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
