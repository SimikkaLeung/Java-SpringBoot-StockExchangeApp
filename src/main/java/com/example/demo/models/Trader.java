package com.example.demo.models;

public class Trader {
	private Integer traderId; 
	private String traderName;
	private String pin;
	
	public Trader() {		
	}

	public Trader(Integer traderId, String traderName, String pin) {
		super();
		this.traderId = traderId;
		this.traderName = traderName;
		this.pin = pin;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	@Override
	public String toString() {
		return "Trader [traderId=" + traderId + ", traderName=" + traderName + ", pin=" + pin + "]";
	}
	
	
	
	
}
