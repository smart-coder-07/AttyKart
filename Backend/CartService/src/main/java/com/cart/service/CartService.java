package com.cart.service;
import java.util.List;

import com.cart.model.CartItem;

public interface CartService {
    CartItem addToCart(CartItem cartItem);
    CartItem updateCartItem(String id, CartItem cartItem);
    void removeCartItem(String id);
    void clearCart(String userId);
    List<CartItem> getCartByUserId(String userId);
}