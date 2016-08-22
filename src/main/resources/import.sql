CREATE DATABASE  IF NOT EXISTS `comparc` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `comparc`;
-- MySQL dump 10.13  Distrib 5.5.50, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: comparc
-- ------------------------------------------------------
-- Server version	5.5.50-0ubuntu0.14.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `inst_format`
--

DROP TABLE IF EXISTS `inst_format`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inst_format` (
  `id` int(11) NOT NULL,
  `fifth_param` varchar(255) DEFAULT NULL,
  `func` varchar(255) DEFAULT NULL,
  `immediate` bit(1) DEFAULT NULL,
  `instruction` varchar(255) DEFAULT NULL,
  `instruction_format_string` varchar(255) DEFAULT NULL,
  `instruction_type` varchar(255) DEFAULT NULL,
  `offset` bit(1) DEFAULT NULL,
  `op_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inst_format`
--

LOCK TABLES `inst_format` WRITE;
/*!40000 ALTER TABLE `inst_format` DISABLE KEYS */;
INSERT INTO `inst_format` VALUES (1,'','','','DADDIU','DADDIU rt, rs, immediate','I','\0','011001'),(2,NULL,NULL,'\0','LD','LD rt, offset(base)','I','','110111'),(3,'','','\0','SD','SD rt, offset(base)','I','','111111'),(4,'00000','100101','\0','OR','OR rd,rs,rt','R','\0','000000'),(5,'00000','101010','\0','SLT','SLT rd,rs,rt','R','\0','000000'),(6,'00000','010110','\0','DSRLV','DSRLV rd,rt,rs','R','\0','000000'),(7,NULL,'','\0','BNE','BNE rs,rt,offset','I','','000101'),(8,'','','\0','J','J instr_index','J','\0','000010'),(9,'','','\0','NOP','NOP','I','\0',NULL);
/*!40000 ALTER TABLE `inst_format` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-08-19 21:26:34
