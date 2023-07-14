package com.example.demo.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.OrderRepository;
import com.example.demo.dao.StockRepository;
import com.example.demo.dao.TraderRepository;
import com.example.demo.dao.TransactionRepository;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.models.Order;
import com.example.demo.models.Stock;
import com.example.demo.models.Trader;
import com.example.demo.models.Transaction;

@Service

public class StockExchangeServiceImpl implements StockExchangeService {
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private TraderRepository traderRepo;
	@Autowired
	private StockRepository stockRepo;
	@Autowired
	private TransactionRepository transactionRepo;
	
	@Override
	public Trader findTraderByTraderId(Integer traderId) {
		return traderRepo.findTraderByTraderId(traderId);
	}
	@Override
	public Trader findTraderByTraderIdAndPin(Integer traderId, String pin) {
		return traderRepo.findTraderByTraderIdAndPin(traderId, pin);
	}
	@Override
	public int addOrder(Trader trader, Order order) {

		order.setOrderId(orderRepo.findMaxOrderId()+1);
		order.setTraderId(trader.getTraderId());
		if (order.getOrderType().equals("MARKET")) {
			order.setPrice(new BigDecimal(0.0000));
		}
		
		order.setRemainingQuantity(order.getInitialQuantity());		
		order.setOrderStatus(OrderStatus.IN_PROGRESS);		
		order.setOrderTime(LocalDateTime.now());
		
		System.out.println(order);
		return orderRepo.addOrder(order);
	}
	
	
	@Override
	public int editOrder(Order order) {
		return orderRepo.editOrder(order);
	}
	
	@Override
	public Order findOrderByOrderId(Integer orderId) {
		return orderRepo.findOrderByOrderId(orderId);
	};
	
	
	@Override
	public int addTransaction(Transaction newTransaction) {
		return transactionRepo.addTransaction(newTransaction);
	}
	@Override
	public List<Transaction> findAllTransactions() {
		return transactionRepo.findAllTransactions();
	}

	public void generateTransaction (Order buyOrder, Order sellOrder, BigDecimal price) {
		if( buyOrder.getRemainingQuantity().compareTo(new BigDecimal(0))!=0 && sellOrder.getRemainingQuantity().compareTo(new BigDecimal(0))!=0) {
				
			int comparisonResult = buyOrder.getRemainingQuantity().compareTo(sellOrder.getRemainingQuantity());
			if (comparisonResult == 0) {		// Both the buyOrder and sellOrder have the same remaining quantity
				Transaction transaction = new Transaction(transactionRepo.findMaxTransactionId()+1, buyOrder.getOrderId(), 
						sellOrder.getOrderId(), sellOrder.getRemainingQuantity(), price);
				transactionRepo.addTransaction(transaction);
				buyOrder.setRemainingQuantity(new BigDecimal(0));
				buyOrder.setOrderStatus(OrderStatus.COMPLETED);
				sellOrder.setRemainingQuantity(new BigDecimal(0));
				sellOrder.setOrderStatus(OrderStatus.COMPLETED);
				orderRepo.editOrder(buyOrder);
				orderRepo.editOrder(sellOrder);
			} else if (comparisonResult > 0) {		// The buyOrder has a larger remaining quantity than the sellOrder.
				Transaction transaction = new Transaction(transactionRepo.findMaxTransactionId()+1, buyOrder.getOrderId(), 
						sellOrder.getOrderId(), sellOrder.getRemainingQuantity(), price);
				transactionRepo.addTransaction(transaction);
				buyOrder.setRemainingQuantity(buyOrder.getRemainingQuantity().subtract(sellOrder.getRemainingQuantity()));
				sellOrder.setRemainingQuantity(new BigDecimal(0));
				sellOrder.setOrderStatus(OrderStatus.COMPLETED);
				orderRepo.editOrder(buyOrder);
				orderRepo.editOrder(sellOrder);
			} else {		// The buyOrder has a smaller remaining quantity than the sellOrder.
				Transaction transaction = new Transaction(transactionRepo.findMaxTransactionId()+1, buyOrder.getOrderId(), 
						sellOrder.getOrderId(), buyOrder.getRemainingQuantity(), price);
				transactionRepo.addTransaction(transaction);
				sellOrder.setRemainingQuantity(sellOrder.getRemainingQuantity().subtract(buyOrder.getRemainingQuantity()));
				buyOrder.setRemainingQuantity(new BigDecimal(0));
				buyOrder.setOrderStatus(OrderStatus.COMPLETED);			
				orderRepo.editOrder(buyOrder);
				orderRepo.editOrder(sellOrder);
			} 
		
		}
	}
	public boolean matchBuyOrdersToSellOrder(Order sellOrder, List<Order> buyOrders) {
		for (Order buyOrder : buyOrders) {
			BigDecimal price = null;
//			BigDecimal price = ( buyOrder.getPrice().compareTo(sellOrder.getPrice()) >=0 ) ? (buyOrder.getPrice()) : (sellOrder.getPrice());
			if (buyOrder.getOrderType().equals("MARKET")) {
				price = sellOrder.getPrice();
			} else {
				price = buyOrder.getPrice();
			}
			generateTransaction(buyOrder, sellOrder, price);
			if (sellOrder.getOrderStatus().equals("COMPLETED")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean fulfillMarketSellOrder(Order sellOrder) {
		
		
		List<Order> buyOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsNotSelling(sellOrder.getStockSymbol(), OrderType.MARKET);
		buyOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsNotSelling(sellOrder.getStockSymbol()));
		
		if (buyOrders.isEmpty()) {
			return false;	// The sellOrder is not yet completely fulfilled..
		}
		// Sort the orders by price in an ascending order.
		buyOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));		
		
		return matchBuyOrdersToSellOrder(sellOrder, buyOrders);
	}
	
