package com.pizzati.carrito.service;

import com.pizzati.carrito.entity.CartItem;

public interface ICartItemService {
    void addCartItem(Long cartId, Long productId, int quantity);
    void removeCartItem(Long cartId, Long productId);

    void updateCartItemQuantity(Long cartId, Long productId, int quantity);
}
