-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: stockexchangetest
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL,
  `trader_id` int NOT NULL,
  `stock_symbol` varchar(20) NOT NULL,
  `is_selling` tinyint(1) NOT NULL,
  `order_type` enum('MARKET','LIMIT','IOC','FOK') NOT NULL,
  `price` decimal(10,4) DEFAULT NULL,
  `initial_quantity` decimal(16,4) NOT NULL,
  `remaining_quantity` decimal(16,4) NOT NULL,
  `order_status` enum('COMPLETED','IN_PROGRESS','PARTIALLY_COMPLETED','FAILED') NOT NULL,
  `order_time` datetime NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `trader_id` (`trader_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`trader_id`) REFERENCES `traders` (`trader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'GOOGL',1,'LIMIT',130.0000,50.0000,0.0000,'COMPLETED','2023-06-01 00:00:00'),(2,2,'META',1,'LIMIT',300.0000,100.0000,70.0000,'IN_PROGRESS','2023-06-01 00:00:01'),(3,3,'GOOGL',0,'LIMIT',131.0000,50.0000,0.0000,'COMPLETED','2023-06-01 00:00:02'),(4,1,'META',0,'MARKET',0.0000,30.0000,0.0000,'COMPLETED','2023-06-01 00:00:03'),(5,1,'AMZN',1,'LIMIT',140.0000,60.0000,60.0000,'IN_PROGRESS','2023-06-01 00:00:04');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocks`
--

DROP TABLE IF EXISTS `stocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stocks` (
  `stock_symbol` varchar(20) NOT NULL,
  `stock_name` varchar(255) NOT NULL,
  PRIMARY KEY (`stock_symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocks`
--

LOCK TABLES `stocks` WRITE;
/*!40000 ALTER TABLE `stocks` DISABLE KEYS */;
INSERT INTO `stocks` VALUES ('TestAAPL','Apple'),('TestAMZN','Amazon'),('TestGOOGL','Google'),('TestMETA','Meta'),('TestNFLX','Netflix');
/*!40000 ALTER TABLE `stocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `traders`
--

DROP TABLE IF EXISTS `traders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `traders` (
  `trader_id` int NOT NULL,
  `trader_name` varchar(255) NOT NULL,
  `pin` varchar(8) NOT NULL,
  PRIMARY KEY (`trader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `traders`
--

LOCK TABLES `traders` WRITE;
/*!40000 ALTER TABLE `traders` DISABLE KEYS */;
INSERT INTO `traders` VALUES (1,'Test Sim Leung','12345678'),(2,'Test Peter Pan','12345678'),(3,'Test Joanna Lam','12345678');
/*!40000 ALTER TABLE `traders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transaction_id` int NOT NULL,
  `buyer_order_id` int NOT NULL,
  `seller_order_id` int NOT NULL,
  `quantity` decimal(16,4) NOT NULL,
  `price` decimal(10,4) NOT NULL,
  `transaction_time` datetime NOT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `buyer_order_id` (`buyer_order_id`),
  KEY `seller_order_id` (`seller_order_id`),
  CONSTRAINT `transactions_ibfk_1` FOREIGN KEY (`buyer_order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `transactions_ibfk_2` FOREIGN KEY (`seller_order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (1,3,1,50.0000,130.0000,'2023-06-01 00:00:02'),(2,4,2,30.0000,300.0000,'2023-06-01 00:00:03');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-11 20:13:54
