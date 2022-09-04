CREATE DATABASE  IF NOT EXISTS `hotel`;
USE `hotel`;
DROP TABLE IF EXISTS `approved_room_order`;
CREATE TABLE `approved_room_order` (
  `id` int NOT NULL,
  `floor` int NOT NULL,
  `room` int DEFAULT NULL,
  `order_date` date NOT NULL DEFAULT (now()),
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT (_utf8mb4'ACTIVE'),
  PRIMARY KEY (`id`)
) 