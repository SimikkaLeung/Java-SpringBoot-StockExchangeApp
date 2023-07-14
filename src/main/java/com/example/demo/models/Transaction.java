package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
	
	private Integer transactionId;
	private Integer buyerOrderId;
	private Integer sellerOrderId;
	private BigDecimal quantity;
	private BigDecimal price ;
	private LocalDateTime transactionTime;

	public Transaction() {
		
	}
	
	

	public Transaction(Integer transactionId, Integer buyerOrderId, Integer sellerOrderId, BigDecimal quantity,
			BigDecimal price) {
		super();
		this.transactionId = transactionId;
		this.buyerOrderId = buyerOrderId;
		this.sellerOrderId = sellerOrderId;
		this.quantity = quantity;
		this.price = price;
		this.transactionTime = LocalDateTime.now();
	}



	public Transaction(Integer transactionId, Integer buyerOrderId, Integer sellerOrderId, BigDecimal quantity,
			BigDecimal price, LocalDateTime transactionTime) {
		super();
		this.transactionId = transactionId;
		this.buyerOrderId = buyerOrderId;
		this.sellerOrderId = sellerOrderId;
		this.quantity = quantity;
		this.price = price;
		this.transactionTime = transactionTime;
	}



	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getBuyerOrderId() {
		return buyerOrderId;
	}

	public void setBuyerOrderId(Integer buyOrderId) {
		this.buyerOrderId = buyOrderId;
	}

	public Integer getSellerOrderId() {
		return sellerOrderId;
	}

	public void setSellerOrderId(Integer sellOrderId) {
		this.sellerOrderId = sellOrderId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}



	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", buyOrderId=" + buyerOrderId + ", sellOrderId="
				+ sellerOrderId + ", quantity=" + quantity + ", price=" + price + ", transactionTime=" + transactionTime
				+ "]";
	}

	
}
