USE `hotel`;
CREATE TABLE `rating` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rating_hotel_id` INT UNSIGNED NOT NULL,
  `rating_feedback_category_id` INT UNSIGNED NOT NULL,
  `rating_category_id` INT UNSIGNED NOT NULL,
  `source` VARCHAR(255),
  `rating` INT NOT NULL,
  `description` VARCHAR(255),
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` VARCHAR(255) NOT NULL,
  `updated_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_RATING_RATING_HOTEL_idx` (`rating_hotel_id`),
  KEY `FK_RATING_FEEDBACK_CATEGORY_idx` (`rating_feedback_category_id`),
  KEY `FK_RATING_CATEGORY_idx` (`rating_category_id`),
  CONSTRAINT `FK_RATING_RATING_HOTEL` FOREIGN KEY (`rating_hotel_id`) REFERENCES `rating_hotel` (`id`),
  CONSTRAINT `FK_RATING_FEEDBACK_CATEGORY` FOREIGN KEY (`rating_feedback_category_id`) REFERENCES `rating_feedback_category` (`id`),
  CONSTRAINT `FK_RATING_CATEGORY` FOREIGN KEY (`rating_category_id`) REFERENCES `rating_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
