package com.transaction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transaction.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByUserId(String userId);
    List<Transaction> findByOrderId(String orderId);
}