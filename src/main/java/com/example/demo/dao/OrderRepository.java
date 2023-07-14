package com.example.demo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.models.Order;

public interface OrderRepository {
	public int addOrder (Order order);
	public Order findOrderByOrderId (Integer orderId);
	public List<Order> findOrdersByTraderId (Integer traderId);	 
	// Only returns orders that are in progress.
	public List<Order> findOrdersByStockSymbol (String stockSymbol);
	// Only returns orders that are in progress.
	public List<Order> findOrdersByStockSymbolAndIsSelling (String stockSymbol);	
	// Only returns orders that are in progress.
	public List<Order> findOrdersByStockSymbolAndIsSellingWithMaxPrice (String stockSymbol, BigDecimal maxPrice);
	// Only returns orders that are in progress.
	public List<Order> findOrdersByStockSymbolAndIsNotSelling (String stockSymbol);
	// Only returns orders that are in progress.
	public List<Order> findOrdersByStockSymbolAndIsNotSellingWithMinPrice (String stockSymbol, BigDecimal minPrice);
	public List<Order> findOrdersByStockSymbolAndOrderStatus  (String stockSymbol, OrderStatus orderStatus);
	// Only returns orders that are in progress.
	public List<Order> findOrdersByStockSymbolAndOrderTypeAndIsSelling   (String stockSymbol, OrderType orderType);
	// Only returns orders that are in progress.
	public List<Order> findOrdersByStockSymbolAndOrderTypeAndIsNotSelling   (String stockSymbol, OrderType orderType);
	public int editOrder (Order order);
	public List<Order> findAllOrders(); 
	public Integer findMaxOrderId();
	
	public int deleteOrder(Integer orderId);	
}
