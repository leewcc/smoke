/*
SQLyog v10.2 
MySQL - 5.5.28 : Database - smoke
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`smoke` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `smoke`;

/*Table structure for table `t_equipment` */

DROP TABLE IF EXISTS `t_equipment`;

CREATE TABLE `t_equipment` (
  `equipment_id` int(11) NOT NULL AUTO_INCREMENT,
  `equipment_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `room_id` int(11) NOT NULL,
  PRIMARY KEY (`equipment_id`),
  KEY `room` (`room_id`),
  CONSTRAINT `room` FOREIGN KEY (`room_id`) REFERENCES `t_room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_equipment` */

/*Table structure for table `t_manager` */

DROP TABLE IF EXISTS `t_manager`;

CREATE TABLE `t_manager` (
  `password` varchar(50) COLLATE utf8_bin NOT NULL,
  `power_limit` int(11) NOT NULL DEFAULT '1',
  `user_id` int(11) NOT NULL,
  `manager_id` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `manager` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_manager` */

insert  into `t_manager`(`password`,`power_limit`,`user_id`,`manager_id`) values ('123456',1,1,'chenjunming');

/*Table structure for table `t_phone` */

DROP TABLE IF EXISTS `t_phone`;

CREATE TABLE `t_phone` (
  `user_phone` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`user_phone`,`user_id`),
  KEY `user` (`user_id`),
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_phone` */

/*Table structure for table `t_room` */

DROP TABLE IF EXISTS `t_room`;

CREATE TABLE `t_room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`room_id`),
  KEY `user_room` (`user_id`),
  CONSTRAINT `user_room` FOREIGN KEY (`user_id`) REFERENCES `t_teacher` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_room` */

/*Table structure for table `t_teacher` */

DROP TABLE IF EXISTS `t_teacher`;

CREATE TABLE `t_teacher` (
  `user_id` int(11) NOT NULL,
  KEY `teacher` (`user_id`),
  CONSTRAINT `teacher` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_teacher` */

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `t_user` */

insert  into `t_user`(`user_id`,`user_name`) values (1,'陈俊铭');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
