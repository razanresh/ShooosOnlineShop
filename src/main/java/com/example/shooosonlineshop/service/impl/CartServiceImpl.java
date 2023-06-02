package com.example.shooosonlineshop.service.impl;

import com.example.shooosonlineshop.model.*;
import com.example.shooosonlineshop.model.dto.CartDTO;
import com.example.shooosonlineshop.model.dto.CartDetailDTO;
import com.example.shooosonlineshop.model.enums.OrderStatus;
import com.example.shooosonlineshop.repository.CartRepository;
import com.example.shooosonlineshop.repository.ProductRepository;
import com.example.shooosonlineshop.service.CartService;
import com.example.shooosonlineshop.service.OrderService;
import com.example.shooosonlineshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final OrderService orderService;

    @Override
    @Transactional
    public Cart createCart(User user, List<Long> productIds) {
        Cart cart = new Cart();
        cart.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        cart.setProducts(productList);
        return cartRepository.save(cart);
    }
    private List<Product> getCollectRefProductsByIds(List<Long> productIds){
        return productIds.stream()
                .map(productRepository::getReferenceById)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addProducts(Cart cart, List<Long> productIds) {
        List<Product> products = cart.getProducts();
        List<Product> newProductsList  = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductsList.addAll(getCollectRefProductsByIds(productIds));
        cart.setProducts(newProductsList);
        cartRepository.save(cart);
    }

    @Override
    public CartDTO getCartByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getCart() == null){
            return new CartDTO();
        }

        CartDTO cartDTO = new CartDTO();
        Map<Long, CartDetailDTO> mapByProductId = new HashMap<>();

        List<Product> products = user.getCart().getProducts();
        for (Product product : products) {
            CartDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new CartDetailDTO(product));
            }
            else {
                detail.setAmount(detail.getAmount() + 1.0);
                detail.setSum(detail.getSum() + product.getPrice());
            }
        }

        cartDTO.setCartDetails(new ArrayList<>(mapByProductId.values()));
        cartDTO.aggregate();
        return cartDTO;
    }

    @Override
    @Transactional
    public void commitCartToOrder(String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User is not found with name" + username);
        }
        Cart cart = user.getCart();
        if(cart == null || cart.getProducts().isEmpty()){
            return;
        }

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        Map<Product, Long> productWithAmount = cart.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .toList();

        BigDecimal total = BigDecimal.valueOf(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");

        orderService.saveOrder(order);
        cart.getProducts().clear();
        cartRepository.save(cart);
    }
}
