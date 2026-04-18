package com.shazam.agent.core;

import com.shazam.agent.chat.ChatService;
import com.shazam.agent.config.AgentProperties;
import com.shazam.agent.tool.ToolRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

/**
 * Agent 基类实现
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
    protected ChatService chatService;

    @Autowired(required = false)
    protected ToolRegistry toolRegistry;

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
            // 构建系统提示词
            String systemPrompt = buildSystemPrompt(context);

            // 执行调用
            ChatResponse chatResponse;
            if (StringUtils.hasText(systemPrompt)) {
                chatResponse = chatClient.prompt()
                        .system(systemPrompt)
                        .user(prompt)
                        .call()
                        .chatResponse();
            } else {
                chatResponse = chatClient.prompt()
                        .user(prompt)
                        .call()
                        .chatResponse();
            }

            // 构建响应
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
            if (StringUtils.hasText(systemPrompt)) {
                return chatClient.prompt()
                        .system(systemPrompt)
                        .user(prompt)
                        .stream()
                        .content();
            } else {
                return chatClient.prompt()
                        .user(prompt)
                        .stream()
                        .content();
            }
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
            response.setContent(chatResponse.getResult().getOutput().getContent());

            // 设置 token 使用统计
            if (chatResponse.getMetadata() != null && chatResponse.getMetadata().getUsage() != null) {
                var usage = chatResponse.getMetadata().getUsage();
                AgentResponse.TokenUsage tokenUsage = new AgentResponse.TokenUsage();
                tokenUsage.setInputTokens(usage.getPromptTokens());
                tokenUsage.setOutputTokens(usage.getGenerationTokens());
                tokenUsage.setTotalTokens(usage.getTotalTokens());
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
