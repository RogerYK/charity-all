-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: charity
-- ------------------------------------------------------
-- Server version	8.0.11

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL,
  `img` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
INSERT INTO `activity` VALUES (1,'http://localhost:8080/activity.jpg','99公益日'),(2,'http://localhost:8080/activity.jpg','99公益日'),(3,'http://localhost:8080/activity.jpg','99公益日'),(4,'http://localhost:8080/activity.jpg','99公益日'),(5,'http://localhost:8080/activity.jpg','99公益日');
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_user`
--

DROP TABLE IF EXISTS `admin_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_user` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_user`
--

LOCK TABLES `admin_user` WRITE;
/*!40000 ALTER TABLE `admin_user` DISABLE KEYS */;
INSERT INTO `admin_user` VALUES (121,'{bcrypt}$2a$10$hqS7jEoR5wAQJsZElF0SWOhyw8pN.gdBf2V7a1g7KyX6n7qmfou1y','root');
/*!40000 ALTER TABLE `admin_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin_user_roles`
--

DROP TABLE IF EXISTS `admin_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_user_roles` (
  `admin_user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FK28t3obv7pelsifgmewrx1jxcm` (`roles_id`),
  KEY `FKm49lam8hlsew02rh5vjob0kik` (`admin_user_id`),
  CONSTRAINT `FK28t3obv7pelsifgmewrx1jxcm` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKm49lam8hlsew02rh5vjob0kik` FOREIGN KEY (`admin_user_id`) REFERENCES `admin_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin_user_roles`
--

LOCK TABLES `admin_user_roles` WRITE;
/*!40000 ALTER TABLE `admin_user_roles` DISABLE KEYS */;
INSERT INTO `admin_user_roles` VALUES (121,1);
/*!40000 ALTER TABLE `admin_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banner`
--

DROP TABLE IF EXISTS `banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banner` (
  `id` bigint(20) NOT NULL,
  `img` varchar(255) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1crin1uom95vb2d2280ln3rx5` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banner`
--

LOCK TABLES `banner` WRITE;
/*!40000 ALTER TABLE `banner` DISABLE KEYS */;
INSERT INTO `banner` VALUES (1,'http://192.168.1.112:8080/slider1.jpg',25),(2,'http://192.168.1.112:8080/slider2.jpg',25),(3,'http://192.168.1.112:8080/slider3.jpg',25);
/*!40000 ALTER TABLE `banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `nname` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,NULL,NULL,'疾病'),(2,NULL,NULL,'灾害');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_projects`
--

DROP TABLE IF EXISTS `category_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_projects` (
  `category_id` bigint(20) NOT NULL,
  `projects_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_r5hkrftd2ibw30fwupj3jtsf1` (`projects_id`),
  KEY `FKbsffu6e2811i3pw39a0yycrlj` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_projects`
--

LOCK TABLES `category_projects` WRITE;
/*!40000 ALTER TABLE `category_projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `category_projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `commenter_id` bigint(20) DEFAULT NULL,
  `last_comment_id` bigint(20) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `parent_comment_id` bigint(20) DEFAULT NULL,
  `reply_comment_id` bigint(20) DEFAULT NULL,
  `reply_user_id` bigint(20) DEFAULT NULL,
  `news_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrd6bylray104n2px3nh25g3dm` (`last_comment_id`),
  KEY `FKt1j37mmifw2q1xldu1ae9aquh` (`reply_user_id`),
  KEY `FK44qghiqgaeg9imb7fq2ukffy1` (`commenter_id`),
  KEY `FKnxm8x9npdhuwxv2x2wxsghm17` (`news_id`),
  KEY `FKhvh0e2ybgg16bpu229a5teje7` (`parent_comment_id`),
  KEY `FKb5kenf6fjka6ck0snroeb5tmh` (`project_id`),
  KEY `FK925lwh0h0xx0e7es4syqdvcr9` (`reply_comment_id`),
  CONSTRAINT `FK44qghiqgaeg9imb7fq2ukffy1` FOREIGN KEY (`commenter_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK925lwh0h0xx0e7es4syqdvcr9` FOREIGN KEY (`reply_comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FKb5kenf6fjka6ck0snroeb5tmh` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKhvh0e2ybgg16bpu229a5teje7` FOREIGN KEY (`parent_comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `FKnxm8x9npdhuwxv2x2wxsghm17` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (74,'评论',58,NULL,20,'2019-04-20 23:13:25',NULL,NULL,NULL,NULL),(75,'评论二',58,NULL,20,'2019-04-20 23:13:50',NULL,NULL,NULL,NULL),(76,'评论',58,NULL,20,'2019-04-20 23:16:43',NULL,NULL,NULL,NULL),(77,'回复',58,NULL,20,'2019-04-23 21:44:57',74,NULL,NULL,NULL),(78,'继续回复',58,NULL,20,'2019-04-23 21:45:12',74,NULL,NULL,NULL),(79,'回复你',58,NULL,20,'2019-04-26 15:08:53',74,78,NULL,NULL),(80,'回复',58,NULL,20,'2019-04-26 15:13:36',74,78,NULL,NULL),(81,'评论',58,NULL,20,'2019-04-26 15:13:46',NULL,NULL,NULL,NULL),(82,'评论一',58,NULL,21,'2019-04-26 15:45:48',NULL,NULL,NULL,NULL),(83,'评论二',58,NULL,21,'2019-04-26 15:45:52',NULL,NULL,NULL,NULL),(84,'评论三',58,NULL,21,'2019-04-26 15:45:57',NULL,NULL,NULL,NULL),(85,'评论四',58,NULL,21,'2019-04-26 15:46:04',NULL,NULL,NULL,NULL),(86,'评论五',58,NULL,21,'2019-04-26 15:46:13',NULL,NULL,NULL,NULL),(87,'评论六',58,NULL,21,'2019-04-26 15:46:19',NULL,NULL,NULL,NULL),(88,'评论七',58,NULL,21,'2019-04-26 15:46:27',NULL,NULL,NULL,NULL),(89,'评论七',58,NULL,21,'2019-04-26 15:46:30',NULL,NULL,NULL,NULL),(90,'评论七',58,NULL,21,'2019-04-26 15:46:31',NULL,NULL,NULL,NULL),(91,'评论七',58,NULL,21,'2019-04-26 15:46:32',NULL,NULL,NULL,NULL),(92,'评论七',58,NULL,21,'2019-04-26 15:46:32',NULL,NULL,NULL,NULL),(93,'评论七',58,NULL,21,'2019-04-26 15:46:45',NULL,NULL,NULL,NULL),(94,'评论七',58,NULL,21,'2019-04-26 15:46:46',NULL,NULL,NULL,NULL),(105,'fff',58,NULL,20,'2019-06-12 14:55:15',NULL,NULL,NULL,NULL),(111,'评论',58,NULL,20,'2019-06-17 16:31:47',NULL,NULL,NULL,NULL),(112,'test',58,NULL,20,'2019-06-17 16:35:27',NULL,NULL,NULL,NULL),(114,'平路',58,NULL,20,'2019-06-17 16:47:33',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_favor_users`
--

DROP TABLE IF EXISTS `comment_favor_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_favor_users` (
  `comment_id` bigint(20) NOT NULL,
  `favor_users_id` bigint(20) NOT NULL,
  KEY `FKql2d49cksw6grugaa8cjcq23p` (`favor_users_id`),
  KEY `FKjj23ef8ulvgkx25k3wihj7rs5` (`comment_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_favor_users`
--

LOCK TABLES `comment_favor_users` WRITE;
/*!40000 ALTER TABLE `comment_favor_users` DISABLE KEYS */;
INSERT INTO `comment_favor_users` VALUES (77,58),(74,58),(78,58),(75,58),(76,58),(81,58);
/*!40000 ALTER TABLE `comment_favor_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ct_projects`
--

DROP TABLE IF EXISTS `ct_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_projects` (
  `ct_id` bigint(20) NOT NULL,
  `projects_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_ndsof03lg9puhp9i573awhtcg` (`projects_id`),
  KEY `FKt09auxbfyel698k0mivrr1yl` (`ct_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ct_projects`
--

LOCK TABLES `ct_projects` WRITE;
/*!40000 ALTER TABLE `ct_projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `ct_projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (128),(128),(128),(128),(128),(128),(128);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `id` bigint(20) NOT NULL,
  `img` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `comment_count` int(11) NOT NULL,
  `content` text,
  `created_time` datetime DEFAULT NULL,
  `favor_count` int(11) NOT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `watch_count` int(11) NOT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKrxsvhjf5khl0mf6jf3au0vqaf` (`author_id`),
  FULLTEXT KEY `news_fulltext_index` (`name`,`content`,`introduce`,`title`) /*!50100 WITH PARSER `ngram` */ ,
  CONSTRAINT `FKrxsvhjf5khl0mf6jf3au0vqaf` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'http://192.168.1.112:8080/activity.jpg','99公益日',0,NULL,'2019-04-30 21:01:26',0,'2018年99公益日已经结束，但我们相信爱与公益仍将继续',1,0,58,'99公益日',1),(2,'http://192.168.1.112:8080/activity.jpg','99公益日',0,NULL,'2019-04-30 21:01:26',0,'2018年99公益日已经结束，但我们相信爱与公益仍将继续',0,0,58,'99公益日',0),(3,'http://192.168.1.112:8080/activity.jpg','99公益日',0,NULL,'2019-04-30 21:01:26',0,'2018年99公益日已经结束，但我们相信爱与公益仍将继续',0,0,58,'99公益日',0),(4,'http://192.168.1.112:8080/activity.jpg','99公益日',0,NULL,'2019-04-30 21:01:26',0,'2018年99公益日已经结束，但我们相信爱与公益仍将继续',0,0,58,'99公益日',0),(5,'http://192.168.1.112:8080/activity.jpg','99公益日',0,NULL,'2019-04-30 21:01:26',0,'2018年99公益日已经结束，但我们相信爱与公益仍将继续',0,0,58,'99公益日',0),(97,'http://192.168.1.112:8080/ff841479923c32b50dd82a6837edb205.jpg','英国国防大臣泄密',0,'<p>据路透社5月2日报道，英国首相特雷莎·梅1日表示，国防大臣加文·威廉姆森应该对“英国政府允许华为有限参加5G网络建设”的消息被提前泄露一事负责，并要求威廉姆森1日晚卸去国防大臣一职。目前，威廉姆森已被开除。1日晚，英国政府任命国际开发大臣彭妮⋅莫当特接替威廉姆森，担任国防大臣一职。</p><p>4月23日，英国国家安全委员会(NSC)召开会议，决定允许中国企业华为“有限参与”英国的5G网络建设，几小时后，这一本该高度机密的信息被泄露给了媒体。唐宁街10号誓言要查国安会“内鬼”。路透社称，特雷莎·梅在给威廉姆森的信中表示，未经政府授权的泄密“是极其严重的事情”，也非常令人失望。内阁秘书对泄密事件的调查提供了令人“信服的证据，表明你(威廉姆森)为此负有不可推卸的责任。”特雷莎·梅还说，“更重要的是，我很信任我的内阁成员和国家安全委员会(NSC)的成员。但此次泄密事件的严重性，以及它对整个NSC的运作和英国国家利益的影响，需要我们采取严厉措施和严肃回应。”</p><p>唐宁街5月1日也发表声明称，“首相特雷莎·梅今晚已要求国防大臣威廉姆森离开政府。因为她对国防大臣和内阁成员的能力已失去信心。”</p><p>报道称，彭妮·莫当特是目前英国下院女性议员中唯一的海军预备役军官，曾任国防部武装力量副大臣。2017年任国际开发大臣，2018年4月30日任妇女及平等大臣。</p><p>英国天空新闻(SKY NEWS)评论称，“在威斯敏斯特之外，彭妮•莫当特最有名的可能是穿着泳装和托马斯•戴利(英国跳水运动员)在电视节目‘Splash!’中的跳水瞬间”。“Splash!”是一档英国名人跳水秀，戴利在其中客串跳水教练，指导社会名人们跳水。除了2014年在跳水秀中“一战成名”外，莫当特之前还做过魔术师助手。</p><p>天空新闻认为，从魔术师助手到英国首位国防大臣，莫当特创造了历史。</p>','2019-05-03 16:12:16',0,'继前任国防大臣加文⋅威廉姆森1日因泄密华为事件被解职后，英国政府1日表示，任命英国国际开发大臣彭妮•莫当特为新任国防大臣，这是英国首次任命女性担当此职务。',4,9,58,'继前任因泄密华为事件被解职后，英国任命首位女性国防大臣',0);
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation` (
  `id` bigint(20) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `id` bigint(20) NOT NULL,
  `content` text,
  `end_time` datetime DEFAULT NULL,
  `gallery` tinyblob,
  `img` varchar(255) DEFAULT NULL,
  `raised_money` decimal(10,2) DEFAULT '0.00',
  `target_money` decimal(10,2) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `donor_count` int(11) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `summary` text,
  `created_time` datetime DEFAULT NULL,
  `bumo_address` varchar(255) DEFAULT NULL,
  `bumo_private_key` varchar(255) DEFAULT NULL,
  `watch_count` int(11) DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FKte6bms4bq1ixfhn024qtysmcg` (`author_id`),
  KEY `FKe0w7gh0rpmxo35nltk6g8517q` (`category_id`),
  FULLTEXT KEY `ft_project_index` (`name`,`summary`,`content`) /*!50100 WITH PARSER `ngram` */ ,
  CONSTRAINT `FKe0w7gh0rpmxo35nltk6g8517q` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKte6bms4bq1ixfhn024qtysmcg` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (20,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',214.00,4000.00,'2019-06-21 14:39:58',1,106,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(21,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/i-1.jpg',0.00,4000.00,NULL,1,100,'想要飞跃墙角的星月',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(22,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',0.00,4000.00,NULL,1,100,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(23,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/i-1.jpg',0.00,4000.00,NULL,1,100,'想要飞跃墙角的星月',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(24,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',0.00,4000.00,NULL,1,100,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(25,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/i-1.jpg',0.00,4000.00,NULL,1,100,'想要飞跃墙角的星月',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(26,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',0.00,4000.00,NULL,1,100,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(27,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/i-1.jpg',0.00,4000.00,NULL,1,100,'想要飞跃墙角的星月',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(28,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',0.00,4000.00,NULL,1,100,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(29,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/i-1.jpg',0.00,4000.00,NULL,1,100,'想要飞跃墙角的星月',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(30,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',0.00,4000.00,NULL,1,100,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(31,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/i-1.jpg',0.00,4000.00,NULL,1,100,'想要飞跃墙角的星月',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(32,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',0.00,4000.00,NULL,1,100,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(33,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/i-1.jpg',0.00,4000.00,NULL,1,100,'想要飞跃墙角的星月',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(34,NULL,'2019-04-20 15:00:00','','http://192.168.1.112:8080/d-1.jpg',0.00,4000.00,NULL,1,100,'玉洪灾区救援',1,'2019-03-20 15:00:00','玉树州称多县珍秦镇是整个青海玉树最贫穷气候最恶劣的一个乡镇。玉树州大部分地区海拔在4000-5000米之间，此次降雪还伴随寒潮，持续时间长，部分乡村积雪厚度接近半米，积雪难以在短时间内消融。只因该地气候恶劣，自然灾害频发，特别是地震使他们失去了亲人。再加上今年的一场雪灾使他们丧失了维持生”计的牛羊，这些人没有工作，没有田地，也没有虫草采挖地，加之失去牛羊，可以说根本没有生活来源，大部分家庭困难重重，无法维持生活。',NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(72,'<p>内容</p>',NULL,_binary 'http://localhost:8080/i-3.jpg','http://192.168.1.112:8080/i-3.jpg',0.00,4000.00,NULL,1,0,NULL,58,NULL,NULL,NULL,'buQa6j7pigTK4LueLTdtSz3ZcXLqbgYBepTT','privbykAGxovoLd8obM9sueHxQ8SEmJWijjUp6ZTpnUcYnqCvbWBWiy7',0,1),(125,'<p>测试内容</p>','2019-07-27 16:04:38',_binary 'http://localhost:8080/ä¸å¯¸èåºè¯ä»¶ç§-compressed.JPG','http://localhost:8080/一寸蓝底证件照-compressed.JPG',0.00,1000.00,'2019-08-09 14:50:52',1,0,'测试项目',58,'2019-07-27 16:04:38',NULL,'2019-07-27 16:05:03','buQjuriEdUxy4b3Lghrp7agRKxWMnE8LGvJE','privbx2kSfasnnzDbmesrUt9bAqGwPXjCWHuvudietpudnCY4seLrbu4',0,4),(127,'<p>无内容</p>','2019-08-08 16:43:53',_binary 'http://localhost:8080/Ã¤Â¸ÂÃ¥Â¯Â¸Ã¨ÂÂÃ¥ÂºÂÃ¨Â¯ÂÃ¤Â»Â¶Ã§ÂÂ§-compressed.JPG','http://localhost:8080/一寸蓝底证件照-compressed.JPG',0.00,1000.00,'2019-08-09 14:49:53',1,0,'发布测试',58,'2019-08-08 16:43:53',NULL,'2019-08-08 16:44:26','buQhLJA84z2wc1SDTeux7ZzxGUCGZLHTAoZM','privbvbXiAFVtU7ukMrEfU7wUAbT3ygxvfXpk9r93Fgu7qNEUNmUPXi6',0,4);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_favor_users`
