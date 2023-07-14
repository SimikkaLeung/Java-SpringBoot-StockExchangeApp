package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Transaction;
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int addTransaction(Transaction transaction) {
		return jdbcTemplate.update("INSERT INTO transactions VALUES(?,?,?,?,?,?)", 
				transaction.getTransactionId(), transaction.getBuyerOrderId(), 
				transaction.getSellerOrderId(), transaction.getQuantity(), 
				transaction.getPrice(), transaction.getTransactionTime());
	}

	@Override
	public Transaction findTransactionByTransactionId(Integer transactionId) {
		return jdbcTemplate.queryForObject("SELECT * FROM transactions WHERE transaction_id = ?", BeanPropertyRowMapper.newInstance(Transaction.class), transactionId);
	}


	@Override
	public List<Transaction> findAllTransactions() {
		return jdbcTemplate.query("SELECT * FROM transactions", BeanPropertyRowMapper.newInstance(Transaction.class));
	}


	@Override
	public Integer findMaxTransactionId() {
		return jdbcTemplate.queryForObject("SELECT MAX(transaction_id) FROM transactions", Integer.class);
	}
	
	@Override
	public int deleteTransaction (Integer transactionId) {
		return jdbcTemplate.update("DELETE FROM transactions WHERE transaction_id = ?", transactionId);
	};

}
