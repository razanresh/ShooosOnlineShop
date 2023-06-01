package com.example.shooosonlineshop.repository;

import com.example.shooosonlineshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
