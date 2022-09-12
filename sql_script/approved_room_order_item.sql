CREATE DATABASE  IF NOT EXISTS `hotel`; 
USE `hotel`;
DROP TABLE IF EXISTS `approved_room_order_item`;
CREATE TABLE `approved_room_order_item` (
  `id` int NOT NULL  AUTO_INCREMENT,
  `order_id` int NOT NULL,
  `item` varchar(100) NOT NULL,
  `amount` int NOT NULL DEFAULT (0),
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  `status` varchar(10) NOT NULL DEFAULT (_utf8mb4'ACTIVE'),
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `approved_room_order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `approved_room_order` (`id`)
) 
