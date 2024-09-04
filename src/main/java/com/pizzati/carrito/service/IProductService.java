package com.pizzati.carrito.service;

import com.pizzati.carrito.entity.Product;
import com.pizzati.carrito.request.AddProductRequest;
import com.pizzati.carrito.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
  Product addProduct(AddProductRequest product);
  List<Product> getAllProducts();
  List<Product> getProductsByCategory(String category);
  List<Product> getProductsByBrand(String brand);
  List<Product> getProductsByCategoryAndBrand(String category, String brand);
  List<Product> getProductsByName(String name);
  List<Product> getProductsByBrandAndName(String brand,String name);

  Product getProductById(Long id);
  void deleteProductById(Long id);
  Product updateProduct(ProductUpdateRequest product, Long productId);

  Long countProductsByBrandAndName(String brand, String name);
}
