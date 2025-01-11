package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product name cannot be null")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "Product description cannot be null")
    @Size(max = 1000, message = "Product description cannot exceed 1000 characters")
    @Column(nullable = false, length = 1000)
    private String description;


    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull(message = "Product image cannot be null")
    @Column(name = "product_image", nullable = false)
    private String productImage;

    @NotNull(message = "Price cannot be null")
    @Column(nullable = false)
    private double price;

    @NotNull(message = "Stock cannot be null")
    @Column(nullable = true)
    private int stock;

    @NotNull(message = "Discount cannot be null")
    @Column(name="discount", nullable = true)
    private int discount;

    // Default constructor
    public Product() {}

    // Constructor with all fields
    public Product(Long id, String name, String description, Category category, String productImage, double price, int stock, int discount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.productImage = productImage;
        this.price = price;
        this.stock = stock;
        this.discount = discount;
        
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category + 
               ", productImage=" + productImage + ", price=" + price + ", stock=" + stock + "discount=" + discount + "]";
    }
}
