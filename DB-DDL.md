# DB DDL Script

### Create Schema
```
CREATE SCHEMA `ph-cafe` ;
```

### Create Table
```
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date_time` datetime(6) DEFAULT NULL,
  `updated_date_time` datetime(6) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `phone_number` varchar(13) NOT NULL,
  `role` enum('OWNER') DEFAULT NULL,
  `is_on_line` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9q63snka3mdh91as4io72espi` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date_time` datetime(6) DEFAULT NULL,
  `updated_date_time` datetime(6) DEFAULT NULL,
  `barcode` varchar(100) NOT NULL,
  `category` enum('COFFEE','TEA','BAKERY','DESSERT','FOOD','BEVERAGE','ETC') NOT NULL,
  `cost` int NOT NULL,
  `description` varchar(1000) NOT NULL,
  `expiration_date` datetime(6) NOT NULL,
  `initial` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` int NOT NULL,
  `size` enum('SMALL','LARGE') NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK47nyv78b35eaufr6aa96vep6n` (`user_id`),
  CONSTRAINT `FK47nyv78b35eaufr6aa96vep6n` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```
