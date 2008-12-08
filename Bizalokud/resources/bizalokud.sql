-- MySQL dump 10.11
--
-- Host: localhost    Database: bizalokud
-- ------------------------------------------------------
-- Server version	5.0.67-0ubuntu6

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

DROP DATABASE IF EXISTS `bizalokud`;
CREATE DATABASE `bizalokud`;

USE `bizalokud`;
--
-- Table structure for table `abisuak`
--

DROP TABLE IF EXISTS `abisuak`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `abisuak` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `fk_erab_nan` varchar(9) NOT NULL,
  `mota` varchar(50) NOT NULL,
  `mezua` text NOT NULL,
  `data` datetime NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FK_ERAB_NAN_2` (`fk_erab_nan`),
  CONSTRAINT `FK_ERAB_NAN_2` FOREIGN KEY (`fk_erab_nan`) REFERENCES `erabiltzailea` (`nan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `abisuak`
--

LOCK TABLES `abisuak` WRITE;
/*!40000 ALTER TABLE `abisuak` DISABLE KEYS */;
/*!40000 ALTER TABLE `abisuak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bizikleta`
--

DROP TABLE IF EXISTS `bizikleta`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `bizikleta` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `alokatuta` tinyint(1) NOT NULL,
  `alta` tinyint(1) NOT NULL default '1',
  `egoera` enum('matxuratuta','konpontzen','galduta','ondo') NOT NULL default 'ondo',
  `modeloa` varchar(30) default NULL,
  `kolorea` varchar(20) default NULL,
  `fk_uneko_gune_id` int(10) unsigned default NULL,
  `fk_jatorri_gune_id` int(10) unsigned NOT NULL COMMENT 'Bizikleta zein gunetan ezarri zen',
  PRIMARY KEY  (`id`),
  KEY `FK_UNEKO_GUNE_ID` (`fk_uneko_gune_id`),
  KEY `FK_JATORRI_GUNE_ID` (`fk_jatorri_gune_id`),
  CONSTRAINT `FK_JATORRI_GUNE_ID` FOREIGN KEY (`fk_jatorri_gune_id`) REFERENCES `gunea` (`id`),
  CONSTRAINT `FK_UNEKO_GUNE_ID` FOREIGN KEY (`fk_uneko_gune_id`) REFERENCES `gunea` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `bizikleta`
--

LOCK TABLES `bizikleta` WRITE;
/*!40000 ALTER TABLE `bizikleta` DISABLE KEYS */;
/*!40000 ALTER TABLE `bizikleta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `erabiltzailea`
--

DROP TABLE IF EXISTS `erabiltzailea`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `erabiltzailea` (
  `nan` varchar(9) NOT NULL,
  `izena` varchar (30) NOT NULL,
  `abizenak` varchar (100) NOT NULL,
  `pasahitza` varchar(100) NOT NULL,
  `alta` tinyint(1) NOT NULL COMMENT 'true = gaituta, false = desgaituta',
  `mota` enum('erab','admin') NOT NULL COMMENT 'erabiltzailea, administratzailea',
  `eposta` varchar(50) NOT NULL,
  `telefonoa` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`nan`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `erabiltzailea`
--

LOCK TABLES `erabiltzailea` WRITE;
/*!40000 ALTER TABLE `erabiltzailea` DISABLE KEYS */;
/*!40000 ALTER TABLE `erabiltzailea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gunea`
--

DROP TABLE IF EXISTS `gunea`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `gunea` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `izena` varchar(30) NOT NULL,
  `alta` tinyint(1) NOT NULL default '1',
  `toki_kop` int(11) NOT NULL,
  `helb` varchar(100) NOT NULL,
  `ip` varchar(15),
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `gunea`
--

LOCK TABLES `gunea` WRITE;
/*!40000 ALTER TABLE `gunea` DISABLE KEYS */;
/*!40000 ALTER TABLE `gunea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ibilbidea`
--

DROP TABLE IF EXISTS `ibilbidea`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `ibilbidea` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `haisera_data` datetime NOT NULL,
  `bukaera_data` datetime default NULL,
  `fk_erab_nan` varchar(9) NOT NULL,
  `fk_bizi_id` int(10) unsigned NOT NULL,
  `fk_gunehas_id` int(10) unsigned NOT NULL,
  `fk_gunehel_id` int(10) unsigned NOT NULL,
  `bukatuta` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`id`),
  KEY `FK_ERAB_NAN` (`fk_erab_nan`),
  KEY `FK_BIZI_ID` (`fk_bizi_id`),
  KEY `FK_GUNEHAS_ID` (`fk_gunehas_id`),
  KEY `FK_GUNEHEL_ID` (`fk_gunehel_id`),
  CONSTRAINT `FK_BIZI_ID` FOREIGN KEY (`fk_bizi_id`) REFERENCES `bizikleta` (`id`),
  CONSTRAINT `FK_ERAB_NAN` FOREIGN KEY (`fk_erab_nan`) REFERENCES `erabiltzailea` (`nan`),
  CONSTRAINT `FK_GUNEHAS_ID` FOREIGN KEY (`fk_gunehas_id`) REFERENCES `gunea` (`id`),
  CONSTRAINT `FK_GUNEHEL_ID` FOREIGN KEY (`fk_gunehel_id`) REFERENCES `gunea` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `ibilbidea`
--

LOCK TABLES `ibilbidea` WRITE;
/*!40000 ALTER TABLE `ibilbidea` DISABLE KEYS */;
/*!40000 ALTER TABLE `ibilbidea` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `informazioa`
--

DROP TABLE IF EXISTS `informazioa`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `informazioa` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `data` datetime NOT NULL,
  `mezua` text NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `informazioa`
--

LOCK TABLES `informazioa` WRITE;
/*!40000 ALTER TABLE `informazioa` DISABLE KEYS */;
/*!40000 ALTER TABLE `informazioa` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2008-11-15 12:10:38