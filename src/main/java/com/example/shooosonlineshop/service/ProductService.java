package com.example.shooosonlineshop.service;

import com.example.shooosonlineshop.model.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserCart(Long productId, String username);
    void addProduct(ProductDTO productDTO);
    ProductDTO getById(Long id);
}
