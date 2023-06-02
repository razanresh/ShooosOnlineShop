package com.example.shooosonlineshop.repository;

import com.example.shooosonlineshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
