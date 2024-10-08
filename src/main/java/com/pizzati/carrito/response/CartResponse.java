package com.pizzati.carrito.response;

import java.math.BigDecimal;

public record CartResponse(
        Long id,
        BigDecimal totalAmount
) {
}
