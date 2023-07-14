package com.example.demo.dao;

import java.util.List;

import com.example.demo.models.Stock;

public interface StockRepository {
	public Stock findStockByStockSymbol (String stockSymbol);
	public List<Stock> findAllStocks();
}
