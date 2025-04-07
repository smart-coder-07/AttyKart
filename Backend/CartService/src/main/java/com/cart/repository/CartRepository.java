package com.cart.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cart.model.CartItem;

import java.util.List;

@Repository
public interface CartRepository extends MongoRepository<CartItem, String> {
    List<CartItem> findByUserId(String userId);
    void deleteByUserId(String userId);
}
