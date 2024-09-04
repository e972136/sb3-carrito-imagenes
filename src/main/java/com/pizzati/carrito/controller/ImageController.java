package com.pizzati.carrito.controller;

import com.pizzati.carrito.dto.ImageDto;
import com.pizzati.carrito.entity.Image;
import com.pizzati.carrito.service.IImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value="/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ImageDto>> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long productId
    ){
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return ResponseEntity.ok(imageDtos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/api/vi/images/image/download/{id}")
    public ResponseEntity<Resource> downloadImage(
        @PathVariable Long id
    ) throws SQLException {
        Image image = imageService.getImageById(id);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachement; filename=\""+image.getFileName()+"\"")
                .body(resource);
    }
}
