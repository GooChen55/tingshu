# 谷粒随享 · 听书平台后端

完整项目介绍、架构、当前实现进度、MongoDB 示例与启动步骤见 [仓库首页 README](../README.md)。第三方配置字段见 [配置说明](docs/configuration.md)。

基于 Java 17、Spring Boot 3 和 Spring Cloud Alibaba 的听书与音频内容平台后端，采用 Maven 多模块与微服务架构。仓库包含专辑和声音管理、微信登录、用户收听进度、内容搜索、账户充值、订单与微信支付等业务代码，适合学习和实践音频平台的服务拆分与业务开发。

## 技术栈

| 分类 | 技术 |
| --- | --- |
| 开发与构建 | Java 17、Maven、Spring Boot 3.0.5 |
| 微服务 | Spring Cloud 2022.0.2、Spring Cloud Alibaba、Nacos、Gateway、OpenFeign |
| 数据与缓存 | MySQL、MyBatis-Plus、Redis |
| 搜索与消息 | Elasticsearch、RabbitMQ、Kafka |
| 文件与音频 | MinIO、腾讯云 VOD |
| 接口与支付 | Knife4j / OpenAPI 3、微信登录、微信支付 |

## 项目结构

| 模块 | 职责 |
| --- | --- |
| `common` | 公共工具、服务基础能力、日志与 RabbitMQ 支持 |
| `model` | 数据实体、请求与响应对象 |
| `server-gateway` | 微服务网关与认证过滤 |
| `service-client` | 服务间调用的 OpenFeign 接口 |
| `service/service-album` | 分类、专辑、声音、文件上传与云点播 |
| `service/service-user` | 微信登录、用户信息、会员配置与收听进度 |
| `service/service-search` | 内容搜索与详情查询 |
| `service/service-account` | 用户账户与充值 |
| `service/service-order` | 订单模块结构，公开业务接口待实现 |
| `service/service-payment` | 微信支付配置与模块结构，公开业务接口待实现 |
| `service/service-dispatch` | 调度服务模块 |
| `docs` | 接口文档与开发笔记 |

## 本地开发

1. 安装 JDK 17 和 Maven，使用 IDE 打开根目录 `pom.xml`，导入所有模块。
2. 准备所需的 MySQL、Redis、Nacos、RabbitMQ、Elasticsearch 等服务；Kafka、MinIO、腾讯云 VOD 与微信相关配置按所运行模块的需要准备。
3. 修改各服务 `src/main/resources/bootstrap.properties` 中的 Nacos 地址与环境设置。目前配置默认使用 `dev` 环境。
4. 在 Nacos 中准备各服务对应的 YAML 配置，以及共享配置 `common.yaml`；数据库连接、缓存、消息队列及第三方服务凭据需按本地环境配置。
5. 在根目录执行构建命令，再根据需要通过 IDE 启动对应服务与网关。

```bash
mvn clean package -DskipTests
```

上述命令用于构建模块。服务启动还需要数据库结构、Nacos 配置和外部服务；当前仓库不提供一键部署环境。各模块的接口实现进度以源码为准。

## 文档

- [接口文档](docs/api.md)
- [开发与学习笔记](docs/development-notes.md)

## 项目来源

本项目基于尚硅谷“谷粒随享”课程项目进行学习与开发。
