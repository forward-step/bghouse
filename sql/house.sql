/*
Navicat MySQL Data Transfer

Source Server         : PHPStudy
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : house

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2021-03-20 22:04:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `pwd` varchar(32) NOT NULL,
  `roleId` int(11) DEFAULT NULL COMMENT '角色id,0表示普通用户',
  `status` int(11) DEFAULT '1' COMMENT '0表示JWT token已经失效',
  `realName` varchar(32) DEFAULT NULL COMMENT '真实姓名',
  `idCard` varchar(32) DEFAULT NULL COMMENT '身份证号码',
  `phone` varchar(16) DEFAULT NULL COMMENT '联系电话',
  `salary` int(11) DEFAULT '0' COMMENT '工资',
  `createtime` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`),
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'supyp', '123123', '1', '1', '苏柏良', '440783199810241234', '13680422246', null, '2020-12-01');
INSERT INTO `account` VALUES ('2', '陈建伟', '123123', '77', '1', '陈建伟', '440783199810241234', '13680422246', '1000', '2020-12-02');
INSERT INTO `account` VALUES ('3', '吴杰辉', '123123', null, '1', '吴杰辉', '440783199810241234', '13680422246', '1000', '2020-12-03');
INSERT INTO `account` VALUES ('4', '李嘉龙', '123123', null, '1', null, null, null, '4000', '2020-12-03');
INSERT INTO `account` VALUES ('5', '陈法霖', '123123', null, null, null, null, null, null, '2020-12-03');
INSERT INTO `account` VALUES ('6', '陈杰', '123123', null, '1', null, null, null, null, '2020-12-05');
INSERT INTO `account` VALUES ('7', '甘荣森', '123123', null, null, null, null, null, null, '2020-12-08');
INSERT INTO `account` VALUES ('8', '林进文', '123123', null, null, null, null, null, null, '2020-12-08');
INSERT INTO `account` VALUES ('9', '李汉超', '123123', null, null, null, null, null, null, '2020-12-09');
INSERT INTO `account` VALUES ('10', '王帅', '123123', null, null, null, null, null, '2000', '2020-12-09');
INSERT INTO `account` VALUES ('11', '测试用户', '123123', null, null, null, null, null, null, '2020-12-17');
INSERT INTO `account` VALUES ('12', '林晨光', '123123', null, '1', null, null, null, null, '2021-01-26');
INSERT INTO `account` VALUES ('13', '张智娴', '123123', null, null, null, null, null, null, '2021-01-26');
INSERT INTO `account` VALUES ('14', '林晓瑜', '123123', null, null, null, null, null, null, '2021-01-26');
INSERT INTO `account` VALUES ('15', '黄清清', '123123', null, null, null, null, null, null, '2021-01-26');
INSERT INTO `account` VALUES ('16', '曾一宇', '123123', null, '1', null, null, null, null, '2021-01-26');

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleId` int(11) DEFAULT NULL COMMENT '角色id',
  `permissioniD` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`),
  KEY `permissioniD` (`permissioniD`),
  CONSTRAINT `authority_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `role` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `authority_ibfk_2` FOREIGN KEY (`permissioniD`) REFERENCES `permission` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('1', '1', '1');
INSERT INTO `authority` VALUES ('2', '1', '6');
INSERT INTO `authority` VALUES ('3', '1', '7');
INSERT INTO `authority` VALUES ('4', '1', '9');
INSERT INTO `authority` VALUES ('10', '1', '2');
INSERT INTO `authority` VALUES ('12', '1', null);
INSERT INTO `authority` VALUES ('15', '1', null);
INSERT INTO `authority` VALUES ('23', '1', '26');
INSERT INTO `authority` VALUES ('29', '1', null);
INSERT INTO `authority` VALUES ('30', '1', null);
INSERT INTO `authority` VALUES ('31', '1', null);
INSERT INTO `authority` VALUES ('32', '1', null);
INSERT INTO `authority` VALUES ('33', '1', '39');
INSERT INTO `authority` VALUES ('34', '1', '41');
INSERT INTO `authority` VALUES ('35', '1', '44');
INSERT INTO `authority` VALUES ('36', '1', '46');
INSERT INTO `authority` VALUES ('37', '1', null);
INSERT INTO `authority` VALUES ('38', '1', '58');
INSERT INTO `authority` VALUES ('39', '1', '63');
INSERT INTO `authority` VALUES ('41', null, '53');
INSERT INTO `authority` VALUES ('42', null, '6');
INSERT INTO `authority` VALUES ('43', null, '52');
INSERT INTO `authority` VALUES ('44', null, '57');
INSERT INTO `authority` VALUES ('45', '1', '70');
INSERT INTO `authority` VALUES ('46', '1', null);
INSERT INTO `authority` VALUES ('47', '1', null);
INSERT INTO `authority` VALUES ('48', '1', null);
INSERT INTO `authority` VALUES ('49', '1', null);
INSERT INTO `authority` VALUES ('50', '1', '100');
INSERT INTO `authority` VALUES ('78', '77', '6');
INSERT INTO `authority` VALUES ('79', '77', '52');
INSERT INTO `authority` VALUES ('80', '77', '53');
INSERT INTO `authority` VALUES ('81', '77', '57');
INSERT INTO `authority` VALUES ('82', '78', '1');
INSERT INTO `authority` VALUES ('83', '78', '2');
INSERT INTO `authority` VALUES ('84', '78', '3');
INSERT INTO `authority` VALUES ('85', '78', '4');
INSERT INTO `authority` VALUES ('86', '78', '5');
INSERT INTO `authority` VALUES ('87', '78', '9');
INSERT INTO `authority` VALUES ('88', '78', '10');
INSERT INTO `authority` VALUES ('89', '78', '11');
INSERT INTO `authority` VALUES ('90', '78', '23');
INSERT INTO `authority` VALUES ('91', '78', '24');

-- ----------------------------
-- Table structure for captcha
-- ----------------------------
DROP TABLE IF EXISTS `captcha`;
CREATE TABLE `captcha` (
  `ip` varchar(40) NOT NULL COMMENT '唯一的ip地址',
  `checkCode` varchar(20) NOT NULL COMMENT '验证码内容',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  UNIQUE KEY `ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of captcha
-- ----------------------------
INSERT INTO `captcha` VALUES ('0:0:0:0:0:0:0:1', 'ne8n', '2021-03-20 22:02:11');
INSERT INTO `captcha` VALUES ('127.0.0.1', '4ca4', '2021-03-20 21:19:26');

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '房客id',
  `isSend` int(11) NOT NULL DEFAULT '0' COMMENT '0表示房客发送，1表示客服发送',
  `content` varchar(128) DEFAULT NULL COMMENT '备注',
  `createTime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chat
-- ----------------------------
INSERT INTO `chat` VALUES ('109', '2', '1', '123', '2020-12-01 15:47:30');
INSERT INTO `chat` VALUES ('110', '2', '1', '456', '2020-12-01 15:48:23');
INSERT INTO `chat` VALUES ('111', '2', '0', '客服-123', '2020-12-01 15:48:52');
INSERT INTO `chat` VALUES ('112', '2', '0', '客服-456', '2020-12-01 15:49:31');
INSERT INTO `chat` VALUES ('113', '2', '0', 'true', '2020-12-01 16:33:32');
INSERT INTO `chat` VALUES ('114', '2', '0', 'false', '2020-12-01 16:33:55');
INSERT INTO `chat` VALUES ('115', '2', '0', 'true', '2020-12-01 16:36:51');
INSERT INTO `chat` VALUES ('116', '2', '0', 'false', '2020-12-01 16:37:00');
INSERT INTO `chat` VALUES ('117', '2', '0', 'false', '2020-12-01 16:38:43');
INSERT INTO `chat` VALUES ('118', '2', '0', 'false', '2020-12-01 16:39:48');
INSERT INTO `chat` VALUES ('119', '2', '0', '123', '2020-12-01 16:40:31');
INSERT INTO `chat` VALUES ('120', '2', '0', '456', '2020-12-01 16:40:42');
INSERT INTO `chat` VALUES ('121', '4', '1', '123', '2020-12-01 17:46:58');
INSERT INTO `chat` VALUES ('122', '4', '1', '456', '2020-12-01 17:47:25');
INSERT INTO `chat` VALUES ('123', '4', '1', '789', '2020-12-01 17:48:57');
INSERT INTO `chat` VALUES ('124', '4', '1', '789', '2020-12-01 17:49:02');
INSERT INTO `chat` VALUES ('125', '4', '1', '123', '2020-12-01 18:24:51');
INSERT INTO `chat` VALUES ('126', '4', '1', '456', '2020-12-01 18:25:01');
INSERT INTO `chat` VALUES ('127', '2', '0', '123', '2020-12-01 18:53:43');
INSERT INTO `chat` VALUES ('128', '2', '0', '我是客服', '2020-12-01 18:53:49');
INSERT INTO `chat` VALUES ('129', '2', '0', '我是客服', '2020-12-01 18:53:57');
INSERT INTO `chat` VALUES ('130', '2', '0', '123', '2020-12-01 18:54:37');
INSERT INTO `chat` VALUES ('131', '2', '0', '456', '2020-12-01 18:55:07');
INSERT INTO `chat` VALUES ('132', '2', '0', '123', '2020-12-01 18:58:23');
INSERT INTO `chat` VALUES ('133', '2', '1', '我是房客', '2020-12-01 18:58:53');
INSERT INTO `chat` VALUES ('134', '2', '0', '我是客服', '2020-12-01 18:58:58');
INSERT INTO `chat` VALUES ('135', '2', '0', '你好，你对租房是否存在问题', '2020-12-01 18:59:20');
INSERT INTO `chat` VALUES ('136', '2', '1', '是的', '2020-12-01 18:59:35');
INSERT INTO `chat` VALUES ('137', '1', '0', '你的租约即将过期', '2020-12-03 09:41:43');
INSERT INTO `chat` VALUES ('138', '2', '0', '你的租约即将过期', '2020-12-03 09:41:43');
INSERT INTO `chat` VALUES ('139', '1', '0', '你的租约即将过期', '2020-12-03 09:42:00');
INSERT INTO `chat` VALUES ('140', '2', '0', '你的租约即将过期', '2020-12-03 09:42:00');
INSERT INTO `chat` VALUES ('141', '1', '0', '你的租约即将过期', '2020-12-03 09:42:10');
INSERT INTO `chat` VALUES ('142', '2', '0', '你的租约即将过期', '2020-12-03 09:42:10');
INSERT INTO `chat` VALUES ('143', '1', '0', '你的租约即将过期', '2020-12-03 10:08:11');
INSERT INTO `chat` VALUES ('144', '2', '0', '你的租约即将过期', '2020-12-03 10:08:11');
INSERT INTO `chat` VALUES ('145', '1', '0', '你的租约即将过期', '2020-12-03 10:08:52');
INSERT INTO `chat` VALUES ('146', '2', '0', '你的租约即将过期', '2020-12-03 10:08:52');
INSERT INTO `chat` VALUES ('147', '2', '0', '系统提醒您: 今天是您的预约入住时间', '2020-12-03 10:40:35');
INSERT INTO `chat` VALUES ('148', '2', '0', '系统提醒您: 明天是您的预约入住时间', '2020-12-03 10:42:27');
INSERT INTO `chat` VALUES ('149', '2', '1', 'zaima', '2020-12-03 10:43:00');
INSERT INTO `chat` VALUES ('150', '2', '1', '13', '2020-12-03 10:43:21');
INSERT INTO `chat` VALUES ('151', '2', '0', '你的租约即将过期', '2020-12-03 11:29:43');
INSERT INTO `chat` VALUES ('152', '2', '0', '系统提醒您: 明天是您的预约入住时间', '2020-12-03 11:29:46');
INSERT INTO `chat` VALUES ('153', '2', '0', '132', '2020-12-04 09:47:17');
INSERT INTO `chat` VALUES ('154', '4', '1', '123', '2020-12-04 09:55:52');
INSERT INTO `chat` VALUES ('155', '4', '1', '123', '2020-12-04 09:56:13');
INSERT INTO `chat` VALUES ('156', '4', '0', '我是客服', '2020-12-04 09:56:24');
INSERT INTO `chat` VALUES ('157', '4', '1', '我是房客', '2020-12-04 09:56:31');
INSERT INTO `chat` VALUES ('158', '4', '1', '123', '2020-12-04 10:00:26');
INSERT INTO `chat` VALUES ('159', '4', '1', '123', '2020-12-04 10:24:39');
INSERT INTO `chat` VALUES ('160', '4', '1', '456', '2020-12-04 10:25:16');
INSERT INTO `chat` VALUES ('161', '4', '0', '123', '2020-12-04 10:25:19');
INSERT INTO `chat` VALUES ('162', '3', '1', '在吗', '2020-12-04 10:27:30');
INSERT INTO `chat` VALUES ('163', '3', '1', '123', '2020-12-04 10:28:38');
INSERT INTO `chat` VALUES ('164', '4', '1', '123', '2020-12-04 10:29:35');
INSERT INTO `chat` VALUES ('165', '3', '1', 'hello', '2020-12-04 10:31:50');
INSERT INTO `chat` VALUES ('166', '4', '0', '我是客服', '2020-12-04 10:32:54');
INSERT INTO `chat` VALUES ('167', '4', '1', '我是房客', '2020-12-04 10:33:00');
INSERT INTO `chat` VALUES ('168', '6', '1', '123', '2020-12-04 10:52:21');
INSERT INTO `chat` VALUES ('169', '4', '1', '123', '2020-12-04 17:03:50');
INSERT INTO `chat` VALUES ('170', '4', '0', '我是客服', '2020-12-04 17:03:59');
INSERT INTO `chat` VALUES ('171', '3', '1', 'hello', '2020-12-07 16:43:21');
INSERT INTO `chat` VALUES ('172', '3', '0', '在', '2020-12-07 16:43:27');
INSERT INTO `chat` VALUES ('173', '3', '1', 'hllo', '2020-12-07 16:47:21');
INSERT INTO `chat` VALUES ('174', '7', '1', '我是甘荣森', '2020-12-07 16:48:11');
INSERT INTO `chat` VALUES ('175', '8', '1', 'asd', '2020-12-07 16:56:52');
INSERT INTO `chat` VALUES ('176', '3', '1', '123', '2020-12-09 19:10:51');
INSERT INTO `chat` VALUES ('177', '3', '0', '456', '2020-12-09 19:11:26');
INSERT INTO `chat` VALUES ('178', '3', '0', '456', '2020-12-09 20:47:06');
INSERT INTO `chat` VALUES ('179', '4', '0', 'hello', '2020-12-09 20:47:13');
INSERT INTO `chat` VALUES ('180', '11', '1', '你好在吗', '2020-12-17 19:53:11');
INSERT INTO `chat` VALUES ('181', '11', '0', '有什么想要咨询的嘛', '2020-12-17 19:54:25');
INSERT INTO `chat` VALUES ('182', '11', '0', '测试1', '2020-12-17 19:55:36');
INSERT INTO `chat` VALUES ('183', '11', '0', '测试2', '2020-12-17 19:55:38');
INSERT INTO `chat` VALUES ('184', '11', '0', '测试3', '2020-12-17 19:55:41');
INSERT INTO `chat` VALUES ('185', '11', '0', '测试4', '2020-12-17 19:55:42');
INSERT INTO `chat` VALUES ('186', '11', '0', '测试5', '2020-12-17 19:55:45');
INSERT INTO `chat` VALUES ('187', '11', '0', '测试6', '2020-12-17 19:55:47');
INSERT INTO `chat` VALUES ('188', '11', '0', '测试7', '2020-12-17 19:55:49');
INSERT INTO `chat` VALUES ('189', '11', '0', '测试8', '2020-12-17 19:55:51');
INSERT INTO `chat` VALUES ('190', '11', '0', '测试9', '2020-12-17 19:55:53');
INSERT INTO `chat` VALUES ('191', '11', '0', '测试10', '2020-12-17 19:55:55');
INSERT INTO `chat` VALUES ('192', '11', '0', '测试11', '2020-12-17 19:55:57');
INSERT INTO `chat` VALUES ('193', '11', '0', '测试12', '2020-12-17 19:55:59');
INSERT INTO `chat` VALUES ('194', '11', '0', '测试13', '2020-12-17 19:56:01');
INSERT INTO `chat` VALUES ('195', '11', '0', '测试14', '2020-12-17 19:56:02');
INSERT INTO `chat` VALUES ('196', '3', '1', '123', '2020-12-25 17:24:15');
INSERT INTO `chat` VALUES ('197', '3', '1', '456', '2020-12-25 17:24:18');
INSERT INTO `chat` VALUES ('198', '3', '1', '789', '2020-12-25 17:24:20');
INSERT INTO `chat` VALUES ('199', '3', '0', '123', '2020-12-25 17:24:30');
INSERT INTO `chat` VALUES ('200', '2', '0', '456', '2021-01-06 23:00:40');
INSERT INTO `chat` VALUES ('201', '2', '0', '789', '2021-01-06 23:01:14');
INSERT INTO `chat` VALUES ('202', '3', '0', '789', '2021-01-06 23:05:50');
INSERT INTO `chat` VALUES ('203', '3', '0', 'hello', '2021-01-06 23:06:24');
INSERT INTO `chat` VALUES ('204', '3', '0', '123', '2021-01-08 12:02:31');
INSERT INTO `chat` VALUES ('205', '3', '0', '123', '2021-01-10 21:46:47');
INSERT INTO `chat` VALUES ('206', '3', '0', '456', '2021-01-10 21:55:20');
INSERT INTO `chat` VALUES ('207', '3', '1', '123', '2021-01-16 11:09:00');
INSERT INTO `chat` VALUES ('208', '3', '1', '456', '2021-01-16 11:14:26');
INSERT INTO `chat` VALUES ('209', '3', '0', '123', '2021-01-16 11:14:39');
INSERT INTO `chat` VALUES ('210', '3', '0', '123', '2021-01-16 11:16:14');
INSERT INTO `chat` VALUES ('211', '3', '1', '年后', '2021-01-16 16:11:57');
INSERT INTO `chat` VALUES ('212', '3', '0', 'hello', '2021-01-16 16:13:23');
INSERT INTO `chat` VALUES ('213', '3', '0', 'hello', '2021-01-16 16:13:43');
INSERT INTO `chat` VALUES ('214', '3', '0', '123', '2021-01-16 16:15:02');
INSERT INTO `chat` VALUES ('215', '3', '0', '123', '2021-01-16 16:15:27');
INSERT INTO `chat` VALUES ('216', '3', '0', '456', '2021-01-16 16:16:04');
INSERT INTO `chat` VALUES ('217', '3', '0', '123', '2021-01-16 16:40:03');
INSERT INTO `chat` VALUES ('218', '3', '1', '465', '2021-01-16 16:40:13');
INSERT INTO `chat` VALUES ('219', '3', '0', '123', '2021-01-16 16:41:57');
INSERT INTO `chat` VALUES ('220', '3', '0', '123', '2021-01-16 17:05:54');
INSERT INTO `chat` VALUES ('221', '3', '0', '123', '2021-01-16 17:06:31');
INSERT INTO `chat` VALUES ('222', '3', '0', '123', '2021-01-16 17:06:36');
INSERT INTO `chat` VALUES ('223', '3', '1', '123', '2021-01-16 17:10:17');
INSERT INTO `chat` VALUES ('224', '3', '1', '123', '2021-01-16 17:10:31');
INSERT INTO `chat` VALUES ('225', '3', '1', '123', '2021-01-16 17:10:37');
INSERT INTO `chat` VALUES ('226', '3', '1', '123', '2021-01-16 17:10:39');
INSERT INTO `chat` VALUES ('227', '3', '0', '123', '2021-01-16 17:11:02');
INSERT INTO `chat` VALUES ('228', '3', '1', '123', '2021-01-16 17:11:05');
INSERT INTO `chat` VALUES ('229', '3', '1', '123', '2021-01-16 17:11:35');
INSERT INTO `chat` VALUES ('230', '3', '1', '123', '2021-01-16 17:14:31');
INSERT INTO `chat` VALUES ('231', '3', '1', '456', '2021-01-16 17:22:04');
INSERT INTO `chat` VALUES ('232', '3', '1', '789', '2021-01-16 17:22:38');
INSERT INTO `chat` VALUES ('233', '3', '1', '1', '2021-01-16 17:23:34');
INSERT INTO `chat` VALUES ('234', '3', '1', '2', '2021-01-16 17:25:29');
INSERT INTO `chat` VALUES ('235', '3', '1', '3', '2021-01-16 17:25:43');
INSERT INTO `chat` VALUES ('236', '3', '1', '4', '2021-01-16 17:25:44');
INSERT INTO `chat` VALUES ('237', '3', '1', '5', '2021-01-16 17:25:46');
INSERT INTO `chat` VALUES ('238', '3', '1', '6', '2021-01-16 17:25:47');
INSERT INTO `chat` VALUES ('239', '3', '1', '1', '2021-01-16 17:26:37');
INSERT INTO `chat` VALUES ('240', '3', '1', '2', '2021-01-16 17:27:20');
INSERT INTO `chat` VALUES ('241', '3', '1', '3', '2021-01-16 17:28:28');
INSERT INTO `chat` VALUES ('242', '3', '1', '4', '2021-01-16 17:28:31');
INSERT INTO `chat` VALUES ('243', '3', '1', '5', '2021-01-16 17:29:46');
INSERT INTO `chat` VALUES ('244', '3', '1', '6', '2021-01-16 17:29:48');
INSERT INTO `chat` VALUES ('245', '3', '1', '1', '2021-01-16 17:29:50');
INSERT INTO `chat` VALUES ('246', '3', '1', '2', '2021-01-16 17:29:52');
INSERT INTO `chat` VALUES ('247', '3', '1', '1', '2021-01-16 17:30:53');
INSERT INTO `chat` VALUES ('248', '3', '1', '2', '2021-01-16 17:30:56');
INSERT INTO `chat` VALUES ('249', '3', '1', '3', '2021-01-16 17:30:59');
INSERT INTO `chat` VALUES ('250', '3', '1', '1', '2021-01-16 17:31:03');
INSERT INTO `chat` VALUES ('251', '3', '1', '1', '2021-01-16 17:31:09');
INSERT INTO `chat` VALUES ('252', '3', '1', '2', '2021-01-16 17:31:13');
INSERT INTO `chat` VALUES ('253', '3', '1', '1', '2021-01-16 17:31:40');
INSERT INTO `chat` VALUES ('254', '3', '1', '2', '2021-01-16 17:31:46');
INSERT INTO `chat` VALUES ('255', '3', '1', '1', '2021-01-16 17:32:16');
INSERT INTO `chat` VALUES ('256', '3', '1', '2', '2021-01-16 17:32:18');
INSERT INTO `chat` VALUES ('257', '3', '1', '1', '2021-01-16 17:50:35');
INSERT INTO `chat` VALUES ('258', '3', '1', '2', '2021-01-16 17:50:37');
INSERT INTO `chat` VALUES ('259', '3', '1', '1', '2021-01-16 17:50:53');
INSERT INTO `chat` VALUES ('260', '3', '1', '1', '2021-01-16 17:59:37');
INSERT INTO `chat` VALUES ('261', '3', '1', '1', '2021-01-16 18:00:42');
INSERT INTO `chat` VALUES ('262', '3', '1', '2', '2021-01-16 18:00:44');
INSERT INTO `chat` VALUES ('263', '3', '1', '1', '2021-01-16 18:00:47');
INSERT INTO `chat` VALUES ('264', '3', '1', '2', '2021-01-16 18:00:48');
INSERT INTO `chat` VALUES ('265', '3', '1', '3', '2021-01-16 18:00:49');
INSERT INTO `chat` VALUES ('266', '3', '0', '1', '2021-01-16 18:00:50');
INSERT INTO `chat` VALUES ('267', '3', '1', '2', '2021-01-16 18:00:52');
INSERT INTO `chat` VALUES ('268', '3', '0', '123', '2021-01-16 21:01:22');
INSERT INTO `chat` VALUES ('269', '3', '0', '1', '2021-01-16 21:37:50');
INSERT INTO `chat` VALUES ('270', '3', '0', '2', '2021-01-16 21:37:51');
INSERT INTO `chat` VALUES ('271', '3', '0', '3', '2021-01-16 21:37:53');
INSERT INTO `chat` VALUES ('272', '3', '0', '4', '2021-01-16 21:37:54');
INSERT INTO `chat` VALUES ('273', '3', '0', '1', '2021-01-16 21:43:15');
INSERT INTO `chat` VALUES ('274', '3', '0', '2', '2021-01-16 21:43:35');
INSERT INTO `chat` VALUES ('275', '3', '0', '3', '2021-01-16 21:43:48');
INSERT INTO `chat` VALUES ('276', '3', '0', '2', '2021-01-16 21:44:19');
INSERT INTO `chat` VALUES ('277', '3', '0', '3', '2021-01-16 21:44:28');
INSERT INTO `chat` VALUES ('278', '3', '0', '123', '2021-01-16 21:44:46');
INSERT INTO `chat` VALUES ('279', '3', '1', '1', '2021-01-16 21:46:22');
INSERT INTO `chat` VALUES ('280', '3', '1', '1', '2021-01-16 21:57:11');
INSERT INTO `chat` VALUES ('281', '3', '1', '2', '2021-01-16 21:57:13');
INSERT INTO `chat` VALUES ('282', '3', '1', '3', '2021-01-16 21:57:27');
INSERT INTO `chat` VALUES ('283', '3', '1', '1', '2021-01-16 21:58:19');
INSERT INTO `chat` VALUES ('284', '3', '1', '2', '2021-01-16 22:00:50');
INSERT INTO `chat` VALUES ('285', '3', '1', '1', '2021-01-16 22:02:22');
INSERT INTO `chat` VALUES ('286', '3', '1', '1', '2021-01-16 22:04:18');
INSERT INTO `chat` VALUES ('287', '6', '1', '1', '2021-01-16 22:09:28');
INSERT INTO `chat` VALUES ('288', '6', '1', '2', '2021-01-16 22:09:34');
INSERT INTO `chat` VALUES ('289', '6', '1', '1', '2021-01-16 22:09:35');
INSERT INTO `chat` VALUES ('290', '6', '1', '123', '2021-01-16 22:09:36');
INSERT INTO `chat` VALUES ('291', '6', '1', '123', '2021-01-16 22:09:38');
INSERT INTO `chat` VALUES ('292', '6', '1', '123', '2021-01-16 22:09:39');
INSERT INTO `chat` VALUES ('293', '6', '1', '1', '2021-01-16 22:14:55');
INSERT INTO `chat` VALUES ('294', '6', '1', '2', '2021-01-16 22:14:59');
INSERT INTO `chat` VALUES ('295', '6', '1', '3', '2021-01-16 22:15:00');
INSERT INTO `chat` VALUES ('296', '12', '1', '你好，我是林晨光', '2021-01-26 21:24:49');
INSERT INTO `chat` VALUES ('297', '13', '1', '你好，我是张智娴', '2021-01-26 21:25:48');
INSERT INTO `chat` VALUES ('298', '14', '1', '你好，我是林晓瑜', '2021-01-26 21:26:33');
INSERT INTO `chat` VALUES ('299', '15', '1', '你好，我是黄清清', '2021-01-26 21:27:20');
INSERT INTO `chat` VALUES ('300', '16', '1', '你好，我是曾一宇', '2021-01-26 21:27:58');
INSERT INTO `chat` VALUES ('301', '16', '0', '你好，我是管理员', '2021-01-26 21:47:18');
INSERT INTO `chat` VALUES ('302', '16', '0', '请先完善信息', '2021-01-30 10:11:26');
INSERT INTO `chat` VALUES ('303', '16', '0', '请先完善信息', '2021-01-30 10:11:30');
INSERT INTO `chat` VALUES ('304', '16', '0', '请先完善信息', '2021-01-30 11:21:03');
INSERT INTO `chat` VALUES ('305', '3', '0', '2', '2021-01-30 17:27:15');
INSERT INTO `chat` VALUES ('306', '3', '0', '系统提醒您: 明天是您的预约入住时间', '2021-01-30 20:52:16');
INSERT INTO `chat` VALUES ('307', '3', '0', '系统提醒您: 明天是您的预约入住时间', '2021-01-30 20:52:16');
INSERT INTO `chat` VALUES ('308', '16', '0', '请先完善信息', '2021-02-05 22:02:54');
INSERT INTO `chat` VALUES ('309', '16', '0', '请先完善信息', '2021-03-06 11:02:19');
INSERT INTO `chat` VALUES ('310', '3', '1', '123', '2021-03-20 21:19:41');
INSERT INTO `chat` VALUES ('311', '3', '1', '123', '2021-03-20 21:19:42');
INSERT INTO `chat` VALUES ('312', '3', '1', '123', '2021-03-20 21:27:30');

-- ----------------------------
-- Table structure for contract
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '房客id',
  `roomId` int(11) DEFAULT NULL COMMENT '房间id',
  `createTime` datetime NOT NULL COMMENT '合同签订时间',
  `checkInTime` date NOT NULL COMMENT '入住时间',
  `durationTime` int(11) NOT NULL COMMENT '租约时间，单位为月份',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `roomId` (`roomId`),
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_2` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract
-- ----------------------------
INSERT INTO `contract` VALUES ('21', '3', '3', '2021-02-02 20:52:45', '2021-02-03', '1', '2021-02-02租房1个月\n');
INSERT INTO `contract` VALUES ('22', '3', '4', '2021-02-02 20:57:47', '2021-02-05', '0', '2021-02-02租房1个月\n2021-02-03 续租1个月\n2021-02-03 退租\n');
INSERT INTO `contract` VALUES ('23', '3', '15', '2021-02-02 21:08:40', '2021-02-03', '1', '2021-02-02租房5个月\n2021-02-02 退租\n2021-02-02 退租\n2021-02-02 退租\n2021-02-03 退租\n2021-02-03 退租\n2021-02-03 退租\n2021-02-03 退租\n2021-02-03 退租\n');

-- ----------------------------
-- Table structure for maintain
-- ----------------------------
DROP TABLE IF EXISTS `maintain`;
CREATE TABLE `maintain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomId` int(11) DEFAULT NULL COMMENT '房间id',
  `remark` varchar(128) NOT NULL COMMENT '维修的原因、描述信息',
  `startTime` date NOT NULL COMMENT '开始维修的时间',
  `isEnd` int(11) NOT NULL COMMENT '维修是否结束',
  `price` float(8,2) DEFAULT NULL COMMENT '维修费用',
  `endTime` date DEFAULT NULL COMMENT '结束维修的时间',
  PRIMARY KEY (`id`),
  KEY `roomId` (`roomId`),
  CONSTRAINT `maintain_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintain
-- ----------------------------
INSERT INTO `maintain` VALUES ('1', null, '水管爆裂', '2020-11-28', '1', '100.00', '2019-12-01');
INSERT INTO `maintain` VALUES ('2', null, '窗户碎裂', '2020-11-28', '1', '50.00', '2020-11-28');
INSERT INTO `maintain` VALUES ('3', null, '墙壁裂开', '2020-11-28', '1', '500.00', '2020-12-01');
INSERT INTO `maintain` VALUES ('4', null, 'reamrk', '2020-11-28', '1', '603.00', '2020-12-02');
INSERT INTO `maintain` VALUES ('5', '4', '地面裂开', '2020-12-07', '1', '100.00', '2021-01-31');
INSERT INTO `maintain` VALUES ('6', '3', '水管破裂', '2021-03-06', '0', null, null);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) DEFAULT NULL COMMENT '父级权限',
  `name` varchar(32) NOT NULL COMMENT '权限名称',
  `url` varchar(128) DEFAULT NULL COMMENT '允许访问的URL',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`),
  CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `permission` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', null, '角色管理', '/admin/role');
INSERT INTO `permission` VALUES ('2', '1', '添加角色', '/admin/role/add');
INSERT INTO `permission` VALUES ('3', '1', '修改角色', '/admin/role/edit');
INSERT INTO `permission` VALUES ('4', '1', '删除角色', '/admin/role/delete');
INSERT INTO `permission` VALUES ('5', '1', '列表角色', '/admin/role/findAll');
INSERT INTO `permission` VALUES ('6', null, '聊天', '/admin/chat');
INSERT INTO `permission` VALUES ('7', null, '授权管理', '/admin/authority');
INSERT INTO `permission` VALUES ('8', '7', '查找我的授权', '/admin/authority/findMyAuthority');
INSERT INTO `permission` VALUES ('9', null, '权限管理', '/admin/permission');
INSERT INTO `permission` VALUES ('10', '9', '查找权限', '/admin/permission/findAll');
INSERT INTO `permission` VALUES ('11', '9', '添加权限', '/admin/permission/add');
INSERT INTO `permission` VALUES ('23', '9', '删除权限', '/admin/permission/delete');
INSERT INTO `permission` VALUES ('24', '9', '编辑权限', '/admin/permission/edit');
INSERT INTO `permission` VALUES ('25', '7', '添加授权', '/admin/authority/add');
INSERT INTO `permission` VALUES ('26', null, '房间管理', '/admin/room');
INSERT INTO `permission` VALUES ('27', '26', '添加房间', '/admin/room/add');
INSERT INTO `permission` VALUES ('28', '26', '查找所有的房间', '/admin/room/findAll');
INSERT INTO `permission` VALUES ('37', '26', '编辑房间', '/admin/room/edit');
INSERT INTO `permission` VALUES ('38', '26', '删除房间', '/admin/room/delete');
INSERT INTO `permission` VALUES ('39', null, '预约管理', '/admin/subscribe');
INSERT INTO `permission` VALUES ('40', '39', '查找预约', '/admin/subscribe/findAll');
INSERT INTO `permission` VALUES ('41', null, '维修管理', '/admin/maintain');
INSERT INTO `permission` VALUES ('42', '41', '添加维修', '/admin/maintain/add');
INSERT INTO `permission` VALUES ('43', '41', '查找维修', '/admin/maintain/findAll');
INSERT INTO `permission` VALUES ('44', null, '员工管理', '/admin/staff');
INSERT INTO `permission` VALUES ('45', '44', '查找员工', '/admin/staff/findAll');
INSERT INTO `permission` VALUES ('46', null, '房客管理', '/admin/roomer');
INSERT INTO `permission` VALUES ('48', '44', '添加员工', '/admin/staff/add');
INSERT INTO `permission` VALUES ('49', '44', '解雇员工', '/admin/staff/delete');
INSERT INTO `permission` VALUES ('50', '44', '修改员工信息', '/admin/staff/edit');
INSERT INTO `permission` VALUES ('51', '41', '完成维修', '/admin/maintain/edit');
INSERT INTO `permission` VALUES ('52', '6', '找朋友', '/admin/chat/findFriedns');
INSERT INTO `permission` VALUES ('53', '6', '查找聊天记录', '/admin/chat/findChat');
INSERT INTO `permission` VALUES ('57', '6', '设置未读信息', '/admin/chat/setup');
INSERT INTO `permission` VALUES ('58', null, '合同管理', '/admin/contract');
INSERT INTO `permission` VALUES ('59', '58', '添加合同', '/admin/contract/add');
INSERT INTO `permission` VALUES ('60', '58', '查找合同', '/admin/contract/findAll');
INSERT INTO `permission` VALUES ('61', '58', '续租', '/admin/contract/relet');
INSERT INTO `permission` VALUES ('62', '58', '退租', '/admin/contract/exit');
INSERT INTO `permission` VALUES ('63', null, '测试功能', '/admin/test');
INSERT INTO `permission` VALUES ('64', '63', '手动触发合同到期提醒', '/admin/test/fireContractJob');
INSERT INTO `permission` VALUES ('66', '63', '手动触发预约提醒', '/admin/test/fireSubscribeJob');
INSERT INTO `permission` VALUES ('67', '63', '释放房间占用', '/admin/test/fireReleaseRoomJob');
INSERT INTO `permission` VALUES ('68', '46', '查找房客', '/admin/roomer/findAll');
INSERT INTO `permission` VALUES ('69', '63', '手动触发预约超时自动取消', '/admin/test/fireMissSubscribeJob');
INSERT INTO `permission` VALUES ('70', null, '经营分析', '/admin/chart');
INSERT INTO `permission` VALUES ('71', '70', '查找顶层信息', '/admin/chart/findTopView');
INSERT INTO `permission` VALUES ('72', '70', '查找维修信息', '/admin/chart/findMaintain');
INSERT INTO `permission` VALUES ('73', '70', '查找预约订单信息', '/admin/chart/findSubscribe');
INSERT INTO `permission` VALUES ('74', '70', '查找合同信息', '/admin/chart/findContract');
INSERT INTO `permission` VALUES ('75', '70', '查找report', '/admin/chart/report');
INSERT INTO `permission` VALUES ('100', null, '测试编辑效果-无意义', '/qweasd');
INSERT INTO `permission` VALUES ('101', '100', '房间名1', '/qwea');
INSERT INTO `permission` VALUES ('102', '100', '房间名2', '/qwe/asdqwe');
INSERT INTO `permission` VALUES ('103', '39', '根据用户id查找预约表单', '/admin/subscribe/findUserSubscribe');
INSERT INTO `permission` VALUES ('104', '39', '预约房间', '/admin/subscribe/add');
INSERT INTO `permission` VALUES ('105', '46', '查找所有的角色', '/admin/roomer/findAllRole');
INSERT INTO `permission` VALUES ('106', '39', '取消预约', '/admin/subscribe/cancel');
INSERT INTO `permission` VALUES ('107', '58', '找到用户的合同订单', '/admin/contract/findUserContract');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(32) NOT NULL COMMENT '角色名称',
  `remark` varchar(128) NOT NULL COMMENT '角色的主要职责',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '拥有所有权限');
INSERT INTO `role` VALUES ('77', '客服', '客服该做的事123');
INSERT INTO `role` VALUES ('78', '人事部经理', '招聘');
INSERT INTO `role` VALUES ('83', 'zxc', 'zcx123');
INSERT INTO `role` VALUES ('84', 'jkl', 'jkl');
INSERT INTO `role` VALUES ('87', 'tyu', 'tuy');
INSERT INTO `role` VALUES ('88', '新角色99', '角色描述');
INSERT INTO `role` VALUES ('89', 'qweasd', 'qweasd');
INSERT INTO `role` VALUES ('90', '新角色99123', '新角色描述');
INSERT INTO `role` VALUES ('91', '新角色99123456', '新角色描述');
INSERT INTO `role` VALUES ('92', '角色-181123', 'asd');
INSERT INTO `role` VALUES ('93', 'asd', 'asd');
INSERT INTO `role` VALUES ('94', 'asd123', 'asd');
INSERT INTO `role` VALUES ('95', 'asd456', 'asd');
INSERT INTO `role` VALUES ('96', 'asd123456', '123');
INSERT INTO `role` VALUES ('97', 'asdasd', 'asdas');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` float(8,2) NOT NULL COMMENT '月租',
  `houseType` varchar(32) NOT NULL COMMENT '户型',
  `area` int(11) NOT NULL COMMENT '面积',
  `dir` varchar(20) NOT NULL COMMENT '朝向',
  `tags` varchar(123) NOT NULL COMMENT '标签信息，使用/分割',
  `name` varchar(32) NOT NULL COMMENT '房间名称',
  `isSell` int(11) NOT NULL COMMENT '0表示没有出租，1表示已经出租',
  `url` varchar(32) NOT NULL COMMENT '房间的图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=395 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('3', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '1', '863ae0ae2fcc44dc9847662980707aa8');
INSERT INTO `room` VALUES ('4', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '1', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('5', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('6', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('7', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('8', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('9', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('10', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '1', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('11', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('12', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('13', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('14', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('15', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '1', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('16', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('17', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('18', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('19', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('26', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('27', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('28', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('29', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('30', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '1', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('31', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('32', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('33', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('34', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('35', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('36', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('37', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('38', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('39', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('40', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('41', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('42', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('43', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '1', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('57', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('58', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('59', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('60', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('61', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('62', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('63', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('64', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('65', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('66', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('67', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('68', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('69', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('70', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('71', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('72', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('73', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('74', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('75', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('76', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('77', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('78', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('79', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('80', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('81', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('82', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('83', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('84', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('85', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('86', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('87', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('88', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('89', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('90', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('91', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('92', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('120', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('121', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('122', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('123', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('124', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('125', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('126', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('127', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('128', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('129', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('130', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('131', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('132', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('133', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('134', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('135', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('136', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('137', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('138', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('139', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('140', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('141', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('142', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('143', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('144', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('145', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('146', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('147', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('148', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('149', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('150', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('151', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('152', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('153', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('154', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('155', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('156', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('157', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('158', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('159', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('160', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('161', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('162', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('163', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('164', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('165', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('166', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('167', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('168', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('169', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('170', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('171', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('172', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('173', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('174', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('175', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('176', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('177', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('178', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('179', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('180', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('181', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('182', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('183', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('184', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('185', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('186', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('187', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('188', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('189', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('190', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('191', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('247', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('248', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('249', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('250', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('251', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('252', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('253', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('254', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('255', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('256', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('257', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('258', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('259', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('260', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('261', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('262', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('263', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('264', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('265', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('266', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('267', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('268', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('269', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('270', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('271', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('272', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('273', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('274', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('275', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('276', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('277', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('278', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('279', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('280', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('281', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('282', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('283', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('284', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('285', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('286', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('287', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('288', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('289', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('290', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('291', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('292', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('293', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('294', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('295', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('296', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('297', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('298', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('299', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('300', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('301', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('302', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('303', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('304', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('305', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('306', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('307', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('308', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('309', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('310', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('311', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('312', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('313', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('314', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('315', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('316', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('317', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('318', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('319', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('320', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('321', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('322', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('323', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('324', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('325', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('326', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('327', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('328', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('329', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('330', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('331', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('332', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('333', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('334', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('335', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('336', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('337', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('338', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('339', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('340', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('341', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('342', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('343', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('344', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('345', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('346', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('347', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('348', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('349', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('350', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('351', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('352', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('353', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('354', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('355', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('356', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('357', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('358', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('359', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('360', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('361', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('362', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('363', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('364', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('365', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('366', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('367', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('368', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('369', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('370', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('371', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('372', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('373', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('374', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('375', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('376', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('377', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('378', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('379', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('380', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('381', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('382', '850.00', '1房1厅1卫', '35', '西北', '配套齐全/精装修/电梯房', '房间2', '0', '88ead52d337f4cbd955f2f6b5f074a07');
INSERT INTO `room` VALUES ('383', '450.00', '单间0卫', '25', '东南', '配套齐全/精装修/拎包入住', '房间3', '0', 'c13994cf6d9f41e480a678b6a87eebdd');
INSERT INTO `room` VALUES ('384', '500.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '平阁楼', '0', 'f1cf9b9b494a4629afc117833bf5d8ac');
INSERT INTO `room` VALUES ('385', '800.00', '单间1卫', '50', '东南', '配套齐全/精装修/拎包入住', '单身公寓1', '0', '78529f24edfe416cad23d330dd4e9ebf');
INSERT INTO `room` VALUES ('386', '3000.00', '单间1卫', '300', '西南', '配套齐全/精装修/拎包入住', '房间1', '0', 'f75b36de687f47b4890da74aa9da8d25');
INSERT INTO `room` VALUES ('387', '3000.00', '单间1卫', '60', '西南', '配套齐全/精装修/拎包入住', '房间5', '0', '48b6d1f00b624fdd9593faddf6cf3a15');
INSERT INTO `room` VALUES ('388', '1000.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', '房间6', '0', '477d2628bd364e78ae97b23cbe074c4d');
INSERT INTO `room` VALUES ('389', '2000.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '房间7', '0', '7a89eea2cb5b41b09cf4d54ffba785be');
INSERT INTO `room` VALUES ('390', '4000.00', '单间1卫', '400', '西北', '配套齐全/精装修/拎包入住', '房间7', '0', 'ea977b664feb4cc08462ee83d76345ad');
INSERT INTO `room` VALUES ('391', '750.00', '单间1卫', '120', '东南', '配套齐全/精装修/拎包入住', '我的房间123', '0', '488e5ecb860642ddaf7e1567561e82c5');
INSERT INTO `room` VALUES ('392', '750.00', '单间1卫', '120', '东南', '配套齐全/精装修/拎包入住', '我的房间123', '0', '14aaa40dcc814d7baa3daab1efb7cebb');
INSERT INTO `room` VALUES ('393', '680.00', '单间1卫', '200', '东南', '配套齐全/精装修/拎包入住', '我的房间', '0', 'e8864de9fc7847d0901625a76cc02b09');
INSERT INTO `room` VALUES ('394', '20.00', '单间1卫', '100', '东南', '配套齐全/精装修/拎包入住', 'asd', '0', 'ae26e7e699574a089f466706800b2f9c');

-- ----------------------------
-- Table structure for staffunread
-- ----------------------------
DROP TABLE IF EXISTS `staffunread`;
CREATE TABLE `staffunread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '房客id',
  `unread` int(11) NOT NULL COMMENT '未读信息数量',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `staffunread_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staffunread
-- ----------------------------
INSERT INTO `staffunread` VALUES ('1', '2', '0');
INSERT INTO `staffunread` VALUES ('2', '4', '0');
INSERT INTO `staffunread` VALUES ('3', '1', '0');
INSERT INTO `staffunread` VALUES ('4', '3', '1');
INSERT INTO `staffunread` VALUES ('5', '6', '0');
INSERT INTO `staffunread` VALUES ('6', '7', '0');
INSERT INTO `staffunread` VALUES ('7', '8', '0');
INSERT INTO `staffunread` VALUES ('8', '11', '0');

-- ----------------------------
-- Table structure for subscribe
-- ----------------------------
DROP TABLE IF EXISTS `subscribe`;
CREATE TABLE `subscribe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '房客id',
  `roomId` int(11) DEFAULT NULL COMMENT '房间id',
  `checkInTime` date NOT NULL COMMENT '入住时间',
  `createTime` datetime NOT NULL COMMENT '约定时间',
  `remark` varchar(128) DEFAULT NULL COMMENT '备注',
  `status` int(11) DEFAULT '0' COMMENT '预定状态,0表示还没到预定时间，1表示错过预定',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `roomId` (`roomId`),
  CONSTRAINT `subscribe_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `subscribe_ibfk_2` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of subscribe
-- ----------------------------
INSERT INTO `subscribe` VALUES ('16', '3', '3', '2021-02-03', '2021-02-02 20:52:24', null, '1');
INSERT INTO `subscribe` VALUES ('17', '3', '4', '2021-02-05', '2021-02-02 20:57:34', null, '1');
INSERT INTO `subscribe` VALUES ('19', '3', '15', '2021-02-03', '2021-02-02 20:59:49', null, '1');
INSERT INTO `subscribe` VALUES ('22', '3', '43', '2021-02-06', '2021-02-02 21:01:07', '后天看房', '0');
INSERT INTO `subscribe` VALUES ('25', '3', '10', '2021-02-06', '2021-02-02 21:04:46', null, '2');

-- ----------------------------
-- Table structure for userunread
-- ----------------------------
DROP TABLE IF EXISTS `userunread`;
CREATE TABLE `userunread` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL COMMENT '房客id',
  `unread` int(11) NOT NULL COMMENT '未读信息数量',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `userunread_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `account` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userunread
-- ----------------------------
INSERT INTO `userunread` VALUES ('1', '4', '1');
INSERT INTO `userunread` VALUES ('2', '2', '2');
INSERT INTO `userunread` VALUES ('3', '1', '1');
INSERT INTO `userunread` VALUES ('4', '3', '0');
INSERT INTO `userunread` VALUES ('5', '5', '0');
INSERT INTO `userunread` VALUES ('6', '6', '0');
INSERT INTO `userunread` VALUES ('7', '7', '0');
INSERT INTO `userunread` VALUES ('9', '8', '0');
INSERT INTO `userunread` VALUES ('10', '11', '0');
INSERT INTO `userunread` VALUES ('11', '1', '0');
INSERT INTO `userunread` VALUES ('12', '12', '0');
INSERT INTO `userunread` VALUES ('13', '1', '0');
INSERT INTO `userunread` VALUES ('14', '13', '0');
INSERT INTO `userunread` VALUES ('15', '1', '0');
INSERT INTO `userunread` VALUES ('16', '14', '0');
INSERT INTO `userunread` VALUES ('17', '1', '0');
INSERT INTO `userunread` VALUES ('18', '15', '0');
INSERT INTO `userunread` VALUES ('19', '1', '0');
INSERT INTO `userunread` VALUES ('20', '16', '6');
