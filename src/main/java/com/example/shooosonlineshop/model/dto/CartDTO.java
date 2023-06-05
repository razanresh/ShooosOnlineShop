package com.example.shooosonlineshop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private long amountProducts;
    private double sum;
    private List<CartDetailDTO> cartDetails = new ArrayList<>();

    public void aggregate(){
        this.amountProducts = cartDetails.size();
        this.sum = cartDetails.stream()
                .map(CartDetailDTO::getSum)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }
}
