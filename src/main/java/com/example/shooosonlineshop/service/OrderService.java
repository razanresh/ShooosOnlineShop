package com.example.shooosonlineshop.service;

import com.example.shooosonlineshop.model.Order;

public interface OrderService {
    void saveOrder(Order order);
    Order getOrder(Long id);
}
