package com.example.shooosonlineshop.service;

import com.example.shooosonlineshop.model.Cart;
import com.example.shooosonlineshop.model.User;
import com.example.shooosonlineshop.model.dto.CartDTO;

import java.util.List;

public interface CartService {
    Cart createCart(User user, List<Long> productIds);

    void addProducts(Cart cart, List<Long> productIds);

    CartDTO getCartByUser(String name);

    void commitCartToOrder(String username);

}
