USE `hotel`;
CREATE TABLE `user_roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_code` varchar(45) NOT NULL,
  `discription` varchar(255) DEFAULT NULL,
  `is_active` bit(1) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_UNIQUE` (`role_code`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
