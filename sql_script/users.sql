USE `hotel`;
CREATE TABLE `users` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user_id` varchar(45) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(45) NOT NULL,
  `is_password_change` bit(1) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `user_role_id` int NOT NULL,
  `is_ask_for_pw_reset` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`),
  KEY `FK_USERS_USER_ROLES_idx` (`user_role_id`),
  CONSTRAINT `FK_USERS_USER_ROLES` FOREIGN KEY (`user_role_id`) REFERENCES `user_roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
