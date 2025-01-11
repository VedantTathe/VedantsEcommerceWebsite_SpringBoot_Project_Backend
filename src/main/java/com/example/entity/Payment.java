package com.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Payment date cannot be null")
    @Column(nullable = false)
    private Date paymentDate;

    @NotNull(message = "Amount cannot be null")
    @Column(nullable = false)
    private double amount;

    @NotNull(message = "Payment method cannot be null")
    @Column(nullable = false)
    private String paymentMethod;

    @NotNull(message = "Status cannot be null")
    @Column(nullable = false)
    private String status;

    // Instead of just Long, let's refer to the actual Order entity
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", paymentDate=" + paymentDate + ", amount=" + amount + ", paymentMethod=" + 
               paymentMethod + ", status=" + status + ", order=" + order + "]";
    }
}
