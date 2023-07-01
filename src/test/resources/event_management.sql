

CREATE DATABASE IF NOT EXISTS `event_management` ;

USE `event_management`;

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `role_id` int NOT NULL,
  `role_name` varchar(100) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


--LOCK TABLES `role` WRITE;

INSERT INTO `role` VALUES (1,'Organiser'),(2,'Visitor');

--UNLOCK TABLES;

DROP TABLE IF EXISTS `organizer`;
CREATE TABLE `organizer` (
  `organizer_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`organizer_id`),
  KEY `organizer_FK` (`role_id`),
  CONSTRAINT `organizer_FK` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;

DROP TABLE IF EXISTS `event`;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;



DROP TABLE IF EXISTS `esession`;

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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;



DROP TABLE IF EXISTS `visitor`;

CREATE TABLE `visitor` (
  `visitor_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `created_by` varchar(100) DEFAULT NULL,
  `updated_by` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `is_active` tinyint NOT NULL DEFAULT '1',
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`visitor_id`),
  KEY `visitor_FK` (`role_id`),
  CONSTRAINT `visitor_FK` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;


DROP TABLE IF EXISTS `visitor_event_map`;

CREATE TABLE `visitor_event_map` (
  `map_id` int NOT NULL AUTO_INCREMENT,
  `event_id` int NOT NULL,
  `visitor_id` int NOT NULL,
  PRIMARY KEY (`map_id`),
  KEY `visitor_event_map_FK` (`event_id`),
  KEY `visitor_event_map_FK_1` (`visitor_id`),
  CONSTRAINT `visitor_event_map_FK` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`),
  CONSTRAINT `visitor_event_map_FK_1` FOREIGN KEY (`visitor_id`) REFERENCES `visitor` (`visitor_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ;

