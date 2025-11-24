# 花店系统 - 用户账号表结构设计文档

## 📊 表结构概览

### 核心表（8张）
1. **user** - 用户基础表（统一存储）
2. **admin** - 管理员扩展表
3. **member** - 会员扩展表
4. **role** - 角色表
5. **permission** - 权限表
6. **user_role** - 用户角色关联表
7. **role_permission** - 角色权限关联表
8. **login_log** - 登录日志表

---

## 🎯 设计思路

### 1. 统一用户表设计
- **优点**：
  - 避免用户名、手机号、邮箱重复
  - 统一的登录验证逻辑
  - 便于统计总用户数
  - 扩展性好

- **通过 `user_type` 字段区分**：
  - `1` - 管理员（后台人员）
  - `2` - 会员（前端用户）

### 2. 扩展表设计
- **admin 表**：存储管理员特有信息（工号、部门、职位）
- **member 表**：存储会员特有信息（等级、积分、余额、微信OpenID）

### 3. RBAC 权限模型
采用 **Role-Based Access Control（基于角色的访问控制）**：
```
用户 → 角色 → 权限
```

---

## 📋 表结构详解

### 1. user（用户基础表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 用户ID（主键） |
| username | VARCHAR(50) | 登录账号（唯一） |
| password | VARCHAR(128) | 加密密码（BCrypt） |
| user_type | TINYINT | 1-管理员，2-会员 |
| phone | VARCHAR(20) | 手机号（唯一，可登录） |
| email | VARCHAR(100) | 邮箱（唯一，可登录） |
| avatar | VARCHAR(255) | 头像URL |
| nickname | VARCHAR(50) | 昵称 |
| real_name | VARCHAR(50) | 真实姓名 |
| gender | TINYINT | 0-未知，1-男，2-女 |
| status | TINYINT | 0-禁用，1-正常 |
| last_login_time | DATETIME | 最后登录时间 |
| last_login_ip | VARCHAR(50) | 最后登录IP |
| deleted_at | DATETIME | 软删除时间 |

**索引**：
- `uk_username`：用户名唯一索引
- `uk_phone`：手机号唯一索引
- `uk_email`：邮箱唯一索引
- `idx_user_type`：用户类型索引
- `idx_status`：状态索引

---

### 2. admin（管理员扩展表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 管理员ID |
| user_id | BIGINT | 关联用户ID（外键） |
| employee_no | VARCHAR(50) | 工号 |
| department | VARCHAR(100) | 所属部门 |
| position | VARCHAR(50) | 职位 |
| is_super_admin | TINYINT | 是否超级管理员 |

---

### 3. member（会员扩展表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 会员ID |
| user_id | BIGINT | 关联用户ID（外键） |
| member_no | VARCHAR(50) | 会员编号 |
| level | TINYINT | 会员等级（1-4） |
| points | INT | 积分 |
| balance | DECIMAL(10,2) | 账户余额 |
| birthday | DATE | 生日 |
| source | VARCHAR(50) | 注册来源 |
| openid | VARCHAR(100) | 微信OpenID |
| unionid | VARCHAR(100) | 微信UnionID |
| referrer_id | BIGINT | 推荐人ID |

**会员等级**：
- `1` - 普通会员
- `2` - 银卡会员
- `3` - 金卡会员
- `4` - 钻石会员

---

### 4. role（角色表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 角色ID |
| role_name | VARCHAR(50) | 角色名称 |
| role_code | VARCHAR(50) | 角色编码（唯一） |
| description | VARCHAR(200) | 角色描述 |
| sort | INT | 排序 |
| status | TINYINT | 状态 |

**预设角色**：
- `SUPER_ADMIN` - 超级管理员
- `STORE_MANAGER` - 店长
- `EMPLOYEE` - 员工
- `MEMBER` - 普通会员
- `VIP_MEMBER` - VIP会员

---

### 5. permission（权限表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 权限ID |
| parent_id | BIGINT | 父级权限ID |
| permission_name | VARCHAR(50) | 权限名称 |
| permission_code | VARCHAR(100) | 权限编码（唯一） |
| permission_type | TINYINT | 1-菜单，2-按钮，3-接口 |
| path | VARCHAR(200) | 路由路径 |
| icon | VARCHAR(50) | 图标 |
| sort | INT | 排序 |
| status | TINYINT | 状态 |

**权限类型**：
- `1` - 菜单权限（页面访问）
- `2` - 按钮权限（操作权限）
- `3` - 接口权限（API调用）

---

### 6-7. 关联表

**user_role（用户角色关联）**：
- 多对多关系：一个用户可以有多个角色

