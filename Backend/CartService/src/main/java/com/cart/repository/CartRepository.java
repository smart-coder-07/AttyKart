package com.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cart.model.CartItem;


@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByUserId(String userId);
    void deleteByUserId(String userId);
}
