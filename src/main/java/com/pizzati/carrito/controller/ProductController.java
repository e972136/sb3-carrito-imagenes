package com.pizzati.carrito.controller;

import com.pizzati.carrito.dto.CategoryDao;
import com.pizzati.carrito.dto.ImageDto;
import com.pizzati.carrito.dto.ProductDto;
import com.pizzati.carrito.entity.Product;
import com.pizzati.carrito.request.AddProductRequest;
import com.pizzati.carrito.request.ProductUpdateRequest;
import com.pizzati.carrito.service.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/product")
    ResponseEntity<ProductDto> getProduct(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(ProductDto.fromEntity(productService.getProductById(id)));
    }

    @GetMapping("/all")
    ResponseEntity<List<ProductDto>> getAll(){
        return ResponseEntity.ok(
                productService
                        .getAllProducts()
                        .stream()
                        .map(ProductDto::fromEntity)
                        .toList());
    }

    @PostMapping
    ResponseEntity<ProductDto> saveProduct(
            @RequestBody AddProductRequest request
    ){
        return ResponseEntity.ok(
                ProductDto.fromEntity(productService.addProduct(request))
        );
    }

    @PutMapping("/{productId}")
    ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductUpdateRequest updateRequest,
            @PathVariable Long productId
            ){
        return ResponseEntity.ok(
                ProductDto.fromEntity(productService.updateProduct(updateRequest, productId))
        );
    }

    @DeleteMapping("/{productId}/delete")
    ResponseEntity<String> deleteProduct(
            @PathVariable Long productId
    ){
        try{
            productService.deleteProductById(productId);
            return ResponseEntity.ok("Success");
        }catch (Exception e){
            return ResponseEntity.ok("Fail to delete");
        }

    }

    @GetMapping("/by-brand")
    ResponseEntity<List<ProductDto>> getByBrand(
            @RequestParam String brand
    ){
        return ResponseEntity.ok(

                productService
                        .getProductsByBrand(brand)
                        .stream()
                        .map(ProductDto::fromEntity)
                        .toList());
    }
}
