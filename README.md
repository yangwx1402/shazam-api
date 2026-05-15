# Shazam AI Agent

基于 Spring AI 1.0.0 GA 构建的通用 Agent 开发框架，支持 ReAct 推理循环。

## 环境要求

- **JDK**: 21+
- **Maven**: 3.6+
- **Spring Boot**: 3.4.5
- **Spring AI**: 1.0.0 GA

## 快速开始

### 1. 配置环境变量

**方式一：使用 .env 文件（推荐）**

```bash
cd shazam-ai-agent
cp .env.example .env
# 编辑 .env 文件，填入你的 API Key
```

**方式二：使用设置脚本**

```bash
cd shazam-ai-agent
source set-env.sh
```

**方式三：直接设置环境变量**

```bash
export OPENAI_API_KEY=sk-your-api-key-here
```

### 2. 启动应用

```bash
cd /Users/yangyong/Documents/works/project/shazam-ai
mvn spring-boot:run -pl shazam-ai-start
```

或者先编译再运行：

```bash
mvn clean package -DskipTests
java -jar shazam-ai-start/target/shazam-ai-start-1.0-SNAPSHOT.jar
```

### 3. 访问页面

启动成功后访问：http://localhost:8080

## 项目结构

```
shazam-ai/
├── shazam-ai-agent/          # Agent 核心模块
│   ├── src/main/java/
│   │   └── com.shazam.ai.agent/
│   │       ├── core/         # Agent 核心抽象
│   │       │   ├── Agent.java              # Agent 接口
│   │       │   ├── BaseAgent.java          # Level 1 Agent（简单场景）
│   │       │   ├── ReActAgent.java         # Level 2 Agent（ReAct 推理）
│   │       │   ├── AgentContext.java       # 运行时上下文
│   │       │   ├── AgentResponse.java      # 响应封装
│   │       │   ├── ReasoningStep.java      # 推理步骤模型
│   │       │   ├── ReActSystemPrompts.java # ReAct 提示词模板
│   │       │   └── AgentExecutionListener.java # 执行监听器
│   │       ├── tools/        # 工具定义
│   │       │   ├── CalculatorTool.java
│   │       │   ├── WeatherTool.java
│   │       │   └── DateTimeTool.java
│   │       ├── config/       # 自动配置
│   │       │   ├── AiAutoConfig.java
│   │       │   └── AgentProperties.java
│   │       ├── chat/         # 聊天服务
│   │       ├── provider/     # AI 提供商适配
│   │       └── example/      # 示例 Agent
│   │           ├── AssistantAgent.java
│   │           └── ReActAssistantAgent.java
│   └── src/main/resources/
│       ├── application.yml
│       └── application-example.yml
├── shazam-ai-wechat/         # 微信公众号 SDK 模块
├── shazam-ai-start/          # 启动模块
│   ├── src/main/java/
│   │   └── com.shazam.ai.boot/
│   │       ├── ApplicationBoot.java       # 启动类
│   │       └── controller/
│   │           └── AgentController.java   # REST API
│   └── src/main/resources/
│       ├── application.yml
│       └── static/            # 前端静态资源
│           ├── index.html
│           ├── css/app.css
│           └── js/app.js
└── pom.xml                   # 父 POM
```

## 核心架构

### 双层 Agent 设计

```
Level 1 — BaseAgent（ChatClient 托管 Tool 循环）
  ┌──────────────────────────────────────────┐
  │  ChatClient.prompt()                     │
  │    .system(systemPrompt)                 │
  │    .user(prompt)                         │
  │    .tools(tool1, tool2, ...)             │
  │    .advisors(memoryAdvisor)              │
  │    .call() / .stream()                   │
  │                                          │
  │  Spring AI 内部自动处理 Tool Call 循环   │
  └──────────────────────────────────────────┘
  适用场景：80% 的标准工具调用需求

Level 2 — ReActAgent（显式 ReAct 循环）
  ┌──────────────────────────────────────────┐
  │  while (iteration < maxIterations) {     │
  │    ChatModel.call(prompt + tools)        │
  │    if (hasToolCalls) {                   │
  │      记录 THOUGHT → ACTION → OBSERVATION │
  │      执行工具，将结果追加到消息历史       │
  │      continue                            │
  │    } else {                              │
  │      记录 FINAL_ANSWER                   │
  │      break                               │
  │    }                                     │
  │  }                                       │
  │  返回完整推理链路                        │
  └──────────────────────────────────────────┘
  适用场景：需要推理过程可观测、复杂多步推理
```

## 功能特性

