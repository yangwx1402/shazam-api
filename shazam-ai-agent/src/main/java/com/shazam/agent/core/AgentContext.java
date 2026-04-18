package com.shazam.agent.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Agent 上下文
 *
 * @author shazam
 * @since 1.0
 */
public class AgentContext {

    /**
     * 会话 ID
     */
    private String sessionId;

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 上下文数据
     */
    private Map<String, Object> data;

    /**
     * 是否启用工具调用
     */
    private boolean toolCallEnabled = true;

    /**
     * 是否启用记忆
     */
    private boolean memoryEnabled = true;

    public AgentContext() {
        this.sessionId = UUID.randomUUID().toString();
        this.data = new HashMap<>();
    }

    public AgentContext(String sessionId) {
        this.sessionId = sessionId;
        this.data = new HashMap<>();
    }

    public AgentContext(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.data = new HashMap<>();
    }

    public static AgentContext create() {
        return new AgentContext();
    }

    public static AgentContext withSession(String sessionId) {
        return new AgentContext(sessionId);
    }

    public static AgentContext withUser(String userId) {
        AgentContext context = new AgentContext();
        context.setUserId(userId);
        return context;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @SuppressWarnings("unchecked")
    public <T> T getData(String key, Class<T> clazz) {
        return (T) this.data.get(key);
    }

    public boolean isToolCallEnabled() {
        return toolCallEnabled;
    }

    public void setToolCallEnabled(boolean toolCallEnabled) {
        this.toolCallEnabled = toolCallEnabled;
    }

    public boolean isMemoryEnabled() {
        return memoryEnabled;
    }

    public void setMemoryEnabled(boolean memoryEnabled) {
        this.memoryEnabled = memoryEnabled;
    }

    @Override
    public String toString() {
        return "AgentContext{" +
                "sessionId='" + sessionId + '\'' +
                ", userId='" + userId + '\'' +
                ", toolCallEnabled=" + toolCallEnabled +
                ", memoryEnabled=" + memoryEnabled +
                '}';
    }
}
