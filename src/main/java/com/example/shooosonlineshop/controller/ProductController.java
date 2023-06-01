package com.example.shooosonlineshop.controller;

import com.example.shooosonlineshop.model.dto.ProductDTO;
import com.example.shooosonlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public String list(Model model){
        List<ProductDTO> list = productService.getAll();
        model.addAttribute("products", list);
        return "products";
    }
}
