package com.wallet.service;

import java.util.List;

import com.wallet.model.Wallet;

public interface WalletService {
    Wallet addFunds(Wallet transaction);
    Wallet deductFunds(Wallet transaction);
    List<Wallet> getTransactionsByUserId(String userId);
}