package com.example.demo.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.models.Order;
@SpringBootTest
class OrderRepositoryImplTest {
	@Autowired
	private OrderRepository orderRepo;
	@Test
	void testAddOrderDeleteOrder() {
		Order order = new Order(99,1,"META",true,"FOK",new BigDecimal(200),new BigDecimal(200),new BigDecimal(200),"IN_PROGRESS");
		assertEquals(1,orderRepo.addOrder(order));
		assertEquals(1,orderRepo.deleteOrder(99));
	}
	
	@Test
	void testFindOrderByOrderId() {
		assertEquals(1,orderRepo.findOrderByOrderId(1).getOrderId());
	}
	
	@Test
	void testFindOrdersByTraderId() {
		assertEquals(3,orderRepo.findOrdersByTraderId(1).size());
	}	
	
	@Test
	void testFindOrdersByStockSymbol() {
		assertEquals(2,orderRepo.findOrdersByStockSymbol("GOOGL").size());
	}	
	
	@Test
	void testFindOrdersByStockSymbolAndIsSelling() {
		Order order = new Order(100,1,"GOOGL",true,"FOK",new BigDecimal(130),new BigDecimal(200),new BigDecimal(200),"IN_PROGRESS");
		orderRepo.addOrder(order);
		
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndIsSelling("GOOGL").size());
		orderRepo.deleteOrder(100);
	}	
	
	@Test
	void testFindOrdersByStockSymbolAndIsSellingWithMaxPrice() {
		Order order = new Order(101,1,"GOOGL",true,"FOK",new BigDecimal(130),new BigDecimal(200),new BigDecimal(200),"IN_PROGRESS");
		orderRepo.addOrder(order);
		
		assertEquals(0,orderRepo.findOrdersByStockSymbolAndIsSellingWithMaxPrice("GOOGL", new BigDecimal(129)).size());
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndIsSellingWithMaxPrice("GOOGL", new BigDecimal(130)).size());
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndIsSellingWithMaxPrice("GOOGL", new BigDecimal(131)).size());
		
		orderRepo.deleteOrder(101);
	}	
	
	
	
	@Test
	void testFindOrdersByStockSymbolAndIsNotSelling() {
		Order order = new Order(102,1,"GOOGL",false,"FOK",new BigDecimal(130),new BigDecimal(200),new BigDecimal(200),"IN_PROGRESS");
		orderRepo.addOrder(order);
		
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndIsNotSelling("GOOGL").size());
		orderRepo.deleteOrder(102);
	}	
	
	@Test
	void testFindOrdersByStockSymbolAndIsNotSellingWithMinPrice() {
		Order order = new Order(103,1,"GOOGL",false,"FOK",new BigDecimal(130),new BigDecimal(200),new BigDecimal(200),"IN_PROGRESS");
		orderRepo.addOrder(order);
		
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndIsNotSellingWithMinPrice("GOOGL", new BigDecimal(129)).size());
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndIsNotSellingWithMinPrice("GOOGL", new BigDecimal(130)).size());
		assertEquals(0,orderRepo.findOrdersByStockSymbolAndIsNotSellingWithMinPrice("GOOGL", new BigDecimal(131)).size());
		
		orderRepo.deleteOrder(103);
	}	
	
	@Test
	void testFindOrdersByStockSymbolAndOrderStatus() {
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndOrderStatus("META", OrderStatus.COMPLETED).size());
	}	
	
	@Test
	void testFindOrdersByStockSymbolAndOrderTypeAndIsSelling() {
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsSelling("META", OrderType.LIMIT).size());
	}	
	
	@Test
	void testFindOrdersByStockSymbolAndOrderTypeAndIsNotSelling() {
		Order order = new Order(104,1,"AMZN",false,"LIMIT",new BigDecimal(145),new BigDecimal(200),new BigDecimal(200),"IN_PROGRESS");
		orderRepo.addOrder(order);
		assertEquals(1,orderRepo.findOrdersByStockSymbolAndOrderTypeAndIsNotSelling("AMZN", OrderType.LIMIT).size());
		
		orderRepo.deleteOrder(104);
		
	}	
	
	
	@Test
	void testEditOrder() {
		
		Order order = orderRepo.findOrderByOrderId(5);
		BigDecimal originalPrice = order.getPrice();
		
		order.setPrice(new BigDecimal(500));
		assertEquals(1, orderRepo.editOrder(order));
		assertEquals(0,orderRepo.findOrderByOrderId(5).getPrice().compareTo(new BigDecimal(500)));
		
		order.setPrice(originalPrice);
		assertEquals(1, orderRepo.editOrder(order));
		assertEquals(0,orderRepo.findOrderByOrderId(5).getPrice().compareTo(originalPrice));
	}	
	
	@Test
	void testFindAllOrders() {
		assertEquals(5, orderRepo.findAllOrders().size());
	}	
	
	@Test
	void testFindMaxOrderId() {
		assertEquals(5, orderRepo.findMaxOrderId());
	}	
	
}
