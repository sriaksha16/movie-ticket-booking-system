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
  `transaction_id` varchar(255) DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK754q349bnqqo5ff94136m7m7s` (`seat_id`),
  KEY `FK3hfymurc2960dg7d793rr0qcr` (`show_id`),
  KEY `FKfab22ye7ykjaykntkq466a1e0` (`theatre_id`),
  CONSTRAINT `FK3hfymurc2960dg7d793rr0qcr` FOREIGN KEY (`show_id`) REFERENCES `shows` (`id`),
  CONSTRAINT `FK754q349bnqqo5ff94136m7m7s` FOREIGN KEY (`seat_id`) REFERENCES `show_seat` (`id`),
  CONSTRAINT `FKfab22ye7ykjaykntkq466a1e0` FOREIGN KEY (`theatre_id`) REFERENCES `theatre_register` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `booking`
--

/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` (`id`,`booking_date`,`movie_title`,`number_of_tickets`,`total_amount`,`user_email`,`seat_id`,`show_id`,`booking_date_time`,`theatre_id`,`transaction_id`,`payment_status`) VALUES 
 (42,'2025-10-24','Romeo',1,2000,'mailjavasend@gmail.com',1589,20,'2025-10-24 11:31:42',9,'pi_3SLe4Q8fbaPcSw8t1yITFjif','SUCCESS');
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movie`
--

/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` (`id`,`description`,`duration`,`genre`,`poster`,`title`,`language`,`seats_per_row`,`total_rows`) VALUES 
 (17,'this drama based fantastic love story','2hours','Drama','/uploads/romeo.jpg','Romeo','English,Tamil',18,12);
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
) ENGINE=InnoDB AUTO_INCREMENT=1689 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `show_seat`
--

