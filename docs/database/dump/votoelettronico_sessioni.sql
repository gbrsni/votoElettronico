-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: votoelettronico
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `sessioni`
--

DROP TABLE IF EXISTS `sessioni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessioni` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `descrizione` text,
  `data` date DEFAULT NULL,
  `modvoto` enum('ORDINALE','CATEGORICO','CATEGORICO_CON_PREFERENZE','REFERENDUM') DEFAULT NULL,
  `modvittoria` enum('MAGGIORANZA','MAGGIORANZA_ASSOLUTA','REFERENDUM_SENZA_QUORUM','REFERENDUM_CON_QUORUM') DEFAULT NULL,
  `stato` enum('CHIUSA','IN_CORSO','CONCLUSA','SCRUTINATA') DEFAULT NULL,
  `nvoti` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessioni`
--

LOCK TABLES `sessioni` WRITE;
/*!40000 ALTER TABLE `sessioni` DISABLE KEYS */;
INSERT INTO `sessioni` VALUES (26,'Elezioni rappresentanti','elezioni belle','3922-04-10','CATEGORICO','MAGGIORANZA','CONCLUSA',0),(27,'elezioni comunali','elezioni rappresentatnti comune','3922-04-14','CATEGORICO','MAGGIORANZA','IN_CORSO',0),(28,'Elezioni Centro Anziani','elezioni per decidere chi organizzerà il torneo di briscola','3922-04-16','ORDINALE','MAGGIORANZA','IN_CORSO',0),(29,'prova','prova','3922-04-21','ORDINALE','MAGGIORANZA','IN_CORSO',0),(30,'a','a','3922-03-25','CATEGORICO','MAGGIORANZA_ASSOLUTA','CHIUSA',0),(31,'prova inserimento nuovi metodi','tentativo di inserimento partito con i nuovi metodi','3922-04-24','ORDINALE','MAGGIORANZA','CHIUSA',0);
/*!40000 ALTER TABLE `sessioni` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-26  8:14:04
