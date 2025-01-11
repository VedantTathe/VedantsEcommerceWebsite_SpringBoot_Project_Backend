package com.example.controller;

import com.example.entity.Category;
import com.example.entity.Product;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(
    		@RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            // Validate image file
            if (imageFile.isEmpty() || imageFile.getSize() > 5000000) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            String contentType = imageFile.getContentType();
            if (!contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Save image to the server
            String imageName = UUID.randomUUID().toString() + ".png";
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/images/category/";
            File imagePath = new File(uploadDir + imageName);
            imageFile.transferTo(imagePath);

        
        	
	        Category category = new Category();
	        category.setName(name);
	        category.setDescription(description);
	        category.setImgPath(imageName);
	       
	
	        // Create product in the database
	        Category createdCategory = categoryService.createCategory(category);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
	    } catch (IOException e) {
	        System.out.println("Error saving image: {}"+ e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    } catch (RuntimeException e) {
	        System.out.println("Error: {}"+ e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	}


    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
