package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Stock;
@Repository
public class StockRepositoryImpl implements StockRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public Stock findStockByStockSymbol(String stockSymbol) {
		return jdbcTemplate.queryForObject("SELECT * FROM stocks WHERE stock_symbol = ?", BeanPropertyRowMapper.newInstance(Stock.class), stockSymbol);
	}

	@Override
	public List<Stock> findAllStocks() {
		return jdbcTemplate.query("SELECT * FROM stocks", BeanPropertyRowMapper.newInstance(Stock.class));
	}

}
