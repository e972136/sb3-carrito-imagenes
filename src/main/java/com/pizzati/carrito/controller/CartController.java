package com.pizzati.carrito.controller;

import com.pizzati.carrito.dto.CartDao;
import com.pizzati.carrito.entity.Cart;
import com.pizzati.carrito.service.ICartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;


    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<CartDao> getCart(
            @PathVariable Long cartId
    ){
        try{
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(CartDao.fromEntity(cart));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<String> clearCart(
        @PathVariable Long cartId
    ){
        cartService.clearCart(cartId);
        return ResponseEntity.ok("Success!");
    }

    @GetMapping("/{cartId}/cart/total")
    public ResponseEntity<BigDecimal> getTotalAmount(
            @PathVariable Long cartId
    ){
        return ResponseEntity.ok(cartService.getTotalPrice(cartId));
    }
}