	@Override
	public boolean fulfillLimitSellOrder(Order sellOrder) {
		List<Order> buyOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsNotSelling(sellOrder.getStockSymbol(), OrderType.MARKET);
		buyOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsNotSellingWithMinPrice(sellOrder.getStockSymbol(), sellOrder.getPrice()));
		if (buyOrders.isEmpty()) {
			return false;
		}
		// Sort the orders by price in an ascending order.
		buyOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
		
		return matchBuyOrdersToSellOrder(sellOrder, buyOrders);
		
	}
	@Override
	public boolean fulfillIOCSellOrder(Order sellOrder) {
		List<Order> buyOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsNotSelling(sellOrder.getStockSymbol(), OrderType.MARKET);
		buyOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsNotSellingWithMinPrice(sellOrder.getStockSymbol(), sellOrder.getPrice()));
		if (buyOrders.isEmpty()) {
			sellOrder.setOrderStatus(OrderStatus.FAILED);
			orderRepo.editOrder(sellOrder);
			return false;
		}
		// Sort the orders by price in an ascending order.
		buyOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
		
		if (matchBuyOrdersToSellOrder(sellOrder, buyOrders) == false) {
			sellOrder.setOrderStatus(OrderStatus.PARTIALLY_COMPLETED);
			orderRepo.editOrder(sellOrder);
			return false;
		} else {
			return true;
		}
	}
	@Override
	public boolean fulfillFOKSellOrder(Order sellOrder) {
		List<Order> buyOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsNotSelling(sellOrder.getStockSymbol(), OrderType.MARKET);
		buyOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsNotSellingWithMinPrice(sellOrder.getStockSymbol(), sellOrder.getPrice()));
		if (buyOrders.isEmpty()) {
			sellOrder.setOrderStatus(OrderStatus.FAILED);
			orderRepo.editOrder(sellOrder);
			return false;
		}
		
		BigDecimal totalQuantity = buyOrders.stream().map(bo->bo.getRemainingQuantity()).reduce(new BigDecimal(0), (subtotal, quantity) -> subtotal.add(quantity));
		if (totalQuantity.compareTo(sellOrder.getRemainingQuantity()) < 0 ) {	
			sellOrder.setOrderStatus(OrderStatus.FAILED);
			orderRepo.editOrder(sellOrder);
			return false;
		}
		// Sort the orders by price in an ascending order.
		buyOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
		return matchBuyOrdersToSellOrder(sellOrder, buyOrders);
	}
	
	public boolean matchSellOrdersToBuyOrder(Order buyOrder, List<Order> sellOrders) {
		for (Order sellOrder : sellOrders) {
			BigDecimal price = null;

			if (sellOrder.getOrderType().equals("MARKET")) {
				price = buyOrder.getPrice();
			} else {
				price = sellOrder.getPrice();
			}
		
			generateTransaction(buyOrder, sellOrder, price);
			if (buyOrder.getOrderStatus().equals("COMPLETED")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean fulfillMarketBuyOrder(Order buyOrder) {
		List<Order> sellOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsSelling(buyOrder.getStockSymbol(), OrderType.MARKET);
		sellOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsSelling(buyOrder.getStockSymbol()));
		if (sellOrders.isEmpty()) {
			return false;	// The buyOrder is not yet completely fulfilled..
		}
		// Sort the orders by price in an ascending order.
		sellOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));		
		
		return matchSellOrdersToBuyOrder(buyOrder, sellOrders);
	}
	@Override
	public boolean fulfillLimitBuyOrder(Order buyOrder) {
		List<Order> sellOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsSelling(buyOrder.getStockSymbol(), OrderType.MARKET);
		sellOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsSellingWithMaxPrice(buyOrder.getStockSymbol(), buyOrder.getPrice()));
		if (sellOrders.isEmpty()) {
			return false;	// The buyOrder is not yet completely fulfilled..
		}
		// Sort the orders by price in an ascending order.
		sellOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));		
		
		return matchSellOrdersToBuyOrder(buyOrder, sellOrders);
	}
	@Override
	public boolean fulfillIOCBuyOrder(Order buyOrder) {
		List<Order> sellOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsSelling(buyOrder.getStockSymbol(), OrderType.MARKET);
		sellOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsSellingWithMaxPrice(buyOrder.getStockSymbol(), buyOrder.getPrice()));
		if (sellOrders.isEmpty()) {
			buyOrder.setOrderStatus(OrderStatus.FAILED);
			orderRepo.editOrder(buyOrder);
			return false;
		}
		
		// Sort the orders by price in an ascending order.
		sellOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));	
		
		if (matchSellOrdersToBuyOrder(buyOrder, sellOrders) == false) {
			buyOrder.setOrderStatus(OrderStatus.PARTIALLY_COMPLETED);
			orderRepo.editOrder(buyOrder);
			return false;
		} else {
			return true;
		}
	}
	@Override
	public boolean fulfillFOKBuyOrder(Order buyOrder) {
		List<Order> sellOrders = orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsSelling(buyOrder.getStockSymbol(), OrderType.MARKET);
		sellOrders.addAll(orderRepo.findOrdersByStockSymbolAndIsSellingWithMaxPrice(buyOrder.getStockSymbol(), buyOrder.getPrice()));
		
		if (sellOrders.isEmpty()) {
			buyOrder.setOrderStatus(OrderStatus.FAILED);
			orderRepo.editOrder(buyOrder);
			return false;
		}
		
		BigDecimal totalQuantity = sellOrders.stream().map(so->so.getRemainingQuantity()).reduce(new BigDecimal(0), (subtotal, quantity) -> subtotal.add(quantity));
		if (totalQuantity.compareTo(buyOrder.getRemainingQuantity()) < 0 ) {	
			buyOrder.setOrderStatus(OrderStatus.FAILED);
			orderRepo.editOrder(buyOrder);
			
			return false;
		}
		
		// Sort the orders by price in an ascending order.
		sellOrders.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));		
		
		return matchSellOrdersToBuyOrder(buyOrder, sellOrders);
	}
	
	@Override
	public boolean fulfillOrder(Order order) {
		if (order.getIsSelling()) {
			switch (order.getOrderType()) {
			case "MARKET" : return fulfillMarketSellOrder(order);
			case "LIMIT" : return fulfillLimitSellOrder(order);
			case "IOC" : return fulfillIOCSellOrder(order);
			case "FOK" : return fulfillFOKSellOrder(order);
			}
			
		} else {
			switch (order.getOrderType()) {
			case "MARKET" : return fulfillMarketBuyOrder(order);
			case "LIMIT" : return fulfillLimitBuyOrder(order);
			case "IOC" : return fulfillIOCBuyOrder(order);
			case "FOK" : return fulfillFOKBuyOrder(order);
			}
		}
		
		return false;
	};
	
	@Override
	public List<Order> findAllOrders() {
		return orderRepo.findAllOrders();
	}
	@Override
	public List<Order> findOrdersByStockSymbol(String stockSymbol) {
		return orderRepo.findOrdersByStockSymbol(stockSymbol);
	}
	
	@Override
	public List<Stock> findAllStocks() {
		return stockRepo.findAllStocks();
	}
	
}
