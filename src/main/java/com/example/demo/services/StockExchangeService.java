package com.example.demo.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.models.Order;
import com.example.demo.models.Stock;
import com.example.demo.models.Trader;
import com.example.demo.models.Transaction;

public interface StockExchangeService {
	public Trader findTraderByTraderId(Integer traderId);
	public Trader findTraderByTraderIdAndPin (Integer traderId, String pin);
	public int addOrder(Trader trader, Order order);
	public int editOrder(Order order);
	public Order findOrderByOrderId(Integer orderId);
	public int addTransaction(Transaction newTransaction);
	public List<Transaction> findAllTransactions(); 
	
	public boolean fulfillOrder(Order order);
	public boolean fulfillMarketSellOrder (Order sellOrder);
	public boolean fulfillLimitSellOrder (Order sellOrder);
	public boolean fulfillIOCSellOrder (Order sellOrder);
	public boolean fulfillFOKSellOrder (Order sellOrder);

	public boolean fulfillMarketBuyOrder (Order buyOrder);
	public boolean fulfillLimitBuyOrder (Order buyOrder);
	public boolean fulfillIOCBuyOrder (Order buyOrder);
	public boolean fulfillFOKBuyOrder (Order buyOrder);
	
	public List<Order> findAllOrders();
	public List<Order> findOrdersByStockSymbol(String stockSymbol);
	
	public List<Stock> findAllStocks();
	
}

