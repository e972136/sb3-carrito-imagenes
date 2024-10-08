package com.pizzati.carrito.dto;

import com.pizzati.carrito.entity.Cart;
import com.pizzati.carrito.entity.CartItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public record CartDao(
        Long id,
        BigDecimal totalAmount,
        Set<CartItemDao> cartItems
) {

    public static CartDao fromEntity(Cart cart){
        return new CartDao(
                cart.getId(),
                cart.getTotalAmount(),
                cart.getCartItems().stream().map(
                        e->new CartItemDao(
                          e.getId(),
                          e.getProduct().getId(),
                          e.getProduct().getName(),
                          e.getProduct().getBrand(),
                          e.getProduct().getCategory().getName(),
                          e.getQuantity(),
                          e.getUnitPrice(),
                          e.getTotalPrice()
                        )
                ).collect(Collectors.toSet())
                );
    }

}
