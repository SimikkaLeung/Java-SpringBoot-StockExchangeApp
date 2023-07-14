package com.example.demo.models;

public class Stock {
	private String stockSymbol;
	private String stockName;
	
	public Stock() {
	}

	public Stock(String stockSymbol, String stockName) {
		super();
		this.stockSymbol = stockSymbol;
		this.stockName = stockName;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Override
	public String toString() {
		return "Stock [stockSymbol=" + stockSymbol + ", stockName=" + stockName + "]";
	}
	
	
	
	
	
}
