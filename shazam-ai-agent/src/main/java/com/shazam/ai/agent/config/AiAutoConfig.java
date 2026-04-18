package com.shazam.ai.agent.config;

import com.shazam.ai.agent.core.Agent;
import com.shazam.ai.agent.provider.AiProvider;
import com.shazam.ai.agent.provider.AiProviderFactory;
import com.shazam.ai.agent.tool.ToolRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * AI Agent 自动配置类
 *
 * @author shazam
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(AgentProperties.class)
public class AiAutoConfig {

    private static final Logger logger = LoggerFactory.getLogger(AiAutoConfig.class);

    /**
     * AI 提供商工厂
     */
    @Bean
    @ConditionalOnMissingBean
    public AiProviderFactory aiProviderFactory(List<ChatModel> chatModels, AgentProperties properties) {
        logger.info("Initializing AiProviderFactory with provider: {}", properties.getProvider());
        return new AiProviderFactory(chatModels, properties);
    }

    /**
     * ChatClient Builder
     */
    @Bean
    @ConditionalOnMissingBean
    public ChatClient.Builder chatClientBuilder(ChatModel chatModel) {
        logger.info("Initializing ChatClient.Builder");
        return ChatClient.builder(chatModel);
    }

    /**
     * ChatClient
     */
    @Bean
    @ConditionalOnMissingBean
    public ChatClient chatClient(ChatClient.Builder builder) {
        logger.info("Initializing ChatClient");
        return builder.build();
    }

    /**
     * 工具注册中心
     */
    @Bean
    @ConditionalOnProperty(prefix = "shazam.agent.tools", name = "enabled", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    public ToolRegistry toolRegistry(AgentProperties properties) {
        logger.info("Initializing ToolRegistry with packages: {}", properties.getTools().getPackages());
        ToolRegistry registry = new ToolRegistry();
        registry.setScanPackages(properties.getTools().getPackages());
        return registry;
    }
}
