package com.shazam.agent.core;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Agent 响应封装
 *
 * @author shazam
 * @since 1.0
 */
public class AgentResponse {

    /**
     * 响应内容
     */
    private String content;

    /**
     * 使用的模型
     */
    private String model;

    /**
     * 使用的 token 数
     */
    private TokenUsage tokenUsage;

    /**
     * 响应时间戳
     */
    private Instant timestamp;

    /**
     * 额外数据
     */
    private Map<String, Object> data;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    public AgentResponse() {
        this.timestamp = Instant.now();
        this.data = new HashMap<>();
        this.success = true;
    }

    public AgentResponse(String content) {
        this();
        this.content = content;
    }

    public static AgentResponse success(String content) {
        return new AgentResponse(content);
    }

    public static AgentResponse error(String errorMessage) {
        AgentResponse response = new AgentResponse();
        response.setSuccess(false);
        response.setErrorMessage(errorMessage);
        return response;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TokenUsage getTokenUsage() {
        return tokenUsage;
    }

    public void setTokenUsage(TokenUsage tokenUsage) {
        this.tokenUsage = tokenUsage;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void putData(String key, Object value) {
        this.data.put(key, value);
    }

    public Object getData(String key) {
        return this.data.get(key);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Token 使用统计
     */
    public static class TokenUsage {
        
        /**
         * 输入 token 数
         */
        private Long inputTokens;

        /**
         * 输出 token 数
         */
        private Long outputTokens;

        /**
         * 总 token 数
         */
        private Long totalTokens;

        public TokenUsage() {
        }

        public TokenUsage(Long inputTokens, Long outputTokens, Long totalTokens) {
            this.inputTokens = inputTokens;
            this.outputTokens = outputTokens;
            this.totalTokens = totalTokens;
        }

        public Long getInputTokens() {
            return inputTokens;
        }

        public void setInputTokens(Long inputTokens) {
            this.inputTokens = inputTokens;
        }

        public Long getOutputTokens() {
            return outputTokens;
        }

        public void setOutputTokens(Long outputTokens) {
            this.outputTokens = outputTokens;
        }

        public Long getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(Long totalTokens) {
            this.totalTokens = totalTokens;
        }
    }
}
