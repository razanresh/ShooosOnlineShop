package com.example.shooosonlineshop.controller;

import com.example.shooosonlineshop.model.dto.CartDTO;
import com.example.shooosonlineshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String aboutCart(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("cart", new CartDTO());
        }
        else {
            CartDTO cartDTO = cartService.getCartByUser(principal.getName());
            model.addAttribute("cart", cartDTO);
        }

        return "cart";
    }
    @PostMapping("/cart")
    public String commitCart(Principal principal){
        if(principal != null){
            cartService.commitCartToOrder(principal.getName());
        }
        return "redirect:/cart";
    }
}
