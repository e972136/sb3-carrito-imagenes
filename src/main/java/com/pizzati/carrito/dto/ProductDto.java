package com.pizzati.carrito.dto;

import com.pizzati.carrito.entity.Category;
import com.pizzati.carrito.entity.Image;
import com.pizzati.carrito.entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,
        String name,
        String brand,
        BigDecimal price,
        int inventory,
        String description,
        CategoryDao category,
        List<ImageDto> images
) {
    public static ProductDto fromEntity(Product p){
        return new  ProductDto(
                p.getId(),
                p.getName(),
                p.getBrand(),
                p.getPrice(),
                p.getInventory(),
                p.getDescription(),
                new CategoryDao(p.getCategory().getId(),p.getCategory().getName()),
                p.getImages().stream().map(act->new ImageDto(act.getId(),act.getFileName(),act.getDownloadUrl())).toList()
        );
    }
}
