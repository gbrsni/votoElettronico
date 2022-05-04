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
-- Table structure for table `elettori`
--

DROP TABLE IF EXISTS `elettori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `elettori` (
  `username` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `cognome` varchar(45) NOT NULL,
  `codicefiscale` varchar(16) NOT NULL,
  `tesseraelettorale` varchar(9) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `tesseraelettorale_UNIQUE` (`tesseraelettorale`),
  UNIQUE KEY `codicefiscale_UNIQUE` (`codicefiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elettori`
--

LOCK TABLES `elettori` WRITE;
/*!40000 ALTER TABLE `elettori` DISABLE KEYS */;
INSERT INTO `elettori` VALUES ('alida','Alida','Giachetto','GCHLDA48S53G248X','647978985'),('clara','Clara','Ferrari','BL18460525','321267135'),('fabio','Fabio','Marmolani','MRMFBA70A04D384I','540641210'),('fabrizio','Fabrizio','Zetticci','DS20553276','422935000'),('gioia','Gioia','Ricci','RO84546847','753550992'),('guglielmo','Guglielmo','Mancini','ED0100274','195152896'),('ivana','Ivana','Rizzo','EZ70314026','478666981'),('leonardo','Leonardo','Milani','CL51898862','194318598'),('lidia','Lidia','Pignattari','PGNLDI73S45G347I','124319969'),('marco','Marco','Folliero','XB79973402','488250684'),('maurizio','Maurizio','Ferrari','EN10468027','954800733'),('melissa','Melissa','Lombardo','UZ50567743','574006726'),('osa','Osanna','Tantini','TNTSNN35R62I738O','419389370'),('prova','prova','prova','prova','0'),('sergi','Sergio','Cavagliato','CVGSRG69M12D356V','414828764'),('stefano','Stefano','Buccho','AQ86986925','429826442'),('vitto','Vittorio','Casiglieri','CSGVTR51M03B839R','269178263');
/*!40000 ALTER TABLE `elettori` ENABLE KEYS */;
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
