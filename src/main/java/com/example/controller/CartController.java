package com.example.controller;

import com.example.entity.Cart;
import com.example.entity.CartItem;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@PathVariable Long userId) {
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    
    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        Cart cart = cartService.addItemToCart(userId, cartItem);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        Cart cart = cartService.removeItemFromCart(userId, cartItemId);
        return ResponseEntity.ok(cart);
    }


    @GetMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItem> getItemFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        CartItem cartitem = cartService.getCartItemByCartId(userId, cartItemId);
        if (cartitem != null) {
            return ResponseEntity.ok(cartitem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.ok(createdCart);
    }
    
    
 // New endpoint to update quantity of a cart item
    @PutMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable Long userId, 
                                                            @PathVariable Long cartItemId, 
                                                            @RequestBody int newQuantity) {
        CartItem updatedItem = cartService.updateCartItemQuantity(userId, cartItemId, newQuantity);
        
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
