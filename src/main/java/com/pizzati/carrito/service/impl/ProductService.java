package com.pizzati.carrito.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzati.carrito.entity.Category;
import com.pizzati.carrito.entity.Product;
import com.pizzati.carrito.exception.ResourceNotFound;
import com.pizzati.carrito.repository.CategoryRepository;
import com.pizzati.carrito.repository.ProductRepository;
import com.pizzati.carrito.request.AddProductRequest;
import com.pizzati.carrito.request.ProductUpdateRequest;
import com.pizzati.carrito.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        Product product;
        try {
            product = mapper.readValue(mapper.writeValueAsString(request), Product.class);
        } catch (JsonProcessingException e) {
            System.out.println("patito: "+e.getMessage());
            product = null;
        }
        Category category =categoryRepository.findByName(request.categoryName());
        if(isNull(category)){
            category = new Category(request.categoryName());
        }
        product.setCategory(categoryRepository.save(category));
        return productRepository.save(product);
    }



    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand,String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFound("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,()->{throw new ResourceNotFound("Product not found");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(productoOriginal->productToUpdate(productoOriginal,request))
                .map(productRepository::save)
                .orElseThrow(()->new ResourceNotFound("No Esta el producto"));
    }

    private Product productToUpdate(Product productOriginal, ProductUpdateRequest request){
        productOriginal.setName(request.name());
        productOriginal.setBrand(request.brand());
        productOriginal.setPrice(request.price());
        productOriginal.setInventory(request.inventory());
        productOriginal.setDescription(request.description());

        Category category =categoryRepository.findByName(request.categoryName());
        productOriginal.setCategory(category);

        return productOriginal;
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
