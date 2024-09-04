package com.pizzati.carrito.dto;

import jakarta.persistence.Lob;

import java.sql.Blob;

public record ImageDto(
        Long id,
        String fileName,
        String downloadUrl
) {
}
