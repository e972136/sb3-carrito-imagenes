package com.pizzati.carrito.service.impl;

import com.pizzati.carrito.entity.Cart;
import com.pizzati.carrito.entity.CartItem;
import com.pizzati.carrito.entity.Product;
import com.pizzati.carrito.exception.ResourceNotFound;
import com.pizzati.carrito.repository.CartItemRepository;
import com.pizzati.carrito.repository.CartRepository;
import com.pizzati.carrito.service.ICartItemService;
import com.pizzati.carrito.service.ICartService;
import com.pizzati.carrito.service.IProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Service
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;

    private final ICartService cartService;
    private final CartRepository cartRepository;

    public CartItemService(CartItemRepository cartItemRepository, IProductService productService, ICartService cartService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
    }

    @Override
    public void addCartItem(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream()
                .parallel()
                .filter(e -> e.getProduct().getId().equals(productId))
                .findFirst().orElse(null);
        if(isNull(cartItem)){
            cartItem = CartItem.of(null,product,quantity,product.getPrice(), BigDecimal.ZERO,cart);
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = cart.getCartItems()
                .stream().parallel()
                .filter(e -> e.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFound("Product not found"));
        cart.removeItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateCartItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        CartItem cartItem = cart.getCartItems()
                .stream().parallel()
                .filter(e -> e.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()->new ResourceNotFound("Product not found"));
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice();
        cart.updateTotalAmount();
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }
}