--

DROP TABLE IF EXISTS `project_favor_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_favor_users` (
  `project_id` bigint(20) NOT NULL,
  `favor_users_id` bigint(20) NOT NULL,
  KEY `FKmppw2jryvvumap7kxr23r4is3` (`favor_users_id`),
  KEY `FK83yfasm4nty54f8rk34vj1dgo` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_favor_users`
--

LOCK TABLES `project_favor_users` WRITE;
/*!40000 ALTER TABLE `project_favor_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_favor_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_followed_users`
--

DROP TABLE IF EXISTS `project_followed_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_followed_users` (
  `follow_projects_id` bigint(20) NOT NULL,
  `followed_users_id` bigint(20) NOT NULL,
  KEY `FKgjbhqrk0ukhijymo0b871w76f` (`followed_users_id`),
  KEY `FKj6ply3wxjahcj2sbd9mxqf103` (`follow_projects_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_followed_users`
--

LOCK TABLES `project_followed_users` WRITE;
/*!40000 ALTER TABLE `project_followed_users` DISABLE KEYS */;
INSERT INTO `project_followed_users` VALUES (20,58);
/*!40000 ALTER TABLE `project_followed_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_schedule`
--

DROP TABLE IF EXISTS `project_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project_schedule` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsp4vkp7er637qidiof2htvl6f` (`project_id`),
  CONSTRAINT `FKsp4vkp7er637qidiof2htvl6f` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_schedule`
--

LOCK TABLES `project_schedule` WRITE;
/*!40000 ALTER TABLE `project_schedule` DISABLE KEYS */;
INSERT INTO `project_schedule` VALUES (95,'项目发布，开始募捐','2019-04-29 22:37:08',72),(96,'<p>添加进展</p>','2019-04-29 22:44:08',72),(126,'<p>添加一条进展详情</p>','2019-08-08 15:44:14',125);
/*!40000 ALTER TABLE `project_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recommend_project`
--

DROP TABLE IF EXISTS `recommend_project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recommend_project` (
  `id` bigint(20) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4i1bcf5hxoonh9d6aa9w4r1ny` (`project_id`),
  CONSTRAINT `FK4i1bcf5hxoonh9d6aa9w4r1ny` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recommend_project`
--

LOCK TABLES `recommend_project` WRITE;
/*!40000 ALTER TABLE `recommend_project` DISABLE KEYS */;
INSERT INTO `recommend_project` VALUES (36,20),(37,20),(38,20),(39,20),(40,20),(41,20),(42,20),(43,20),(44,20),(45,20),(46,20),(47,20),(48,20),(49,20),(50,20),(51,20);
/*!40000 ALTER TABLE `recommend_project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_authorities`
--

DROP TABLE IF EXISTS `role_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_authorities` (
  `role_id` bigint(20) NOT NULL,
  `authorities_id` bigint(20) NOT NULL,
  KEY `FK5ol8su48pobx3r3k87tcqvd94` (`authorities_id`),
  KEY `FK8dv2uo3imjpm4dmk7pge9v2vo` (`role_id`),
  CONSTRAINT `FK5ol8su48pobx3r3k87tcqvd94` FOREIGN KEY (`authorities_id`) REFERENCES `operation` (`id`),
  CONSTRAINT `FK8dv2uo3imjpm4dmk7pge9v2vo` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_authorities`
--

LOCK TABLES `role_authorities` WRITE;
/*!40000 ALTER TABLE `role_authorities` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint(20) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `money` decimal(19,2) DEFAULT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `payee_id` bigint(20) DEFAULT NULL,
  `payer_id` bigint(20) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `asset_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_transaction_id` bigint(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  `unique_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_id_UNIQUE` (`unique_id`),
  KEY `FKign9bv52nu1v48bk5bews03im` (`asset_id`),
  KEY `FKfmnsrnhjuus8b6kwdai0q786i` (`last_transaction_id`),
  KEY `FKcgyse5n2et34vlaafxw9bb4an` (`payee_id`),
  KEY `FKfy3qkr7fidpcwibm1dv48hjhd` (`payer_id`),
  KEY `FKh0tf5dukhwwwiivhdy6jbkp5x` (`project_id`),
  CONSTRAINT `FKcgyse5n2et34vlaafxw9bb4an` FOREIGN KEY (`payee_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKfy3qkr7fidpcwibm1dv48hjhd` FOREIGN KEY (`payer_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKh0tf5dukhwwwiivhdy6jbkp5x` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (70,0,50.00,20,1,58,NULL,68,NULL,NULL,NULL,NULL,70),(99,2,10.00,NULL,58,NULL,NULL,NULL,NULL,NULL,NULL,NULL,99),(100,0,1.00,20,NULL,58,NULL,NULL,NULL,NULL,NULL,'f4b8b75f160a517d027399d5cb947b2424c7eabb28c9a9ef2f07ad302e40af18',100),(102,0,2.00,20,NULL,58,NULL,NULL,NULL,NULL,NULL,'bdb74482a689c388575a506b1fd9a5c940edae50e865824293f9774ea28e6dad',102),(103,2,10.00,NULL,58,NULL,NULL,NULL,NULL,NULL,NULL,'48cc75e8651d7f52997d5653857f4986176fb9969a837115ed32d359ae4524ad',103),(104,0,5.00,20,NULL,58,NULL,NULL,NULL,NULL,NULL,'180177f6b98bf1693d464c7168814314eecaf1bf0facdc282ab898e69f9cd09d',104),(113,0,2.00,20,NULL,58,NULL,NULL,NULL,NULL,NULL,'34fcc6612a50290d1eda2fb3a35b643aca60b31cb0765055239d84191de08f6d',113),(115,0,5.00,20,NULL,58,NULL,NULL,NULL,NULL,NULL,'75586ee7a6e11e1aa4a06c3a4ef592d46d50a13b98ba5dceba15344c2cd2e51b',115),(117,2,95.00,NULL,58,NULL,NULL,NULL,NULL,NULL,NULL,'61f630a27f9e270b9c4d553f2ecf2e9e6d74081b348c5746aa6f06c40be9b5bd',117),(118,0,100.00,20,NULL,58,NULL,NULL,NULL,NULL,NULL,'1d8e4963e7dcf26ae6c4ceef548b6c019706ff6b4ca14fd01c72ea2b7f25d321',118),(119,2,100.00,NULL,58,NULL,NULL,NULL,NULL,NULL,NULL,'197de057c2a97e2fe5204d26b3fe8ef508cdbc8b8eef840c8a4fdd0daae3794e',119),(120,0,100.00,20,NULL,58,NULL,NULL,NULL,NULL,NULL,'5bebdec8588e3295105d2c948532d7741b53c85181c0d82a7d2ebb2a95bf6b20',120);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `avatar_link` varchar(255) DEFAULT NULL,
  `bubi_address` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `presentation` varchar(255) DEFAULT NULL,
  `profession` varchar(255) DEFAULT NULL,
  `money` decimal(19,2) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `bumo_address` varchar(255) DEFAULT NULL,
  `bumo_private_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `user_fulltext_index` (`nick_name`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,NULL,NULL,'爱心十字会','{bcrypt}$2a$10$YrcHKqQaaL3obleZT3wlDOuIzE2CFKLO599BHyfgvnVfiLucQ8Q0i','12345678903','http://192.168.1.112:8080/charity.png',NULL,NULL,NULL,50.00,NULL,NULL,1,'buQsx9VwNbWnGEyiQDhp1tE9e8Ny21cUTUHy','privbvqbDHEtgqigbuCpDTz275XN4STG8b5w8fuNrtCBzEKM6M5jPHz5'),(58,NULL,NULL,'袁坤','{bcrypt}$2a$10$VoyDMEaIpl7QJ4CvzWpplO5QOG.YGAh2ralwjZSLGZCSg7YjsJaYC','12345678901','http://192.168.1.112:8080/charity.png','江西:南昌:青山湖','积土成山，积善成德',NULL,0.00,'2018-12-31 05:59:03',NULL,22,'buQsx9VwNbWnGEyiQDhp1tE9e8Ny21cUTUHy','privbvqbDHEtgqigbuCpDTz275XN4STG8b5w8fuNrtCBzEKM6M5jPHz5'),(59,NULL,NULL,'75500','{bcrypt}$2a$10$Z9qoxixo5DYHLRNawmoyReE8FP2LN72XneGtNhLACQTM7tdr7XYUG','12345678902','http://192.168.1.112:8080/profile.png',NULL,NULL,NULL,0.00,NULL,NULL,0,'buQsx9VwNbWnGEyiQDhp1tE9e8Ny21cUTUHy','privbvqbDHEtgqigbuCpDTz275XN4STG8b5w8fuNrtCBzEKM6M5jPHz5'),(64,NULL,NULL,'CS_000064','{bcrypt}$2a$10$sctWua5hMgZeB8Bx79zhGOJhsJn3mzspnTvO.nVZIIp49UEw6h68m','12345678905',NULL,NULL,NULL,NULL,0.00,NULL,NULL,0,'buQsx9VwNbWnGEyiQDhp1tE9e8Ny21cUTUHy','privbvqbDHEtgqigbuCpDTz275XN4STG8b5w8fuNrtCBzEKM6M5jPHz5'),(106,NULL,NULL,'75500','{bcrypt}$2a$10$pjRvFgOJTPzruuHPpYyqJecI8xpubflms5jXhI0UWzgFKdS6nU7I6','10000000001',NULL,NULL,NULL,NULL,0.00,NULL,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_favor_projects`
--

DROP TABLE IF EXISTS `user_favor_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_favor_projects` (
  `user_id` bigint(20) NOT NULL,
  `favor_projects_id` bigint(20) NOT NULL,
  KEY `FKmy2hb4j5ny634b2wf7qvv14k3` (`favor_projects_id`),
  KEY `FKgs1b7xhrjf2hb82jhpyp7u05b` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favor_projects`
--

LOCK TABLES `user_favor_projects` WRITE;
/*!40000 ALTER TABLE `user_favor_projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_favor_projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_favor_user`
--

DROP TABLE IF EXISTS `user_favor_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_favor_user` (
  `user_id` bigint(20) NOT NULL,
  `favor_user_id` bigint(20) NOT NULL,
  KEY `FKqck52dnuvigl464rr29m53qxs` (`favor_user_id`),
  KEY `FK4i30c66al8mub6tn4muct0voj` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_favor_user`
--

LOCK TABLES `user_favor_user` WRITE;
/*!40000 ALTER TABLE `user_favor_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_favor_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_follow_projects`
--

DROP TABLE IF EXISTS `user_follow_projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_follow_projects` (
  `followed_users_id` bigint(20) NOT NULL,
  `follow_projects_id` bigint(20) NOT NULL,
  KEY `FKaiq4sm4o2btdskuvjm5k2adtt` (`follow_projects_id`),
  KEY `FKtajerv8i7i0t8rnih2p0i147y` (`followed_users_id`),
  CONSTRAINT `FKaiq4sm4o2btdskuvjm5k2adtt` FOREIGN KEY (`follow_projects_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FKtajerv8i7i0t8rnih2p0i147y` FOREIGN KEY (`followed_users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_follow_projects`
--

LOCK TABLES `user_follow_projects` WRITE;
/*!40000 ALTER TABLE `user_follow_projects` DISABLE KEYS */;
INSERT INTO `user_follow_projects` VALUES (58,24),(58,22),(58,21),(58,23),(58,26),(58,20);
/*!40000 ALTER TABLE `user_follow_projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_followed_users`
--

DROP TABLE IF EXISTS `user_followed_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_followed_users` (
  `user_id` bigint(20) NOT NULL,
  `followed_users_id` bigint(20) NOT NULL,
  KEY `FKmt0ri497ma911v0uwnji3illc` (`followed_users_id`),
  KEY `FKec3rwbn6tjx2n835y162i7v0o` (`user_id`),
  CONSTRAINT `FKec3rwbn6tjx2n835y162i7v0o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKmt0ri497ma911v0uwnji3illc` FOREIGN KEY (`followed_users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_followed_users`
--

LOCK TABLES `user_followed_users` WRITE;
/*!40000 ALTER TABLE `user_followed_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_followed_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_transactions`
--

DROP TABLE IF EXISTS `user_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_transactions` (
  `user_id` bigint(20) NOT NULL,
  `transactions_id` bigint(20) NOT NULL,
  KEY `FKli5uya1wifm860dqhjke7kyuj` (`transactions_id`),
  KEY `FK7ow9tl4pncou0lojsgqej6sce` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_transactions`
--

LOCK TABLES `user_transactions` WRITE;
/*!40000 ALTER TABLE `user_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_transactions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-25 19:26:39
