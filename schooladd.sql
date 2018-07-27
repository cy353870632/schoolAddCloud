/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50628
Source Host           : localhost:3306
Source Database       : schooladd

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2018-07-27 13:25:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_dic
-- ----------------------------
DROP TABLE IF EXISTS `system_dic`;
CREATE TABLE `system_dic` (
  `id` varchar(255) NOT NULL,
  `dic_name` varchar(10) NOT NULL,
  `parent_id` varchar(255) NOT NULL,
  `dic_code` varchar(1) NOT NULL,
  `status` varchar(1) NOT NULL,
  `creat_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `read_only` varchar(1) NOT NULL,
  `parent_title` varchar(10) DEFAULT NULL,
  `end_mark` varchar(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_dic
-- ----------------------------

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `id` varchar(255) NOT NULL,
  `title` varchar(10) NOT NULL,
  `parent_id` varchar(255) NOT NULL,
  `end_mark` varchar(1) NOT NULL,
  `status` varchar(1) NOT NULL,
  `creat_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `read_only` varchar(1) NOT NULL,
  `code_path` varchar(20) NOT NULL COMMENT '跳转路径路由',
  `parent_title` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `parentTitle_title` (`title`,`parent_title`) USING BTREE,
  UNIQUE KEY `parentTile_codePath` (`code_path`,`parent_title`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('6fbbf048-62c3-4e5e-ba9f-968e38b2d63e', '操作日志', 'qwertyuiopadwadwacxcvbnm123456789', '1', '2', '2018-07-24 14:36:22', '2018-07-24 14:36:22', '1', 'sysdoDoc', '系统设置');
INSERT INTO `system_menu` VALUES ('dwacertyuiopaddwacqqcxcvbnm123acea6789', '推广员管理', 'qwertfacaxpadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'promoterMange', '用户管理');
INSERT INTO `system_menu` VALUES ('dwacertyuiopadwavbnm123acea6789', '管理员管理', 'qwertfacaxpadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'userMange', '用户管理');
INSERT INTO `system_menu` VALUES ('qwertfacaxpadwadwacxcvbnm123456789', '用户管理', '0', '0', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'systemManage', '');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwaacqqcxdawm123456789', '权限管理', 'qwertyuiopadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'roleManage', '系统设置');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwacqqcxcvbnm123456789', '字典管理', 'qwertyuiopadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'dicManage', '系统设置');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwacqqcxcvbnm123acea6789', '商户管理', 'qwertfacaxpadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'busineMange', '用户管理');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwacqqcxcvbnm123ca6789', '学校管理', 'qwertfacaxpadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'schoolMange', '用户管理');
INSERT INTO `system_menu` VALUES ('qwertyuiopaddwacqqcxdawm123456789', '菜单管理', 'qwertyuiopadwadwacxcvbnm123456789', '1', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'menuManage', '系统设置');
INSERT INTO `system_menu` VALUES ('qwertyuiopadwadwacxcvbnm123456789', '系统设置', '0', '0', '1', '2018-07-10 09:09:25', '2018-07-10 09:09:28', '1', 'system', '');

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
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbn1W1', 'qwertyuiopaddwacqqcxcvbnm123456789', 'qwertyuiopasdfghjklzxdwdwada', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbn1W2', 'qwertfacaxpadwadwacxcvbnm123456789', 'qwertyuiopasdfghjklzxdwdwada', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbn1W3', 'qwertyuiopaddwacqqcxcvbnm123ca6789', 'qwertyuiopasdfghjklzxdwdwada', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbn1W4', 'qwertyuiopaddwacqqcxcvbnm123acea6789', 'qwertyuiopasdfghjklzxdwdwada', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbn1W5', 'dwacertyuiopaddwacqqcxcvbnm123acea6789', 'qwertyuiopasdfghjklzxdwdwada', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbn1WA', 'qwertyuiopadwadwacxcvbnm123456789', 'qwertyuiopasdfghjklzxdwdwada', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW1', 'qwertyuiopaddwacqqcxcvbnm123456789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW2', 'qwertfacaxpadwadwacxcvbnm123456789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW3', 'qwertyuiopaddwacqqcxcvbnm123ca6789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW4', 'qwertyuiopaddwacqqcxcvbnm123acea6789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW5', 'dwacertyuiopaddwacqqcxcvbnm123acea6789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW6', 'dwacertyuiopadwavbnm123acea6789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW7', 'qwertyuiopaddwacqqcxdawm123456789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
INSERT INTO `system_menu_role` VALUES ('qwertyuiopadwadwacxcvbnDW8', 'qwertyuiopaddwaacqqcxdawm123456789', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-07-10 09:16:02', '2018-07-10 09:16:04', '1');
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
  PRIMARY KEY (`id`),
  UNIQUE KEY `r_name` (`r_name`) USING BTREE,
  UNIQUE KEY `r_name_china` (`r_name_china`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('qwertyuiopasdfdawdwadwhauidwaudnhawi', 'PROMOTER', '2018-06-27 11:19:28', '2018-06-27 11:19:30', '0', '99', '推广员');
INSERT INTO `system_role` VALUES ('qwertyuiopasdfghjklzxdwdwada', 'SYSADMIN', '2018-06-27 11:19:28', '2018-06-27 11:19:30', '1', '9998', '系统管理员');
INSERT INTO `system_role` VALUES ('qwertyuiopasdfghjklzxdwhauidwaudnhawi', 'ADMIN', '2018-06-27 11:19:28', '2018-06-27 11:19:30', '1', '9999', '超级管理员');

-- ----------------------------
-- Table structure for system_school
-- ----------------------------
DROP TABLE IF EXISTS `system_school`;
CREATE TABLE `system_school` (
  `id` varchar(255) NOT NULL,
  `s_name` varchar(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(11) NOT NULL,
  `s_user_name` varchar(20) NOT NULL,
  `creat_date` datetime NOT NULL,
  `update_date` datetime NOT NULL,
  `read_only` varchar(1) NOT NULL,
  `local` varchar(20) NOT NULL COMMENT '定位坐标',
  `city` varchar(10) NOT NULL,
  `style` varchar(2) NOT NULL COMMENT '学校类型，编码',
  `description` varchar(255) NOT NULL,
  `president` varchar(30) NOT NULL COMMENT '校长',
  `status` varchar(1) NOT NULL,
  `style_name` varchar(30) NOT NULL COMMENT '类型编码的name',
  PRIMARY KEY (`id`),
  UNIQUE KEY `parentTitle_title` (`s_name`,`city`) USING BTREE,
  UNIQUE KEY `parentTile_codePath` (`local`,`city`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_school
-- ----------------------------
INSERT INTO `system_school` VALUES ('6fbbf048-62c3-4e5e-ba9f-968dawdawd63e', '厦门第十中学', '福建省厦门市集美区杏林纺织东路7号', '13062439111', '林主任', '2018-07-24 14:36:22', '2018-07-24 14:36:22', '1', '118.055984,24.575074', '厦门', '高中', '厦门集美区的一个高中，优质学校', '李校长', '', '');

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
  `delete_status` varchar(1) NOT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `id_card` varchar(18) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `l_name` (`l_name`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `id_card` (`id_card`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('13852b7f-05f9-2-928d-498bcdf49885', '老张', 'FF881AF9C34842910BED0E2A1E628E20', 'zhanglin', 'qwertyuiopasdfdawdwadwhauidwaudnhawi', '2018-07-17 16:40:18', '2018-07-18 10:11:22', '0', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531904829838&di=0fa15e20ec4f66058784bd25b7b62e7a&imgtype=0&src=http%3A%2F%2Fpic5.photophoto.cn%2F20071020%2F0010023857354026_b.jpg', '1', '13888888887', '1', '女', '620502199111221622');
INSERT INTO `system_user` VALUES ('13852b7f-05f9-4cb0-928d-498bcdf49885', '刘亦菲', 'F379EAF3C831B04DE153469D1BEC345E', 'liuyifei', 'qwertyuiopasdfdawdwadwhauidwaudnhawi', '2018-07-17 16:40:18', '2018-07-18 10:11:22', '0', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531904829838&di=0fa15e20ec4f66058784bd25b7b62e7a&imgtype=0&src=http%3A%2F%2Fpic5.photophoto.cn%2F20071020%2F0010023857354026_b.jpg', '1', '13888888888', '1', '女', '620502199111221655');
INSERT INTO `system_user` VALUES ('fc6b9304-4b2b-41cf-85c9-3995e9b6d976', '陈佳', 'FF881AF9C34842910BED0E2A1E628E20', 'chenjia', 'qwertyuiopasdfghjklzxdwdwada', '2018-07-18 12:29:00', '2018-07-18 12:29:09', '0', null, '998', '18259460057', '1', '女', '620502199101010202');
INSERT INTO `system_user` VALUES ('qwertyuiopasdfghjklzxcvbnm12345621', '程远大大', 'A61D7229A961BE179D2EF0329A6005F7', 'chengyuan', 'qwertyuiopasdfghjklzxdwdwada', '2018-06-27 11:16:49', '2018-07-23 11:39:22', '0', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531904829838&di=0fa15e20ec4f66058784bd25b7b62e7a&imgtype=0&src=http%3A%2F%2Fpic5.photophoto.cn%2F20071020%2F0010023857354026_b.jpg', '998', '13062439522', '1', '男', '620502199111221654');
INSERT INTO `system_user` VALUES ('qwertyuiopasdfghjklzxcvbnm123456789', '超级管理员', '69E17EBC47491F2A95EBB33B4FFD8903', 'admin', 'qwertyuiopasdfghjklzxdwhauidwaudnhawi', '2018-06-27 11:16:49', '2018-06-27 11:16:52', '1', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1531894764&di=51ccf42cfaf433cfa36a07c2601d6bdb&src=http://ww2.sinaimg.cn/large/686315edjw1f7ugayz0gtj20rs0rs3zx.jpg', '999', '13062439513', '1', '男', '620502199111221653');
