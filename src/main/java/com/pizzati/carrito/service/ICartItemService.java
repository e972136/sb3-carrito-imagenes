package com.pizzati.carrito.service;

import com.pizzati.carrito.entity.CartItem;
import com.pizzati.carrito.response.CartResponse;

public interface ICartItemService {
    CartResponse addCartItem(Long cartId, Long productId, int quantity);
    void removeCartItem(Long cartId, Long productId);

    void updateCartItemQuantity(Long cartId, Long productId, int quantity);
}
