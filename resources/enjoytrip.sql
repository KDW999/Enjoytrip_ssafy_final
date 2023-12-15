-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: enjoytrip
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `article_no` int NOT NULL AUTO_INCREMENT COMMENT '글번호',
  `user_id` varchar(32) NOT NULL COMMENT '작성자 ID',
  `subject` varchar(128) NOT NULL COMMENT '제목',
  `content` varchar(5120) NOT NULL COMMENT '내용 (Markdown text)',
  `attraction_id` varchar(16) DEFAULT NULL COMMENT '관광지 (공공데이터의 contentid), 일반글/리뷰 여부',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일자',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '글 삭제 여부',
  PRIMARY KEY (`article_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `comment_no` int NOT NULL AUTO_INCREMENT COMMENT '댓글번호',
  `article_no` int NOT NULL COMMENT '글번호',
  `user_id` varchar(32) NOT NULL COMMENT '작성자 ID',
  `content` varchar(1024) NOT NULL COMMENT '댓글 내용',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일자',
  PRIMARY KEY (`comment_no`),
  KEY `article_no` (`article_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`article_no`) REFERENCES `board` (`article_no`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` varchar(32) NOT NULL COMMENT '회원 ID',
  `name` varchar(32) NOT NULL COMMENT '이름',
  `password` varchar(256) NOT NULL COMMENT '비밀번호',
  `age_group` int DEFAULT NULL COMMENT '연령대 (10, 20, 30, ...)',
  `gender` char(6) DEFAULT NULL COMMENT '성별 (male, female)',
  `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일자',
  `token` varchar(1000) DEFAULT NULL COMMENT 'Refresh token',
  `is_deleted` tinyint NOT NULL DEFAULT '0' COMMENT '회원 삭제 여부',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('ssafy','김싸피','{bcrypt}$2a$10$PywRS8hPtXKoxNiOLIguLeqQvlSkayIdWl57wSFqlZ/1gCPvP1wPm',NULL,NULL,'2023-11-21 06:14:20',NULL,0);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_plans`
--

DROP TABLE IF EXISTS `trip_plans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trip_plans` (
  `plan_no` int NOT NULL AUTO_INCREMENT COMMENT '여행계획 번호',
  `user_id` varchar(32) NOT NULL COMMENT '작성자 ID',
  `title` varchar(128) NOT NULL COMMENT '여행계획 이름',
  `plan` json NOT NULL COMMENT '여행계획 데이터 (관광지 ID의 배열)',
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일자',
  `edit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일자',
  PRIMARY KEY (`plan_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `trip_plans_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_plans`
--

LOCK TABLES `trip_plans` WRITE;
/*!40000 ALTER TABLE `trip_plans` DISABLE KEYS */;
/*!40000 ALTER TABLE `trip_plans` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-21 15:16:37
