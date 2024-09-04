package com.pizzati.carrito.request;

import com.pizzati.carrito.entity.Category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public record AddProductRequest(
        Long id,
        String name,
        String brand,
        BigDecimal price,
        int inventory,
        String description,
        String categoryName
) {
}
