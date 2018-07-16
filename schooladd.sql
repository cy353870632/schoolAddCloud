/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50628
Source Host           : localhost:3306
Source Database       : schooladd

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2018-07-16 17:29:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` varchar(255) NOT NULL,
  `title` varchar(10) NOT NULL,
  `parent_id` varchar(255) NOT NULL,
  `end_mark` varchar(1) NOT NULL,
  `state` varchar(1) NOT NULL,
  `creat_date` datetime NOT NULL,
  `upda_date` datetime NOT NULL,
  `read_only` varchar(1) NOT NULL,
  `code_path` varchar(20) NOT NULL COMMENT '跳转路径路由',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('dwacertyuiopaddwacqqcxcvbnm123acea6789', '推广员管理', 'qwertfacaxpadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'promoterMange');
INSERT INTO `system_menu` VALUES ('qwertfacaxpadwadwacxcvbnm123456789', '用户管理', '0', '0', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'systemManage');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwacqqcxcvbnm123456789', '字典管理', 'qwertyuiopadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'dicManage');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwacqqcxcvbnm123acea6789', '商户管理', 'qwertfacaxpadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'busineMange');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwacqqcxcvbnm123ca6789', '学校管理', 'qwertfacaxpadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'schoolMange');
INSERT INTO `system_menu` VALUES ('qwertyuiopadwadwacxcvbnm123456789', '系统设置', '0', '0', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'system');

-- ----------------------------
-- Table structure for system_menu_role
-- ----------------------------
DROP TABLE IF EXISTS `system_menu_role`;
CREATE TABLE `system_menu_role` (
  `id` varchar(255) NOT NULL,
  `menu_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `creat_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `read_only` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_menu_role
-- ----------------------------
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcv1', 'qwertfacaxpadwadwacxcvbnm123456789', 'qwertyuiopasdfdawdwadwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '0');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcv2', 'qwertyuiopaddwacqqcxcvbnm123ca6789', 'qwertyuiopasdfdawdwadwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '0');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcv3', 'qwertyuiopaddwacqqcxcvbnm123acea6789', 'qwertyuiopasdfdawdwadwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '0');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW1', 'qwertyuiopaddwacqqcxcvbnm123456789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW2', 'qwertfacaxpadwadwacxcvbnm123456789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW3', 'qwertyuiopaddwacqqcxcvbnm123ca6789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW4', 'qwertyuiopaddwacqqcxcvbnm123acea6789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW5', 'dwacertyuiopaddwacqqcxcvbnm123acea6789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDWA', 'qwertyuiopadwadwacxcvbnm123456789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `id` varchar(255) NOT NULL,
  `r_name` varchar(30) NOT NULL,
  `creat_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `read_only` varchar(1) NOT NULL,
  `sort` varchar(4) NOT NULL,
  `r_name_china` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('qwertyuiopasdfdawdwadwhauidwaudnhawi', 'PROMOTER', '2018-06-27 11:19:28', '2018-06-27 11:19:30', '0', '99', '推广员');
INSERT INTO `system_role` VALUES ('qwertyuiopasdfghjklzxdwdwada', 'SYSADMIN', '2018-06-27 11:19:28', '2018-06-27 11:19:30', '1', '9998', '系统管理员');
INSERT INTO `system_role` VALUES ('qwertyuiopasdfghjklzxdwhauidwaudnhawi', 'ADMIN', '2018-06-27 11:19:28', '2018-06-27 11:19:30', '1', '9999', '超级管理员');

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
  `image` varchar(255) DEFAULT NULL,
  `user_code` varchar(10) NOT NULL COMMENT '用户种类，系统管理员/推广员',
  `phone` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('qwertyuiopasdfghjklzxcdwada12345621', '老张', '69E17EBC47491F2A95EBB33B4FFD8903', 'zhanglin', 'qwertyuiopasdfdawdwadwhauidwaudnhawi', '2018-06-27 11:16:49', '2018-06-27 11:16:52', '0', '', '1', '13062439512');
INSERT INTO `system_user` VALUES ('qwertyuiopasdfghjklzxcvbnm12345621', '程远', '69E17EBC47491F2A95EBB33B4FFD8903', 'chengyuan', 'qwertyuiopasdfghjklzxdwdwada', '2018-06-27 11:16:49', '2018-06-27 11:16:52', '0', '', '998', '13062439511');
INSERT INTO `system_user` VALUES ('qwertyuiopasdfghjklzxcvbnm123456789', '超级管理员', '69E17EBC47491F2A95EBB33B4FFD8903', 'admin', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-06-27 11:16:49', '2018-06-27 11:16:52', '1', null, '999', '13062439513');
