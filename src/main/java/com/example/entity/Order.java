package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Order date cannot be null")
    @Column(nullable = false)
    private Date orderDate;

    @NotNull(message = "Order status cannot be null")
    @Column(nullable = false)
    private String status; // PENDING, PAID, FAILED

    @NotNull(message = "Total amount cannot be null")
    @Column(nullable = false)
    private double totalAmount;
    
    @NotNull(message = "Order Delivered cannot be null")
    @Column(name="delivery_status", nullable = false)
    private Boolean deliveryStatus = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setDelivered(Boolean status) {
        this.deliveryStatus = status;
    }

    public Boolean getDelivered() {
        return deliveryStatus;
    }

    
    @Override
    public String toString() {
        return "Order [id=" + id + ", orderDate=" + orderDate + ", status=" + status + ", totalAmount=" + totalAmount + 
               ", user=" + user + ", orderItems=" + orderItems + "]";
    }
}
