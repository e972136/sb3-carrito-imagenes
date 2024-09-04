package com.pizzati.carrito.controller;

import com.pizzati.carrito.entity.Product;
import com.pizzati.carrito.request.AddProductRequest;
import com.pizzati.carrito.service.IProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    Product getProduct(
            @PathVariable Long id
    ){
        return productService.getProductById(id);
    }

    @PostMapping
    Product saveProduct(
            @RequestBody AddProductRequest request
    ){
        return productService.addProduct(request);
    }
}
