USE `hotel`;
CREATE TABLE `hotels` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hotel_code` varchar(45) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `hotel_check_in` time NOT NULL,
  `hotel_check_out` time NOT NULL,
  `hotel_check_in_threshold` time NOT NULL,
  `hotel_check_out_threshold` time NOT NULL,
  `is_active` bit(1) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `hotel_code_UNIQUE` (`hotel_code`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
