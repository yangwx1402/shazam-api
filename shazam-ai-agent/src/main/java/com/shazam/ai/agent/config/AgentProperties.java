package com.shazam.ai.agent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Agent 配置属性
 *
 * @author shazam
 * @since 1.0
 */
@ConfigurationProperties(prefix = "shazam.agent")
public class AgentProperties {

    /**
     * 是否启用 Agent
     */
    private boolean enabled = true;

    /**
     * AI 提供商类型：openai, ollama, azure
     */
    private String provider = "openai";

    /**
     * 模型名称
     */
    private String model = "gpt-4o-mini";

    /**
     * 温度参数 (0.0 - 2.0)
     */
    private Double temperature = 0.7;

    /**
     * 最大 token 数
     */
    private Integer maxTokens = 2048;

    /**
     * 工具调用配置
     */
    private ToolConfig tools = new ToolConfig();

    /**
     * 记忆配置
     */
    private MemoryConfig memory = new MemoryConfig();

    /**
     * ReAct 推理配置
     */
    private ReactConfig react = new ReactConfig();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public ToolConfig getTools() {
        return tools;
    }

    public void setTools(ToolConfig tools) {
        this.tools = tools;
    }

    public MemoryConfig getMemory() {
        return memory;
    }

    public void setMemory(MemoryConfig memory) {
        this.memory = memory;
    }

    public ReactConfig getReact() {
        return react;
    }

    public void setReact(ReactConfig react) {
        this.react = react;
    }

    /**
     * 工具调用配置
     */
    public static class ToolConfig {

        /**
         * 是否启用工具调用
         */
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    /**
     * 记忆配置
     */
    public static class MemoryConfig {

        /**
         * 是否启用聊天记忆
         */
        private boolean enabled = true;

        /**
         * 消息窗口大小（最大保留消息条数）
         */
        private int maxMessages = 20;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaxMessages() {
            return maxMessages;
        }

        public void setMaxMessages(int maxMessages) {
            this.maxMessages = maxMessages;
        }
    }

    /**
     * ReAct 推理配置
     */
    public static class ReactConfig {

        /**
         * 是否启用 ReAct 推理
         */
        private boolean enabled = true;

        /**
         * 最大推理迭代次数
         */
        private int maxIterations = 10;

        /**
         * 是否在响应中包含推理链路
         */
        private boolean includeTrace = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getMaxIterations() {
            return maxIterations;
        }

        public void setMaxIterations(int maxIterations) {
            this.maxIterations = maxIterations;
        }

        public boolean isIncludeTrace() {
            return includeTrace;
        }

        public void setIncludeTrace(boolean includeTrace) {
            this.includeTrace = includeTrace;
        }
    }
}
