package com.wallet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.model.Wallet;
import com.wallet.service.WalletService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/add")
    public ResponseEntity<Wallet> addFunds(@Valid @RequestBody Wallet transaction) {
        return ResponseEntity.ok(walletService.addFunds(transaction));
    }

    @PostMapping("/deduct")
    public ResponseEntity<Wallet> deductFunds(@Valid @RequestBody Wallet transaction) {
        return ResponseEntity.ok(walletService.deductFunds(transaction));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Wallet>> getTransactionsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(walletService.getTransactionsByUserId(userId));
    }
}
