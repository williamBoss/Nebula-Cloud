/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 47.103.5.196:3309
 Source Schema         : nebula-cloud

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 08/04/2024 18:11:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '参数键名',
  `config_value` json NULL COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '系统状态', 'sys_status', '[{\"dictLabel\": \"正常\", \"dictValue\": \"0\"}, {\"dictLabel\": \"停用\", \"dictValue\": \"1\"}]', 'Y', 0, 1, 'admin', '2024-03-02 17:32:56', 1, 'admin', '2024-03-28 14:00:24', NULL);
INSERT INTO `sys_config` VALUES (2, '用户性别', 'sys_user_sex', '[{\"dictLabel\": \"男\", \"dictValue\": \"1\"}, {\"dictLabel\": \"女\", \"dictValue\": \"2\"}, {\"dictLabel\": \"未知\", \"dictValue\": \"0\"}]', 'Y', 0, 1, 'admin', '2024-03-02 17:34:19', 1, 'admin', '2024-03-28 14:00:24', NULL);

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `leader` bigint NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 112 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (100, 0, '0', '科技公司', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 16:09:14', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', 'XX总公司', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', 'ZZ分公司', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', '研发部门', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', '市场部门', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', '测试部门', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', '财务部门', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', '运维部门', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', '市场部门', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', '财务部门', 1, '15888888888', 'xxx@qq.com', '0', 0, 1, 'admin', '2023-12-25 17:07:53', 1, 'admin', '2024-03-28 14:00:34', NULL);

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor`  (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户账号',
  `client_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '客户端',
  `device_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '设备类型',
  `ipaddr` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '提示消息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  INDEX `idx_sys_logininfor_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_logininfor_lt`(`login_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 199 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_logininfor
