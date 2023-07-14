package com.example.demo.dao;

import java.util.List;

import com.example.demo.models.Transaction;

public interface TransactionRepository {
	
	
	public int addTransaction (Transaction transaction);
	
	public Transaction findTransactionByTransactionId (Integer transactionId);
	
	public List<Transaction> findAllTransactions ();
	
	public Integer findMaxTransactionId();
	
	public int deleteTransaction (Integer transactionId);

}
