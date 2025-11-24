-- ==========================================
-- 修复字符编码问题
-- ==========================================

-- 1. 清除现有数据（保持表结构）
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE `role_permission`;
TRUNCATE TABLE `user_role`;
TRUNCATE TABLE `permission`;
TRUNCATE TABLE `role`;
TRUNCATE TABLE `admin`;
TRUNCATE TABLE `user`;
TRUNCATE TABLE `login_log`;

SET FOREIGN_KEY_CHECKS = 1;

-- 2. 重新插入数据（确保使用UTF-8编码）

-- 插入默认超级管理员（密码：admin123）
-- BCrypt哈希已验证正确
INSERT INTO `user` (`id`, `username`, `password`, `user_type`, `phone`, `email`, `nickname`, `real_name`, `status`)
VALUES (1, 'admin', '$2a$10$FE9KTPveVFY7KLW0i.wj8.vlSuX8g0vWGCVJLVP2AApdB0SZAXBU.', 1, '13800138000', 'admin@florist.com', '超级管理员', '系统管理员', 1);

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

-- 插入基础权限
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
