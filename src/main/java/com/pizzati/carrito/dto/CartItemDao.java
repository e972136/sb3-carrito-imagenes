package com.pizzati.carrito.dto;

import com.pizzati.carrito.entity.Product;

import java.math.BigDecimal;

public record CartItemDao(
        Long id,
        Long productId,
        String productName,
        String productBrand,
        String productCategory,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
