package com.pizzati.carrito.service.impl;

import com.pizzati.carrito.dto.ImageDto;
import com.pizzati.carrito.entity.Image;
import com.pizzati.carrito.entity.Product;
import com.pizzati.carrito.exception.ResourceNotFound;
import com.pizzati.carrito.repository.ImageRepository;
import com.pizzati.carrito.service.IImageService;
import com.pizzati.carrito.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService implements IImageService {

    private final IProductService productService;
    private final ImageRepository imageRepository;

    public ImageService(IProductService productService, ImageRepository imageRepository) {
        this.productService = productService;
        this.imageRepository = imageRepository;
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(()->{throw new ResourceNotFound("Imagen no encontrada");});
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->new ResourceNotFound("Imagen no encontrada"));
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();
        for(MultipartFile file:files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                image = imageRepository.save(image);

                String download = "/api/vi/images/image/download/"+image.getId();
                image.setDownloadUrl(download);

                image = imageRepository.save(image);

                imageDtos.add(new ImageDto(
                        image.getId(),
                        image.getFileName(),
                        image.getDownloadUrl()
                ));
            }catch (Exception e){

            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
        }catch (Exception e){
            throw new RuntimeException("Pedo");
        }

    }
}
