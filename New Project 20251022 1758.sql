-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.25a


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema movieticketbooking
--

CREATE DATABASE IF NOT EXISTS movieticketbooking;
USE movieticketbooking;

--
-- Definition of table `booking`
--

DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `booking_date` date DEFAULT NULL,
  `movie_title` varchar(255) DEFAULT NULL,
  `number_of_tickets` int(11) NOT NULL,
  `total_amount` double NOT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `seat_id` bigint(20) DEFAULT NULL,
  `show_id` bigint(20) DEFAULT NULL,
  `booking_date_time` datetime DEFAULT NULL,
  `theatre_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK754q349bnqqo5ff94136m7m7s` (`seat_id`),
  KEY `FK3hfymurc2960dg7d793rr0qcr` (`show_id`),
  KEY `FKfab22ye7ykjaykntkq466a1e0` (`theatre_id`),
  CONSTRAINT `FK3hfymurc2960dg7d793rr0qcr` FOREIGN KEY (`show_id`) REFERENCES `shows` (`id`),
  CONSTRAINT `FK754q349bnqqo5ff94136m7m7s` FOREIGN KEY (`seat_id`) REFERENCES `show_seat` (`id`),
  CONSTRAINT `FKfab22ye7ykjaykntkq466a1e0` FOREIGN KEY (`theatre_id`) REFERENCES `theatre_register` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` (`id`,`booking_date`,`movie_title`,`number_of_tickets`,`total_amount`,`user_email`,`seat_id`,`show_id`,`booking_date_time`,`theatre_id`) VALUES 
 (2,'2025-10-16','vanam',1,200,'vinaykumar@gmail.com',201,11,'2025-10-16 15:16:31',7),
 (3,'2025-10-16','vanam',1,200,'ajith@gmail.com',318,11,'2025-10-16 17:12:34',7),
 (4,'2025-10-16','vanam',1,200,'ajith@gmail.com',320,11,'2025-10-16 17:14:48',7),
 (5,'2025-10-16','vanam',1,200,'vinaykumar@gmail.com',248,11,'2025-10-16 17:24:12',7),
 (6,'2025-10-16','vanam',1,200,'ajith@gmail.com',254,11,'2025-10-16 17:25:53',7),
 (7,'2025-10-16','vanam',1,200,'ajith@gmail.com',256,11,'2025-10-16 17:51:11',7),
 (8,'2025-10-16','vanam',1,200,'ajith@gmail.com',282,11,'2025-10-16 18:05:41',7),
 (9,'2025-10-16','vanam',1,200,'vinaykumar@gmail.com',279,11,'2025-10-16 18:14:50',7),
 (10,'2025-10-16','vanam',1,200,'ajith@gmail.com',274,11,'2025-10-16 18:17:53',7),
 (11,'2025-10-16','vanam',1,200,'ajith@gmail.com',216,11,'2025-10-16 18:21:40',7),
 (12,'2025-10-16','vanam',1,200,'vinaykumar@gmail.com',209,11,'2025-10-16 18:23:33',7),
 (13,'2025-10-16','vanam',1,200,'ajith@gmail.com',243,11,'2025-10-16 18:57:58',7),
 (14,'2025-10-16','vanam',1,200,'ajith@gmail.com',270,11,'2025-10-16 19:07:51',7),
 (15,'2025-10-16','vanam',1,200,'ajith@gmail.com',234,11,'2025-10-16 19:15:54',7),
 (16,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',314,11,'2025-10-17 15:05:34',7),
 (17,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',212,11,'2025-10-17 15:21:22',7),
 (18,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',308,11,'2025-10-17 15:25:08',7),
 (19,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',241,11,'2025-10-17 15:28:06',7),
 (20,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',265,11,'2025-10-17 15:30:50',7),
 (21,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',292,11,'2025-10-17 15:32:56',7),
 (22,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',219,11,'2025-10-17 15:49:37',7),
 (23,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',230,11,'2025-10-17 15:51:32',7),
 (24,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',289,11,'2025-10-17 16:20:22',7),
 (25,'2025-10-17','vanam',1,200,'mailjavasend@gmail.com',202,11,'2025-10-17 16:23:36',7),
 (26,'2025-10-21','shazam',1,30,'sanjay@gmail.com',384,12,'2025-10-21 18:49:28',8),
 (27,'2025-10-21','shazam',1,300,'sanjay@gmail.com',391,12,'2025-10-21 18:51:06',8);
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;


--
-- Definition of table `movie`
--

DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `poster` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `language` varchar(255) DEFAULT NULL,
  `seats_per_row` int(11) NOT NULL,
  `total_rows` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movie`
