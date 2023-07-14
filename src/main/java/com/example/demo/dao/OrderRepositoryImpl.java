package com.example.demo.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.models.Order;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int addOrder(Order order) {
		return jdbcTemplate.update("INSERT INTO orders VALUES(?,?,?,?,?,?,?,?,?,?)", 
				order.getOrderId(), order.getTraderId(), order.getStockSymbol(), 
				order.getIsSelling(), order.getOrderType(), order.getPrice(), order.getInitialQuantity(),
				order.getRemainingQuantity(), order.getOrderStatus(), order.getOrderTime());
	}

	@Override
	public Order findOrderByOrderId(Integer orderId) {
		return jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = ?", BeanPropertyRowMapper.newInstance(Order.class), orderId);
	}

	@Override
	public List<Order> findOrdersByTraderId(Integer traderId) {
		return jdbcTemplate.query("SELECT * FROM orders WHERE trader_id = ?", BeanPropertyRowMapper.newInstance(Order.class), traderId);
	}

	@Override
	public List<Order> findOrdersByStockSymbol(String stockSymbol) {
		return jdbcTemplate.query("SELECT * FROM orders WHERE stock_symbol = ?", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol);
	}

	@Override
	public List<Order> findOrdersByStockSymbolAndIsSelling(String stockSymbol) {
		return jdbcTemplate.query("SELECT * FROM orders WHERE is_selling = 1 AND stock_symbol = ? AND order_type != 'MARKET' AND order_status = 'IN_PROGRESS' ORDER BY price ASC", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol);
	}

	@Override
	public List<Order> findOrdersByStockSymbolAndIsSellingWithMaxPrice(String stockSymbol, BigDecimal maxPrice) {
		return jdbcTemplate.query("SELECT * FROM orders WHERE is_selling = 1 AND stock_symbol = ? AND price <= ? AND order_type != 'MARKET' AND order_status = 'IN_PROGRESS' ORDER BY price ASC", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol, maxPrice);
	}

	@Override
	public List<Order> findOrdersByStockSymbolAndIsNotSelling(String stockSymbol) {
		return jdbcTemplate.query("SELECT * FROM orders WHERE is_selling = 0 AND stock_symbol = ? AND order_type != 'MARKET' AND order_status = 'IN_PROGRESS' ORDER BY price DESC", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol);
	}

	@Override
	public List<Order> findOrdersByStockSymbolAndIsNotSellingWithMinPrice(String stockSymbol, BigDecimal minPrice) {
		return jdbcTemplate.query("SELECT * FROM orders WHERE is_selling = 0 AND stock_symbol = ? AND price >= ? AND order_type != 'MARKET' AND order_status = 'IN_PROGRESS' ORDER BY price DESC", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol, minPrice);
	}

	@Override
	public List<Order> findOrdersByStockSymbolAndOrderStatus(String stockSymbol, OrderStatus orderStatus) {
		return jdbcTemplate.query("SELECT * FROM orders WHERE stock_symbol = ? AND order_status = ?", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol, orderStatus.name());
	}

	@Override
	public List<Order> findOrdersByStockSymbolAndOrderTypeAndIsSelling(String stockSymbol, OrderType orderType){
		return jdbcTemplate.query("SELECT * FROM orders WHERE stock_symbol = ? AND order_type = ? AND is_selling = 1 AND order_status = 'IN_PROGRESS'", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol, orderType.name());
	}
	
	@Override
	public List<Order> findOrdersByStockSymbolAndOrderTypeAndIsNotSelling(String stockSymbol, OrderType orderType){
		return jdbcTemplate.query("SELECT * FROM orders WHERE stock_symbol = ? AND order_type = ? AND is_selling = 0 AND order_status = 'IN_PROGRESS'", BeanPropertyRowMapper.newInstance(Order.class), stockSymbol, orderType.name());
	}
	
	@Override
	public int editOrder(Order order) {
		// TODO Auto-generated method stub
		return jdbcTemplate.update("UPDATE orders SET trader_id = ?, stock_symbol = ?, is_selling = ?, order_type = ?, price = ?, initial_quantity = ?, remaining_quantity = ?, order_status = ?, order_time = ? WHERE order_id = ?", 
				order.getTraderId(), order.getStockSymbol(), 
				order.getIsSelling(), order.getOrderType(), order.getPrice(), order.getInitialQuantity(),
				order.getRemainingQuantity(), order.getOrderStatus(), order.getOrderTime(), order.getOrderId());
	}

	@Override
	public List<Order> findAllOrders() {
		// TODO Auto-generated method stub
		
		return jdbcTemplate.query("SELECT * FROM orders", BeanPropertyRowMapper.newInstance(Order.class));
	}

	@Override
	public Integer findMaxOrderId() {
		// TODO Auto-generated method stub
		return jdbcTemplate.queryForObject("SELECT MAX(order_id) FROM orders", Integer.class);
	}

	@Override
	public int deleteOrder(Integer orderId) {
		return jdbcTemplate.update("DELETE FROM orders WHERE order_id = ?", orderId);
	};	
}
