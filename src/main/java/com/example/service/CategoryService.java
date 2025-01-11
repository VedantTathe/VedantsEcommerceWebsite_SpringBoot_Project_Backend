package com.example.service;

import com.example.entity.Category;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get a category by ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Create a new category
    public Category createCategory(Category category){
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("Category already exists with name: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    // Update a category
    public Category updateCategory(Long id, Category category) {
        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(category.getName());
            existingCategory.setImgPath(category.getImgPath());
            return categoryRepository.save(existingCategory);
        }).orElse(null);
    }

    // Delete a category
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
