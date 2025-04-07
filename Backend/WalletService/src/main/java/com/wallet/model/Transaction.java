package com.wallet.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String id;
    private String userId;
    private String orderId;
    private LocalDate transactionDate;
    private String type;
    private Double amount;
    private String transactionStatus;
}