**role_permission（角色权限关联）**：
- 多对多关系：一个角色可以有多个权限

---

### 8. login_log（登录日志表）

| 字段 | 类型 | 说明 |
|------|------|------|
| id | BIGINT | 日志ID |
| user_id | BIGINT | 用户ID |
| username | VARCHAR(50) | 用户名 |
| user_type | TINYINT | 用户类型 |
| login_time | DATETIME | 登录时间 |
| login_ip | VARCHAR(50) | 登录IP |
| login_location | VARCHAR(100) | 登录地点 |
| browser | VARCHAR(50) | 浏览器 |
| os | VARCHAR(50) | 操作系统 |
| status | TINYINT | 0-失败，1-成功 |
| message | VARCHAR(255) | 提示消息 |

**用途**：
- 安全审计
- 异常登录检测
- 用户行为分析

---

## 🔐 安全设计

### 1. 密码加密
- **推荐算法**：BCrypt（Spring Security 默认）
- **Java 示例**：
```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hashedPassword = encoder.encode("password123");
```

### 2. 登录方式支持
- ✅ 用户名 + 密码
- ✅ 手机号 + 密码
- ✅ 邮箱 + 密码
- ✅ 手机号 + 验证码
- ✅ 微信授权登录（会员端）

### 3. Token 认证
- 推荐使用 **JWT（JSON Web Token）**
- 存储用户ID、用户类型、角色等信息

---

## 📝 初始化数据

### 默认超级管理员
- **用户名**：admin
- **密码**：admin123（需要修改为加密后的密码）
- **手机号**：13800138000
- **邮箱**：admin@florist.com

### 默认角色
1. 超级管理员（SUPER_ADMIN）
2. 店长（STORE_MANAGER）
3. 员工（EMPLOYEE）
4. 普通会员（MEMBER）
5. VIP会员（VIP_MEMBER）

---

## 🚀 使用方式

### 1. 执行 SQL 创建表
```bash
mysql -h sh-cynosdbmysql-grp-q7wvpzzo.sql.tencentcdb.com -P 21072 -u root -p florist < src/main/resources/db/schema.sql
```

### 2. 注册管理员流程
```
1. 在 user 表插入记录（user_type=1）
2. 在 admin 表插入关联记录
3. 在 user_role 表绑定角色
```

### 3. 注册会员流程
```
1. 在 user 表插入记录（user_type=2）
2. 在 member 表插入关联记录
3. 在 user_role 表绑定默认会员角色
4. 生成会员编号（如：M20250101001）
```

### 4. 权限验证流程
```
1. 用户登录 → 查询用户信息
2. 查询用户角色（user_role）
3. 查询角色权限（role_permission）
4. 返回权限列表给前端
5. 前端根据权限控制菜单和按钮显示
6. 后端接口通过注解验证权限
```

---

## 🎨 扩展建议

### 1. 会员积分系统
可新增表：
- `member_points_log`（积分变动记录）
- `member_level_config`（会员等级配置）

### 2. 地址管理
可新增表：
- `user_address`（用户收货地址）

### 3. 第三方登录
member 表已预留：
- `openid`（微信）
- `unionid`（微信）
可继续扩展支付宝、QQ等

### 4. 部门管理
可新增表：
- `department`（部门表）
- admin 表的 department 改为 department_id

---

## 📊 ER 关系图（文字版）

```
user（用户基础表）
  ├─ admin（管理员扩展）
  └─ member（会员扩展）

user ──< user_role >── role ──< role_permission >── permission

user ──< login_log（登录日志）
```

---

## ⚠️ 注意事项

1. **密码安全**：
   - 生产环境必须使用 BCrypt 等加密算法
   - 初始化的默认密码需要立即修改

2. **唯一约束**：
   - username、phone、email 都是唯一的
   - 注册时需要校验是否已存在

3. **软删除**：
   - 使用 `deleted_at` 字段标记删除
   - 查询时需要过滤 `deleted_at IS NULL`

4. **数据库选择**：
   - 当前 SQL 使用 MySQL，字段类型需根据实际数据库调整

5. **索引优化**：
   - 根据实际查询需求调整索引
   - 避免过多索引影响写入性能

---

## 📞 后续开发

表结构创建完成后，需要开发：
1. ✅ Java 实体类（Entity）
2. ✅ Mapper 接口（MyBatis）
3. ✅ Service 业务层
4. ✅ Controller 接口层
5. ✅ 登录注册接口
6. ✅ JWT Token 生成和验证
7. ✅ 权限拦截器
8. ✅ 前端登录页面
