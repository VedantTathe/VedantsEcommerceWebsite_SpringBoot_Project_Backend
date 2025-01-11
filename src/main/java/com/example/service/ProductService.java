package com.example.service;

import com.example.entity.Category;
import com.example.entity.Product;
import com.example.repository.CategoryRepository;
import com.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Create a new product
    public Product createProduct(Product product, String categoryName) {
        Optional<Category> existingCategory = categoryRepository.findByName(categoryName);

        Category category;
        if (existingCategory.isPresent()) {
            category = existingCategory.get();
        } else {
            category = new Category();
            category.setName(categoryName);
            category.setDescription(categoryName+"_description_dummy");
            category.setImgPath(categoryName+"_category.png");
            category = categoryRepository.save(category);
        }

        product.setCategory(category);
        return productRepository.save(product);
    }


    // Update an existing product
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
