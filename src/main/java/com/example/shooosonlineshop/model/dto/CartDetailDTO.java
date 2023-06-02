package com.example.shooosonlineshop.model.dto;

import com.example.shooosonlineshop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDetailDTO {
    private String title;
    private Long productId;
    private Double price;
    private Double amount;
    private Double sum;

    public CartDetailDTO(Product product){
        this.title = product.getTitle();
        this.productId = product.getId();
        this.price = product.getPrice();
        this.amount = 1.0;
        this.sum = product.getPrice();
    }
}
