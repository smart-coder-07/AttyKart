package com.wallet.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wallet.model.Wallet;

public interface WalletRepository extends MongoRepository<Wallet, String> {
    List<Wallet> findByUserId(String userId);
}