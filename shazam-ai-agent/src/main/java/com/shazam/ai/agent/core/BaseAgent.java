package com.shazam.ai.agent.core;

import com.shazam.ai.agent.config.AgentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

/**
 * Agent 基类实现（Level 1）
 * 使用 ChatClient 托管 Tool Call 循环，Spring AI 内部自动处理工具调用
 *
 * @author shazam
 * @since 1.0
 */
public abstract class BaseAgent implements Agent {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = false)
    protected ChatClient chatClient;

    @Autowired(required = false)
    protected ChatModel chatModel;

    @Autowired(required = false)
    protected AgentProperties agentProperties;

    @Override
    public AgentResponse execute(String prompt) {
        return execute(prompt, AgentContext.create());
    }

    @Override
    public AgentResponse execute(String prompt, AgentContext context) {
        logger.debug("Executing agent: {}, prompt: {}, context: {}", getName(), prompt, context);

        try {
            String systemPrompt = buildSystemPrompt(context);

            // 构建 ChatClient 调用链
            ChatClient.ChatClientRequestSpec spec = chatClient.prompt();

            // 设置系统提示词
            if (StringUtils.hasText(systemPrompt)) {
                spec.system(systemPrompt);
            }

            // 设置用户提示词
            spec.user(prompt);

            // 绑定工具
            if (context.isToolCallEnabled() && !getTools().isEmpty()) {
                spec.tools(getTools().toArray());
            }

            // 绑定会话记忆（通过 advisor 参数传递 conversationId）
            if (context.isMemoryEnabled()) {
                spec.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, context.getSessionId()));
            }

            // 执行调用（Spring AI 内部自动处理 Tool Call 循环）
            ChatResponse chatResponse = spec.call().chatResponse();

            return buildResponse(chatResponse);

        } catch (Exception e) {
            logger.error("Agent execution failed: {}", e.getMessage(), e);
            return AgentResponse.error(e.getMessage());
        }
    }

    @Override
    public Flux<String> stream(String prompt) {
        return stream(prompt, AgentContext.create());
    }

    @Override
    public Flux<String> stream(String prompt, AgentContext context) {
        logger.debug("Streaming agent: {}, prompt: {}", getName(), prompt);

        try {
            String systemPrompt = buildSystemPrompt(context);

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
            return Flux.error(e);
        }
    }

    /**
     * 构建系统提示词
     */
    protected String buildSystemPrompt(AgentContext context) {
        String customPrompt = getSystemPrompt();
        if (!StringUtils.hasText(customPrompt)) {
            return null;
        }
        return customPrompt;
    }

    /**
     * 构建响应对象
     */
    protected AgentResponse buildResponse(ChatResponse chatResponse) {
        AgentResponse response = new AgentResponse();

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

        return response;
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
}
