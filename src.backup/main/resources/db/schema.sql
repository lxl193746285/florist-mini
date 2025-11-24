-- ==========================================
-- 花店系统 - 用户账号数据库设计
-- ==========================================

-- 1. 用户基础表（统一存储所有用户）
CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名/登录账号',
  `password` VARCHAR(128) NOT NULL COMMENT '密码（加密存储）',
  `user_type` TINYINT(1) NOT NULL DEFAULT 2 COMMENT '用户类型：1-管理员，2-会员',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `gender` TINYINT(1) DEFAULT NULL COMMENT '性别：0-未知，1-男，2-女',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `last_login_time` DATETIME DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` VARCHAR(50) DEFAULT NULL COMMENT '最后登录IP',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted_at` DATETIME DEFAULT NULL COMMENT '软删除时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_phone` (`phone`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_user_type` (`user_type`),
  KEY `idx_status` (`status`),
  KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户基础表';

-- 2. 管理员扩展表
CREATE TABLE `admin` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '关联用户ID',
  `employee_no` VARCHAR(50) DEFAULT NULL COMMENT '工号',
  `department` VARCHAR(100) DEFAULT NULL COMMENT '所属部门',
  `position` VARCHAR(50) DEFAULT NULL COMMENT '职位',
  `is_super_admin` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否超级管理员：0-否，1-是',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_employee_no` (`employee_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员扩展信息表';

-- 3. 会员扩展表
CREATE TABLE `member` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '会员ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '关联用户ID',
  `member_no` VARCHAR(50) DEFAULT NULL COMMENT '会员编号',
  `level` TINYINT(2) NOT NULL DEFAULT 1 COMMENT '会员等级：1-普通，2-银卡，3-金卡，4-钻石',
  `points` INT(11) NOT NULL DEFAULT 0 COMMENT '积分',
  `balance` DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '账户余额',
  `birthday` DATE DEFAULT NULL COMMENT '生日',
  `source` VARCHAR(50) DEFAULT NULL COMMENT '注册来源：wechat-微信，app-APP，web-网页',
  `openid` VARCHAR(100) DEFAULT NULL COMMENT '微信OpenID（微信登录）',
  `unionid` VARCHAR(100) DEFAULT NULL COMMENT '微信UnionID',
  `referrer_id` BIGINT(20) DEFAULT NULL COMMENT '推荐人ID',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  UNIQUE KEY `uk_member_no` (`member_no`),
  UNIQUE KEY `uk_openid` (`openid`),
  KEY `idx_level` (`level`),
  KEY `idx_referrer_id` (`referrer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员扩展信息表';

-- 4. 角色表（用于权限管理）
CREATE TABLE `role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '角色描述',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 5. 权限表
CREATE TABLE `permission` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `parent_id` BIGINT(20) DEFAULT 0 COMMENT '父级权限ID（0为顶级）',
  `permission_name` VARCHAR(50) NOT NULL COMMENT '权限名称',
  `permission_code` VARCHAR(100) NOT NULL COMMENT '权限编码',
  `permission_type` TINYINT(1) NOT NULL COMMENT '权限类型：1-菜单，2-按钮，3-接口',
  `path` VARCHAR(200) DEFAULT NULL COMMENT '路由路径',
  `icon` VARCHAR(50) DEFAULT NULL COMMENT '图标',
  `sort` INT(11) DEFAULT 0 COMMENT '排序',
  `status` TINYINT(1) NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_permission_code` (`permission_code`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- 6. 用户角色关联表
CREATE TABLE `user_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
  KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 7. 角色权限关联表
CREATE TABLE `role_permission` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `permission_id` BIGINT(20) NOT NULL COMMENT '权限ID',
  `created_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role_permission` (`role_id`, `permission_id`),
  KEY `idx_permission_id` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- 8. 用户登录日志表（可选，用于安全审计）
CREATE TABLE `login_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `user_type` TINYINT(1) NOT NULL COMMENT '用户类型：1-管理员，2-会员',
  `login_time` DATETIME NOT NULL COMMENT '登录时间',
  `login_ip` VARCHAR(50) DEFAULT NULL COMMENT '登录IP',
  `login_location` VARCHAR(100) DEFAULT NULL COMMENT '登录地点',
  `browser` VARCHAR(50) DEFAULT NULL COMMENT '浏览器类型',
  `os` VARCHAR(50) DEFAULT NULL COMMENT '操作系统',
  `status` TINYINT(1) NOT NULL COMMENT '登录状态：0-失败，1-成功',
  `message` VARCHAR(255) DEFAULT NULL COMMENT '提示消息',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户登录日志表';

-- ==========================================
-- 初始化数据
-- ==========================================

-- 插入默认超级管理员（密码：admin123，需要在应用中加密）
INSERT INTO `user` (`id`, `username`, `password`, `user_type`, `phone`, `email`, `nickname`, `real_name`, `status`)
VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 1, '13800138000', 'admin@florist.com', '超级管理员', '系统管理员', 1);

INSERT INTO `admin` (`user_id`, `employee_no`, `department`, `position`, `is_super_admin`)
VALUES (1, 'EMP001', '系统部', '系统管理员', 1);

-- 插入默认角色
INSERT INTO `role` (`id`, `role_name`, `role_code`, `description`, `sort`) VALUES
(1, '超级管理员', 'SUPER_ADMIN', '拥有所有权限', 1),
(2, '店长', 'STORE_MANAGER', '门店管理权限', 2),
(3, '员工', 'EMPLOYEE', '基础操作权限', 3),
(4, '普通会员', 'MEMBER', '会员基础权限', 10),
(5, 'VIP会员', 'VIP_MEMBER', '会员高级权限', 11);

-- 绑定超级管理员角色
INSERT INTO `user_role` (`user_id`, `role_id`) VALUES (1, 1);

-- 插入基础权限（示例）
INSERT INTO `permission` (`id`, `parent_id`, `permission_name`, `permission_code`, `permission_type`, `path`, `sort`) VALUES
(1, 0, '系统管理', 'system', 1, '/system', 1),
(2, 1, '用户管理', 'system:user', 1, '/system/user', 1),
(3, 2, '查看用户', 'system:user:view', 2, NULL, 1),
(4, 2, '新增用户', 'system:user:add', 2, NULL, 2),
(5, 2, '编辑用户', 'system:user:edit', 2, NULL, 3),
(6, 2, '删除用户', 'system:user:delete', 2, NULL, 4),
(7, 1, '角色管理', 'system:role', 1, '/system/role', 2),
(8, 0, '商品管理', 'product', 1, '/product', 2),
(9, 8, '商品列表', 'product:list', 1, '/product/list', 1),
(10, 0, '订单管理', 'order', 1, '/order', 3);

-- 绑定超级管理员的所有权限
INSERT INTO `role_permission` (`role_id`, `permission_id`)
SELECT 1, id FROM `permission`;
