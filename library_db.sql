/*
 Navicat Premium Dump SQL

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : localhost:3306
 Source Schema         : library_db

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 13/07/2026 16:11:42
*/
CREATE DATABASE IF NOT EXISTS `library_db` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `library_db`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `bisbn` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `typeid` int NULL DEFAULT NULL,
  `bname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `author` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '佚名',
  `publish` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `pdate` date NULL DEFAULT NULL,
  `ptimes` int NULL DEFAULT NULL,
  `bprice` decimal(4, 1) NULL DEFAULT NULL,
  `is_children` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '否',
  `num` int NULL DEFAULT 0,
  PRIMARY KEY (`bisbn`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('9787020002207', 2, '红楼梦', '曹雪芹', '人民文学', '1996-12-01', 3, 59.7, '否', 119);
INSERT INTO `book` VALUES ('9787020008735', 2, '水浒传', '施耐庵', '人民文学', '1997-06-01', 4, 50.6, '否', 98);
INSERT INTO `book` VALUES ('9787020012329', 2, '三国演义', '罗贯中', '人民文学', '1998-02-01', 3, 39.8, '否', 135);
INSERT INTO `book` VALUES ('9787020012336', 2, '西游记', '吴承恩', '人民文学', '1997-08-01', 5, 47.2, '否', 150);
INSERT INTO `book` VALUES ('9787040571615', 3, '从一到无穷大', '伽莫夫', '高等教育', '2020-01-01', 3, 62.0, '否', 70);
INSERT INTO `book` VALUES ('9787100133443', 6, '理想国', '柏拉图', '商务印书馆', '2015-12-01', 4, 38.0, '否', 60);
INSERT INTO `book` VALUES ('9787108004664', 6, '论语译注', '杨伯峻', '中华书局', '2017-03-01', 6, 29.0, '否', 90);
INSERT INTO `book` VALUES ('9787108021166', 6, '庄子今注今译', '陈鼓应', '中华书局', '2016-05-01', 3, 58.0, '否', 50);
INSERT INTO `book` VALUES ('9787108033107', 6, '西方哲学史', '罗素', '商务印书馆', '2015-08-01', 4, 86.0, '否', 45);
INSERT INTO `book` VALUES ('9787108041454', 6, '道德经', '老子', '中华书局', '2018-06-01', 5, 22.0, '否', 85);
INSERT INTO `book` VALUES ('9787108051583', 3, '人类群星闪耀时', '茨威格', '三联书店', '2019-03-01', 3, 68.0, '否', 75);
INSERT INTO `book` VALUES ('9787108053570', 7, '史记', '司马迁', '中华书局', '2019-08-01', 2, 88.0, '否', 60);
INSERT INTO `book` VALUES ('9787108060134', 7, '万历十五年', '黄仁宇', '中华书局', '2018-01-01', 5, 42.0, '否', 110);
INSERT INTO `book` VALUES ('9787108064293', 7, '中国通史', '吕思勉', '中华书局', '2020-06-01', 3, 65.0, '否', 45);
INSERT INTO `book` VALUES ('9787111111111', 1, 'Java程序设计', '张三', '机械工业出版社', '2023-01-01', 3, 59.0, '否', 9);
INSERT INTO `book` VALUES ('9787111595588', 1, '算法导论', 'T. Cormen', '机械工业', '2018-07-01', 3, 128.0, '否', 70);
INSERT INTO `book` VALUES ('9787111638346', 1, '深入理解Java虚拟机', '周志明', '机械工业', '2019-12-01', 4, 129.0, '否', 85);
INSERT INTO `book` VALUES ('9787111676300', 1, '计算机网络自顶向下', 'J. Kurose', '机械工业', '2021-03-01', 2, 75.0, '否', 95);
INSERT INTO `book` VALUES ('9787121376524', 1, 'Python编程从入门到实践', 'E. Matthes', '人民邮电', '2020-10-01', 5, 89.0, '否', 130);
INSERT INTO `book` VALUES ('9787302552827', 1, 'JavaScript高级程序设计', 'N. Zakas', '清华大学', '2020-04-01', 4, 99.8, '否', 110);
INSERT INTO `book` VALUES ('9787508687225', 7, '人类简史', '尤瓦尔·赫拉利', '中信出版社', '2017-09-01', 7, 68.0, '否', 130);
INSERT INTO `book` VALUES ('9787508687232', 7, '未来简史', '尤瓦尔·赫拉利', '中信出版社', '2018-01-01', 4, 78.0, '否', 110);
INSERT INTO `book` VALUES ('9787535732309', 3, '时间简史', '霍金', '湖南科技', '2010-04-01', 6, 45.0, '否', 80);
INSERT INTO `book` VALUES ('9787535738431', 3, '果壳中的宇宙', '霍金', '湖南科技', '2015-09-01', 5, 52.0, '否', 55);
INSERT INTO `book` VALUES ('9787542850984', 3, '万物简史', '比尔·布莱森', '上海科技', '2018-11-01', 4, 58.0, '否', 65);
INSERT INTO `book` VALUES ('9787544268343', 2, '小王子', '圣埃克苏佩里', '人民文学', '2018-06-01', 5, 25.0, '是', 220);

-- ----------------------------
-- Table structure for booktype
-- ----------------------------
DROP TABLE IF EXISTS `booktype`;
CREATE TABLE `booktype`  (
  `typeid` int NOT NULL AUTO_INCREMENT,
  `typename` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`typeid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of booktype
