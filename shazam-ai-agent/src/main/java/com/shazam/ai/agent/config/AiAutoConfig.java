package com.shazam.ai.agent.config;

import com.shazam.ai.agent.provider.AiProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
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
     * 聊天记忆存储仓库
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "shazam.agent.memory", name = "enabled", havingValue = "true", matchIfMissing = true)
    public ChatMemoryRepository chatMemoryRepository() {
        logger.info("Initializing InMemoryChatMemoryRepository");
        return new InMemoryChatMemoryRepository();
    }

    /**
     * 聊天记忆（消息窗口策略）
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "shazam.agent.memory", name = "enabled", havingValue = "true", matchIfMissing = true)
    public ChatMemory chatMemory(ChatMemoryRepository repository, AgentProperties properties) {
        int maxMessages = properties.getMemory().getMaxMessages();
        logger.info("Initializing MessageWindowChatMemory with maxMessages: {}", maxMessages);
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)
                .maxMessages(maxMessages)
                .build();
    }

    /**
     * 消息记忆 Advisor
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "shazam.agent.memory", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MessageChatMemoryAdvisor messageChatMemoryAdvisor(ChatMemory chatMemory) {
        logger.info("Initializing MessageChatMemoryAdvisor");
        return MessageChatMemoryAdvisor.builder(chatMemory).build();
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
     * ChatClient（绑定默认 Memory Advisor）
     */
    @Bean
    @ConditionalOnMissingBean
    public ChatClient chatClient(ChatClient.Builder builder,
                                 MessageChatMemoryAdvisor memoryChatMemoryAdvisor,
                                 AgentProperties properties) {
        logger.info("Initializing ChatClient with memory advisor");
        if (properties.getMemory().isEnabled()) {
            builder.defaultAdvisors(memoryChatMemoryAdvisor);
        }
        return builder.build();
    }
}
