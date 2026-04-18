# Spring AI Agent Framework

基于 Spring AI 构建的可扩展 Agent 开发框架。

## 环境要求

- **JDK**: 17+
- **Maven**: 3.6+
- **Spring Boot**: 3.2.x
- **Spring AI**: 1.0.0-M4

## 快速开始

### 1. 添加依赖

```xml
<dependency>
    <groupId>org.example</groupId>
    <artifactId>shazam-ai-agent</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### 2. 配置环境变量

**重要**: API Key 等敏感信息通过环境变量配置，不会被提交到 Git。

#### 方式一：使用 .env 文件（推荐）

```bash
# 复制模板文件
cp .env.example .env

# 编辑 .env 文件，填入你的 API Key
# OPENAI_API_KEY=sk-your-actual-key-here
```

#### 方式二：直接设置环境变量

**macOS/Linux:**
```bash
export OPENAI_API_KEY=sk-your-api-key-here
export OPENAI_MODEL=gpt-4o-mini
```

**Windows:**
```cmd
set OPENAI_API_KEY=sk-your-api-key-here
set OPENAI_MODEL=gpt-4o-mini
```

#### 方式三：使用设置脚本

**macOS/Linux:**
```bash
source set-env.sh
```

**Windows:**
```cmd
set-env.bat
```

### 3. 配置文件

`application.yml` 已配置为从环境变量读取：

```yaml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}  # 从环境变量读取
      chat:
        options:
          model: ${OPENAI_MODEL:gpt-4o-mini}
          temperature: ${OPENAI_TEMPERATURE:0.7}

shazam:
  agent:
    enabled: true
    provider: ${AGENT_PROVIDER:openai}
    model: ${AGENT_MODEL:gpt-4o-mini}
    temperature: ${AGENT_TEMPERATURE:0.7}
```

### 4. 创建自定义 Agent

```java
@Service
public class MyAgent extends BaseAgent {

    @Override
    public String getName() {
        return "my-assistant";
    }

    @Override
    public String getSystemPrompt() {
        return "你是一个专业的助手";
    }
}
```

### 5. 创建工具

```java
@Component
public class MyTool extends BaseTool {

    @Override
    public String getToolName() {
        return "MyTool";
    }

    @Tool(description = "执行某个功能")
    public String doSomething(String param) {
        return "结果";
    }
}
```

### 6. 使用 Agent

```java
@RestController
public class AgentController {

    @Autowired
    private MyAgent agent;

    @PostMapping("/chat")
    public AgentResponse chat(@RequestBody ChatRequest request) {
        return agent.execute(request.getPrompt());
    }
}
```

### 7. 运行应用

```bash
# macOS/Linux
source set-env.sh
mvn spring-boot:run

# Windows
set-env.bat
mvn spring-boot:run
```

## 核心组件

### 架构概览

```
┌─────────────────────────────────────────────────────────┐
│                      Application                         │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                    Agent (Interface)                     │
│                   BaseAgent (Impl)                       │
└─────────────────────────────────────────────────────────┘
                            │
            ┌───────────────┼───────────────┐
            ▼               ▼               ▼
┌─────────────────┐ ┌─────────────┐ ┌─────────────────┐
│   ChatService   │ │  ToolRegistry│ │  ChatMemory     │
└─────────────────┘ └─────────────┘ └─────────────────┘
```

### 包结构

```
com.shazam.agent
├── config/           # 配置类
│   ├── AiAutoConfig.java
│   └── AgentProperties.java
├── core/             # 核心抽象层
│   ├── Agent.java
│   ├── BaseAgent.java
│   ├── AgentContext.java
│   └── AgentResponse.java
├── chat/             # 聊天能力
│   ├── ChatService.java
│   └── ChatServiceImpl.java
├── tool/             # 工具调用层
│   ├── Tool.java
│   ├── ToolRegistry.java
│   └── BaseTool.java
├── memory/           # 记忆管理
│   ├── ChatMemory.java
│   └── InMemoryChatMemory.java
├── advisor/          # 增强器（预留）
└── provider/         # 模型提供商适配
    ├── AiProvider.java
    └── AiProviderFactory.java
