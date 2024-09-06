package com.pizzati.carrito.controller;

import com.pizzati.carrito.dto.CategoryDao;
import com.pizzati.carrito.entity.Category;
import com.pizzati.carrito.service.ICategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDao>> getAllCategories(){
        List<CategoryDao> categories =categoryService.getAllCategories()
                .stream().map(cat->new CategoryDao(cat.getId(),cat.getName()))
                .toList();
        return ResponseEntity.ok(categories);
    }

    @PutMapping
    public ResponseEntity<CategoryDao> addCategory(
            @RequestBody CategoryDao categoryName
    ){
        Category category = new Category();
        category.setName(categoryName.name());
        Category categoryNew = categoryService.addCategory(category);
        return ResponseEntity.ok().body(new CategoryDao(categoryNew.getId(),categoryNew.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDao> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(
                Optional.ofNullable(categoryService.getCategoryById(id))
                        .map(r->new CategoryDao(r.getId(),r.getName())).orElse(null));
    }
}