--

/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` (`id`,`description`,`duration`,`genre`,`poster`,`title`,`language`,`seats_per_row`,`total_rows`) VALUES 
 (7,'family based entertain movie','3hours','general ','/uploads/vanam.jpg','vanam','tamil',0,0),
 (8,'this is family watching u certificate movie','3hours','general','/uploads/leo.jpg','leo','tamil',0,0),
 (9,'a good movie ','2hours','general','/uploads/shazam.jpg','shazam','tamil ',6,12);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;


--
-- Definition of table `show_seat`
--

DROP TABLE IF EXISTS `show_seat`;
CREATE TABLE `show_seat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `seat_no` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `show_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl8h7odotr49cuig9dy7t6cfj0` (`show_id`),
  CONSTRAINT `FKl8h7odotr49cuig9dy7t6cfj0` FOREIGN KEY (`show_id`) REFERENCES `shows` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=393 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `show_seat`
--

/*!40000 ALTER TABLE `show_seat` DISABLE KEYS */;
INSERT INTO `show_seat` (`id`,`seat_no`,`status`,`show_id`) VALUES 
 (201,'A1','booked',11),
 (202,'A2','booked',11),
 (203,'A3','available',11),
 (204,'A4','available',11),
 (205,'A5','available',11),
 (206,'A6','available',11),
 (207,'A7','available',11),
 (208,'A8','available',11),
 (209,'A9','booked',11),
 (210,'A10','available',11),
 (211,'A11','available',11),
 (212,'A12','booked',11),
 (213,'B1','available',11),
 (214,'B2','available',11),
 (215,'B3','available',11),
 (216,'B4','booked',11),
 (217,'B5','available',11),
 (218,'B6','available',11),
 (219,'B7','booked',11),
 (220,'B8','available',11),
 (221,'B9','available',11),
 (222,'B10','available',11),
 (223,'B11','available',11),
 (224,'B12','available',11),
 (225,'C1','available',11),
 (226,'C2','available',11),
 (227,'C3','available',11),
 (228,'C4','available',11),
 (229,'C5','available',11),
 (230,'C6','booked',11),
 (231,'C7','available',11),
 (232,'C8','available',11),
 (233,'C9','available',11),
 (234,'C10','booked',11),
 (235,'C11','available',11),
 (236,'C12','available',11),
 (237,'D1','available',11),
 (238,'D2','available',11),
 (239,'D3','available',11),
 (240,'D4','available',11),
 (241,'D5','booked',11),
 (242,'D6','available',11),
 (243,'D7','booked',11),
 (244,'D8','available',11),
 (245,'D9','available',11),
 (246,'D10','available',11),
 (247,'D11','available',11),
 (248,'D12','booked',11),
 (249,'E1','available',11),
 (250,'E2','available',11),
 (251,'E3','available',11),
 (252,'E4','available',11),
 (253,'E5','available',11),
 (254,'E6','booked',11),
 (255,'E7','available',11),
 (256,'E8','booked',11),
 (257,'E9','available',11),
 (258,'E10','available',11),
 (259,'E11','available',11),
 (260,'E12','available',11),
 (261,'F1','available',11),
 (262,'F2','available',11),
 (263,'F3','available',11),
 (264,'F4','available',11),
 (265,'F5','booked',11),
 (266,'F6','available',11),
 (267,'F7','available',11),
 (268,'F8','available',11),
 (269,'F9','available',11),
 (270,'F10','booked',11),
 (271,'F11','available',11),
 (272,'F12','available',11),
 (273,'G1','available',11),
 (274,'G2','booked',11),
 (275,'G3','available',11),
 (276,'G4','available',11),
 (277,'G5','available',11),
 (278,'G6','available',11),
 (279,'G7','booked',11),
 (280,'G8','available',11),
 (281,'G9','available',11),
 (282,'G10','booked',11),
 (283,'G11','available',11),
 (284,'G12','available',11),
 (285,'H1','available',11),
 (286,'H2','available',11),
 (287,'H3','available',11),
 (288,'H4','available',11),
 (289,'H5','booked',11),
 (290,'H6','available',11),
 (291,'H7','available',11),
 (292,'H8','booked',11),
 (293,'H9','available',11),
 (294,'H10','available',11),
 (295,'H11','available',11),
 (296,'H12','available',11),
 (297,'I1','available',11),
 (298,'I2','available',11),
 (299,'I3','available',11),
 (300,'I4','available',11),
 (301,'I5','available',11),
 (302,'I6','available',11),
 (303,'I7','available',11),
 (304,'I8','available',11),
 (305,'I9','available',11),
 (306,'I10','available',11),
 (307,'I11','available',11),
 (308,'I12','booked',11),
 (309,'J1','available',11),
 (310,'J2','available',11),
 (311,'J3','available',11),
 (312,'J4','available',11),
 (313,'J5','available',11),
 (314,'J6','booked',11),
 (315,'J7','available',11),
 (316,'J8','available',11),
 (317,'J9','available',11),
 (318,'J10','booked',11),
 (319,'J11','available',11),
 (320,'J12','booked',11),
 (321,'A1','available',12),
 (322,'A2','available',12),
 (323,'A3','available',12),
 (324,'A4','available',12),
 (325,'A5','available',12),
 (326,'A6','available',12),
 (327,'B1','available',12),
 (328,'B2','available',12),
 (329,'B3','available',12),
 (330,'B4','available',12),
 (331,'B5','available',12),
 (332,'B6','available',12),
 (333,'C1','available',12),
 (334,'C2','available',12),
 (335,'C3','available',12),
 (336,'C4','available',12),
 (337,'C5','available',12),
 (338,'C6','available',12),
 (339,'D1','available',12),
 (340,'D2','available',12),
 (341,'D3','available',12),
 (342,'D4','available',12),
 (343,'D5','available',12),
 (344,'D6','available',12),
 (345,'E1','available',12),
 (346,'E2','available',12),
 (347,'E3','available',12),
 (348,'E4','available',12),
 (349,'E5','available',12),
 (350,'E6','available',12),
 (351,'F1','available',12),
 (352,'F2','available',12),
 (353,'F3','available',12),
 (354,'F4','available',12),
 (355,'F5','available',12),
 (356,'F6','available',12),
 (357,'G1','available',12),
 (358,'G2','available',12),
 (359,'G3','available',12),
 (360,'G4','available',12),
 (361,'G5','available',12),
 (362,'G6','available',12),
 (363,'H1','available',12),
 (364,'H2','available',12),
 (365,'H3','available',12),
 (366,'H4','available',12),
 (367,'H5','available',12),
 (368,'H6','available',12),
 (369,'I1','available',12),
 (370,'I2','available',12),
 (371,'I3','available',12),
 (372,'I4','available',12),
 (373,'I5','available',12),
 (374,'I6','available',12),
 (375,'J1','available',12),
 (376,'J2','available',12),
 (377,'J3','available',12),
 (378,'J4','available',12),
 (379,'J5','available',12),
 (380,'J6','available',12),
 (381,'K1','available',12),
 (382,'K2','available',12),
 (383,'K3','available',12),
 (384,'K4','booked',12),
 (385,'K5','available',12),
 (386,'K6','available',12),
 (387,'L1','available',12),
 (388,'L2','available',12),
 (389,'L3','available',12),
 (390,'L4','available',12),
 (391,'L5','available',12),
 (392,'L6','available',12);
