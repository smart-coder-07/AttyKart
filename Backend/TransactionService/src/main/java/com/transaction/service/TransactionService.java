package com.transaction.service;

import java.util.List;

import com.transaction.model.Transaction;

public interface TransactionService {
	 Transaction processTransaction(Transaction transaction);
	    Transaction getTransactionById(String id);
	    List<Transaction> getTransactionsByUserId(String userId);
	    List<Transaction> getTransactionsByOrderId(String orderId);
}
