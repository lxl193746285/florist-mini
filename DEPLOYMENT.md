# Florist 单体应用部署指南

## 📦 项目结构说明

本项目采用**多模块Maven项目**结构，但部署时打包为**单体应用**（Single Service）。

```
florist-java/
├── pom.xml                    # 父POM（packaging=pom）
├── qy-common/                 # 公共模块（jar）
├── qy-base/                   # 基础服务模块（jar）
├── qy-base-org/               # 组织权限模块（jar）
├── qy-member/                 # 会员模块（jar）
└── florist-app/               # ⭐ 主应用模块（可执行jar）
```

## 🚀 打包部署

### 1. 完整打包（推荐）

```bash
# 进入项目根目录
cd /path/to/florist-java

# 清理并打包整个项目
mvn clean package -DskipTests

# 生成的可执行jar位置
# florist-app/target/florist-app.jar
```

### 2. 快速打包（已编译过其他模块）

```bash
# 只打包florist-app模块
cd florist-app
mvn package -DskipTests
```

### 3. 安装到本地仓库（开发环境）

```bash
# 将所有模块安装到本地Maven仓库
mvn clean install -DskipTests
```

## 🏃 运行应用

### 方式1：直接运行

```bash
# 使用默认配置运行
java -jar florist-app/target/florist-app.jar

# 指定配置文件
java -jar florist-app/target/florist-app.jar --spring.profiles.active=prod

# 指定端口
java -jar florist-app/target/florist-app.jar --server.port=8080
```

### 方式2：后台运行

```bash
# Linux/Mac 后台运行
nohup java -jar florist-app/target/florist-app.jar > app.log 2>&1 &

# 查看日志
tail -f app.log

# 查看进程
ps aux | grep florist-app

# 停止应用
kill -9 <PID>
```

### 方式3：使用systemd管理（生产环境推荐）

创建服务文件 `/etc/systemd/system/florist-app.service`:

```ini
[Unit]
Description=Florist Application
After=syslog.target network.target

[Service]
Type=simple
User=appuser
WorkingDirectory=/opt/florist
ExecStart=/usr/bin/java -jar /opt/florist/florist-app.jar --spring.profiles.active=prod
SuccessExitStatus=143
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

启动服务：

```bash
# 重载systemd配置
sudo systemctl daemon-reload

# 启动应用
sudo systemctl start florist-app

# 开机自启
sudo systemctl enable florist-app

# 查看状态
sudo systemctl status florist-app

# 查看日志
sudo journalctl -u florist-app -f
```

## ⚙️ JVM参数调优（生产环境）

```bash
java -jar \
  -Xms512m \
  -Xmx2048m \
  -XX:MetaspaceSize=128m \
  -XX:MaxMetaspaceSize=512m \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/logs/heapdump.hprof \
  -Dfile.encoding=UTF-8 \
  -Duser.timezone=GMT+08 \
  florist-app/target/florist-app.jar \
  --spring.profiles.active=prod
```

## 📋 配置文件说明

### application.yml（开发环境）
位置：`florist-app/src/main/resources/application.yml`

### application-prod.yml（生产环境）
位置：`florist-app/src/main/resources/application-prod.yml`

生产环境配置示例：

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/florist?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver

  # MyBatis-Plus配置
  mybatis-plus:
    mapper-locations: classpath*:mapper/**/*.xml
    type-aliases-package: com.qy.**.domain,com.tencent.wxcloudrun.model
    configuration:
      map-underscore-to-camel-case: true
      log-impl: org.apache.ibatis.logging.slf4j.Slf4jImpl

# 日志配置
logging:
  level:
    root: INFO
    com.qy: DEBUG
    com.tencent: DEBUG
  file:
    name: /logs/florist-app.log
```

## 🐳 Docker部署（可选）

### 创建Dockerfile

```dockerfile
FROM openjdk:8-jre-alpine
VOLUME /tmp
COPY florist-app/target/florist-app.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
```

### 构建和运行

```bash
# 构建镜像
docker build -t florist-app:1.0.0 .

# 运行容器
docker run -d \
  -p 8080:8080 \
  -v /path/to/config:/config \
  -e SPRING_PROFILES_ACTIVE=prod \
  --name florist-app \
  florist-app:1.0.0
```

## 📊 监控和健康检查

### 添加Spring Boot Actuator（已配置）

访问健康检查端点：
```bash
curl http://localhost:8080/actuator/health
```

### 常用监控端点

- 健康检查: `/actuator/health`
- 应用信息: `/actuator/info`
- Metrics: `/actuator/metrics`

## 🔍 故障排查

### 1. 查看启动日志
```bash
# 如果使用nohup
tail -f app.log

# 如果使用systemd
sudo journalctl -u florist-app -f
```

### 2. 检查端口占用
```bash
# Linux
netstat -tlnp | grep 8080

# Mac
lsof -i :8080
```

### 3. 内存溢出分析
```bash
# 使用jmap查看内存使用
jmap -heap <PID>

# 生成堆转储
jmap -dump:format=b,file=heap.bin <PID>
```

## 📝 部署检查清单

- [ ] 确保Java 8环境已安装
- [ ] 数据库已创建并导入初始数据
- [ ] 配置文件中的数据库连接信息正确
- [ ] 防火墙已开放应用端口
- [ ] 日志目录有写入权限
- [ ] JVM内存参数根据服务器配置调整
- [ ] 已配置应用监控和告警

## 🎯 快速部署命令汇总

```bash
# 1. 打包
cd /path/to/florist-java
mvn clean package -DskipTests

# 2. 部署jar包
scp florist-app/target/florist-app.jar user@server:/opt/florist/

# 3. 远程启动
ssh user@server "cd /opt/florist && nohup java -jar florist-app.jar > app.log 2>&1 &"

# 4. 检查应用状态
curl http://server:8080/actuator/health
```

## 📞 支持

如有问题，请查看日志文件或联系技术支持。
