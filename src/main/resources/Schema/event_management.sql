-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
Database: event_management
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `esession`
--

DROP TABLE IF EXISTS `esession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `esession` (
  `esession_id` int NOT NULL AUTO_INCREMENT,
  `esession_title` varchar(100) NOT NULL,
  `start_at` timestamp NULL DEFAULT NULL,
  `end_at` timestamp NULL DEFAULT NULL,
  `event_id` int NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`esession_id`),
  KEY `esession_FK` (`event_id`),
  CONSTRAINT `esession_FK` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `esession`
--

LOCK TABLES `esession` WRITE;
/*!40000 ALTER TABLE `esession` DISABLE KEYS */;
INSERT INTO `esession` VALUES (1,'Test Session','2023-06-26 08:52:26','2023-06-27 08:52:26',1,'manual','manual','2023-06-26 13:51:26','2023-06-26 13:51:26'),(2,'Test Session 2','2023-06-27 08:52:26','2023-06-28 08:52:26',1,'manual','manual','2023-06-26 13:51:45','2023-06-26 13:51:45'),(3,'Test Session 3','2023-06-28 08:52:26','2023-06-29 08:52:26',1,'manual','manual','2023-06-26 13:52:17','2023-06-26 13:52:17'),(4,'Test Session 4','2023-06-29 08:52:26','2023-06-30 08:52:26',1,'manual','manual','2023-06-26 13:53:19','2023-06-26 13:53:19');
/*!40000 ALTER TABLE `esession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `event_name` varchar(100) NOT NULL,
  `start_at` timestamp NULL DEFAULT NULL,
  `end_at` timestamp NULL DEFAULT NULL,
  `owner` int NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_closed` tinyint NOT NULL DEFAULT '0',
  `location` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `event_FK` (`owner`),
  CONSTRAINT `event_FK` FOREIGN KEY (`owner`) REFERENCES `organizer` (`organizer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'Test event','2023-06-26 08:52:26','2023-06-30 08:52:26',2,'manual','manual','2023-06-24 08:52:26','2023-06-24 08:52:26',0,'Indore'),(2,'Test event 2','2023-06-30 08:52:26','2023-07-03 08:52:26',2,'manual','manual','2023-06-24 08:52:26','2023-06-24 08:52:26',0,' Pune'),(5,'string','2023-06-28 04:13:02','2023-06-30 04:13:02',2,'System','System','2023-06-28 09:47:26','2023-06-28 09:47:26',0,'string'),(6,'Event 4','2023-06-28 04:13:02','2023-06-30 04:13:02',2,'System','System','2023-06-28 09:49:35','2023-06-28 09:49:35',0,'Pune');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizer`
--

DROP TABLE IF EXISTS `organizer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organizer` (
  `organizer_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`organizer_id`),
  KEY `organizer_FK` (`role_id`),
  CONSTRAINT `organizer_FK` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizer`
--

LOCK TABLES `organizer` WRITE;
/*!40000 ALTER TABLE `organizer` DISABLE KEYS */;
INSERT INTO `organizer` VALUES (2,'jariyasandeep','sandeep','jariya','pass','System','System','2023-06-24 14:22:26','2023-06-25 17:02:30',1,1),(3,'Org11','Organizer','lastname','password','System','System','2023-06-30 10:40:39','2023-06-30 10:40:39',0,NULL),(4,'Org11','Organizer','lastname','password','System','System','2023-06-30 10:43:32','2023-06-30 10:43:32',0,NULL),(5,'User101','User','lastname','password','SYSTEM','SYSTEM','2023-06-30 14:11:08','2023-06-30 14:11:08',1,1);
/*!40000 ALTER TABLE `organizer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `role_id` int NOT NULL,
  `role_name` varchar(100) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Organiser'),(2,'Visitor');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitor`
--

DROP TABLE IF EXISTS `visitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitor` (
  `visitor_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`visitor_id`),
  KEY `visitor_FK` (`role_id`),
  CONSTRAINT `visitor_FK` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor`
--

LOCK TABLES `visitor` WRITE;
/*!40000 ALTER TABLE `visitor` DISABLE KEYS */;
INSERT INTO `visitor` VALUES (1,'visitor1','Visitor','First','password','System','System','2023-06-27 14:45:09','2023-06-27 14:45:09',1,2),(2,'visitor2','Visitor2','First','password','System','System','2023-06-27 14:52:32','2023-06-27 14:52:32',1,2),(3,'visitor3','Visitor3','First','password','System','System','2023-06-27 14:55:50','2023-06-27 14:55:50',1,2),(4,'visitor5','Visitor5','First','password','System','System','2023-06-27 15:08:29','2023-06-27 15:08:29',1,2),(5,'visitor6','Visitor6','First','password','System','System','2023-06-27 17:01:38','2023-06-27 17:01:38',1,2),(6,'visitor7','Visitor7','First','passwor7','System','System','2023-06-27 17:09:08','2023-06-27 17:09:08',1,2),(7,'sandeep','sandeep','jariya','password','System','System','2023-06-27 17:49:18','2023-06-27 17:49:18',1,2),(8,'sandeep1','sandeep1','jariya1','password1','System','System','2023-06-27 17:54:52','2023-06-27 17:54:52',1,2),(9,'sandeep1','sandeep1','jariya1','password1','System','System','2023-06-27 17:57:52','2023-06-27 17:57:52',1,2),(10,'sandeep1','sandeep1','jariya1','password1','System','System','2023-06-27 18:28:53','2023-06-27 18:28:53',1,2),(11,'sandeepj','sandeepj','lastname','password','System','System','2023-06-28 08:10:58','2023-06-28 08:10:58',1,2),(13,'sandeepj','sandeepj','lastname','password','System','System','2023-06-28 08:17:53','2023-06-28 08:17:53',1,2),(14,'sandeepj','sandeepj','lastname','password','System','System','2023-06-28 08:19:33','2023-06-28 08:19:33',1,2),(15,'sandeepj','sandeepj','lastname','password','System','System','2023-06-28 08:24:23','2023-06-28 08:24:23',1,2);
/*!40000 ALTER TABLE `visitor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitor_event_map`
--

DROP TABLE IF EXISTS `visitor_event_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitor_event_map` (
  `map_id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `visitor_id` int NOT NULL,
  PRIMARY KEY (`map_id`),
  KEY `visitor_event_map_FK` (`event_id`),
  KEY `visitor_event_map_FK_1` (`visitor_id`),
  CONSTRAINT `visitor_event_map_FK` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  CONSTRAINT `visitor_event_map_FK_1` FOREIGN KEY (`visitor_id`) REFERENCES `visitor` (`visitor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitor_event_map`
--

LOCK TABLES `visitor_event_map` WRITE;
/*!40000 ALTER TABLE `visitor_event_map` DISABLE KEYS */;
INSERT INTO `visitor_event_map` VALUES (1,1,15),(2,1,1),(3,1,2),(4,2,1),(5,1,3),(6,2,3);
/*!40000 ALTER TABLE `visitor_event_map` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-30 22:09:15
