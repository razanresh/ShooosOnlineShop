package com.example.shooosonlineshop.model.dto;

import com.example.shooosonlineshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailDTO {
    private String title;
    private Long productId;
    private BigDecimal price;
    private Double amount;
    private BigDecimal sum;

    public CartDetailDTO(Product product){
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = 1.0;
        this.sum = product.getPrice();
    }
}
