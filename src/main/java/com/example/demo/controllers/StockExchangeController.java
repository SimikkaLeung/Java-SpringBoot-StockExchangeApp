package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.models.Order;
import com.example.demo.models.Stock;
import com.example.demo.models.Trader;
import com.example.demo.models.Transaction;
import com.example.demo.services.StockExchangeService;

@Controller
public class StockExchangeController {
	
	@Autowired
	private StockExchangeService service;
	
	@GetMapping("stocks")
	public String displayOrderBookOfAllStocks(Model model) {

		List<Order> orderList = service.findAllOrders();
		model.addAttribute("orderList", orderList.toArray());
		return "allStocks";
	}
	
	@GetMapping("stocks/filter")
	public String chooseOneStock(Model model) {
		Stock selectedStock = new Stock();
		model.addAttribute("selectedStock", selectedStock);
		List<Stock> stockList = service.findAllStocks();
		model.addAttribute("stockList", stockList);
		
		return "chooseOneStock";
	}
	
	
	@PostMapping("stocks/filter")
	public String displayOrderBookOfOneStock(Model model, @ModelAttribute("selectedStock") Stock selectedStock) {
		
		List<Order> stockOrderList = service.findOrdersByStockSymbol(selectedStock.getStockSymbol());
		model.addAttribute("stockOrderList", stockOrderList);
		return "oneStock";
	}

	@GetMapping("order")
	public String placeOrder(Model model) {
		Trader trader = new Trader();
		model.addAttribute("trader", trader);
		
		Order order = new Order();
		model.addAttribute("order", order);
		List<Stock> stockList = service.findAllStocks();
		model.addAttribute("stockList", stockList);
		
		return "placeOrder";
	}
	
	@PostMapping("order")
	public String addOrder(Model model, @ModelAttribute("trader") Trader trader, @Valid @ModelAttribute("order") Order order, BindingResult result) {
		
		if (result.hasErrors()) {
			List<Stock> stockList = service.findAllStocks();
			model.addAttribute("stockList", stockList);
			return "placeOrder";
		}
		
		if (service.findTraderByTraderIdAndPin(trader.getTraderId(),trader.getPin())==null) {
			return "traderNotFound";
		}
		service.addOrder(trader, order);
		service.fulfillOrder(order);
		
		Order updatedOrder=service.findOrderByOrderId(order.getOrderId());
		model.addAttribute("updatedOrder", updatedOrder);
		
		return "orderAdded";
	}
	
	
	@GetMapping("transactions")	
	public String displayAllTransactions(Model model) {
		List<Transaction> transactionList = service.findAllTransactions();
		model.addAttribute("transactionList",transactionList);
		return "allTransactions";
	}
	
//	@PostMapping("transactions/filter")
//	public String chooseOneStockTransactions(String stockSymbol) {
//		
//		return "OneStocktransactions";
//	}
//	
//	@GetMapping("transactions/filter")
//	public String displayAllTransactionsOfOneStock(String stockSymbol) {
//		
//		return "OneStocktransactions";
//	}

	
}
