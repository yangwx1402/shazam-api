package com.shazam.ai.agent.core;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Agent 响应封装
 *
 * @author shazam
 * @since 1.0
 */
public class AgentResponse {

    private String content;
    private String model;
    private TokenUsage tokenUsage;
    private Instant timestamp;
    private Map<String, Object> data;
    private boolean success;
    private String errorMessage;

    /**
     * 推理链路（仅 ReAct Agent 使用）
     */
    private List<ReasoningStep> reasoningTrace = new ArrayList<>();

    /**
     * 推理迭代次数
     */
    private int iterationsUsed;

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

    public boolean hasReasoningTrace() {
        return reasoningTrace != null && !reasoningTrace.isEmpty();
    }

    // --- Getters and Setters ---

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

    public List<ReasoningStep> getReasoningTrace() {
        return reasoningTrace;
    }

    public void setReasoningTrace(List<ReasoningStep> reasoningTrace) {
        this.reasoningTrace = reasoningTrace;
    }

    public void addReasoningStep(ReasoningStep step) {
        this.reasoningTrace.add(step);
    }

    public int getIterationsUsed() {
        return iterationsUsed;
    }

    public void setIterationsUsed(int iterationsUsed) {
        this.iterationsUsed = iterationsUsed;
    }

    /**
     * Token 使用统计
     */
    public static class TokenUsage {

        private int inputTokens;
        private int outputTokens;
        private int totalTokens;

        public TokenUsage() {
        }

        public TokenUsage(int inputTokens, int outputTokens, int totalTokens) {
            this.inputTokens = inputTokens;
            this.outputTokens = outputTokens;
            this.totalTokens = totalTokens;
        }

        public int getInputTokens() {
            return inputTokens;
        }

        public void setInputTokens(int inputTokens) {
            this.inputTokens = inputTokens;
        }

        public int getOutputTokens() {
            return outputTokens;
        }

        public void setOutputTokens(int outputTokens) {
            this.outputTokens = outputTokens;
        }

        public int getTotalTokens() {
            return totalTokens;
        }

        public void setTotalTokens(int totalTokens) {
            this.totalTokens = totalTokens;
        }
    }
}
