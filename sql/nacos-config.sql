/*
 Navicat Premium Data Transfer

 Source Server         : dev
 Source Server Type    : MySQL
 Source Server Version : 80032 (8.0.32)
 Source Host           : 47.103.5.196:3309
 Source Schema         : nacos-config

 Target Server Type    : MySQL
 Target Server Version : 80032 (8.0.32)
 File Encoding         : 65001

 Date: 08/04/2024 18:11:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'nebula-system-dev.yml', 'DEFAULT_GROUP', 'server:\r\n  port: 8083', '532c796819405e20294a86961ad3b1e8', '2023-12-24 19:39:45', '2023-12-24 19:39:45', 'nacos', '112.80.229.166', '', '38db37e5-01c7-4447-8e56-117f8b43c106', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (2, 'application-dev.yml', 'DEFAULT_GROUP', 'spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    # JDBC配置：\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://47.103.5.196:3309/nebula-cloud?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true\n    username: root\n    password: root@MySql8.0\n    # 连接池配置：\n    druid:\n      initial-size: 10 # 初始化时建立物理连接的个数。默认0\n      max-active: 30 # 最大连接池数量，默认8\n      min-idle: 1 # 最小连接池数量\n      max-wait: 2000 # 获取连接时最大等待时间，单位毫秒。\n      pool-prepared-statements: false # 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。\n      max-pool-prepared-statement-per-connection-size: -1 # 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100\n      # druid节点下的其它参数见官方文档：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8\n      # 启用Druid内置的Filter，会使用默认的配置。可自定义配置，见下方的各个filter节点。\n      filters: stat,wall\n      # StatViewServlet监控器。开启后，访问http://域名/druid/index.html\n      stat-view-servlet:\n        enabled: true # 开启 StatViewServlet，即开启监控功能\n        login-username: daniel # 访问监控页面时登录的账号\n        login-password: 1234 # 密码\n        url-pattern: /druid/* # Servlet的映射地址，不填写默认为\"/druid/*\"。如填写其它地址，访问监控页面时，要使用相应的地址\n        reset-enable: false # 是否允许重置数据（在页面的重置按钮）。（停用后，依然会有重置按钮，但重置后不会真的重置数据）\n        allow: 192.168.1.2,192.168.1.1 # 监控页面访问白名单。默认为127.0.0.1。与黑名单一样，支持子网掩码，如128.242.127.1/24。多个ip用英文逗号分隔\n        deny: 18.2.1.3 # 监控页面访问黑名单\n      # 配置 WebStatFilter（StatFilter监控器中的Web模板）\n      web-stat-filter:\n        enabled: true # 开启 WebStatFilter，即开启监控功能中的 Web 监控功能\n        url-pattern: /* # 映射地址，即统计指定地址的web请求\n        exclusions: \'*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\' # 不统计的web请求，如下是不统计静态资源及druid监控页面本身的请求\n        session-stat-enable: true # 是否启用session统计\n        session-stat-max-count: 1 # session统计的最大个数，默认是1000。当统计超过这个数，只统计最新的\n        principal-session-name: userName # 所存用户信息的serssion参数名。Druid会依照此参数名读取相应session对应的用户名记录下来（在监控页面可看到）。如果指定参数不是基础数据类型，将会自动调用相应参数对象的toString方法来取值\n        principal-cookie-name: userName # 与上类似，但这是通过Cookie名取到用户信息\n        profile-enable: true # 监控单个url调用的sql列表（试了没生效，以后需要用再研究）\n      filter:\n        wall:\n          enabled: true  # 开启SQL防火墙功能\n          config:\n            select-allow: true # 允许执行Select查询操作\n            delete-allow: false # 不允许执行delete操作\n            create-table-allow: true # 不允许创建表\n            # 更多用法，参考官方文档：https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter\n\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      packages-to-scan: com.nebula.system.controller\n', '763a5dde5690870fae1bdd3b81569734', '2023-12-24 19:40:17', '2024-03-17 00:04:52', 'nacos', '122.96.68.15', '', '38db37e5-01c7-4447-8e56-117f8b43c106', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (8, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /auth/v3/**\n    - /system/v3/**\n    - /auth/captcha\n    - /auth/login\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', 'abe26d9c60bb8e0d9a81e597ec6bee53', '2024-03-15 22:08:22', '2024-03-25 14:06:52', 'nacos', '112.80.229.49', '', '38db37e5-01c7-4447-8e56-117f8b43c106', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (10, 'sentinel-gateway.json', 'DEFAULT_GROUP', '[\r\n	{\r\n        \"resource\": \"nebula-system\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]', '62fd1273f42a3a1ce414cb3592896276', '2024-03-15 22:15:20', '2024-03-15 22:15:20', 'nacos', '122.96.68.157', '', '38db37e5-01c7-4447-8e56-117f8b43c106', NULL, NULL, NULL, 'json', NULL, '');
INSERT INTO `config_info` VALUES (15, 'nebula-auth-dev.yml', 'DEFAULT_GROUP', 'captcha.type: char', '07f5cc16988bc946e1c063d56ba1eeae', '2024-03-19 17:12:55', '2024-03-19 17:12:55', 'nacos', '122.96.68.152', '', '38db37e5-01c7-4447-8e56-117f8b43c106', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (16, 'redisson-application-dev.yml', 'DEFAULT_GROUP', 'spring:\n  data:\n    redis:\n      host: 47.103.5.196\n      port: 6379\n      password: MHp6o1qGCiSSZ24ngVYG\n      database: 0\n      timeout: 10000ms\n      lettuce:\n        pool:\n          max-active: 8\n          max-idle: 8\n          max-wait: -1ms\n          min-idle: 0\n\n# redisson 配置\nredisson:\n  # redis key前缀\n  keyPrefix:\n  # 线程池数量\n  threads: 4\n  # Netty线程池数量\n  nettyThreads: 8\n  # 单节点配置\n  singleServerConfig:\n    # 客户端名称\n    clientName: ${spring.application.name}\n    # 最小空闲连接数\n    connectionMinimumIdleSize: 8\n    # 连接池大小\n    connectionPoolSize: 32\n    # 连接空闲超时，单位：毫秒\n    idleConnectionTimeout: 10000\n    # 命令等待超时，单位：毫秒\n    timeout: 3000\n    # 发布和订阅连接池大小\n    subscriptionConnectionPoolSize: 50', '555827a69cf0aa17e9d6a5ce999d65f7', '2024-03-19 17:49:01', '2024-03-19 20:59:23', 'nacos', '122.96.68.152', '', '38db37e5-01c7-4447-8e56-117f8b43c106', '', '', '', 'yaml', '', '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `datum_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id` ASC, `tag_name` ASC, `tag_type` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create` ASC) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified` ASC) USING BTREE,
  INDEX `idx_did`(`data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (0, 6, 'nebula-gateway-dev', 'DEFAULT_GROUP', '', '# 不校验白名单\r\nignore:\r\n  whites:\r\n    - /auth/logout\r\n    - /auth/login\r\n    - /*/v2/api-docs\r\n    - /csrf', '56812a902f998ebe379a8ddaf5e3e946', '2024-03-15 15:55:10', '2024-03-15 15:55:10', 'nacos', '122.96.68.157', 'I', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (0, 7, 'sentinel-gateway', 'DEFAULT_GROUP', '', '[\r\n	{\r\n        \"resource\": \"nebula-system\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]', '62fd1273f42a3a1ce414cb3592896276', '2024-03-15 22:01:09', '2024-03-15 22:01:09', 'nacos', '122.96.68.157', 'I', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (6, 8, 'nebula-gateway-dev', 'DEFAULT_GROUP', '', '# 不校验白名单\r\nignore:\r\n  whites:\r\n    - /auth/logout\r\n    - /auth/login\r\n    - /*/v2/api-docs\r\n    - /csrf', '56812a902f998ebe379a8ddaf5e3e946', '2024-03-15 22:07:52', '2024-03-15 22:07:52', 'nacos', '122.96.68.157', 'D', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (0, 9, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\r\nignore:\r\n  whites:\r\n    - /auth/logout\r\n    - /auth/login\r\n    - /*/v2/api-docs\r\n    - /csrf', '56812a902f998ebe379a8ddaf5e3e946', '2024-03-15 22:08:21', '2024-03-15 22:08:22', 'nacos', '122.96.68.157', 'I', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (7, 10, 'sentinel-gateway', 'DEFAULT_GROUP', '', '[\r\n	{\r\n        \"resource\": \"nebula-system\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]', '62fd1273f42a3a1ce414cb3592896276', '2024-03-15 22:08:31', '2024-03-15 22:08:31', 'nacos', '122.96.68.157', 'D', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (0, 11, 'sentinel-gateway-dev.json', 'DEFAULT_GROUP', '', '[\r\n	{\r\n        \"resource\": \"nebula-system\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]', '62fd1273f42a3a1ce414cb3592896276', '2024-03-15 22:09:26', '2024-03-15 22:09:26', 'nacos', '122.96.68.157', 'I', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (9, 12, 'sentinel-gateway-dev.json', 'DEFAULT_GROUP', '', '[\r\n	{\r\n        \"resource\": \"nebula-system\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]', '62fd1273f42a3a1ce414cb3592896276', '2024-03-15 22:14:54', '2024-03-15 22:14:55', 'nacos', '122.96.68.157', 'D', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (0, 13, 'sentinel-gateway.json', 'DEFAULT_GROUP', '', '[\r\n	{\r\n        \"resource\": \"nebula-system\",\r\n        \"count\": 1000,\r\n        \"grade\": 1,\r\n        \"limitApp\": \"default\",\r\n        \"strategy\": 0,\r\n        \"controlBehavior\": 0\r\n    }\r\n]', '62fd1273f42a3a1ce414cb3592896276', '2024-03-15 22:15:19', '2024-03-15 22:15:20', 'nacos', '122.96.68.157', 'I', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 14, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\r\nignore:\r\n  whites:\r\n    - /auth/logout\r\n    - /auth/login\r\n    - /*/v2/api-docs\r\n    - /csrf', '56812a902f998ebe379a8ddaf5e3e946', '2024-03-16 22:58:49', '2024-03-16 22:58:49', 'nacos', '122.96.68.15', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (2, 15, 'application-dev.yml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    # JDBC配置：\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://47.103.5.196:3309/nebula-cloud?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true\n    username: root\n    password: root@MySql8.0\n    # 连接池配置：\n    druid:\n      initial-size: 10 # 初始化时建立物理连接的个数。默认0\n      max-active: 30 # 最大连接池数量，默认8\n      min-idle: 1 # 最小连接池数量\n      max-wait: 2000 # 获取连接时最大等待时间，单位毫秒。\n      pool-prepared-statements: false # 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。\n      max-pool-prepared-statement-per-connection-size: -1 # 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100\n      # druid节点下的其它参数见官方文档：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8\n      # 启用Druid内置的Filter，会使用默认的配置。可自定义配置，见下方的各个filter节点。\n      filters: stat,wall\n      # StatViewServlet监控器。开启后，访问http://域名/druid/index.html\n      stat-view-servlet:\n        enabled: true # 开启 StatViewServlet，即开启监控功能\n        login-username: daniel # 访问监控页面时登录的账号\n        login-password: 1234 # 密码\n        url-pattern: /druid/* # Servlet的映射地址，不填写默认为\"/druid/*\"。如填写其它地址，访问监控页面时，要使用相应的地址\n        reset-enable: false # 是否允许重置数据（在页面的重置按钮）。（停用后，依然会有重置按钮，但重置后不会真的重置数据）\n        allow: 192.168.1.2,192.168.1.1 # 监控页面访问白名单。默认为127.0.0.1。与黑名单一样，支持子网掩码，如128.242.127.1/24。多个ip用英文逗号分隔\n        deny: 18.2.1.3 # 监控页面访问黑名单\n      # 配置 WebStatFilter（StatFilter监控器中的Web模板）\n      web-stat-filter:\n        enabled: true # 开启 WebStatFilter，即开启监控功能中的 Web 监控功能\n        url-pattern: /* # 映射地址，即统计指定地址的web请求\n        exclusions: \'*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\' # 不统计的web请求，如下是不统计静态资源及druid监控页面本身的请求\n        session-stat-enable: true # 是否启用session统计\n        session-stat-max-count: 1 # session统计的最大个数，默认是1000。当统计超过这个数，只统计最新的\n        principal-session-name: userName # 所存用户信息的serssion参数名。Druid会依照此参数名读取相应session对应的用户名记录下来（在监控页面可看到）。如果指定参数不是基础数据类型，将会自动调用相应参数对象的toString方法来取值\n        principal-cookie-name: userName # 与上类似，但这是通过Cookie名取到用户信息\n        profile-enable: true # 监控单个url调用的sql列表（试了没生效，以后需要用再研究）\n      filter:\n        wall:\n          enabled: true  # 开启SQL防火墙功能\n          config:\n            select-allow: true # 允许执行Select查询操作\n            delete-allow: false # 不允许执行delete操作\n            create-table-allow: true # 不允许创建表\n            # 更多用法，参考官方文档：https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter', '86fd1af295684d8f81909343fe28cebc', '2024-03-16 23:58:20', '2024-03-16 23:58:20', 'nacos', '122.96.68.15', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (2, 16, 'application-dev.yml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    # JDBC配置：\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://47.103.5.196:3309/nebula-cloud?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true\n    username: root\n    password: root@MySql8.0\n    # 连接池配置：\n    druid:\n      initial-size: 10 # 初始化时建立物理连接的个数。默认0\n      max-active: 30 # 最大连接池数量，默认8\n      min-idle: 1 # 最小连接池数量\n      max-wait: 2000 # 获取连接时最大等待时间，单位毫秒。\n      pool-prepared-statements: false # 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。\n      max-pool-prepared-statement-per-connection-size: -1 # 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100\n      # druid节点下的其它参数见官方文档：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8\n      # 启用Druid内置的Filter，会使用默认的配置。可自定义配置，见下方的各个filter节点。\n      filters: stat,wall\n      # StatViewServlet监控器。开启后，访问http://域名/druid/index.html\n      stat-view-servlet:\n        enabled: true # 开启 StatViewServlet，即开启监控功能\n        login-username: daniel # 访问监控页面时登录的账号\n        login-password: 1234 # 密码\n        url-pattern: /druid/* # Servlet的映射地址，不填写默认为\"/druid/*\"。如填写其它地址，访问监控页面时，要使用相应的地址\n        reset-enable: false # 是否允许重置数据（在页面的重置按钮）。（停用后，依然会有重置按钮，但重置后不会真的重置数据）\n        allow: 192.168.1.2,192.168.1.1 # 监控页面访问白名单。默认为127.0.0.1。与黑名单一样，支持子网掩码，如128.242.127.1/24。多个ip用英文逗号分隔\n        deny: 18.2.1.3 # 监控页面访问黑名单\n      # 配置 WebStatFilter（StatFilter监控器中的Web模板）\n      web-stat-filter:\n        enabled: true # 开启 WebStatFilter，即开启监控功能中的 Web 监控功能\n        url-pattern: /* # 映射地址，即统计指定地址的web请求\n        exclusions: \'*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\' # 不统计的web请求，如下是不统计静态资源及druid监控页面本身的请求\n        session-stat-enable: true # 是否启用session统计\n        session-stat-max-count: 1 # session统计的最大个数，默认是1000。当统计超过这个数，只统计最新的\n        principal-session-name: userName # 所存用户信息的serssion参数名。Druid会依照此参数名读取相应session对应的用户名记录下来（在监控页面可看到）。如果指定参数不是基础数据类型，将会自动调用相应参数对象的toString方法来取值\n        principal-cookie-name: userName # 与上类似，但这是通过Cookie名取到用户信息\n        profile-enable: true # 监控单个url调用的sql列表（试了没生效，以后需要用再研究）\n      filter:\n        wall:\n          enabled: true  # 开启SQL防火墙功能\n          config:\n            select-allow: true # 允许执行Select查询操作\n            delete-allow: false # 不允许执行delete操作\n            create-table-allow: true # 不允许创建表\n            # 更多用法，参考官方文档：https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter\n\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      paths-to-match: \'/**\'\n      packages-to-scan: com.nebula.**.controller\n', '950793a119b04401d947ef585ad60da2', '2024-03-17 00:01:38', '2024-03-17 00:01:39', 'nacos', '122.96.68.15', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (2, 17, 'application-dev.yml', 'DEFAULT_GROUP', '', 'spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    # JDBC配置：\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://47.103.5.196:3309/nebula-cloud?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true\n    username: root\n    password: root@MySql8.0\n    # 连接池配置：\n    druid:\n      initial-size: 10 # 初始化时建立物理连接的个数。默认0\n      max-active: 30 # 最大连接池数量，默认8\n      min-idle: 1 # 最小连接池数量\n      max-wait: 2000 # 获取连接时最大等待时间，单位毫秒。\n      pool-prepared-statements: false # 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。\n      max-pool-prepared-statement-per-connection-size: -1 # 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100\n      # druid节点下的其它参数见官方文档：https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8\n      # 启用Druid内置的Filter，会使用默认的配置。可自定义配置，见下方的各个filter节点。\n      filters: stat,wall\n      # StatViewServlet监控器。开启后，访问http://域名/druid/index.html\n      stat-view-servlet:\n        enabled: true # 开启 StatViewServlet，即开启监控功能\n        login-username: daniel # 访问监控页面时登录的账号\n        login-password: 1234 # 密码\n        url-pattern: /druid/* # Servlet的映射地址，不填写默认为\"/druid/*\"。如填写其它地址，访问监控页面时，要使用相应的地址\n        reset-enable: false # 是否允许重置数据（在页面的重置按钮）。（停用后，依然会有重置按钮，但重置后不会真的重置数据）\n        allow: 192.168.1.2,192.168.1.1 # 监控页面访问白名单。默认为127.0.0.1。与黑名单一样，支持子网掩码，如128.242.127.1/24。多个ip用英文逗号分隔\n        deny: 18.2.1.3 # 监控页面访问黑名单\n      # 配置 WebStatFilter（StatFilter监控器中的Web模板）\n      web-stat-filter:\n        enabled: true # 开启 WebStatFilter，即开启监控功能中的 Web 监控功能\n        url-pattern: /* # 映射地址，即统计指定地址的web请求\n        exclusions: \'*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*\' # 不统计的web请求，如下是不统计静态资源及druid监控页面本身的请求\n        session-stat-enable: true # 是否启用session统计\n        session-stat-max-count: 1 # session统计的最大个数，默认是1000。当统计超过这个数，只统计最新的\n        principal-session-name: userName # 所存用户信息的serssion参数名。Druid会依照此参数名读取相应session对应的用户名记录下来（在监控页面可看到）。如果指定参数不是基础数据类型，将会自动调用相应参数对象的toString方法来取值\n        principal-cookie-name: userName # 与上类似，但这是通过Cookie名取到用户信息\n        profile-enable: true # 监控单个url调用的sql列表（试了没生效，以后需要用再研究）\n      filter:\n        wall:\n          enabled: true  # 开启SQL防火墙功能\n          config:\n            select-allow: true # 允许执行Select查询操作\n            delete-allow: false # 不允许执行delete操作\n            create-table-allow: true # 不允许创建表\n            # 更多用法，参考官方文档：https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE-wallfilter\n\nspringdoc:\n  swagger-ui:\n    path: /swagger-ui.html\n    tags-sorter: alpha\n    operations-sorter: alpha\n  api-docs:\n    path: /v3/api-docs\n  group-configs:\n    - group: \'default\'\n      packages-to-scan: com.nebula.**.controller\n', '1f6566fd3de3c1af6f443814cf8dd5ea', '2024-03-17 00:04:52', '2024-03-17 00:04:52', 'nacos', '122.96.68.15', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (0, 18, 'nebula-auth-dev.yml', 'DEFAULT_GROUP', '', 'captcha.type: char', '07f5cc16988bc946e1c063d56ba1eeae', '2024-03-19 17:12:55', '2024-03-19 17:12:55', 'nacos', '122.96.68.152', 'I', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (0, 19, 'redisson-application-dev.yml', 'DEFAULT_GROUP', '', '# redisson 配置\r\nredisson:\r\n  # redis key前缀\r\n  keyPrefix:\r\n  # 线程池数量\r\n  threads: 4\r\n  # Netty线程池数量\r\n  nettyThreads: 8\r\n  # 单节点配置\r\n  singleServerConfig:\r\n    # 客户端名称\r\n    clientName: ${spring.application.name}\r\n    # 最小空闲连接数\r\n    connectionMinimumIdleSize: 8\r\n    # 连接池大小\r\n    connectionPoolSize: 32\r\n    # 连接空闲超时，单位：毫秒\r\n    idleConnectionTimeout: 10000\r\n    # 命令等待超时，单位：毫秒\r\n    timeout: 3000\r\n    # 发布和订阅连接池大小\r\n    subscriptionConnectionPoolSize: 50', '09034b94ed979e91083ad088fd4cac9b', '2024-03-19 17:49:00', '2024-03-19 17:49:01', 'nacos', '122.96.68.152', 'I', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (16, 20, 'redisson-application-dev.yml', 'DEFAULT_GROUP', '', '# redisson 配置\r\nredisson:\r\n  # redis key前缀\r\n  keyPrefix:\r\n  # 线程池数量\r\n  threads: 4\r\n  # Netty线程池数量\r\n  nettyThreads: 8\r\n  # 单节点配置\r\n  singleServerConfig:\r\n    # 客户端名称\r\n    clientName: ${spring.application.name}\r\n    # 最小空闲连接数\r\n    connectionMinimumIdleSize: 8\r\n    # 连接池大小\r\n    connectionPoolSize: 32\r\n    # 连接空闲超时，单位：毫秒\r\n    idleConnectionTimeout: 10000\r\n    # 命令等待超时，单位：毫秒\r\n    timeout: 3000\r\n    # 发布和订阅连接池大小\r\n    subscriptionConnectionPoolSize: 50', '09034b94ed979e91083ad088fd4cac9b', '2024-03-19 17:51:39', '2024-03-19 17:51:39', 'nacos', '122.96.68.152', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (16, 21, 'redisson-application-dev.yml', 'DEFAULT_GROUP', '', 'spring:\n  data:\n    redis:\n      host: 47.103.5.196\n      port: 6379\n      database: 0\n      password: MHp6o1qGCiSSZ24ngVYG\n      lettuce:\n        pool:\n          max-active: 8\n          max-idle: 8\n          max-wait: -1ms\n          min-idle: 0\n\n# redisson 配置\nredisson:\n  # redis key前缀\n  keyPrefix:\n  # 线程池数量\n  threads: 4\n  # Netty线程池数量\n  nettyThreads: 8\n  # 单节点配置\n  singleServerConfig:\n    # 客户端名称\n    clientName: ${spring.application.name}\n    # 最小空闲连接数\n    connectionMinimumIdleSize: 8\n    # 连接池大小\n    connectionPoolSize: 32\n    # 连接空闲超时，单位：毫秒\n    idleConnectionTimeout: 10000\n    # 命令等待超时，单位：毫秒\n    timeout: 3000\n    # 发布和订阅连接池大小\n    subscriptionConnectionPoolSize: 50', '5299737b687388e45e705d42d3b6d637', '2024-03-19 18:03:10', '2024-03-19 18:03:10', 'nacos', '122.96.68.152', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (16, 22, 'redisson-application-dev.yml', 'DEFAULT_GROUP', '', '# redisson 配置\nredisson:\n  # redis key前缀\n  keyPrefix:\n  # 线程池数量\n  threads: 4\n  # Netty线程池数量\n  nettyThreads: 8\n  # 单节点配置\n  singleServerConfig:\n    # 客户端名称\n    clientName: ${spring.application.name}\n    # 最小空闲连接数\n    connectionMinimumIdleSize: 8\n    # 连接池大小\n    connectionPoolSize: 32\n    # 连接空闲超时，单位：毫秒\n    idleConnectionTimeout: 10000\n    # 命令等待超时，单位：毫秒\n    timeout: 3000\n    # 发布和订阅连接池大小\n    subscriptionConnectionPoolSize: 50', '91136dcf231abff800d1050618ad2d04', '2024-03-19 20:59:23', '2024-03-19 20:59:23', 'nacos', '122.96.68.152', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 23, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /auth/logout\n    - /auth/login\n    - /*/v3/api-docs\n    - /csrf', '8a269aae09b2aeec1eba062a2e5078af', '2024-03-24 23:22:41', '2024-03-24 23:22:42', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 24, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /doc.html\n    - /swagger-ui/index.html\n    - /csrf', '66863798751bf749040c6b1e395d64a4', '2024-03-24 23:25:47', '2024-03-24 23:25:48', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 25, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /doc.html\n    - /swagger-ui/index.html\n    - /*.js\n    - /*.css\n    - /*.html\n    - /csrf', '0bbd565550351e10fe96005c3fc90ed5', '2024-03-24 23:36:52', '2024-03-24 23:36:53', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 26, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /doc.html\n    - /swagger-ui/index.html\n    - /*.js\n    - /css/*.css\n    - /*.html\n    - /csrf', 'ad881ff5e99c54836e4397d2ed691caa', '2024-03-24 23:37:10', '2024-03-24 23:37:11', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 27, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /doc.html\n    - /swagger-ui/index.html\n    - /*.js\n    - /css/*.css\n    - /*.html\n    - /csrf', 'ad881ff5e99c54836e4397d2ed691caa', '2024-03-24 23:38:52', '2024-03-24 23:38:53', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 28, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - \"*.js\"\n    - \"*.css\"\n    - \"*.html\"\n    - /csrf', '188d2a0698ea67258dfb94a7a29b445b', '2024-03-25 00:01:56', '2024-03-25 00:01:57', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 29, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - \"*.js\"\n    - \"*.css\"\n    - \"*.html\"\n    - /swagger-ui/index.html\n    - /csrf', '761d8c542e4c0816318b963912f536c4', '2024-03-25 00:28:40', '2024-03-25 00:28:40', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 30, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /*.js\n    - /*.css\n    - \"/*.html\"\n    - /swagger-ui/index.html\n    - /csrf', '682753edc498cd85e4e6e013aeb229e5', '2024-03-25 00:30:05', '2024-03-25 00:30:06', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 31, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - \"/*.js\"\n    - \"/*.css\"\n    - \"/*.html\"\n    - /swagger-ui/index.html\n    - /csrf', '0cd3c5d2e9f2010212dfcc3e2fd3a140', '2024-03-25 00:31:36', '2024-03-25 00:31:36', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 32, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /webjars/**\n    - /swagger-ui/index.html\n    - /csrf', '8abcf942e65a66b1843ec98146b5f02a', '2024-03-25 00:32:40', '2024-03-25 00:32:41', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 33, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /webjars/**\n    - /swagger-ui/index.html\n    - /doc.html/**\n    - /csrf', 'e69e5dc6a061b7969c65cee9a7ffa042', '2024-03-25 00:33:05', '2024-03-25 00:33:06', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 34, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', '88cff95bfa21eeca8fd48e8119dab890', '2024-03-25 00:36:13', '2024-03-25 00:36:13', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 35, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /auth/v3/**\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', 'dbb02dfcd144c10e5c15a794336c2c56', '2024-03-25 00:37:57', '2024-03-25 00:37:58', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 36, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /v3/api-docs/**\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', '5696a43a9404d7366181954bd668b9b6', '2024-03-25 00:39:07', '2024-03-25 00:39:08', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 37, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /v3/api-docs/**\n    - /v3/api-docs/swagger-config\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', 'ab63a6f96e3646fd708a10a29bd9aa32', '2024-03-25 00:39:53', '2024-03-25 00:39:54', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 38, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /v3/api-docs\n    - /v3/api-docs/**\n    - /v3/api-docs/swagger-config\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', '7c1638a8cd0d20d330f5fad6ea4983a9', '2024-03-25 00:42:36', '2024-03-25 00:42:36', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 39, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /v3/api-docs/**\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', '5696a43a9404d7366181954bd668b9b6', '2024-03-25 01:05:30', '2024-03-25 01:05:31', 'nacos', '122.96.68.48', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 40, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /auth/v3/**\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', 'f44844f932b563dd7c5e2a84864ea6f1', '2024-03-25 11:56:01', '2024-03-25 11:56:02', 'nacos', '112.80.229.49', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');
INSERT INTO `his_config_info` VALUES (8, 41, 'nebula-gateway-dev.yml', 'DEFAULT_GROUP', '', '# 不校验白名单\nignore:\n  whites:\n    - /v3/**\n    - /auth/v3/**\n    - /auth/captcha\n    - /auth/login\n    - /webjars/**\n    - /swagger-ui/index.html/**\n    - /doc.html/**\n    - /csrf', 'c15be85665b3f9385f31505e7b8d87b7', '2024-03-25 14:06:52', '2024-03-25 14:06:52', 'nacos', '112.80.229.49', 'U', '38db37e5-01c7-4447-8e56-117f8b43c106', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role` ASC, `resource` ASC, `action` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username` ASC, `role` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp` ASC, `tenant_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', '38db37e5-01c7-4447-8e56-117f8b43c106', 'dev', '开发空间', 'nacos', 1702636488402, 1702636488402);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
