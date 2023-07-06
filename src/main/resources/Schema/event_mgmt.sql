-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: event_mgmt
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
INSERT INTO `esession` VALUES (1,'Test Session','2023-06-26 03:22:26','2023-06-27 03:22:26',1,'manual','manual','2023-06-26 08:21:26','2023-06-26 08:21:26'),(2,'Test Session 2','2023-06-27 03:22:26','2023-06-28 03:22:26',1,'manual','manual','2023-06-26 08:21:45','2023-06-26 08:21:45'),(3,'Test Session 3','2023-06-28 03:22:26','2023-06-29 03:22:26',1,'manual','manual','2023-06-26 08:22:17','2023-06-26 08:22:17'),(4,'Test Session 4','2023-06-29 03:22:26','2023-06-30 03:22:26',1,'manual','manual','2023-06-26 08:23:19','2023-06-26 08:23:19');
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
  CONSTRAINT `event_FK` FOREIGN KEY (`owner`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'Test event','2023-06-26 03:22:26','2023-06-30 03:22:26',1,'manual','manual','2023-06-24 03:22:26','2023-06-24 03:22:26',0,'Indore'),(2,'Test event 2','2023-06-30 03:22:26','2023-07-03 03:22:26',1,'manual','manual','2023-06-24 03:22:26','2023-06-24 03:22:26',0,' Pune'),(5,'string','2023-06-27 22:43:02','2023-06-29 22:43:02',1,'System','System','2023-06-28 04:17:26','2023-06-28 04:17:26',0,'string'),(6,'Event 4','2023-06-27 22:43:02','2023-06-29 22:43:02',1,'System','System','2023-06-28 04:19:35','2023-06-28 04:19:35',0,'Pune'),(7,'NEw Event','2023-07-05 09:15:08','2023-07-10 09:15:08',1,'Organizer_user','Organizer_user','2023-07-05 14:46:42','2023-07-05 14:46:42',0,'Indore');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
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
INSERT INTO `role` VALUES (1,'ORGANIZER'),(2,'VISITOR');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`user_id`),
  KEY `user_FK` (`role_id`),
  CONSTRAINT `user_FK` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'organizer1','sandeep','jariya','$2a$10$9CPLVhFmVDZRfT.jx30qk.6Gc2MmhZLMNfJ8YumUvKvWxWHJV4V/m','System','System','2023-06-24 08:52:26','2023-06-25 11:32:30',1,1),(2,'visitor1','sandeep','jariya','$2a$10$9CPLVhFmVDZRfT.jx30qk.6Gc2MmhZLMNfJ8YumUvKvWxWHJV4V/m','System','System','2023-06-24 08:52:26','2023-06-25 11:32:30',1,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_event_map`
--

DROP TABLE IF EXISTS `user_event_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_event_map` (
  `map_id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`map_id`),
  KEY `user_event_map_FK` (`event_id`),
  KEY `user_event_map_FK_1` (`user_id`),
  CONSTRAINT `user_event_map_FK` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  CONSTRAINT `user_event_map_FK_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_event_map`
--

LOCK TABLES `user_event_map` WRITE;
/*!40000 ALTER TABLE `user_event_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_event_map` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-06 13:22:44
