package com.example.shooosonlineshop.service.impl;

import com.example.shooosonlineshop.mapper.ProductMapper;
import com.example.shooosonlineshop.model.Product;
import com.example.shooosonlineshop.model.dto.ProductDTO;
import com.example.shooosonlineshop.repository.ProductRepository;
import com.example.shooosonlineshop.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final ProductRepository productRepository;
    @Override
    public List<ProductDTO> getAll(){
        return productMapper.fromProductList(productRepository.findAll());
    }

    @Override
    public void addToUserCart(Long productId, String username) {

    }

    @Override
    @Transactional
    public void addProduct(ProductDTO productDTO) {
        Product product = productMapper.toProduct(productDTO);
        Product savedProduct = productRepository.save(product);



    }

    @Override
    public ProductDTO getById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return ProductMapper.INSTANCE.fromProduct(product);
    }
}