-- ----------------------------
INSERT INTO `sys_logininfor` VALUES (1, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (2, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 20:58:20');
INSERT INTO `sys_logininfor` VALUES (3, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 20:58:47');
INSERT INTO `sys_logininfor` VALUES (4, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:17:45');
INSERT INTO `sys_logininfor` VALUES (5, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (6, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:18:33');
INSERT INTO `sys_logininfor` VALUES (7, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:18:53');
INSERT INTO `sys_logininfor` VALUES (8, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (9, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:22:13');
INSERT INTO `sys_logininfor` VALUES (10, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:23:47');
INSERT INTO `sys_logininfor` VALUES (11, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (12, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:27:18');
INSERT INTO `sys_logininfor` VALUES (13, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:35:01');
INSERT INTO `sys_logininfor` VALUES (14, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:36:55');
INSERT INTO `sys_logininfor` VALUES (15, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (16, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:39:05');
INSERT INTO `sys_logininfor` VALUES (17, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:39:17');
INSERT INTO `sys_logininfor` VALUES (18, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:40:33');
INSERT INTO `sys_logininfor` VALUES (19, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (20, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:41:48');
INSERT INTO `sys_logininfor` VALUES (21, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:42:07');
INSERT INTO `sys_logininfor` VALUES (22, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (23, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:43:22');
INSERT INTO `sys_logininfor` VALUES (24, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:43:26');
INSERT INTO `sys_logininfor` VALUES (25, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (26, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:43:32');
INSERT INTO `sys_logininfor` VALUES (27, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:43:40');
INSERT INTO `sys_logininfor` VALUES (28, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:50:07');
INSERT INTO `sys_logininfor` VALUES (29, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:50:38');
INSERT INTO `sys_logininfor` VALUES (30, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:51:16');
INSERT INTO `sys_logininfor` VALUES (31, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (32, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 21:59:13');
INSERT INTO `sys_logininfor` VALUES (33, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-02 21:59:19');
INSERT INTO `sys_logininfor` VALUES (34, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 21:59:28');
INSERT INTO `sys_logininfor` VALUES (35, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (36, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 22:00:45');
INSERT INTO `sys_logininfor` VALUES (37, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 22:00:51');
INSERT INTO `sys_logininfor` VALUES (38, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (39, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 22:01:33');
INSERT INTO `sys_logininfor` VALUES (40, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 22:01:43');
INSERT INTO `sys_logininfor` VALUES (41, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 22:41:17');
INSERT INTO `sys_logininfor` VALUES (42, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 22:46:22');
INSERT INTO `sys_logininfor` VALUES (43, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 22:47:35');
INSERT INTO `sys_logininfor` VALUES (44, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:09:44');
INSERT INTO `sys_logininfor` VALUES (45, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:11:03');
INSERT INTO `sys_logininfor` VALUES (46, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:16:20');
INSERT INTO `sys_logininfor` VALUES (47, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:16:58');
INSERT INTO `sys_logininfor` VALUES (48, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:19:34');
INSERT INTO `sys_logininfor` VALUES (49, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (50, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 23:44:31');
INSERT INTO `sys_logininfor` VALUES (51, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-02 23:44:42');
INSERT INTO `sys_logininfor` VALUES (52, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:44:50');
INSERT INTO `sys_logininfor` VALUES (53, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (54, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 23:48:56');
INSERT INTO `sys_logininfor` VALUES (55, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:49:05');
INSERT INTO `sys_logininfor` VALUES (56, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-02 23:49:51');
INSERT INTO `sys_logininfor` VALUES (57, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:49:59');
INSERT INTO `sys_logininfor` VALUES (58, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (59, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 23:51:51');
INSERT INTO `sys_logininfor` VALUES (60, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:51:55');
INSERT INTO `sys_logininfor` VALUES (61, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (62, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 23:52:37');
INSERT INTO `sys_logininfor` VALUES (63, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:54:01');
INSERT INTO `sys_logininfor` VALUES (64, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (65, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 23:56:26');
INSERT INTO `sys_logininfor` VALUES (66, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:56:29');
INSERT INTO `sys_logininfor` VALUES (67, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (68, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 23:57:41');
INSERT INTO `sys_logininfor` VALUES (69, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:57:50');
INSERT INTO `sys_logininfor` VALUES (70, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:59:36');
INSERT INTO `sys_logininfor` VALUES (71, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (72, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-02 23:59:50');
INSERT INTO `sys_logininfor` VALUES (73, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-02 23:59:56');
INSERT INTO `sys_logininfor` VALUES (74, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (75, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:01:20');
INSERT INTO `sys_logininfor` VALUES (76, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-03 00:01:30');
INSERT INTO `sys_logininfor` VALUES (77, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:01:38');
INSERT INTO `sys_logininfor` VALUES (78, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (79, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:04:43');
INSERT INTO `sys_logininfor` VALUES (80, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-03 00:04:50');
INSERT INTO `sys_logininfor` VALUES (81, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:04:58');
INSERT INTO `sys_logininfor` VALUES (82, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:36:48');
INSERT INTO `sys_logininfor` VALUES (83, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (84, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:37:27');
INSERT INTO `sys_logininfor` VALUES (85, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:37:33');
INSERT INTO `sys_logininfor` VALUES (86, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (87, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:40:09');
INSERT INTO `sys_logininfor` VALUES (88, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:40:16');
INSERT INTO `sys_logininfor` VALUES (89, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (90, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:44:18');
INSERT INTO `sys_logininfor` VALUES (91, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-03 00:44:25');
INSERT INTO `sys_logininfor` VALUES (92, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:44:34');
INSERT INTO `sys_logininfor` VALUES (93, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (94, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:50:28');
INSERT INTO `sys_logininfor` VALUES (95, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:50:35');
INSERT INTO `sys_logininfor` VALUES (96, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (97, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:51:12');
INSERT INTO `sys_logininfor` VALUES (98, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:51:24');
INSERT INTO `sys_logininfor` VALUES (99, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (100, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:52:20');
INSERT INTO `sys_logininfor` VALUES (101, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:52:27');
INSERT INTO `sys_logininfor` VALUES (102, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (103, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:52:31');
INSERT INTO `sys_logininfor` VALUES (104, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:52:41');
INSERT INTO `sys_logininfor` VALUES (105, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (106, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-03 00:52:46');
INSERT INTO `sys_logininfor` VALUES (107, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 00:52:50');
INSERT INTO `sys_logininfor` VALUES (108, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 15:52:55');
INSERT INTO `sys_logininfor` VALUES (109, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 15:54:57');
INSERT INTO `sys_logininfor` VALUES (110, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 16:20:19');
INSERT INTO `sys_logininfor` VALUES (111, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 16:31:35');
INSERT INTO `sys_logininfor` VALUES (112, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 16:35:05');
INSERT INTO `sys_logininfor` VALUES (113, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 16:35:44');
INSERT INTO `sys_logininfor` VALUES (114, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 16:56:59');
INSERT INTO `sys_logininfor` VALUES (115, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 16:59:49');
INSERT INTO `sys_logininfor` VALUES (116, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-03 17:00:42');
INSERT INTO `sys_logininfor` VALUES (117, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:00:53');
INSERT INTO `sys_logininfor` VALUES (118, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:02:06');
INSERT INTO `sys_logininfor` VALUES (119, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-03 17:03:41');
INSERT INTO `sys_logininfor` VALUES (120, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:03:48');
INSERT INTO `sys_logininfor` VALUES (121, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:04:09');
INSERT INTO `sys_logininfor` VALUES (122, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:04:28');
INSERT INTO `sys_logininfor` VALUES (123, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:05:24');
INSERT INTO `sys_logininfor` VALUES (124, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:05:50');
INSERT INTO `sys_logininfor` VALUES (125, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:06:17');
INSERT INTO `sys_logininfor` VALUES (126, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:06:30');
INSERT INTO `sys_logininfor` VALUES (127, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:07:11');
INSERT INTO `sys_logininfor` VALUES (128, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:07:25');
INSERT INTO `sys_logininfor` VALUES (129, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:07:52');
INSERT INTO `sys_logininfor` VALUES (130, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-03 17:40:50');
INSERT INTO `sys_logininfor` VALUES (131, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-03 17:40:59');
INSERT INTO `sys_logininfor` VALUES (132, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:31:06');
INSERT INTO `sys_logininfor` VALUES (133, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:31:54');
INSERT INTO `sys_logininfor` VALUES (134, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:32:47');
INSERT INTO `sys_logininfor` VALUES (135, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:34:25');
INSERT INTO `sys_logininfor` VALUES (136, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:35:15');
INSERT INTO `sys_logininfor` VALUES (137, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:36:31');
INSERT INTO `sys_logininfor` VALUES (138, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:37:19');
INSERT INTO `sys_logininfor` VALUES (139, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:37:58');
INSERT INTO `sys_logininfor` VALUES (140, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:41:03');
INSERT INTO `sys_logininfor` VALUES (141, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-04 00:42:51');
INSERT INTO `sys_logininfor` VALUES (142, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:42:58');
INSERT INTO `sys_logininfor` VALUES (143, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-04 00:44:03');
INSERT INTO `sys_logininfor` VALUES (144, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:44:11');
INSERT INTO `sys_logininfor` VALUES (145, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:51:08');
INSERT INTO `sys_logininfor` VALUES (146, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (147, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 00:52:16');
INSERT INTO `sys_logininfor` VALUES (148, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:52:32');
INSERT INTO `sys_logininfor` VALUES (149, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (150, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 00:57:25');
INSERT INTO `sys_logininfor` VALUES (151, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:57:32');
INSERT INTO `sys_logininfor` VALUES (152, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (153, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 00:58:29');
INSERT INTO `sys_logininfor` VALUES (154, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 00:58:37');
INSERT INTO `sys_logininfor` VALUES (155, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (156, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 00:59:30');
INSERT INTO `sys_logininfor` VALUES (157, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:01:05');
INSERT INTO `sys_logininfor` VALUES (158, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (159, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:02:50');
INSERT INTO `sys_logininfor` VALUES (160, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:02:56');
INSERT INTO `sys_logininfor` VALUES (161, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (162, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:05:09');
INSERT INTO `sys_logininfor` VALUES (163, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:05:14');
INSERT INTO `sys_logininfor` VALUES (164, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (165, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:07:28');
INSERT INTO `sys_logininfor` VALUES (166, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:07:37');
INSERT INTO `sys_logininfor` VALUES (167, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (168, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:11:02');
INSERT INTO `sys_logininfor` VALUES (169, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:11:11');
INSERT INTO `sys_logininfor` VALUES (170, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (171, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:13:13');
INSERT INTO `sys_logininfor` VALUES (172, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-04 01:13:19');
INSERT INTO `sys_logininfor` VALUES (173, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:13:33');
INSERT INTO `sys_logininfor` VALUES (174, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:14:52');
INSERT INTO `sys_logininfor` VALUES (175, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (176, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:19:20');
INSERT INTO `sys_logininfor` VALUES (177, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:19:37');
INSERT INTO `sys_logininfor` VALUES (178, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (179, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:30:34');
INSERT INTO `sys_logininfor` VALUES (180, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:30:40');
INSERT INTO `sys_logininfor` VALUES (181, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (182, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:36:56');
INSERT INTO `sys_logininfor` VALUES (183, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:37:38');
INSERT INTO `sys_logininfor` VALUES (184, '', '', '', '', '', '', '', '0', NULL, NULL);
INSERT INTO `sys_logininfor` VALUES (185, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登出成功', '2024-04-04 01:38:09');
INSERT INTO `sys_logininfor` VALUES (186, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-04 01:38:17');
INSERT INTO `sys_logininfor` VALUES (187, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-05 00:40:07');
INSERT INTO `sys_logininfor` VALUES (188, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 00:40:22');
INSERT INTO `sys_logininfor` VALUES (189, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 20:23:42');
INSERT INTO `sys_logininfor` VALUES (190, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '1', '登录失败:ServiceException(message=验证码错误)', '2024-04-05 20:30:36');
INSERT INTO `sys_logininfor` VALUES (191, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 20:30:45');
INSERT INTO `sys_logininfor` VALUES (192, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 20:48:20');
INSERT INTO `sys_logininfor` VALUES (193, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 21:08:20');
INSERT INTO `sys_logininfor` VALUES (194, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 21:10:20');
INSERT INTO `sys_logininfor` VALUES (195, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 21:16:20');
INSERT INTO `sys_logininfor` VALUES (196, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 21:48:09');
INSERT INTO `sys_logininfor` VALUES (197, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 22:01:13');
INSERT INTO `sys_logininfor` VALUES (198, 'admin', '', '', '127.0.0.1', '', 'Edge 123', 'Windows >=10', '0', '登录成功', '2024-04-05 22:02:49');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query_param` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由参数',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '显示状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标类型 sl:自定义图标 el：el-icon',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '首页', 0, 1, '/home/index', 'home/index', '', 1, 1, 'C', '0', '0', NULL, 'el', 'HomeFilled', 1, 1, 'admin', '2024-04-03 12:48:52', 1, 'admin', '2024-04-04 01:38:33', '');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 0, 2, '/system/user', 'system/user/index', '', 1, 1, 'C', '0', '0', NULL, 'el', 'UserFilled', 0, 1, 'admin', '2024-03-09 16:52:17', 1, 'admin', '2024-04-03 15:52:11', '');
INSERT INTO `sys_menu` VALUES (3, '查询用户', 2, 1, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:user:list', NULL, '#', 0, 1, 'admin', '2024-03-28 23:12:04', 1, 'admin', '2024-03-28 23:15:17', '');
INSERT INTO `sys_menu` VALUES (4, '添加用户', 2, 2, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:user:save', NULL, '#', 0, 1, 'admin', '2024-03-28 23:12:04', 1, 'admin', '2024-03-28 23:15:17', '');
INSERT INTO `sys_menu` VALUES (5, '修改用户', 2, 3, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:user:update', NULL, '#', 0, 1, 'admin', '2024-03-28 23:12:04', 1, 'admin', '2024-03-28 23:15:17', '');
INSERT INTO `sys_menu` VALUES (6, '删除用户', 2, 4, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:user:remove', NULL, '#', 0, 1, 'admin', '2024-03-28 23:12:04', 1, 'admin', '2024-03-28 23:15:17', '');
INSERT INTO `sys_menu` VALUES (7, '角色管理', 0, 3, '/system/role', 'system/role/index', '', 1, 1, 'C', '0', '0', NULL, 'sl', 'icon-role', 0, 1, 'admin', '2024-03-28 14:06:50', 1, 'admin', '2024-04-03 15:52:11', '');
INSERT INTO `sys_menu` VALUES (8, '查询角色', 7, 1, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:role:list', NULL, '#', 0, 1, 'admin', '2024-03-28 23:14:18', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (9, '添加角色', 7, 2, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:role:save', NULL, '#', 0, 1, 'admin', '2024-03-28 23:14:18', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (10, '修改角色', 7, 3, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:role:update', NULL, '#', 0, 1, 'admin', '2024-03-28 23:14:18', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (11, '删除角色', 7, 4, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:role:remove', NULL, '#', 0, 1, 'admin', '2024-03-28 23:14:18', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (12, '菜单管理', 0, 5, '/system/menu', 'system/menu/index', '', 1, 1, 'C', '0', '0', NULL, 'sl', 'icon-menu', 0, 1, 'admin', '2024-03-28 14:08:01', 1, 'admin', '2024-04-05 20:02:58', '');
INSERT INTO `sys_menu` VALUES (13, '查询菜单', 12, 1, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:menu:list', NULL, '#', 0, 1, 'admin', '2024-03-28 23:16:34', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (14, '添加菜单', 12, 2, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:menu:save', NULL, '#', 0, 1, 'admin', '2024-03-28 23:16:35', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (15, '修改菜单', 12, 3, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:menu:update', NULL, '#', 0, 1, 'admin', '2024-03-28 23:16:35', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (16, '删除菜单', 12, 4, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:menu:remove', NULL, '#', 0, 1, 'admin', '2024-03-28 23:16:35', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (17, '部门管理', 0, 4, '/system/dept', 'system/dept/index', '', 1, 1, 'C', '0', '0', NULL, 'sl', 'icon-dept', 0, 1, 'admin', '2024-03-12 20:04:47', 1, 'admin', '2024-04-03 15:52:11', '');
INSERT INTO `sys_menu` VALUES (18, '查询部门', 17, 1, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:dept:list', NULL, '#', 0, 1, 'admin', '2024-03-28 23:19:58', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (19, '添加部门', 17, 2, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:dept:save', NULL, '#', 0, 1, 'admin', '2024-03-28 23:19:58', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (20, '修改部门', 17, 3, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:dept:update', NULL, '#', 0, 1, 'admin', '2024-03-28 23:19:59', 1, 'admin', '2024-03-28 23:22:56', '');
INSERT INTO `sys_menu` VALUES (21, '删除部门', 17, 4, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:dept:remove', NULL, '#', 0, 1, 'admin', '2024-03-28 23:19:59', 1, 'admin', '2024-03-28 23:22:56', '');
INSERT INTO `sys_menu` VALUES (22, '参数配置', 0, 6, '/system/config', 'system/config/index', '', 1, 1, 'C', '0', '0', NULL, 'el', 'Tools', 0, 1, 'admin', '2024-03-28 14:09:31', 1, 'admin', '2024-04-03 15:52:11', '');
INSERT INTO `sys_menu` VALUES (23, '查询参数配置', 22, 1, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:config:list', NULL, '#', 0, 1, 'admin', '2024-03-28 23:22:31', 1, 'admin', '2024-03-28 23:23:49', '');
INSERT INTO `sys_menu` VALUES (24, '添加参数配置', 22, 2, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:config:save', NULL, '#', 0, 1, 'admin', '2024-03-28 23:22:31', 1, 'admin', '2024-03-28 23:23:49', '');
INSERT INTO `sys_menu` VALUES (25, '修改参数配置', 22, 3, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:config:update', NULL, '#', 0, 1, 'admin', '2024-03-28 23:22:31', 1, 'admin', '2024-03-28 23:23:49', '');
INSERT INTO `sys_menu` VALUES (26, '删除参数配置', 22, 4, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:config:remove', NULL, '#', 0, 1, 'admin', '2024-03-28 23:22:31', 1, 'admin', '2024-03-28 23:23:49', '');
INSERT INTO `sys_menu` VALUES (27, '重置密码', 2, 5, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:user:password:update', NULL, '#', 0, 1, 'admin', '2024-03-28 23:12:04', 1, 'admin', '2024-04-05 19:50:14', '');
INSERT INTO `sys_menu` VALUES (28, '状态修改', 2, 6, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:user:status:update', NULL, '#', 0, 1, 'admin', '2024-03-28 23:12:04', 1, 'admin', '2024-04-05 19:50:14', '');
INSERT INTO `sys_menu` VALUES (29, '添加用户角色关系', 7, 5, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:role:user:save', NULL, '#', 0, 1, 'admin', '2024-03-28 23:14:18', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (30, '移除用户角色关系', 7, 6, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:role:user:remove', NULL, '#', 0, 1, 'admin', '2024-03-28 23:14:18', 1, 'admin', '2024-03-28 23:21:18', '');
INSERT INTO `sys_menu` VALUES (31, '保存角色菜单关系', 7, 7, '', NULL, '', 1, 1, 'F', '0', '0', 'sys:role:menu:save', NULL, '#', 0, 1, 'admin', '2024-03-28 23:14:18', 1, 'admin', '2024-03-28 23:21:18', '');

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log`  (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_user_id` bigint NULL DEFAULT NULL COMMENT '操作人id',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `json_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回参数',
  `status` int NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime NULL DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint NULL DEFAULT 0 COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  INDEX `idx_sys_oper_log_bt`(`business_type` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_s`(`status` ASC) USING BTREE,
  INDEX `idx_sys_oper_log_ot`(`oper_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oper_log
-- ----------------------------
INSERT INTO `sys_oper_log` VALUES (1, '用户管理-修改状态', 2, 'com.nebula.system.controller.SysUserController.changeStatus()', 'PUT', 0, 1, 'admin', '', '/sysUser/changeStatus', '198.18.0.1', '', '{\"avatar\":\"\",\"dept\":{},\"deptId\":0,\"deptName\":\"\",\"email\":\"\",\"loginDate\":{},\"loginIp\":\"\",\"nickName\":\"\",\"params\":{},\"password\":\"\",\"phonenumber\":\"\",\"role\":{},\"roleId\":0,\"sex\":\"\",\"status\":\"1\",\"userId\":132265121719341056,\"userName\":\"\",\"userType\":\"\"}', NULL, 0, '', '2024-04-05 00:40:31', 87);
INSERT INTO `sys_oper_log` VALUES (2, '用户管理-修改状态', 2, 'com.nebula.system.controller.SysUserController.changeStatus()', 'PUT', 0, 1, 'admin', '', '/sysUser/changeStatus', '198.18.0.1', '', '{\"avatar\":\"\",\"dept\":{},\"deptId\":0,\"deptName\":\"\",\"email\":\"\",\"loginDate\":{},\"loginIp\":\"\",\"nickName\":\"\",\"params\":{},\"password\":\"\",\"phonenumber\":\"\",\"role\":{},\"roleId\":0,\"sex\":\"\",\"status\":\"0\",\"userId\":132265121719341056,\"userName\":\"\",\"userType\":\"\"}', NULL, 0, '', '2024-04-05 00:50:52', 166);
INSERT INTO `sys_oper_log` VALUES (3, '角色管理-保存角色菜单关系', 1, 'com.nebula.system.controller.SysRoleController.addRoleMenu()', 'POST', 0, 1, 'admin', '', '/sysRole/addRoleMenu/superadmin', '192.168.1.114', '', '\"superadmin\" [2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,31,27,28,29,30]', NULL, 0, '', '2024-04-05 20:25:30', 181);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色Key字符串',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `index_role_key`(`role_key` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'superadmin', '1', '0', 0, 1, 'admin', '2023-12-26 22:55:41', 1, 'admin', '2024-03-28 14:11:41', ' ');
INSERT INTO `sys_role` VALUES (2, '普通角色', 'common', '2', '0', 0, 1, 'admin', '2023-12-26 22:56:29', 1, 'admin', '2024-03-28 14:11:41', ' ');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 27);
INSERT INTO `sys_role_menu` VALUES (1, 28);
INSERT INTO `sys_role_menu` VALUES (1, 29);
INSERT INTO `sys_role_menu` VALUES (1, 30);
INSERT INTO `sys_role_menu` VALUES (1, 31);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称',
  `user_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'sys_user' COMMENT '用户类型（sys_user系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '用户性别（0未知 1男 2女）',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `del_flag` tinyint NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `create_id` bigint NULL DEFAULT NULL COMMENT '创建者id',
  `create_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_id` bigint NULL DEFAULT NULL COMMENT '更新者id',
  `update_by` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 100, 'admin', 'Administrator', 'sys_user', 'xxx@163.com', '15888888888', '1', NULL, '$2a$10$rnfBXkHMaNLoJot1XqEm7OqebljCPQNm4YUJY.YIek.rxdGLn0oxu', '0', '', NULL, 0, 1, 'admin', '2023-12-25 17:17:46', 1, 'admin', '2024-03-28 23:10:01', ' ');
INSERT INTO `sys_user` VALUES (132265121719341056, NULL, 'test', '测试', NULL, NULL, NULL, NULL, NULL, '$2a$10$EZzocm2DWmrWp2qGs2ll/exDmmEoZJ7KBffT2CKiDh5RNiVOpFFIS', '0', NULL, NULL, 0, 1, 'admin', '2024-04-01 12:35:20', 1, 'admin', '2024-04-05 00:50:52', NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (132265121719341056, 2);

SET FOREIGN_KEY_CHECKS = 1;
