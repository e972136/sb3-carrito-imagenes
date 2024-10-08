package com.pizzati.carrito.controller;

import com.pizzati.carrito.response.CartResponse;
import com.pizzati.carrito.service.ICartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;

    public CartItemController(ICartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/item/add")
    public ResponseEntity<CartResponse> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ){
        return ResponseEntity.ok(cartItemService.addCartItem(cartId, productId, quantity));
    }

    @DeleteMapping("/{cartId}/product/{productId}/remove")
    public ResponseEntity<String> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId
    ){
        cartItemService.removeCartItem(cartId,productId);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/cart/update")
    public ResponseEntity<String> updateItem(
            @RequestParam Long cartId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ){
        cartItemService.updateCartItemQuantity(cartId,productId,quantity);
        return ResponseEntity.ok("Success");
    }
}
