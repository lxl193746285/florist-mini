# 花店后台管理框架实现说明

## ✅ 已完成的功能

### 1. 后端API实现

#### 菜单管理API
- **GET `/api/menu/list`** - 获取当前用户的菜单树（基于角色权限）
- **GET `/api/menu/all`** - 获取所有菜单树（管理员使用）

#### 用户信息API
- **GET `/api/user/info`** - 获取当前登录用户信息

#### 实现文件
- `MenuController.java` - 菜单接口控制器
- `UserController.java` - 用户信息控制器（已存在，已有 `/info` 接口）
- `MenuService.java` - 菜单服务，负责构建权限树
- `MenuTreeVO.java` - 菜单树VO
- `Permission.java` - 权限/菜单实体
- `PermissionMapper.java` - 权限数据访问层

#### 数据库设计
使用现有的 `permission` 表作为菜单数据源：
- `permission_type = 1` 表示菜单类型
- 通过 `parent_id` 构建树形结构
- 支持基于角色的权限控制

### 2. 前端框架适配

#### 修改的文件
1. **`src/services/global.ts`**
   - 将 `getMenus` 接口从 `/v4/organization/frontend-menus` 改为 `/api/menu/list`
   - 将 `getUser` 接口从 `/v4/employee/employee/user` 改为 `/api/user/info`

2. **`src/hooks/globalModel.ts`**
   - 修改 `initUser()` 方法，管理员同时设置 member 信息用于显示
   - 修改 `initMember()` 方法，添加错误处理（管理员没有会员信息）
   - 修改 `initSystemConfig()` 方法，改为async/await，暂时不需要公司切换功能

3. **`src/layouts/mainLayout/components/LayoutHeaderRight.tsx`**
   - 将右上角用户显示从 `member` 改为 `user`
   - 显示 `user.nickname || user.name`

#### 现有的前端框架（无需修改）
- ✅ `MainLayout` - 主布局框架（左侧菜单 + 右侧内容区）
- ✅ `LayoutMenu` - 左侧树状菜单组件
- ✅ `LayoutHeaderRight` - 右上角用户信息显示
- ✅ `LayoutTab` - 顶部标签页
- ✅ `LayoutContent` - 内容区域

---

## 📋 数据库菜单数据

当前数据库已有以下菜单（来自 `permission` 表）：

| ID | 菜单名称 | 权限编码 | 路由路径 | 父级ID |
|----|---------|---------|---------|-------|
| 1  | 系统管理 | system | /system | 0 |
| 2  | 用户管理 | system:user | /system/user | 1 |
| 3  | 查看用户 | system:user:view | NULL | 2 |
| 4  | 新增用户 | system:user:add | NULL | 2 |
| 5  | 编辑用户 | system:user:edit | NULL | 2 |
| 6  | 删除用户 | system:user:delete | NULL | 2 |
| 7  | 角色管理 | system:role | /system/role | 1 |
| 8  | 商品管理 | product | /product | 0 |
| 9  | 商品列表 | product:list | /product/list | 8 |
| 10 | 订单管理 | order | /order | 0 |

**注意**：
- `permission_type = 1` 的是菜单
- `permission_type = 2` 的是按钮权限（如查看、新增、编辑、删除）
- `permission_type = 3` 的是接口权限

---

## 🚀 测试步骤

### 1. 重启后端服务

**方法一：在 IntelliJ IDEA 中**
- 停止当前运行的应用（点击红色停止按钮）
- 重新运行 `WxCloudRunApplication`

**方法二：使用命令行**
```bash
cd /Users/lixiaolong/Desktop/学习/WxMini/florist-mini
mvn spring-boot:run
```

### 2. 启动前端服务

```bash
cd /Users/lixiaolong/Desktop/学习/WxMini/florist-web
npm run dev
```

### 3. 测试登录和菜单显示

1. **访问登录页**：http://localhost:8000/system/login
2. **使用管理员账号登录**：
   - 账号：`admin`
   - 密码：`admin123`
3. **验证功能**：
   - ✅ 登录成功后自动跳转到主框架
   - ✅ 左侧显示树状菜单（系统管理、用户管理、角色管理、商品管理、订单管理）
   - ✅ 右上角显示当前用户昵称："超级管理员"
   - ✅ 点击菜单可以切换页面

### 4. 测试API（可选）

```bash
# 1. 登录获取token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"account":"admin","password":"admin123"}' \
  | grep -o '"token":"[^"]*"' | cut -d'"' -f4)

# 2. 获取用户信息
curl -X GET "http://localhost:8080/api/user/info" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"

# 3. 获取菜单树
curl -X GET "http://localhost:8080/api/menu/list" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
```

**期望返回**：
- `/api/user/info` 返回当前用户信息，包括昵称、部门、职位等
- `/api/menu/list` 返回菜单树结构，按照父子层级组织

---

## 📝 后续扩展建议

