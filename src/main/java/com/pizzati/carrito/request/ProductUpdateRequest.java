package com.pizzati.carrito.request;

import java.math.BigDecimal;

public record ProductUpdateRequest(
        Long id,
        String name,
        String brand,
        BigDecimal price,
        int inventory,
        String description,
        String categoryName
) {
}
