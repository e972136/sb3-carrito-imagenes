package com.pizzati.carrito.service;

import com.pizzati.carrito.dto.ImageDto;
import com.pizzati.carrito.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file,Long imageId);
}
