package com.shazam.agent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 工具调用配置
     */
    public static class ToolConfig {

        /**
         * 是否启用工具调用
         */
        private boolean enabled = true;

        /**
         * 扫描工具的包路径列表
         */
        private List<String> packages = new ArrayList<>();

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getPackages() {
            return packages;
        }

        public void setPackages(List<String> packages) {
            this.packages = packages;
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
         * 最大记忆条数
         */
        private Integer maxSize = 100;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public Integer getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(Integer maxSize) {
            this.maxSize = maxSize;
        }
    }
}
