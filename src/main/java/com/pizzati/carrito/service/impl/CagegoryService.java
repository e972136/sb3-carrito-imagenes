package com.pizzati.carrito.service.impl;

import com.pizzati.carrito.entity.Category;
import com.pizzati.carrito.exception.ResourceNotFound;
import com.pizzati.carrito.repository.CategoryRepository;
import com.pizzati.carrito.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CagegoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CagegoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Categoria no existe!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
                .filter(c->!categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(()->new ResourceNotFound(category.getName()+" Ya existe esta categoria!"));
    }

    @Override
    public Category updateCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory->{
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(()->new ResourceNotFound("No encontrada"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(
                categoryRepository::delete,()->{throw new ResourceNotFound("No encontrada");}
        );
    }
}
