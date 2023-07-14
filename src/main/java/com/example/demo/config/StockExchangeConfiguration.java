package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.dao.OrderRepository;
import com.example.demo.dao.OrderRepositoryImpl;
import com.example.demo.dao.StockRepository;
import com.example.demo.dao.StockRepositoryImpl;
import com.example.demo.dao.TraderRepository;
import com.example.demo.dao.TraderRepositoryImpl;
import com.example.demo.dao.TransactionRepository;
import com.example.demo.dao.TransactionRepositoryImpl;

@Configuration
public class StockExchangeConfiguration {
	
	@Bean
	public OrderRepository orderRepo () {
		return new OrderRepositoryImpl();
	}
	
	@Bean
	public StockRepository stockRepo () {
		return new StockRepositoryImpl();
	}
	
	@Bean
	public TraderRepository traderRepo () {
		return new TraderRepositoryImpl();
	}
	
	@Bean
	public TransactionRepository transactionRepo () {
		return new TransactionRepositoryImpl();
	}
	
}