### 1. 添加更多菜单项

在数据库 `permission` 表中插入新的菜单记录：

```sql
-- 示例：添加库存管理菜单
INSERT INTO `permission` (`parent_id`, `permission_name`, `permission_code`, `permission_type`, `path`, `sort`, `status`)
VALUES
(0, '库存管理', 'inventory', 1, '/inventory', 4, 1),
(11, '库存查询', 'inventory:query', 1, '/inventory/query', 1, 1),
(11, '入库管理', 'inventory:inbound', 1, '/inventory/inbound', 2, 1);
```

### 2. 添加菜单图标

修改数据库中的 `icon` 字段，使用 Ant Design 图标名称：

```sql
UPDATE permission SET icon = 'SettingOutlined' WHERE id = 1;  -- 系统管理
UPDATE permission SET icon = 'UserOutlined' WHERE id = 2;      -- 用户管理
UPDATE permission SET icon = 'ShoppingOutlined' WHERE id = 8;  -- 商品管理
UPDATE permission SET icon = 'OrderedListOutlined' WHERE id = 10; -- 订单管理
```

然后修改前端 `LayoutMenu.tsx`，根据 `icon` 字段动态加载图标。

### 3. 创建实际的页面组件

在 `florist-web/src/pages/` 下创建对应的页面：

```
src/pages/
├── system/
│   ├── user/         # 用户管理页面
│   └── role/         # 角色管理页面
├── product/
│   └── list/         # 商品列表页面
└── order/
    └── list/         # 订单列表页面
```

### 4. 实现权限控制

- 按钮级权限：根据 `permission_type = 2` 的权限控制按钮显示
- 接口级权限：在后端拦截器中验证接口权限

---

## 🎯 架构说明

### 前端架构
```
前端 (React + UmiJS + Ant Design + Jotai)
├── layouts/
│   ├── index.tsx                 # 主布局路由
│   └── mainLayout/
│       ├── index.tsx             # 主框架布局
│       └── components/
│           ├── LayoutMenu.tsx    # 左侧菜单
│           ├── LayoutHeaderRight.tsx  # 右上角用户信息
│           ├── LayoutTab.tsx     # 顶部标签页
│           └── LayoutContent.tsx # 内容区域
├── hooks/
│   └── globalModel.ts            # 全局状态管理（菜单、用户）
└── services/
    └── global.ts                 # API服务调用
```

### 后端架构
```
后端 (Spring Boot + MyBatis + MySQL + JWT)
├── controller/
│   ├── MenuController.java       # 菜单接口
│   └── UserController.java       # 用户信息接口
├── service/
│   └── MenuService.java          # 菜单业务逻辑
├── dao/
│   └── PermissionMapper.java     # 权限数据访问
├── model/
│   └── Permission.java           # 权限/菜单实体
├── dto/
│   └── MenuTreeVO.java           # 菜单树VO
└── interceptor/
    └── JwtInterceptor.java       # JWT拦截器（已有）
```

### 数据流
```
登录成功
  → 获取Token
  → 前端调用 initSystemConfig()
  → 并行调用：
     ├── GET /api/user/info  (获取用户信息)
     └── GET /api/menu/list  (获取菜单树)
  → 渲染框架：
     ├── 左侧菜单树
     ├── 右上角用户信息
     └── 内容区域
```

---

## ⚠️ 注意事项

1. **JWT Token验证**：所有 `/api/**` 接口都需要JWT Token，除了登录、注册等公开接口
2. **菜单权限**：菜单显示基于用户的角色权限，超级管理员可以看到所有菜单
3. **跨域配置**：后端已配置CORS，允许前端跨域访问
4. **字符编码**：数据库使用UTF-8编码，中文显示正常

---

## 🔗 相关文件清单

### 后端新增文件
- `/florist-mini/src/main/java/com/tencent/wxcloudrun/controller/MenuController.java`
- `/florist-mini/src/main/java/com/tencent/wxcloudrun/service/MenuService.java`
- `/florist-mini/src/main/java/com/tencent/wxcloudrun/dao/PermissionMapper.java`
- `/florist-mini/src/main/java/com/tencent/wxcloudrun/model/Permission.java`
- `/florist-mini/src/main/java/com/tencent/wxcloudrun/dto/MenuTreeVO.java`

### 前端修改文件
- `/florist-web/src/services/global.ts`
- `/florist-web/src/hooks/globalModel.ts`
- `/florist-web/src/layouts/mainLayout/components/LayoutHeaderRight.tsx`

### 数据库表
- `permission` - 权限/菜单表
- `role` - 角色表
- `user_role` - 用户角色关联表
- `role_permission` - 角色权限关联表

---

需要帮助？请检查：
- 后端服务是否正常启动（端口 8080）
- 前端服务是否正常启动（端口 8000）
- 数据库连接是否正常
- Token是否在请求头中正确传递
