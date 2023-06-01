package com.example.shooosonlineshop.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private BigDecimal price;
}
