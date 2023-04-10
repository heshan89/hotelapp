CREATE DATABASE  IF NOT EXISTS `hotel`; 
USE `hotel`;
INSERT INTO `hotel`.`rating_feedback_category` (`name`, `created_by`, `updated_by`) VALUES
('Direct customer', 'Admin', 'Admin'),
('Through website', 'Admin', 'Admin'),
('Hotel', 'Admin', 'Admin'),
('Audit', 'Admin', 'Admin');
