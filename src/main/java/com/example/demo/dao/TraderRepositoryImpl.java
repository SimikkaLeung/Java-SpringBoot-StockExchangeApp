package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Trader;
@Repository
public class TraderRepositoryImpl implements TraderRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Trader findTraderByTraderId(Integer traderId) {
		return jdbcTemplate.queryForObject("SELECT * FROM traders WHERE trader_id = ?", BeanPropertyRowMapper.newInstance(Trader.class), traderId);
	}

	@Override
	public Trader findTraderByTraderIdAndPin(Integer traderId, String pin) {
		return jdbcTemplate.queryForObject("SELECT * FROM traders WHERE trader_id = ? AND pin = ?", BeanPropertyRowMapper.newInstance(Trader.class), traderId, pin);
	}
	
	

}
