package com.example.demo.dao;

import com.example.demo.models.Trader;

public interface TraderRepository {
	public Trader findTraderByTraderId (Integer traderId);
	public Trader findTraderByTraderIdAndPin (Integer traderId, String pin);
}