/*!40000 ALTER TABLE `show_seat` DISABLE KEYS */;
INSERT INTO `show_seat` (`id`,`seat_no`,`status`,`show_id`) VALUES 
 (1473,'A1','available',20),
 (1474,'A2','available',20),
 (1475,'A3','available',20),
 (1476,'A4','available',20),
 (1477,'A5','available',20),
 (1478,'A6','available',20),
 (1479,'A7','available',20),
 (1480,'A8','available',20),
 (1481,'A9','available',20),
 (1482,'A10','available',20),
 (1483,'A11','available',20),
 (1484,'A12','available',20),
 (1485,'A13','available',20),
 (1486,'A14','available',20),
 (1487,'A15','available',20),
 (1488,'A16','available',20),
 (1489,'A17','available',20),
 (1490,'A18','available',20),
 (1491,'B1','available',20),
 (1492,'B2','available',20),
 (1493,'B3','available',20),
 (1494,'B4','available',20),
 (1495,'B5','available',20),
 (1496,'B6','available',20),
 (1497,'B7','available',20),
 (1498,'B8','available',20),
 (1499,'B9','available',20),
 (1500,'B10','available',20),
 (1501,'B11','available',20),
 (1502,'B12','available',20),
 (1503,'B13','available',20),
 (1504,'B14','available',20),
 (1505,'B15','available',20),
 (1506,'B16','available',20),
 (1507,'B17','available',20),
 (1508,'B18','available',20),
 (1509,'C1','available',20),
 (1510,'C2','available',20),
 (1511,'C3','available',20),
 (1512,'C4','available',20),
 (1513,'C5','available',20),
 (1514,'C6','available',20),
 (1515,'C7','available',20),
 (1516,'C8','available',20),
 (1517,'C9','available',20),
 (1518,'C10','available',20),
 (1519,'C11','available',20),
 (1520,'C12','available',20),
 (1521,'C13','available',20),
 (1522,'C14','available',20),
 (1523,'C15','available',20),
 (1524,'C16','available',20),
 (1525,'C17','available',20),
 (1526,'C18','available',20),
 (1527,'D1','available',20),
 (1528,'D2','available',20),
 (1529,'D3','available',20),
 (1530,'D4','available',20),
 (1531,'D5','available',20),
 (1532,'D6','available',20),
 (1533,'D7','available',20),
 (1534,'D8','available',20),
 (1535,'D9','available',20),
 (1536,'D10','available',20),
 (1537,'D11','available',20),
 (1538,'D12','available',20),
 (1539,'D13','available',20),
 (1540,'D14','available',20),
 (1541,'D15','available',20),
 (1542,'D16','available',20),
 (1543,'D17','available',20),
 (1544,'D18','available',20),
 (1545,'E1','available',20),
 (1546,'E2','available',20),
 (1547,'E3','available',20),
 (1548,'E4','available',20),
 (1549,'E5','available',20),
 (1550,'E6','available',20),
 (1551,'E7','available',20),
 (1552,'E8','available',20),
 (1553,'E9','available',20),
 (1554,'E10','available',20),
 (1555,'E11','available',20),
 (1556,'E12','available',20),
 (1557,'E13','available',20),
 (1558,'E14','available',20),
 (1559,'E15','available',20),
 (1560,'E16','available',20),
 (1561,'E17','available',20),
 (1562,'E18','available',20),
 (1563,'F1','available',20),
 (1564,'F2','available',20),
 (1565,'F3','available',20),
 (1566,'F4','available',20),
 (1567,'F5','available',20),
 (1568,'F6','available',20),
 (1569,'F7','available',20),
 (1570,'F8','available',20),
 (1571,'F9','available',20),
 (1572,'F10','available',20),
 (1573,'F11','available',20),
 (1574,'F12','available',20),
 (1575,'F13','available',20),
 (1576,'F14','available',20),
 (1577,'F15','available',20),
 (1578,'F16','available',20),
 (1579,'F17','available',20),
 (1580,'F18','available',20),
 (1581,'G1','available',20),
 (1582,'G2','available',20),
 (1583,'G3','available',20),
 (1584,'G4','available',20),
 (1585,'G5','available',20),
 (1586,'G6','available',20),
 (1587,'G7','available',20),
 (1588,'G8','available',20),
 (1589,'G9','BOOKED',20),
 (1590,'G10','available',20),
 (1591,'G11','available',20),
 (1592,'G12','available',20),
 (1593,'G13','available',20),
 (1594,'G14','available',20),
 (1595,'G15','available',20),
 (1596,'G16','available',20),
 (1597,'G17','available',20),
 (1598,'G18','available',20),
 (1599,'H1','available',20),
 (1600,'H2','available',20),
 (1601,'H3','available',20),
 (1602,'H4','available',20),
 (1603,'H5','available',20),
 (1604,'H6','available',20),
 (1605,'H7','available',20),
 (1606,'H8','available',20),
 (1607,'H9','available',20),
 (1608,'H10','available',20),
 (1609,'H11','available',20),
 (1610,'H12','available',20),
 (1611,'H13','available',20),
 (1612,'H14','available',20),
 (1613,'H15','available',20),
 (1614,'H16','available',20),
 (1615,'H17','available',20),
 (1616,'H18','available',20),
 (1617,'I1','available',20),
 (1618,'I2','available',20),
 (1619,'I3','available',20),
 (1620,'I4','available',20),
 (1621,'I5','available',20),
 (1622,'I6','available',20),
 (1623,'I7','available',20),
 (1624,'I8','available',20),
 (1625,'I9','available',20),
 (1626,'I10','available',20),
 (1627,'I11','available',20),
 (1628,'I12','available',20),
 (1629,'I13','available',20),
 (1630,'I14','available',20),
 (1631,'I15','available',20),
 (1632,'I16','available',20),
 (1633,'I17','available',20),
 (1634,'I18','available',20),
 (1635,'J1','available',20),
 (1636,'J2','available',20),
 (1637,'J3','available',20),
 (1638,'J4','available',20),
 (1639,'J5','available',20),
 (1640,'J6','available',20),
 (1641,'J7','available',20),
 (1642,'J8','available',20),
 (1643,'J9','available',20),
 (1644,'J10','available',20),
 (1645,'J11','available',20),
 (1646,'J12','available',20),
 (1647,'J13','available',20),
 (1648,'J14','available',20),
 (1649,'J15','available',20),
 (1650,'J16','available',20),
 (1651,'J17','available',20),
 (1652,'J18','available',20),
 (1653,'K1','available',20),
 (1654,'K2','available',20),
 (1655,'K3','available',20),
 (1656,'K4','available',20),
 (1657,'K5','available',20),
 (1658,'K6','available',20),
 (1659,'K7','available',20),
 (1660,'K8','available',20),
 (1661,'K9','available',20),
 (1662,'K10','available',20),
 (1663,'K11','available',20),
 (1664,'K12','available',20),
 (1665,'K13','available',20),
 (1666,'K14','available',20),
 (1667,'K15','available',20),
 (1668,'K16','available',20),
 (1669,'K17','available',20),
 (1670,'K18','available',20),
 (1671,'L1','available',20),
 (1672,'L2','available',20),
 (1673,'L3','available',20),
 (1674,'L4','available',20),
 (1675,'L5','available',20),
 (1676,'L6','available',20),
 (1677,'L7','available',20),
 (1678,'L8','available',20),
 (1679,'L9','available',20),
 (1680,'L10','available',20),
 (1681,'L11','available',20),
 (1682,'L12','available',20),
 (1683,'L13','available',20),
 (1684,'L14','available',20),
 (1685,'L15','available',20),
 (1686,'L16','available',20),
 (1687,'L17','available',20),
 (1688,'L18','available',20);
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `shows`
--

