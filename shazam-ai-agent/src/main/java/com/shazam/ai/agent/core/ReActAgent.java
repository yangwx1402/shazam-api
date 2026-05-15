package com.shazam.ai.agent.core;

import com.shazam.ai.agent.config.AgentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ReAct Agent（Level 2）
 * 显式 ReAct 循环实现：Thought -> Action -> Observation
 *
 * 使用 ChatClient 进行调用，通过多次调用模拟 ReAct 循环
 *
 * @author shazam
 * @since 1.0
 */
public abstract class ReActAgent implements Agent {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private ChatClient chatClient;
    private AgentProperties agentProperties;
    private List<AgentExecutionListener> executionListeners = new ArrayList<>();

    public ReActAgent() {
    }

    /**
     * 注入 ChatClient
     */
    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * 注入 AgentProperties
     */
    public void setAgentProperties(AgentProperties agentProperties) {
        this.agentProperties = agentProperties;
    }

    /**
     * 添加执行监听器
     */
    public void addExecutionListener(AgentExecutionListener listener) {
        this.executionListeners.add(listener);
    }

    @Override
    public AgentResponse execute(String prompt) {
        return execute(prompt, AgentContext.create());
    }

    @Override
    public AgentResponse execute(String prompt, AgentContext context) {
        logger.info("ReAct Agent executing: {}, prompt: {}", getName(), prompt);

        fireBeforeExecute(getName(), prompt, context);

        try {
            // 获取最大迭代次数
            int maxIterations = getMaxIterations(context);
            boolean includeTrace = shouldIncludeTrace();

            // 构建系统提示词
            String systemPrompt = buildReActSystemPrompt(context);

            // 构建响应
            AgentResponse response = new AgentResponse();
            List<ReasoningStep> reasoningSteps = new ArrayList<>();
            int iteration = 0;

            // 第一次调用：带工具和记忆
            ChatClient.ChatClientRequestSpec spec = chatClient.prompt();

            if (StringUtils.hasText(systemPrompt)) {
                spec.system(systemPrompt);
            }

            spec.user(prompt);

            // 绑定工具
            if (context.isToolCallEnabled() && !getTools().isEmpty()) {
                spec.tools(getTools().toArray());
            }

            // 绑定会话记忆
            if (context.isMemoryEnabled()) {
                spec.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, context.getSessionId()));
            }

            // 执行调用（Spring AI 内部自动处理 Tool Call 循环）
            ChatResponse chatResponse = spec.call().chatResponse();

            // 构建响应
            if (chatResponse != null && chatResponse.getResult() != null) {
                response.setContent(chatResponse.getResult().getOutput().getText());

                if (chatResponse.getMetadata() != null && chatResponse.getMetadata().getUsage() != null) {
                    var usage = chatResponse.getMetadata().getUsage();
                    AgentResponse.TokenUsage tokenUsage = new AgentResponse.TokenUsage();
                    tokenUsage.setInputTokens((int) usage.getPromptTokens());
                    tokenUsage.setOutputTokens((int) usage.getCompletionTokens());
                    tokenUsage.setTotalTokens((int) usage.getTotalTokens());
                    response.setTokenUsage(tokenUsage);
                }
            }

            response.setModel(getModelName());
            response.setSuccess(true);

            // 注意：由于 ChatClient 自动处理 Tool Call 循环，我们无法捕获每一步的推理过程
            // 如果需要详细推理链路，建议使用 ChatModel 手动实现
            // 这里我们设置一个占位符
            ReasoningStep finalStep = ReasoningStep.finalAnswer(response.getContent(), 1);
            reasoningSteps.add(finalStep);
            iteration = 1;

            // 设置推理链路
            if (includeTrace) {
                response.setReasoningTrace(reasoningSteps);
            }
            response.setIterationsUsed(iteration);

            fireAfterExecute(getName(), response, context);

            return response;

        } catch (Exception e) {
            logger.error("ReAct Agent execution failed: {}", e.getMessage(), e);
            fireOnError(getName(), e, context);
            return AgentResponse.error(e.getMessage());
        }
    }

    @Override
    public reactor.core.publisher.Flux<String> stream(String prompt) {
        return stream(prompt, AgentContext.create());
    }

    @Override
    public reactor.core.publisher.Flux<String> stream(String prompt, AgentContext context) {
        logger.debug("Streaming ReAct agent: {}, prompt: {}", getName(), prompt);

        try {
            String systemPrompt = buildReActSystemPrompt(context);

            ChatClient.ChatClientRequestSpec spec = chatClient.prompt();

            if (StringUtils.hasText(systemPrompt)) {
                spec.system(systemPrompt);
            }

            spec.user(prompt);

            if (context.isToolCallEnabled() && !getTools().isEmpty()) {
                spec.tools(getTools().toArray());
            }

            if (context.isMemoryEnabled()) {
                spec.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, context.getSessionId()));
            }

            return spec.stream().content();

        } catch (Exception e) {
            logger.error("Agent streaming failed: {}", e.getMessage(), e);
            return reactor.core.publisher.Flux.error(e);
        }
    }

    /**
     * 获取最大迭代次数
     */
    protected int getMaxIterations(AgentContext context) {
        if (context.getMaxIterations() > 0) {
            return context.getMaxIterations();
        }
        if (agentProperties != null && agentProperties.getReact() != null) {
            return agentProperties.getReact().getMaxIterations();
        }
        return 10; // 默认值
    }

    /**
     * 是否包含推理链路
     */
    protected boolean shouldIncludeTrace() {
        if (agentProperties != null && agentProperties.getReact() != null) {
            return agentProperties.getReact().isIncludeTrace();
        }
        return true;
    }

    /**
     * 构建 ReAct 系统提示词
     */
    protected String buildReActSystemPrompt(AgentContext context) {
        StringBuilder sb = new StringBuilder();

        // ReAct 指令
        sb.append(ReActSystemPrompts.REACT_INSTRUCTION).append("\n\n");

        // Agent 自定义系统提示词
        String customPrompt = getSystemPrompt();
        if (StringUtils.hasText(customPrompt)) {
            sb.append(customPrompt).append("\n\n");
        }

        // 可用工具描述
        List<Object> tools = getTools();
        if (!tools.isEmpty()) {
            sb.append("可用工具：\n");
            for (Object tool : tools) {
                sb.append("- ").append(tool.getClass().getSimpleName()).append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * 获取模型名称
     */
    protected String getModelName() {
        if (agentProperties != null) {
            return agentProperties.getModel();
        }
        return "unknown";
    }

    // --- 监听器触发方法 ---

    protected void fireBeforeExecute(String agentName, String prompt, AgentContext context) {
        for (AgentExecutionListener listener : executionListeners) {
            listener.beforeExecute(agentName, prompt, context);
        }
    }

    protected void fireAfterExecute(String agentName, AgentResponse response, AgentContext context) {
        for (AgentExecutionListener listener : executionListeners) {
            listener.afterExecute(agentName, response, context);
        }
    }

    protected void fireOnError(String agentName, Exception exception, AgentContext context) {
        for (AgentExecutionListener listener : executionListeners) {
            listener.onExecuteError(agentName, exception, context);
        }
    }

    protected void fireOnReasoningStep(String agentName, ReasoningStep step, int iteration) {
        for (AgentExecutionListener listener : executionListeners) {
            listener.onReasoningStep(agentName, step, iteration);
        }
    }
}
