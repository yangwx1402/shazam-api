package com.shazam.ai.agent.provider;

/**
 * AI 提供商枚举
 *
 * @author shazam
 * @since 1.0
 */
public enum AiProvider {

    /**
     * OpenAI
     */
    OPENAI("openai", "OpenAI"),

    /**
     * Ollama (本地模型)
     */
    OLLAMA("ollama", "Ollama"),

    /**
     * Azure OpenAI
     */
    AZURE("azure", "Azure OpenAI"),

    /**
     * Anthropic
     */
    ANTHROPIC("anthropic", "Anthropic"),

    /**
     * Google AI
     */
    GOOGLE("google", "Google AI"),

    /**
     * AWS Bedrock
     */
    BEDROCK("bedrock", "AWS Bedrock"),

    /**
     * 未知提供商
     */
    UNKNOWN("unknown", "Unknown");

    private final String code;
    private final String displayName;

    AiProvider(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * 根据字符串获取提供商
     *
     * @param code 提供商代码
     * @return 对应的提供商枚举
     */
    public static AiProvider fromString(String code) {
        if (code == null) {
            return UNKNOWN;
        }
        
        for (AiProvider provider : values()) {
            if (provider.code.equalsIgnoreCase(code)) {
                return provider;
            }
        }
        return UNKNOWN;
    }

    /**
     * 判断是否为支持的提供商
     *
     * @return true 如果支持
     */
    public boolean isSupported() {
        return this != UNKNOWN;
    }
}