/*!40000 ALTER TABLE `show_seat` ENABLE KEYS */;


--
-- Definition of table `shows`
--

DROP TABLE IF EXISTS `shows`;
CREATE TABLE `shows` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `price` double NOT NULL,
  `show_time` varchar(255) DEFAULT NULL,
  `movie_id` bigint(20) DEFAULT NULL,
  `theatre_id` bigint(20) DEFAULT NULL,
  `seats_per_row` int(11) NOT NULL,
  `total_rows` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4qskmne5ijrtsqlxym3c70nha` (`movie_id`),
  KEY `FKdvf6lxr86v7mfbtealncgl68j` (`theatre_id`),
  CONSTRAINT `FK4qskmne5ijrtsqlxym3c70nha` FOREIGN KEY (`movie_id`) REFERENCES `movie` (`id`),
  CONSTRAINT `FKdvf6lxr86v7mfbtealncgl68j` FOREIGN KEY (`theatre_id`) REFERENCES `theatre_register` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `shows`
--

/*!40000 ALTER TABLE `shows` DISABLE KEYS */;
INSERT INTO `shows` (`id`,`date`,`price`,`show_time`,`movie_id`,`theatre_id`,`seats_per_row`,`total_rows`) VALUES 
 (11,'2025-10-15',200,'18:31',7,7,12,10),
 (12,'2025-10-21',300,'19:00',9,8,6,12);
/*!40000 ALTER TABLE `shows` ENABLE KEYS */;


--
-- Definition of table `theatre_register`
--

DROP TABLE IF EXISTS `theatre_register`;
CREATE TABLE `theatre_register` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_password` varchar(255) DEFAULT NULL,
  `admin_username` varchar(255) DEFAULT NULL,
  `business_email` varchar(255) DEFAULT NULL,
  `business_phone` varchar(255) DEFAULT NULL,
  `established_year` int(11) DEFAULT NULL,
  `facilities` varchar(255) DEFAULT NULL,
  `owner_name` varchar(255) DEFAULT NULL,
  `theatre_address` varchar(255) DEFAULT NULL,
  `theatre_city` varchar(255) DEFAULT NULL,
  `theatre_name` varchar(255) DEFAULT NULL,
  `theatre_state` varchar(255) DEFAULT NULL,
  `theatre_type` varchar(255) DEFAULT NULL,
  `theatre_zip` varchar(255) DEFAULT NULL,
  `total_screens` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `theatre_register`
