package com.example.controller;

import com.example.entity.Product;
import com.example.entity.ProductRequest;
import com.example.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String categoryName,
            @RequestParam("price") double price,
            @RequestParam("stock") int stock,
            @RequestParam("discount") int discount,
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
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/images/";
            File imagePath = new File(uploadDir + imageName);
            imageFile.transferTo(imagePath);


            // Create product and set the image path
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock(stock);
            product.setDiscount(discount);
  
            product.setProductImage(imageName);
           

            // Create product in the database
            Product createdProduct = productService.createProduct(product, categoryName);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IOException e) {
            System.out.println("Error saving image: {}"+ e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (RuntimeException e) {
            System.out.println("Error: {}"+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Product>> createProducts(@RequestBody List<ProductRequest> productRequests) {
        try {
            List<Product> createdProducts = new ArrayList<>();
            
            for (ProductRequest productRequest : productRequests) {
                // Create a new Product object from the request
                Product product = new Product();
                product.setName(productRequest.getTitle());
                product.setDescription(productRequest.getDescription());
                product.setPrice(productRequest.getPrice());
//                product.setStock(productRequest.getStock());
//                product.setDiscount(productRequest.getDiscount());
                product.setProductImage(productRequest.getImage());

                // Save the product using your service
                Product createdProduct = productService.createProduct(product, productRequest.getCategory());
                createdProducts.add(createdProduct);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdProducts);

        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
