package com.example.demo.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class StockRepositoryImplTest {
	@Autowired
	private StockRepository stockRepo;
	
	@Test
	void testFindStockByStockSymbol() {
		assertEquals(stockRepo.findStockByStockSymbol("TestAAPL").getStockName(), "Apple");
	}
	
	
	@Test
	void testFindAllStocks() {
		assertEquals(stockRepo.findAllStocks().size(), 5);
	}
	

}
