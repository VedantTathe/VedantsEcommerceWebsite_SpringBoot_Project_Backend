package com.example.service;

import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.entity.User;
import com.example.repository.CartItemRepository;
import com.example.repository.CartRepository;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    // Get cart by user ID
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // Get cart items by user ID
    public List<CartItem> getCartItemsByUserId(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        return cart != null ? cart.getCartItems() : List.of();
    }
    
 // Get cart items by user ID
    public CartItem getCartItemByCartId(Long userId, Long cartItemId) {
        Optional<CartItem> cartitem = cartItemRepository.findById(cartItemId);
        return cartitem.get();
    }

    // Add an item to the user's cart
    public Cart addItemToCart(Long userId, CartItem cartItem) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
       

        User user = optionalUser.get();
        Cart cart = cartRepository.findByUserId(userId);
        


        
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        // Set the cart reference in the cart item
        cartItem.setCart(cart);

        // Add the item to the cart
        cart.getCartItems().add(cartItem);

        // Save the updated cart
        return cartRepository.save(cart);
    }

    // Remove an item from the user's cart
    public Cart removeItemFromCart(Long userId, Long cartItemId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            // Remove the item by matching ID
            cart.getCartItems().removeIf(cartItem -> cartItem.getId().equals(cartItemId));
            return cartRepository.save(cart);
        }
        throw new RuntimeException("Cart not found for user ID: " + userId);
    }

    // Create a new cart
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }
    
    public CartItem updateCartItemQuantity(Long userId, Long cartItemId, int newQuantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        
//        if (cartItem != null && cartItem.getCart().getUser().getId().equals(userId)) {
        if (cartItem != null) {
        	cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
            return cartItem;
        } else {
            return null;
        }
    }
    
}