-- ----------------------------
INSERT INTO `booktype` VALUES (1, '计算机');
INSERT INTO `booktype` VALUES (2, '文学');
INSERT INTO `booktype` VALUES (3, '科学');
INSERT INTO `booktype` VALUES (4, '少儿');
INSERT INTO `booktype` VALUES (5, '历史');
INSERT INTO `booktype` VALUES (6, '哲学');
INSERT INTO `booktype` VALUES (7, '经济管理');
INSERT INTO `booktype` VALUES (8, '艺术设计');
INSERT INTO `booktype` VALUES (9, '医学健康');
INSERT INTO `booktype` VALUES (10, '教育考试');

-- ----------------------------
-- Table structure for borrowbook
-- ----------------------------
DROP TABLE IF EXISTS `borrowbook`;
CREATE TABLE `borrowbook`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `rid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `bisbn` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `bdate` date NOT NULL,
  `rdate` date NULL DEFAULT NULL,
  `fine` decimal(10, 2) NULL DEFAULT 0.00,
  `is_returned` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_rid_bisbn_bdate`(`rid` ASC, `bisbn` ASC, `bdate` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrowbook
-- ----------------------------
INSERT INTO `borrowbook` VALUES (1, '114514', '9787111111111', '2026-07-13', '2026-07-13', 0.00, 1);
INSERT INTO `borrowbook` VALUES (2, '114514', '9787111111111', '2026-07-13', NULL, 0.00, 0);
INSERT INTO `borrowbook` VALUES (3, '114514', '9787020002207', '2026-07-13', NULL, 0.00, 0);

-- ----------------------------
-- Table structure for operation
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `record_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `operator` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operation
-- ----------------------------
INSERT INTO `operation` VALUES (1, 'delete', 'booktype', '5', '操作人:', 'admin', '2026-07-13 10:37:34');
INSERT INTO `operation` VALUES (2, 'insert', 'booktype', '哲学', '操作人:', 'admin', '2026-07-13 11:16:24');
INSERT INTO `operation` VALUES (3, 'insert', 'booktype', '历史', '添加图书分类', 'admin', '2026-07-13 11:18:14');

-- ----------------------------
-- Table structure for reader
-- ----------------------------
DROP TABLE IF EXISTS `reader`;
CREATE TABLE `reader`  (
  `rid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rtype` int NULL DEFAULT NULL,
  `rname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `gender` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `tele` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `major` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `indate` date NULL DEFAULT NULL,
  PRIMARY KEY (`rid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reader
-- ----------------------------
INSERT INTO `reader` VALUES ('114514', 2, '驴得水', 18, NULL, '13112156849', '软件', '2026-07-11');
INSERT INTO `reader` VALUES ('20240001', 2, '张三', 20, '男', '13800138000', '计算机学院', '2024-01-01');
INSERT INTO `reader` VALUES ('驴得水', 2, '驴得水', NULL, NULL, NULL, NULL, '2026-07-09');

-- ----------------------------
-- Table structure for readertype
-- ----------------------------
DROP TABLE IF EXISTS `readertype`;
CREATE TABLE `readertype`  (
  `tid` int NOT NULL AUTO_INCREMENT,
  `typename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `maxbnum` int NULL DEFAULT 5,
  `limitdays` int NULL DEFAULT 30,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of readertype
-- ----------------------------
INSERT INTO `readertype` VALUES (1, '教师', 10, 60);
INSERT INTO `readertype` VALUES (2, '学生', 5, 30);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `upassword` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `utype` int NULL DEFAULT 2 COMMENT '0=管理员,1=馆员,2=普通用户',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `uname`(`uname` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '123456', 0);
INSERT INTO `users` VALUES (2, 'librarian', '123456', 1);
INSERT INTO `users` VALUES (3, 'user1', '123456', 2);
INSERT INTO `users` VALUES (4, 'hajimimi', '114514', 2);
INSERT INTO `users` VALUES (7, '驴得水', '114514', 2);
INSERT INTO `users` VALUES (8, '114514', '114514', 2);

SET FOREIGN_KEY_CHECKS = 1;
