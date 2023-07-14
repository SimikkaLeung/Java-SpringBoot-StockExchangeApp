package com.example.demo.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.models.Transaction;

@SpringBootTest
class TransactionRepositoryImplTest {

	@Autowired
	private TransactionRepository transactionRepo;
	
	@Test
	void testFindTransactionByTransactionId() {

		assertEquals(2,transactionRepo.findTransactionByTransactionId(2).getTransactionId());
	}

	
	@Test
	void testAddTransactionDeleteTransaction() {
		Transaction transaction = transactionRepo.findTransactionByTransactionId(2);
		assertEquals(1,transactionRepo.deleteTransaction(2));
		assertEquals(1,transactionRepo.addTransaction(transaction));
		
	}
	
	@Test
	void testFindAllTransactions() {
		assertEquals(2,transactionRepo.findAllTransactions().size());
	}

	
	@Test
	void testFindMaxTransactionId() {
		assertEquals(2,transactionRepo.findMaxTransactionId());
	}

	
}