/*!40000 ALTER TABLE `shows` DISABLE KEYS */;
INSERT INTO `shows` (`id`,`date`,`price`,`show_time`,`movie_id`,`theatre_id`,`seats_per_row`,`total_rows`) VALUES 
 (20,'2025-10-24',2000,'00:00',17,9,18,12);
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `theatre_register`
--

/*!40000 ALTER TABLE `theatre_register` DISABLE KEYS */;
INSERT INTO `theatre_register` (`id`,`admin_password`,`admin_username`,`business_email`,`business_phone`,`established_year`,`facilities`,`owner_name`,`theatre_address`,`theatre_city`,`theatre_name`,`theatre_state`,`theatre_type`,`theatre_zip`,`total_screens`,`status`,`role`) VALUES 
 (9,'$2a$10$2pwkp86wXD4mJx07ZcVhdOgaAw/BkHg8qWpCGpPL3NPmGSSKUfkKm','arunkumar','sangamcinimeas@gmail.com','7894561201',2025,'Parking,Food Court,3D','arun','chennai kilapuk near nehru studion','chennai','sangam cinimeas','Tamilnadu','imax','605644',3,'Approved','THEATRE');
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
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `userregister`
--

/*!40000 ALTER TABLE `userregister` DISABLE KEYS */;
INSERT INTO `userregister` (`id`,`cpsw`,`dob`,`email`,`fname`,`fullname`,`lname`,`password`,`phone`,`status`,`role`) VALUES 
 (7,'11','2012-10-17','mailjavasend@gmail.com','sanjay','sanjay','san','$2a$10$QJ4KvUTmFBnHamATopg6ZeijCK.iWGSJN1.vhdn7ZbDjh8hNUFnWu','7894561230',NULL,'USER'),
 (8,'11','2012-10-19','javaa3775@gmail.com','vijay','vijaykumar','kumar','$2a$10$nEM2IXD8ttFxJ2EM.m/71uiwzMKIx.Z4FLYUyqbpDNbWHCX0sujX2','87942121222',NULL,'USER');
/*!40000 ALTER TABLE `userregister` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
