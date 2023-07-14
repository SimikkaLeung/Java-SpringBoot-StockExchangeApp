package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;

public class Order {
	private Integer orderId;
	private Integer traderId; 
	private String stockSymbol;
	private boolean isSelling;
	private String orderType;
	
	@NotNull
	@DecimalMin(value="0.0000", message="The price must be zero or higher.")
	@DecimalMax(value="99999.9999", message="Our online tool cannot handle this amount. Please enter a number between 0 and 99999.9999, inclusive.")
	private BigDecimal price;
	@NotNull
	@DecimalMin(value="0.0000", message="The price must be zero or higher.")
	@DecimalMax(value="99999.9999", message="Our online tool cannot handle this amount. Please enter a number between 0 and 99999.9999, inclusive.")
	private BigDecimal initialQuantity;
	private BigDecimal remainingQuantity;
	private String orderStatus;
	private LocalDateTime orderTime;
	
	public Order() {
		
	}




	public Order(Integer orderId, Integer traderId, String stockSymbol, boolean isSelling, String orderType,
			BigDecimal price, BigDecimal initialQuantity, BigDecimal remainingQuantity, String orderStatus) {
		super();
		this.orderId = orderId;
		this.traderId = traderId;
		this.stockSymbol = stockSymbol;
		this.isSelling = isSelling;
		this.orderType = orderType;		
		this.price = price;		
		this.initialQuantity = initialQuantity;
		this.remainingQuantity = remainingQuantity;
		this.orderStatus = orderStatus;
		this.orderTime = LocalDateTime.now();
	}


	public Order(Integer orderId, Integer traderId, String stockSymbol, boolean isSelling, String orderType,
			BigDecimal price, BigDecimal initialQuantity, BigDecimal remainingQuantity, String orderStatus,
			LocalDateTime orderTime) {
		super();
		this.orderId = orderId;
		this.traderId = traderId;
		this.stockSymbol = stockSymbol;
		this.isSelling = isSelling;
		this.orderType = orderType;
		this.price = price;
		this.initialQuantity = initialQuantity;
		this.remainingQuantity = remainingQuantity;
		this.orderStatus = orderStatus;
		this.orderTime = orderTime;
	}


	public Integer getOrderId() {
		return orderId;
	}



	public Integer getTraderId() {
		return traderId;
	}




	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}




	public String getStockSymbol() {
		return stockSymbol;
	}




	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}



	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}



	public boolean getIsSelling() {
		return isSelling;
	}



	public void setIsSelling(boolean isSelling) {
		this.isSelling = isSelling;
	}

	

	public String getOrderType() {
		return orderType;
	}



	public void setOrderType(OrderType orderType) {
		this.orderType = orderType.name();
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


//	public OrderType getOrderType() {
//		return orderType;
//	}
//
//
//	public void setOrderType(OrderType orderType) {
//		this.orderType = orderType;
//	}
//	
//	public void setOrderType(String orderType) {
//		this.orderType = OrderType.valueOf(orderType);
//	}
//	



	public BigDecimal getPrice() {
		
		return price;
	}



	public void setPrice(BigDecimal price) {
//		if (price.compareTo(new BigDecimal(0))==0) {
//			this.price = null;
//		} else {
//			this.price = price;
//		}
		this.price = price;
	}



	public BigDecimal getInitialQuantity() {
		return initialQuantity;
	}



	public void setInitialQuantity(BigDecimal initialQuantity) {
		this.initialQuantity = initialQuantity;
	}



	public BigDecimal getRemainingQuantity() {
		return remainingQuantity;
	}



	public void setRemainingQuantity(BigDecimal remainingQuantity) {
		this.remainingQuantity = remainingQuantity;
	}



	public String getOrderStatus() {
		return orderStatus;
	}



	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus.name();
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}


	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}




	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", traderId=" + traderId + ", stockSymbol=" + stockSymbol + ", isSelling="
				+ isSelling + ", orderType=" + orderType + ", price=" + price + ", initialQuantity=" + initialQuantity
				+ ", remainingQuantity=" + remainingQuantity + ", orderStatus=" + orderStatus + ", orderTime="
				+ orderTime + "]";
	}



	
	
}
