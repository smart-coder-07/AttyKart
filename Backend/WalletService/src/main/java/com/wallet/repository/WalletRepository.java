package com.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

//import org.springframework.data.mongodb.repository.MongoRepository;

import com.wallet.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findByUserId(String userId);
}