```

## 功能特性

| 功能 | 说明 |
|------|------|
| **多模型支持** | 支持 OpenAI、Ollama、Azure OpenAI 等 |
| **工具调用** | 注解式工具注册，AI 自动调用 |
| **聊天记忆** | 支持对话历史管理 |
| **结构化输出** | 支持将 AI 响应映射为 POJO |
| **流式响应** | 支持 SSE 流式输出 |

## 配置说明

### 环境变量

| 变量名 | 必填 | 默认值 | 说明 |
|--------|------|--------|------|
| `OPENAI_API_KEY` | 是 | - | OpenAI API Key |
| `OPENAI_BASE_URL` | 否 | https://api.openai.com/v1 | API 基础 URL |
| `OPENAI_MODEL` | 否 | gpt-4o-mini | 模型名称 |
| `OPENAI_TEMPERATURE` | 否 | 0.7 | 温度参数 |
| `AGENT_PROVIDER` | 否 | openai | 提供商类型 |
| `AGENT_MODEL` | 否 | gpt-4o-mini | Agent 模型 |
| `AGENT_TEMPERATURE` | 否 | 0.7 | Agent 温度 |
| `AGENT_MAX_TOKENS` | 否 | 2048 | 最大 token 数 |
| `SERVER_PORT` | 否 | 8080 | 服务端口 |

### AgentProperties 配置项

| 配置项 | 类型 | 默认值 | 说明 |
|--------|------|--------|------|
| `shazam.agent.enabled` | boolean | true | 是否启用 Agent |
| `shazam.agent.provider` | string | openai | 提供商类型 |
| `shazam.agent.model` | string | gpt-4o-mini | 模型名称 |
| `shazam.agent.temperature` | double | 0.7 | 温度参数 |
| `shazam.agent.max-tokens` | int | 2048 | 最大 token 数 |
| `shazam.agent.tools.enabled` | boolean | true | 是否启用工具 |
| `shazam.agent.tools.packages` | list | [] | 工具扫描包路径 |
| `shazam.agent.memory.enabled` | boolean | true | 是否启用记忆 |
| `shazam.agent.memory.max-size` | int | 100 | 最大记忆条数 |

## 示例工具

框架内置了以下示例工具：

- **DateTimeTool**: 获取当前日期、时间、星期
- **CalculatorTool**: 基础数学计算
- **WeatherTool**: 天气查询（示例）

## 开发指南

### 1. 创建自定义工具

```java
@Component
public class CustomTool extends BaseTool {

    @Override
    public String getToolName() {
        return "CustomTool";
    }

    @Override
    public String getToolDescription() {
        return "自定义工具描述";
    }

    @Tool(description = "工具方法描述")
    public String execute(String param1, Integer param2) {
        // 实现逻辑
        return result;
    }
}
```

### 2. 创建自定义 Agent

```java
@Service
public class CustomAgent extends BaseAgent {

    @Override
    public String getName() {
        return "custom-agent";
    }

    @Override
    public String getSystemPrompt() {
        return "你是一个特定领域的专家助手";
    }

    // 可以重写其他方法来自定义行为
}
```

### 3. 使用上下文

```java
AgentContext context = AgentContext.withUser("user-123");
context.putData("userName", "张三");
context.setToolCallEnabled(true);

AgentResponse response = agent.execute("你好", context);
```

## 安全说明

### API Key 保护

本项目使用**环境变量**方式管理 API Key 等敏感信息：

1. ✅ **API Key 不提交到 Git** - `.env` 文件已在 `.gitignore` 中排除
2. ✅ **使用模板文件** - 复制 `.env.example` 为 `.env` 后配置
3. ✅ **支持多种设置方式** - 环境变量、.env 文件、设置脚本

### 文件说明

| 文件 | 说明 | 是否提交 |
|------|------|----------|
| `.env.example` | 环境变量模板 | ✅ 是 |
| `.env` | 实际环境变量配置 | ❌ 否 |
| `application.yml` | 应用配置（使用环境变量） | ✅ 是 |
| `application-example.yml` | 配置示例 | ✅ 是 |

## 注意事项

1. **JDK 版本**: 本项目使用 Spring Boot 3.x + Spring AI 1.x，需要 JDK 17 或更高版本
2. **API Key**: 使用前请确保通过环境变量配置了正确的 AI 服务提供商 API Key
3. **工具扫描**: 确保工具类在配置的扫描包路径下，并标注了 `@Component`
4. **Git 安全**: 不要将 `.env` 文件或包含真实 API Key 的配置文件提交到 Git

## 许可证

MIT License
