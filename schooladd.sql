/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50628
Source Host           : localhost:3306
Source Database       : schooladd

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2018-07-05 14:02:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` varchar(255) NOT NULL,
  `r_name` varchar(30) NOT NULL,
  `creat_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('qwertyuiopasdfghjklzxdwhauidwaudnhawi', 'ADMIN', '2018-06-27 11:19:28', '2018-06-27 11:19:30');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `id` varchar(255) NOT NULL,
  `u_name` varchar(8) NOT NULL,
  `pwd` varchar(255) NOT NULL,
  `l_name` varchar(25) NOT NULL COMMENT '登录用户名',
  `role` varchar(255) NOT NULL,
  `creat_date` datetime NOT NULL,
  `update_date` datetime DEFAULT NULL,
  `read_only` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('qwertyuiopasdfghjklzxcvbnm123456789', '系统管理员', '69E17EBC47491F2A95EBB33B4FFD8903', 'admin', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-06-27 11:16:49', '2018-06-27 11:16:52', '1');