--

/*!40000 ALTER TABLE `theatre_register` DISABLE KEYS */;
INSERT INTO `theatre_register` (`id`,`admin_password`,`admin_username`,`business_email`,`business_phone`,`established_year`,`facilities`,`owner_name`,`theatre_address`,`theatre_city`,`theatre_name`,`theatre_state`,`theatre_type`,`theatre_zip`,`total_screens`,`status`,`role`) VALUES 
 (2,'12345','akshaya senthil','akshaya@gmail.com','044326521',2025,'Parking,3D','akshaya','3rd Floor, Upstairs Hotel Murugan Idly, 78, N Usman Rd, T. Nagar, Chennai, Tamil Nadu 600017','tnagar','ags cinemas','Tamil Nadu','single-screen','600017',2,'Approved','THEATRE'),
 (7,'$2a$10$sFNdoBekQzdOApjHhJvGWupJ8wueqz7edFEEFURVavw8R.SZHRhhe','vinoth','sangamcinimeas@gmail.com','7982312348',2025,'Parking,Food Court,3D','vinoth','4/46, Lajapathirai street','paramakudi','sangam cinimeas','Tamil Nadu','single-screen','623707',4,'Approved','THEATRE'),
 (8,'$2a$10$.h6qaWyR1cqlL9JepOIx3.0QVf/PgMnz2rVmZ5cYVwqqdc9JaDB1e','arunkumar','agscinimeas@gmail.com','87946546',2020,'Food Court','arun','chennai kilpauk','chennai','ags cinemas','tamilnadu','premium','87965',3,'Approved','THEATRE');
/*!40000 ALTER TABLE `theatre_register` ENABLE KEYS */;


--
-- Definition of table `userregister`
--

DROP TABLE IF EXISTS `userregister`;
CREATE TABLE `userregister` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cpsw` varchar(255) DEFAULT NULL,
  `dob` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fname` varchar(255) DEFAULT NULL,
  `fullname` varchar(255) DEFAULT NULL,
  `lname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userregister`
--

/*!40000 ALTER TABLE `userregister` DISABLE KEYS */;
INSERT INTO `userregister` (`id`,`cpsw`,`dob`,`email`,`fname`,`fullname`,`lname`,`password`,`phone`,`status`,`profile_image`,`role`) VALUES 
 (2,'11','2007-10-25','ajith@gmail.com','ajith','ajithkumar','kumar','$2a$10$vuDZGAvVMFtyUIesiv4TuulFoNVz0VbYjY./4I5C01qElSifJUWcW','994032445',NULL,NULL,'USER'),
 (3,'123456','2012-10-09','vinaykumar@gmail.com','vinay','vinay','kumar','$2a$10$o2ETPOok/EXb/PI4VgZlheCYs5ZMm5I0N1KU57e6kJ6oI7P5.cEiO','778745940',NULL,NULL,'USER'),
 (4,'11','2012-10-06','mailjavasend@gmail.com','sanjay','sanjay','s','$2a$10$/EjW1vcVt6P6yDqokfRAOOKtaafbWNexAnECtmsfKzbVr9U6tG93u','7894652130',NULL,NULL,'USER'),
 (5,'11','2012-10-19','sanjay@gmail.com','sanjay','sanjay','san','$2a$10$B6IvAlHU7yvXVHd07xeo.eI7fvhsYLL4gpXDUEPKg8VPXGZCSjQvO','789746512',NULL,NULL,'USER');
/*!40000 ALTER TABLE `userregister` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
