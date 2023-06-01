package com.example.shooosonlineshop.model.dto;

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
}
