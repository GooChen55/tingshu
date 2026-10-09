# 本地配置说明

本项目使用 Nacos 管理服务配置。以下字段根据当前源码整理，用于准备自己的环境，不是完整的可直接部署配置。

## Nacos 启动配置

各业务服务及网关的 `src/main/resources/bootstrap.properties` 指定：

| 配置项 | 当前约定 |
| --- | --- |
| `spring.application.name` | 服务名，例如 `service-album` |
| `spring.profiles.active` | `dev` |
| `spring.cloud.nacos.discovery.server-addr` | 示例地址 `192.168.200.6:8848`，需按环境覆盖 |
| `spring.cloud.nacos.config.server-addr` | 同上 |
| `spring.cloud.nacos.config.prefix` | `${spring.application.name}` |
| `spring.cloud.nacos.config.file-extension` | `yaml` |
| `spring.cloud.nacos.config.shared-configs[0].data-id` | `common.yaml` |

在对应 Nacos 环境准备 `common.yaml` 与 `<服务名>-dev.yaml`。如调整 namespace 或 group，需保持客户端与配置中心一致。除源码中已有的端口外，各服务的实际端口需在配置中心设置；网关需配置到对应服务的路由。

## 基础设施

| 依赖 | 配置方向 |
| --- | --- |
| MySQL | `spring.datasource`，连接信息与匹配实体、Mapper 的表结构 |
| Redis | Spring Boot 3 的 `spring.data.redis`，缓存及登录状态 |
| RabbitMQ | `spring.rabbitmq`，消息连接及账户初始化业务 |
| Elasticsearch | 搜索服务所用客户端的连接配置，索引及映射需匹配源码 |
| MongoDB | `spring.data.mongodb`，用户收听进度及独立示例 |

具体客户端的连接方式以各模块依赖及配置类为准。数据库初始化脚本与配置中心导出目前未包含在仓库中。

## 第三方配置字段

以下字段来自源码中的 `@ConfigurationProperties` 绑定类。请在自己管理的配置中心或环境中提供值，避免将真实凭据提交到 Git。

| 模块 | 前缀 | 字段 |
| --- | --- | --- |
| 专辑 / MinIO | `minio` | `endpointUrl`、`accessKey`、`secreKey`、`bucketName` |
| 专辑 / 腾讯云 VOD | `vod` | `appId`、`secretId`、`secretKey`、`region`、`tempPath` |
| 支付 / 微信支付 | `wechat.v3pay` | `appid`、`merchantId`、`privateKeyPath`、`merchantSerialNumber`、`apiV3key`、`notifyUrl` |

MinIO 的 `secreKey` 为当前源码中的字段拼写，配置时需要与之匹配。`vod.tempPath` 应为进程可写的临时目录。微信支付私钥路径指向本地证书文件，证书与密钥不应进入仓库。

微信登录配置与其他业务依赖请结合用户服务所引用的配置类补充。支付配置类在启动时会创建微信支付客户端，即使业务接口仍未完成，启动支付服务也需要有效配置。

## 验证顺序

1. 确认 Nacos 可访问且 Data ID、环境、namespace、group 匹配。
2. 确认数据库、缓存、消息队列与搜索服务可连接。
3. 启动需要的业务服务，查看启动日志并确认 Nacos 注册状态。
4. 配置网关路由后启动网关，再验证接口与登录流程。
5. 对需要第三方凭据的上传、微信登录和支付能力分别验证。

已有集成测试需要外部环境。`-DskipTests` 只跳过测试执行，构建通过不表示服务已经完成运行验证。
