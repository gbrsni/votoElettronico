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
-- Table structure for table `candidati`
--

DROP TABLE IF EXISTS `candidati`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `candidati` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `partiti` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_partiti_idx` (`partiti`),
  CONSTRAINT `fk_partiti` FOREIGN KEY (`partiti`) REFERENCES `partiti` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `candidati`
--

LOCK TABLES `candidati` WRITE;
/*!40000 ALTER TABLE `candidati` DISABLE KEYS */;
INSERT INTO `candidati` VALUES (7,'Homer','Simpson',5),(8,'Marge','Simpson',5),(9,'Lisa','Simpson',5),(10,'Ned ','Flanders',5),(11,'Milhouse','Van Houten',5),(12,'Krusty','il Clown',5),(13,'Boe',' Szyslak',5),(14,'Seymour','Skinner',5),(15,'Charles','Montgomery Burns',5),(16,'Ralph','Winchester',5),(17,'Nelson','Muntz',5),(18,'Waylon','Smithers',5),(20,'Lino','Banfi',9),(21,'Massimo','Boldi',9),(22,'Carlo','Conti',9),(23,'Maurizio','Costanzo',9),(24,'Ezio','Greggio',9),(25,'Michelle','Hunziker',9),(26,'Frank','Matano',9),(27,'Paolo','Bonolis',9),(28,'Gerry','Scotti',9),(29,'Enrico','Papi',9),(30,'Raffaella','Carrà',9),(31,'Fabio','Fazio',9),(32,'Antonella','Clerici',9),(33,'Alberto','Angela',9),(34,'Maria','De Filippi',9),(35,'Harry','Potter',10),(36,'Ron','Weasley',10),(37,'Hermione','Granger',10),(38,'Lord','Voldemort',10),(39,'Albus','Silente',10),(40,'Severus','Piton',10),(41,'Draco','Malfoy',10),(42,'Remus','Lupin',10),(43,'Neville','Paciock',10),(44,'Rubeus','Hagrid',10),(45,'Newt','Scamandro',10),(46,'Bellatrix','Lestrange',10),(47,'Minerva','McGranitt',10),(48,'Bugs','Bunny',11),(49,'Daffy','Duck',11),(50,'Road','Runner',11),(51,'Wile','E. Coyote',11),(52,'Marvin','The Martian',11),(53,'Pepè','Le Pew',11),(54,'Porky','Pig',11),(55,'Speedy','Gonzales',11),(56,'Tasmanian','Devil',11),(57,'Lola','Bunny',11),(58,'Lewis','Hamilton',12),(59,'George','Russell',12),(60,'Max','Verstappen',12),(61,'Sergio','Perez',12),(62,'Carlos','Sainz',12),(63,'Charles','Leclerc',12),(64,'Lando','Norris',12),(65,'Daniel','Riccardo',12),(66,'Fernando','Alonso',12),(67,'Sebastian','Vettel',12),(68,'Valtteri','Bottas',12),(69,'Esteban','Ocon',12),(70,'Napoleone','Bonaparte',13),(71,'George','Washington',13),(72,'Abraham','Lincoln',13),(73,'Alessandro','Magno',13),(74,'Thomas','Jefferson',13),(75,'Giulio','Cesare',13),(76,'Cristoforo','Colombo',13),(77,'Theodore','Roosevelt',13),(78,'Benjamin','Franklin',13),(79,'George','W. Bush',13),(80,'Albert','Einstein',14),(81,'Galileo','Galilei',14),(82,'Isaac',' Newton',14),(83,'Leonardo','Da Vinci',14),(84,'Charles','Darwin',14),(85,'Nicolò','Copernico',14),(86,'Enrico','Fermi',14),(87,'Louis','Pasteur',14);
/*!40000 ALTER TABLE `candidati` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-17 14:44:58
