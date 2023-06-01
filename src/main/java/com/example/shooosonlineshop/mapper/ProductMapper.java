package com.example.shooosonlineshop.mapper;

import com.example.shooosonlineshop.model.Product;
import com.example.shooosonlineshop.model.dto.ProductDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Product toProduct(ProductDTO productDTO);
    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    List<Product> toProductList(List<ProductDTO> productDTOS);
    List<ProductDTO> fromProductList(List<Product> products);
}
