/*
 Navicat Premium Data Transfer

 Source Server         : salary
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : salary

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 24/05/2024 12:59:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attendance
-- ----------------------------
DROP TABLE IF EXISTS `attendance`;
CREATE TABLE `attendance`  (
  `yearmonth` char(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `employee_id` int(0) NOT NULL AUTO_INCREMENT,
  `overtime_day` int(0) DEFAULT 0,
  `absence_day` int(0) DEFAULT 0,
  `attend_day` int(0) DEFAULT NULL,
  PRIMARY KEY (`employee_id`, `yearmonth`) USING BTREE,
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attendance
-- ----------------------------
INSERT INTO `attendance` VALUES ('2024-05', 1, 0, 0, 28);
INSERT INTO `attendance` VALUES ('2024-05', 2, 0, 1, 27);
INSERT INTO `attendance` VALUES ('2024-05', 3, 3, 0, 28);

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `department_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dmanger` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `dphone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`department_id`) USING BTREE,
  UNIQUE INDEX `dphone`(`dphone`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('01', '研发部', '张三', '667788');
INSERT INTO `department` VALUES ('02', '生产部', '刘六', '223344');
INSERT INTO `department` VALUES ('03', '销售部', '赵七', '123321');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `employee_id` int(0) NOT NULL AUTO_INCREMENT,
  `department_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `age` int(0) DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `enterdate` date NOT NULL,
  `identification` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`employee_id`) USING BTREE,
  UNIQUE INDEX `identification`(`identification`) USING BTREE,
  INDEX `department_id`(`department_id`) USING BTREE,
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES (1, '01', '陈松桂', '男', 20, '18378950813', '2024-05-21', '123456789');
INSERT INTO `employee` VALUES (2, '01', '李四', '男', 23, '183789', '2024-05-02', '123456X');
INSERT INTO `employee` VALUES (3, '01', '王五', '男', 25, '18378950', '2024-05-01', '12789x');
INSERT INTO `employee` VALUES (4, '02', '张飞', '男', 30, '5678', '2020-03-03', '34567863545');
INSERT INTO `employee` VALUES (5, '03', '貂蝉', '女', 28, '666', '2020-06-06', '7784674');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2200310807', '123456');
INSERT INTO `user` VALUES ('2200310816', '123456');
INSERT INTO `user` VALUES ('2200310817', '123456');
INSERT INTO `user` VALUES ('2200310818', '123456');
INSERT INTO `user` VALUES ('2200310819', '123456');

-- ----------------------------
-- Table structure for wage
-- ----------------------------
DROP TABLE IF EXISTS `wage`;
CREATE TABLE `wage`  (
  `yearmonth` char(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `employee_id` int(0) NOT NULL AUTO_INCREMENT,
  `basic` int(0) NOT NULL,
  `overtime_pay` int(0) DEFAULT 0,
  `absence_pay` int(0) DEFAULT 0,
  `should_pay` int(0) NOT NULL,
  PRIMARY KEY (`employee_id`, `yearmonth`) USING BTREE,
  CONSTRAINT `wage_ibfk_1` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wage
-- ----------------------------
INSERT INTO `wage` VALUES ('2024-05', 1, 5555, 0, 0, 5555);
INSERT INTO `wage` VALUES ('2019-09', 3, 4000, 300, 200, 4100);
INSERT INTO `wage` VALUES ('2024-01', 3, 5000, 0, 0, 5000);
INSERT INTO `wage` VALUES ('2024-05', 3, 5000, 300, 400, 4900);

SET FOREIGN_KEY_CHECKS = 1;
