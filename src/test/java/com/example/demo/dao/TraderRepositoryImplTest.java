package com.example.demo.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class TraderRepositoryImplTest {
	@Autowired
	private TraderRepository traderRepo;
	
	@Test
	void testFindTraderByTraderId() {
		assertEquals(traderRepo.findTraderByTraderId(1).getTraderName().trim(),"Test Sim Leung");
	}
	
	@Test
	void testFindTraderByTraderIdAndPin() {
		assertEquals(traderRepo.findTraderByTraderIdAndPin(1,"12345678").getTraderName().trim(),"Test Sim Leung");
		assertNotNull(traderRepo.findTraderByTraderIdAndPin(1,"12345678"));
	}
	
}
