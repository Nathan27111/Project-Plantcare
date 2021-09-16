/*
SQLyog Community v13.1.5  (64 bit)
MySQL - 8.0.23-0ubuntu0.20.04.1 : Database - plantcare
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`plantcare` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `plantcare`;

/*Table structure for table `migrations` */

DROP TABLE IF EXISTS `migrations`;

CREATE TABLE `migrations` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `migrations` */

insert  into `migrations`(`id`,`migration`,`batch`) values 
(1,'2014_10_12_000000_create_users_table',1),
(2,'2021_03_16_084317_create_rooms_table',1),
(3,'2021_03_16_084550_create_plants_table',1),
(4,'2021_03_19_140932_create_pictures_table',1),
(5,'2021_08_07_083535_create_plants_users_table',1);

/*Table structure for table `pictures` */

DROP TABLE IF EXISTS `pictures`;

CREATE TABLE `pictures` (
  `picture_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `plant_id` bigint unsigned NOT NULL,
  `picture` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `taken_at` datetime NOT NULL DEFAULT '2021-01-01 00:00:00',
  PRIMARY KEY (`picture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `pictures` */

insert  into `pictures`(`picture_id`,`plant_id`,`picture`,`taken_at`) values 
(1,3,'/tmp/e5db530e52a521e459bf0ff0a0e39ec2.png','2021-08-13 12:35:18'),
(2,1,'/tmp/5029485c8f17fe3b4a43ac31f6e9d8eb.png','2021-08-06 08:54:20'),
(3,4,'/tmp/b254e65f9152423b12c22afd315468c1.png','2021-08-03 15:30:25'),
(4,9,'/tmp/a09eb47f3a8e0a99a58c877b235cf323.png','2021-08-15 01:56:48'),
(5,10,'/tmp/ea70846cb05e0ca1162ff377922b4e4e.png','2021-08-14 17:56:06'),
(6,8,'/tmp/5eb5de2211b80d08816d4c167fb1c3d0.png','2021-08-07 03:12:03'),
(7,4,'/tmp/70ed7f2c62bab4adb8ef116874081bb9.png','2021-08-03 01:12:23'),
(8,7,'/tmp/82cd68b118c6b4ce94517731af2ef62d.png','2021-08-04 10:39:57'),
(9,8,'/tmp/1260b14202bdc752dbcdc6cd397c559f.png','2021-08-07 15:55:28'),
(10,10,'/tmp/e1f2275ca333ba2acb83e5b291adb375.png','2021-08-04 12:48:24');

/*Table structure for table `plants` */

DROP TABLE IF EXISTS `plants`;

CREATE TABLE `plants` (
  `plant_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `room_id` bigint unsigned NOT NULL,
  `needs_water` int NOT NULL,
  `last_watered_at` datetime DEFAULT NULL,
  `plant_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `flowers` int NOT NULL DEFAULT '0',
  `height` int NOT NULL DEFAULT '20',
  `is_blooming` tinyint(1) NOT NULL,
  PRIMARY KEY (`plant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `plants` */

insert  into `plants`(`plant_id`,`room_id`,`needs_water`,`last_watered_at`,`plant_description`,`flowers`,`height`,`is_blooming`) values 
(1,10,4,'2021-08-15 22:59:51','Hatter and the White Rabbit, \'and that\'s the queerest thing about it.\' (The jury all brightened up at the other was sitting on a three-legged stool in the last few minutes, and began an account of.',4,68,0),
(2,2,6,'2021-08-16 00:32:14','I\'m going to begin with; and being ordered about in the window?\' \'Sure, it\'s an arm, yer honour!\' (He pronounced it \'arrum.\') \'An arm, you goose! Who ever saw one that size? Why, it fills the whole.',1,67,1),
(3,3,3,'2021-08-15 16:47:50','Bill! I wouldn\'t be so kind,\' Alice replied, so eagerly that the reason of that?\' \'In my youth,\' Father William replied to his son, \'I feared it might be hungry, in which the words did not quite.',5,53,0),
(4,2,4,'2021-08-16 08:59:04','Duchess was VERY ugly; and secondly, because they\'re making such VERY short remarks, and she thought it would,\' said the Cat. \'I said pig,\' replied Alice; \'and I wish you could manage it?) \'And what.',1,63,0),
(5,8,5,'2021-08-16 11:05:59','Alice, \'to pretend to be patted on the same thing a bit!\' said the sage, as he wore his crown over the edge with each hand. \'And now which is which?\' she said this, she came upon a low voice. \'Not.',5,15,1),
(6,2,6,'2021-08-16 01:30:58','Alice heard it muttering to itself in a whisper, half afraid that she was small enough to try the experiment?\' \'HE might bite,\' Alice cautiously replied, not feeling at all know whether it was.',4,3,0),
(7,7,4,'2021-08-16 00:42:41','I could let you out, you know.\' \'I DON\'T know,\' said the King, the Queen, stamping on the top of his tail. \'As if it makes me grow large again, for this curious child was very fond of beheading.',3,66,0),
(8,4,1,'2021-08-16 08:54:03','March Hare said to the door. \'Call the first really clever thing the King hastily said, and went back to her: its face was quite impossible to say which), and they went on again:-- \'I didn\'t write.',2,60,1),
(9,5,4,'2021-08-15 21:30:10','YOU with us!\"\' \'They were obliged to write with one finger, as he spoke. \'A cat may look at the cook tulip-roots instead of onions.\' Seven flung down his cheeks, he went on, turning to the beginning.',2,39,0),
(10,7,2,'2021-08-16 00:22:34','Alice. \'I\'ve so often read in the direction it pointed to, without trying to invent something!\' \'I--I\'m a little sharp bark just over her head in the distance. \'Come on!\' and ran the faster, while.',2,79,1);

/*Table structure for table `plants_users` */

DROP TABLE IF EXISTS `plants_users`;

CREATE TABLE `plants_users` (
  `plant_id` bigint unsigned NOT NULL,
  `user_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`plant_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `plants_users` */

insert  into `plants_users`(`plant_id`,`user_id`) values 
(1,2),
(2,3),
(2,6),
(3,4),
(4,1),
(4,9),
(4,11),
(5,7),
(5,8),
(5,10),
(5,11),
(9,5);

/*Table structure for table `rooms` */

DROP TABLE IF EXISTS `rooms`;

CREATE TABLE `rooms` (
  `room_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `room_description` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `rooms` */

insert  into `rooms`(`room_id`,`user_id`,`room_description`) values 
(1,5,'Hall'),
(2,9,'Living Room'),
(3,6,'Hall'),
(4,10,'Study'),
(5,8,'Hall'),
(6,1,'Living Room'),
(7,3,'Living Room'),
(8,2,'Living Room'),
(9,8,'Living Room'),
(10,1,'Living Room'),
(11,11,'Bedroom'),
(12,11,'Living room');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `users` */

insert  into `users`(`user_id`,`first_name`,`last_name`,`email`,`password`) values 
(1,'Helmer','Hauck','alden08@gmail.com','$2y$10$OlLTWCo9hVPzzp4K4HLgmOKDINKWrlw2Xx8YNk/bFMQ/gGwJal4zS'),
(2,'Anibal','Schaefer','nicolas.hammes@durgan.com','$2y$10$ckwpWPj2Gmx6ogIxfvHql.WDuuTkMAZUx5o11UF2dIZen8s8Lptsi'),
(3,'Samara','Orn','colin94@yahoo.com','$2y$10$F49QxesqxxYvm6JnvRFkA.WiX7Zrw1ShKhRm6jW1w4T/m3Dw6YbhW'),
(4,'Kyleigh','Howell','hintz.lilla@hotmail.com','$2y$10$GNz/USqP/SGSS3HDRMXnIOiutRQStQSlznnz5YHn3o4xFovcs0w0a'),
(5,'Emmalee','Prosacco','lemke.maymie@gmail.com','$2y$10$UsZGjfaXoFAZTgXbJWhheOKBH0UdNgigAQhOThGjcGVydyq6tFedC'),
(6,'Delilah','Rogahn','cwisoky@abbott.com','$2y$10$b6m2u392WsZS.vzjTncbtuuklT1lpiQLUFE7YqBvfGTRLaTFvMpMi'),
(7,'Isaiah','Konopelski','simonis.lilliana@christiansen.com','$2y$10$UjzTCwFG0Suts0edQBanzOUKLD/anKqqB4ThAOY7ifAeOdjded/Y.'),
(8,'Jeff','Dietrich','jrogahn@zemlak.com','$2y$10$NxZfjRTMr6ALhOXhx.sJNe8oVF259I/ZsRMVxilcjz0R/A5jYBKbK'),
(9,'Jana','D\'Amore','blake.hettinger@hotmail.com','$2y$10$RBksWuSyNl0aqtRRzYnBJus1SCRuBJ1T.Mlu7VKj34MVsSeBWAt6O'),
(10,'Clemens','Jones','nikolas.collins@nikolaus.com','$2y$10$WXyTR77czzLNZ8.LfQbLX.rA1GycmLL.XZEyDSxcH8WbHT131XdEO'),
(11,'test','test','test@test.be','$2y$10$XMvgI7SCF0e1JlehMx9f5uFr7Em4s1Czp2rdl3YC1k/DgNEhK8HOG');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
