USE `hotel`;
CREATE TABLE `user_attendance_hotel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int unsigned NOT NULL,
  `hotel_id` int NOT NULL,
  `check_in` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `check_out` datetime NULL,
  `user_comment` text NULL,
  `revised_by` varchar(255) NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(255) NOT NULL,
  `updated_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_by` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_ATTENDANCE_HOTEL_USERS_idx` (`user_id`),
  CONSTRAINT `FK_USER_ATTENDANCE_HOTEL_USERS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  KEY `FK_USER_ATTENDANCE_HOTEL_HOTELS_idx` (`hotel_id`),
  CONSTRAINT `FK_USER_ATTENDANCE_HOTEL_HOTELS` FOREIGN KEY (`hotel_id`) REFERENCES `hotels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
