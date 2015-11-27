CREATE DATABASE  IF NOT EXISTS `aviia_my_work` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `aviia_my_work`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: aviia_my_work
-- ------------------------------------------------------
-- Server version	5.5.25

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
-- Table structure for table `spr_place`
--

DROP TABLE IF EXISTS `spr_place`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spr_place` (
  `id` bigint(20) NOT NULL,
  `seatLetter` char(1) NOT NULL,
  `seatNumber` int(100) NOT NULL,
  `flight_class_id` bigint(20) NOT NULL COMMENT 'Какому классу принадлежит это место Эконом или бизнес класс',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idspr_place_UNIQUE` (`id`),
  UNIQUE KEY `index_uniq1` (`seatLetter`,`seatNumber`),
  KEY `fk_flight_class_idx` (`flight_class_id`),
  CONSTRAINT `fk_flight_class` FOREIGN KEY (`flight_class_id`) REFERENCES `spr_flight_class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Справочник мест';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spr_place`
--

LOCK TABLES `spr_place` WRITE;
/*!40000 ALTER TABLE `spr_place` DISABLE KEYS */;
/*!40000 ALTER TABLE `spr_place` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-19 18:02:18
