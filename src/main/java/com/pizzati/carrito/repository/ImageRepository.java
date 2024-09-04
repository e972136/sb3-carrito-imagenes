package com.pizzati.carrito.repository;

import com.pizzati.carrito.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
