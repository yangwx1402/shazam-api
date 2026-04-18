# Spring AI Agent 开发框架设计文档

## 1. 概述

### 1.1 项目目标
基于 Spring AI 构建一个可扩展的 Agent 开发框架，提供统一的 AI 能力抽象层，支持多种模型提供商，便于快速开发 AI 应用。

### 1.2 技术栈
- Spring Boot 3.x + Spring AI 1.x
- JDK 17+
- Maven

### 1.3 核心特性
- 多模型提供商支持（OpenAI、Ollama、Azure 等）
- 注解式工具调用
- 聊天记忆管理
- 结构化输出
- 流式响应支持
- 可观测性集成

## 2. 架构设计

### 2.1 模块结构
```
com.shazam.agent
├── config/           # 配置类
├── core/             # 核心抽象层
├── chat/             # 聊天能力
├── tool/             # 工具调用层
├── memory/           # 记忆管理
├── advisor/          # 增强器
└── provider/         # 模型提供商适配
```

### 2.2 核心组件关系

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
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│                   AiProvider Factory                     │
│         (OpenAI / Ollama / Azure / ...)                 │
└─────────────────────────────────────────────────────────┘
```

## 3. API 设计

### 3.1 Agent 接口
```java
public interface Agent {
    String getName();
    String getSystemPrompt();
    AgentResponse execute(String prompt);
    AgentResponse execute(String prompt, AgentContext context);
    Flux<String> stream(String prompt);
}
```

### 3.2 Tool 注解
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Tool {
    String name() default "";
    String description();
}
```

### 3.3 配置属性
```yaml
shazam:
  agent:
    enabled: true
    provider: openai
    model: gpt-4o-mini
    temperature: 0.7
    tools:
      enabled: true
      packages: com.shazam.agent.tools
    memory:
      enabled: true
      max-size: 100
```

## 4. 扩展点

### 4.1 自定义 Agent
继承 `BaseAgent` 类，实现 `getName()` 和 `getSystemPrompt()` 方法。

### 4.2 自定义 Tool
在方法上添加 `@Tool` 注解，框架自动注册。

### 4.3 自定义 Advisor
实现 `AgentAdvisor` 接口，拦截处理请求/响应。

### 4.4 自定义 ChatMemory
实现 `ChatMemory` 接口，持久化对话历史。

## 5. 使用示例

### 5.1 定义工具
```java
@Component
public class WeatherTool extends BaseTool {
    
    @Tool(description = "查询天气信息")
    public String getWeather(String city) {
        return "晴朗，25°C";
    }
}
```

### 5.2 创建 Agent
```java
@Service
public class AssistantAgent extends BaseAgent {
    
    @Override
    public String getName() {
        return "assistant";
    }
    
    @Override
    public String getSystemPrompt() {
        return "你是一个智能助手";
    }
}
```

### 5.3 使用 Agent
```java
@RestController
public class AgentController {
    
    @Autowired
    private AssistantAgent agent;
    
    @PostMapping("/chat")
    public AgentResponse chat(@RequestBody ChatRequest request) {
        return agent.execute(request.getPrompt());
    }
}
```

## 6. 依赖说明

### 6.1 核心依赖
- spring-boot-starter
- spring-ai-openai-spring-boot-starter
- spring-ai-ollama-spring-boot-starter (可选)

### 6.2 可选依赖
- spring-ai-vector-store (RAG 场景)
- spring-ai-model-evaluation (模型评估)

## 7. 版本信息
- Spring Boot: 3.2.x
- Spring AI: 1.0.0-M4
- JDK: 17+
