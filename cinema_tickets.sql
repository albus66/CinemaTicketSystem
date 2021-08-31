/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : cinema_tickets

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-05-31 13:41:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for movies
-- ----------------------------
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `type` varchar(50) NOT NULL,
  `director` varchar(100) NOT NULL,
  `source` varchar(50) NOT NULL,
  `publisher` varchar(100) NOT NULL,
  `release_date` varchar(30) NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of movies
-- ----------------------------
INSERT INTO `movies` VALUES ('1', '加勒比海盗', '奇幻', '乔阿吉姆·罗恩尼', '美国', '迪士尼公司', '05/26/2017');
INSERT INTO `movies` VALUES ('2', '摔跤吧！爸爸', '传记', '涅提帝瓦里', '印度', '迪士尼公司', '05/10/2017');
INSERT INTO `movies` VALUES ('3', '黑白照相馆', '悬疑', '李柯', '中国', '霍城指南影业', '05/26/2017');
INSERT INTO `movies` VALUES ('11', '测试', '电影', '导演', '中国', '测试', '05/28/2017');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `data` varchar(1000) NOT NULL,
  `place_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('1', '相遇', '13345678900', '1 1,2|加勒比海盗|05/28/2017 19:30;2 2,9|摔跤吧！爸爸|05/28/2017 14:30;3 3,5|黑白照相馆|05/27/2017 12:00;2 2,5|摔跤吧！爸爸|05/28/2017 14:30', '2017-05-27 05:50:17');
INSERT INTO `orders` VALUES ('2', '王强', '13456789012', '2 1,2|摔跤吧！爸爸|05/28/2017 14:30', '2017-05-27 06:06:22');
INSERT INTO `orders` VALUES ('3', '娜娜', '13619926654', '3 1,2|黑白照相馆|05/27/2017 12:00;3 3,2|黑白照相馆|05/27/2017 12:00', '2017-05-28 10:26:50');
INSERT INTO `orders` VALUES ('4', '测试', '13536456886', '3 6,6|黑白照相馆|05/27/2017 12:00', '2017-05-28 20:59:14');

-- ----------------------------
-- Table structure for shows
-- ----------------------------
DROP TABLE IF EXISTS `shows`;
CREATE TABLE `shows` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) NOT NULL,
  `hall` int(11) NOT NULL,
  `time` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `seats_used` varchar(2000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shows
-- ----------------------------
INSERT INTO `shows` VALUES ('1', '1', '5', '05/28/2017 19:30', '60', '1,2');
INSERT INTO `shows` VALUES ('2', '2', '3', '05/28/2017 14:30', '50', '2,9 2,5 1,2');
INSERT INTO `shows` VALUES ('3', '2', '6', '05/27/2017 12:00', '30', '3,5 1,2 3,2 6,6');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('1', 'admin', '123456', '99');
INSERT INTO `staff` VALUES ('2', 'user2', '123456', '4');
INSERT INTO `staff` VALUES ('3', 'user3', '333333', '2');
INSERT INTO `staff` VALUES ('4', 'user4', '444444', '3');
INSERT INTO `staff` VALUES ('5', 'user5', '555555', '1');
INSERT INTO `staff` VALUES ('6', 'test', 'test', '50');
INSERT INTO `staff` VALUES ('7', 'gen', 'gen', '99');
INSERT INTO `staff` VALUES ('9', 'gen', '123', '3');
