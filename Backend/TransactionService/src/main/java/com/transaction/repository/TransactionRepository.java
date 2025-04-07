package com.transaction.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.transaction.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUserId(String userId);
    List<Transaction> findByOrderId(String orderId);
}