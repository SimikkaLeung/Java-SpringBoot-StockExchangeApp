package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan
public class TestConfiguration {
	

}
