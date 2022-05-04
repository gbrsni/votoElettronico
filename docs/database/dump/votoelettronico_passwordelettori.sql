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
-- Table structure for table `passwordelettori`
--

DROP TABLE IF EXISTS `passwordelettori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `passwordelettori` (
  `elettori` varchar(45) NOT NULL,
  `salt` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hash` varchar(200) NOT NULL,
  PRIMARY KEY (`elettori`),
  CONSTRAINT `fk_elettori_passwordelettori` FOREIGN KEY (`elettori`) REFERENCES `elettori` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passwordelettori`
--

LOCK TABLES `passwordelettori` WRITE;
/*!40000 ALTER TABLE `passwordelettori` DISABLE KEYS */;
INSERT INTO `passwordelettori` VALUES ('alida','��;:3Ӗ�','-1486702848'),('clara','����>','1728228794'),('fabio','[ٔЕM�k','650482284'),('fabrizio','̾��nN��','-489061252'),('gioia','�-���\\�S','-1361807286'),('guglielmo','��|�W��<','2010977797'),('ivana','��U[/','-2034731592'),('leonardo','�j�Na�W','-1480706025'),('lidia','�i�2�Z+','-768221785'),('marco','&J���','1993371161'),('maurizio','��9�\r��?','786754238'),('melissa','0As���','-407814102'),('osa',':;V:��','204973334'),('sergi','��}��w�t','-2007730253'),('stefano','9��e\\f','-506923033'),('vitto','��s��','1519685879');
/*!40000 ALTER TABLE `passwordelettori` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-04 10:06:55
