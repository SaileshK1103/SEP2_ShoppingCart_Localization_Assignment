-- MySQL dump 10.13  Distrib 5.7.24, for osx11.1 (x86_64)
--
-- Host: 127.0.0.1    Database: shopping_cart_localization
-- ------------------------------------------------------
-- Server version	11.5.2-MariaDB

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
-- Table structure for table `ui_messages`
--

DROP TABLE IF EXISTS `ui_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ui_messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `msg_key` varchar(100) DEFAULT NULL,
  `language_code` varchar(10) DEFAULT NULL,
  `msg_value` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `msg_key` (`msg_key`,`language_code`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ui_messages`
--

LOCK TABLES `ui_messages` WRITE;
/*!40000 ALTER TABLE `ui_messages` DISABLE KEYS */;
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (1,'msg.price','en','Price ($):');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (2,'msg.price','ja','「商品の価格を入力してください:」');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (3,'msg.price','fi','Syötä hinta:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (4,'msg.price','ar','أدخل السعر:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (5,'msg.quantity','en','Quantity');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (6,'msg.quantity','ja','「商品の数量を入力してください:」');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (7,'msg.quantity','fi','Syötä määrä:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (8,'msg.quantity','ar','أدخل الكمية:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (9,'msg.total','en','Total');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (10,'msg.total','ja','「合計金額:」');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (11,'msg.total','fi','Yhteensä:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (12,'msg.total','ar','المجموع:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (13,'msg.calculate','en','Calculate');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (14,'msg.calculate','ja','合計を計算する');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (15,'msg.calculate','fi','Laske yhteensä');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (16,'msg.calculate','ar','احسب المجموع');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (17,'msg.price','sv','Ange priset för varan:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (18,'msg.quantity','sv','Ange mängden varor:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (19,'msg.total','sv','Total kostnad:');
INSERT INTO `ui_messages` (`id`, `msg_key`, `language_code`, `msg_value`) VALUES (20,'msg.calculate','sv','Beräkna totalt');
/*!40000 ALTER TABLE `ui_messages` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-07 17:34:32

--
-- Table structure for table `cart_records`
--

CREATE TABLE IF NOT EXISTS `cart_records` (
                                              `id` int(11) NOT NULL AUTO_INCREMENT,
    `total_items` int(11) NOT NULL,
    `total_cost` double NOT NULL,
    `language` varchar(10) DEFAULT NULL,
    `created_at` timestamp DEFAULT current_timestamp(),
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `cart_items`
--

CREATE TABLE IF NOT EXISTS `cart_items` (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
    `cart_record_id` int(11) DEFAULT NULL,
    `item_number` int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `cart_record_id` (`cart_record_id`),
    CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`cart_record_id`) REFERENCES `cart_records` (`id`) ON DELETE CASCADE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
