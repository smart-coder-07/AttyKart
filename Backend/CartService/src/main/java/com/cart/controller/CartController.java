package com.cart.controller;

import java.util.List;

import org.apache.el.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cart.model.CartItem;
import com.cart.service.CartService;

import jakarta.validation.Valid;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@Valid @RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartService.addToCart(cartItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable String id, @Valid @RequestBody CartItem cartItem) {
        return ResponseEntity.ok(cartService.updateCartItem(id, cartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCartItem(@PathVariable String id) {
        cartService.removeCartItem(id);
        return ResponseEntity.ok("Cart item removed successfully");
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<String> clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getCartByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }
}