# 核心数据管理
1.转JSON框架jackson使用 @JsonInclude(JsonInclude.Include.NON_NULL) 不为空属性会转JSON


#认证状态校验
1.自定义认证注解
2.通过AOP对注解进行增强

#微信一键登录
1.对接微信服务获取微信账户唯一标识（验证账户合法性）
2.将微信账户跟本地用户绑定
3.如果首次登录 采用RabbitMQ异步初始化账户记录 可靠性消息


1.安装claudecode
Windows（PowerShell 管理员） powershell命令行，执行命令

# 替换成你自己的端口，如 7890、10809  开启科学上网
$env:HTTP_PROXY="http://127.0.0.1:7890"
$env:HTTPS_PROXY="http://127.0.0.1:7890"
irm https://claude.ai/install.ps1 | iex

2.安装ccswitch 申请api秘钥  在ccswtich中添加模型 deepseek-v4-pro

3.在项目根目录下 通过powershell 执行 `claude` 命令


4.在claude中 执行命令 `/init` 产生CLAUDE.md文件 ： 记录项目架构、命令、代码规范，提升 AI 输出质量。

5.需求完成后，通过对话提交代码。‘好了，提交代码’

6.新开需求，建议执行`/clear`命令，新开会话

shift+tab 切换模式
进入计划模式： plan mode on
    先梳理方案、步骤、文件改动清单，不会直接修改代码 / 文件；
    你确认方案无误后，再让它执行实际修改；
    适合复杂重构、多文件改动、大型需求，避免一次性改错。

线程池：七项参数
1.核心线程数
2.最大线程数 如果大于核心线程数，说明存在非核心线程
3.空闲时间
4.时间单位
5.阻塞队列（用于暂存任务，核心线程占满后，新任务进入队列）
6.线程工厂（产生线程）
7.拒绝策略（当核心线程数已满时，阻塞队列满，达到最大线程数，新任务拒绝执行）

拒绝策略：
    1.默认拒绝策略 抛出异常且拒绝任务，造成任务丢失
    2.静默方式丢弃新提交任务，不会抛出异常
    3.静默方式丢弃阻塞队列队首任务，并提交当前任务到线程池
    4.返回给调用者线程执行 任务不会丢失


日志框架：规范（顶级接口slf4） 日志框架（log4j, log4j2, logback）
    日志级别 从低到高： debug < info < warn < error
    全局日志级别设置为：warn 日志输出级别高于等于warn的日志

开源日志解决方案：https://gitee.com/plumeorg/plumelog

建议安装的skills:
Superpowers 是给 AI 编码 Agent（Claude Code、Cursor、OpenCode 等）用的一套标准化技能包，
安装后能强制 AI 按「先澄清→再计划→再执行→复盘」的流程工作，显著提升交付质量与稳定性。
下面分平台给出完整安装步骤。
在claude code命令行执行命令：
# 直接安装官方版
`/plugin install superpowers@claude-plugins-official`
安装成功后通过加载
`reload-plugins`


幂等性方案：
  1. 采用Redis提供 set k v ex nx 命令 当Key不存在才能写入成功
  2. 采用MySQL数据库提供的唯一索引 ，当插入数据时，如果数据已存在，则返回错误
