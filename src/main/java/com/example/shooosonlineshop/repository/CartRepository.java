package com.example.shooosonlineshop.repository;

import com.example.shooosonlineshop.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
