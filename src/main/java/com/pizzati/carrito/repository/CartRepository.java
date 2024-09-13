package com.pizzati.carrito.repository;

import com.pizzati.carrito.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
