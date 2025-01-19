package com.example.service;

import com.example.entity.Order;
import com.example.entity.User;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        order.setOrderDate(new Date());
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(id);
            return orderRepository.save(order);
        } else {
            return null;
        }	
    }
    
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus("PAID");
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
       orderRepository.deleteById(id);
    }
    
    public List<Order> getOrdersByUserId(Long userId) {
        // Find the user first
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Fetch all orders that belong to this user
            return orderRepository.findByUser(user);
        } else {
            throw new RuntimeException("User not found with id: " + userId);
        }
    }
}
