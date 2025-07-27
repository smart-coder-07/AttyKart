package com.wallet.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.wallet.exception.WalletNotFoundException;
import com.wallet.model.Transaction;
import com.wallet.model.Wallet;
import com.wallet.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Wallet addFunds(Wallet transaction) {
        // Get the wallet by userId
        Wallet wallet = walletRepository.findByUserId(transaction.getUserId())
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    // If not exists, create a new one
                    Wallet newWallet = new Wallet();
                    newWallet.setUserId(transaction.getUserId());
//                    newWallet.setId(walletRepository.count()+1);
                    newWallet.setAmount(0.0);
                    return newWallet;
                });

        // Update balance
        wallet.setAmount(wallet.getAmount() + transaction.getAmount());
        Transaction t = new Transaction();
        t.setAmount(transaction.getAmount());
        t.setOrderId(walletRepository.count()+1+"");
        t.setUserId(transaction.getUserId());
        t.setType("CREDIT");
        saveTransaction(t);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deductFunds(Wallet transaction) {
        Wallet wallet = walletRepository.findByUserId(transaction.getUserId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for user: " + transaction.getUserId()));

        double currentBalance = wallet.getAmount();
        double deductionAmount = transaction.getAmount();

        if (currentBalance < deductionAmount) {
            throw new WalletNotFoundException("Insufficient balance for user: " + transaction.getUserId());
        }

        wallet.setAmount(currentBalance - deductionAmount);
        Transaction t = new Transaction();
        t.setAmount(transaction.getAmount());
        t.setOrderId(walletRepository.count()+1+"");
        t.setUserId(transaction.getUserId());
        t.setType("DEBIT");
        saveTransaction(t);
        return walletRepository.save(wallet);
    }
    
    private void saveTransaction(Transaction transaction) {
    	try {
    				webClientBuilder.build()
					.post()
					.uri("http://TRANSACTIONSERVICE/api")
					.bodyValue(transaction)
					.retrieve()
					.bodyToMono(Transaction.class)
					.block();
					
		} catch (Exception e) {
			throw new WalletNotFoundException("Transaction failed");
		}
    }


    @Override
    public List<Wallet> getTransactionsByUserId(String userId) {
        return walletRepository.findByUserId(userId);
    }
}
