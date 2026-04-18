package com.shazam.agent.provider;

import com.shazam.agent.config.AgentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AI 提供商工厂
 * 根据配置创建和管理不同提供商的 ChatModel 实例
 *
 * @author shazam
 * @since 1.0
 */
public class AiProviderFactory {

    private static final Logger logger = LoggerFactory.getLogger(AiProviderFactory.class);

    /**
     * 已注册的 ChatModel 列表
     */
    private final List<ChatModel> chatModels;

    /**
     * Agent 配置属性
     */
    private final AgentProperties properties;

    /**
     * 缓存的提供商实例
     */
    private final Map<AiProvider, AiProviderWrapper> providerCache;

    public AiProviderFactory(List<ChatModel> chatModels, AgentProperties properties) {
        this.chatModels = chatModels;
        this.properties = properties;
        this.providerCache = new ConcurrentHashMap<>();
    }

    /**
     * 获取提供商实例
     *
     * @param providerType 提供商类型
     * @return 提供商包装器
     */
    public AiProviderWrapper getProvider(AiProvider providerType) {
        return providerCache.computeIfAbsent(providerType, this::createProvider);
    }

    /**
     * 获取当前配置的提供商
     *
     * @return 提供商包装器
     */
    public AiProviderWrapper getCurrentProvider() {
        AiProvider providerType = AiProvider.fromString(properties.getProvider());
        return getProvider(providerType);
    }

    /**
     * 创建提供商实例
     */
    private AiProviderWrapper createProvider(AiProvider providerType) {
        ChatModel chatModel = findChatModel(providerType);
        
        if (chatModel == null) {
            logger.warn("No ChatModel found for provider: {}", providerType);
        } else {
            logger.info("Created provider: {} with model: {}", providerType, properties.getModel());
        }
        
        return new AiProviderWrapper(providerType, chatModel, properties);
    }

    /**
     * 查找匹配的 ChatModel
     */
    private ChatModel findChatModel(AiProvider providerType) {
        if (chatModels == null || chatModels.isEmpty()) {
            return null;
        }

        for (ChatModel model : chatModels) {
            if (isModelMatch(model, providerType)) {
                return model;
            }
        }

        // 如果没有找到匹配的，返回第一个可用的 ChatModel
        return chatModels.get(0);
    }

    /**
     * 判断 ChatModel 是否匹配提供商类型
     */
    private boolean isModelMatch(ChatModel model, AiProvider providerType) {
        String modelClassName = model.getClass().getSimpleName().toLowerCase();
        
        return switch (providerType) {
            case OPENAI -> modelClassName.contains("openai") && !modelClassName.contains("azure");
            case OLLAMA -> modelClassName.contains("ollama");
            case AZURE -> modelClassName.contains("azure") && modelClassName.contains("openai");
            case ANTHROPIC -> modelClassName.contains("anthropic");
            case GOOGLE -> modelClassName.contains("google") || modelClassName.contains("vertex");
            case BEDROCK -> modelClassName.contains("bedrock");
            default -> false;
        };
    }

    /**
     * 获取所有可用的提供商类型
     *
     * @return 提供商类型列表
     */
    public List<AiProvider> getAvailableProviders() {
        return chatModels.stream()
                .map(this::detectProviderType)
                .filter(p -> p != AiProvider.UNKNOWN)
                .distinct()
                .toList();
    }

    /**
     * 检测 ChatModel 的提供商类型
     */
    private AiProvider detectProviderType(ChatModel model) {
        String className = model.getClass().getName().toLowerCase();
        
        if (className.contains("openai") && !className.contains("azure")) {
            return AiProvider.OPENAI;
        } else if (className.contains("ollama")) {
            return AiProvider.OLLAMA;
        } else if (className.contains("azure") && className.contains("openai")) {
            return AiProvider.AZURE;
        } else if (className.contains("anthropic")) {
            return AiProvider.ANTHROPIC;
        } else if (className.contains("google") || className.contains("vertex")) {
            return AiProvider.GOOGLE;
        } else if (className.contains("bedrock")) {
            return AiProvider.BEDROCK;
        }
        
        return AiProvider.UNKNOWN;
    }

    /**
     * 提供商包装器
     */
    public static class AiProviderWrapper {
        
        private final AiProvider providerType;
        private final ChatModel chatModel;
        private final AgentProperties properties;

        public AiProviderWrapper(AiProvider providerType, ChatModel chatModel, AgentProperties properties) {
            this.providerType = providerType;
            this.chatModel = chatModel;
            this.properties = properties;
        }

        public AiProvider getProviderType() {
            return providerType;
        }

        public ChatModel getChatModel() {
            return chatModel;
        }

        public AgentProperties getProperties() {
            return properties;
        }

        public boolean isAvailable() {
            return chatModel != null;
        }

        @Override
        public String toString() {
            return "AiProviderWrapper{" +
                    "providerType=" + providerType +
                    ", available=" + isAvailable() +
                    ", model='" + properties.getModel() + '\'' +
                    '}';
        }
    }
}
