USE `hotel`;
CREATE TABLE `fault` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `floor` VARCHAR(45) NOT NULL,
  `room` VARCHAR(45) NOT NULL,
  `fault_type_id` INT UNSIGNED NOT NULL,
  `fault_status_id` INT UNSIGNED NOT NULL,
  `attachment` VARCHAR(255),
  `created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` VARCHAR(255) NOT NULL,
  `updated_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_FAULT_FAULT_TYPE_idx` (`fault_type_id`),
  KEY `FK_FAULT_FAULT_STATUS_idx` (`fault_status_id`),
  CONSTRAINT `FK_FAULT_FAULT_TYPE` FOREIGN KEY (`fault_type_id`) REFERENCES `fault_type` (`id`),
  CONSTRAINT `FK_FAULT_FAULT_STATUS` FOREIGN KEY (`fault_status_id`) REFERENCES `fault_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