| 功能 | 说明 |
|------|------|
| **双层 Agent** | BaseAgent（简单）+ ReActAgent（可观测推理） |
| **多模型支持** | OpenAI、Ollama、Azure OpenAI 等 |
| **工具调用** | Spring AI 原生 `@Tool` 注解，自动注册 |
| **会话记忆** | MessageWindowChatMemory，多会话支持 |
| **推理链路** | THOUGHT/ACTION/OBSERVATION 完整记录 |
| **流式输出** | SSE 实时流式响应 |
| **Web 界面** | Vue 3 + Element Plus，开箱即用 |

## Web 页面功能

访问 http://localhost:8080 可使用内置交互页面：

- **Agent 选择**：下拉选择不同 Agent 实现
- **配置开关**：工具调用、记忆、推理链路展示
- **消息展示**：用户/Agent 消息分色显示
- **Token 用量**：显示 input/output/total 消耗
- **推理链路**：可折叠面板展示完整推理过程
- **快捷键**：Ctrl+Enter 发送消息

## REST API

### 获取 Agent 列表

```http
GET /api/agent/agents
```

### 获取配置信息

```http
GET /api/agent/config
```

### 普通对话

```http
POST /api/agent/chat
Content-Type: application/json

{
  "agentName": "assistant",
  "prompt": "今天星期几？",
  "sessionId": "可选，用于多轮对话",
  "toolCallEnabled": true,
  "memoryEnabled": true
}
```

**响应示例：**

```json
{
  "success": true,
  "content": "今天是星期五。",
  "model": "gpt-4o-mini",
  "tokenUsage": {
    "inputTokens": 50,
    "outputTokens": 20,
    "totalTokens": 70
  },
  "sessionId": "abc-123",
  "reasoningTrace": [
    {
      "type": "THOUGHT",
      "content": "需要查询当前日期",
      "iterationNumber": 1
    },
    {
      "type": "ACTION",
      "toolName": "DateTimeTool",
      "toolInput": "",
      "iterationNumber": 1
    },
    {
      "type": "OBSERVATION",
      "content": "2026-05-01",
      "iterationNumber": 1
    }
  ],
  "iterationsUsed": 1
}
```

### 流式对话（SSE）

```http
POST /api/agent/chat/stream
Content-Type: application/json

{
  "agentName": "assistant",
  "prompt": "讲个故事"
}
```

## 开发指南

### 创建自定义 Agent

```java
@Service
public class MyAgent extends BaseAgent {

    @Autowired
    private MyTool myTool;

    @Override
    public String getName() {
        return "my-assistant";
    }

    @Override
    public String getSystemPrompt() {
        return "你是一个专业的助手";
    }

    @Override
    public List<Object> getTools() {
        return List.of(myTool);
    }
}
```

### 创建自定义工具

```java
@Component
public class MyTool {

    @Tool(description = "工具功能描述")
    public String myMethod(@ToolParam(description = "参数描述") String param) {
        // 实现逻辑
        return result;
    }
}
```

### 使用 ReAct Agent

```java
@Service
public class MyReActAgent extends ReActAgent {

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Autowired
    private AgentProperties agentProperties;

    @PostConstruct
    public void init() {
        setChatClient(chatClientBuilder.build());
        setAgentProperties(agentProperties);
    }

    @Override
    public String getName() {
        return "my-react-agent";
    }

    @Override
    public String getSystemPrompt() {
        return "你是一个支持 ReAct 推理的智能助手";
    }

    @Override
    public List<Object> getTools() {
        return List.of(tool1, tool2);
    }
}
```

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

### application.yml 配置项

```yaml
shazam:
  agent:
    enabled: true
    provider: openai
    model: gpt-4o-mini
    temperature: 0.7
    max-tokens: 2048

    # ReAct 推理配置
    react:
      enabled: true
      max-iterations: 10
      include-trace: true

    # 记忆配置
    memory:
      enabled: true
      max-messages: 20

    # 工具调用配置
    tools:
      enabled: true
```

## 示例工具

框架内置了以下示例工具：

| 工具 | 说明 |
|------|------|
| **DateTimeTool** | 获取当前日期、时间、星期几 |
| **CalculatorTool** | 加减乘除、百分比计算 |
| **WeatherTool** | 模拟天气查询（内置部分城市数据） |

## 安全说明

- **API Key 保护**：使用环境变量管理，`.env` 文件已在 `.gitignore` 中排除
- **不要提交敏感信息**：`.env` 文件不会被 Git 跟踪

## 注意事项

1. **JDK 版本**：需要 JDK 21 或更高版本
2. **API Key**：使用前确保通过环境变量配置了正确的 API Key
3. **工具注册**：工具类使用 `@Component` 标注，方法使用 `@Tool` 注解
4. **ReAct Agent 流式**：当前 ReActAgent 不支持流式输出（因需要完整推理循环）

## 许可证

MIT License
