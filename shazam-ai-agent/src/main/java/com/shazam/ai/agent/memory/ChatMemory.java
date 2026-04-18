package com.shazam.ai.agent.memory;

import java.util.List;

/**
 * 聊天记忆接口
 * 用于存储和检索对话历史
 *
 * @author shazam
 * @since 1.0
 */
public interface ChatMemory {

    /**
     * 获取会话历史
     *
     * @param sessionId 会话 ID
     * @return 历史消息列表
     */
    List<MemoryMessage> getHistory(String sessionId);

    /**
     * 添加消息到会话
     *
     * @param sessionId 会话 ID
     * @param role      角色（user/assistant）
     * @param content   消息内容
     */
    void addMessage(String sessionId, String role, String content);

    /**
     * 添加用户消息
     *
     * @param sessionId 会话 ID
     * @param content   消息内容
     */
    default void addUserMessage(String sessionId, String content) {
        addMessage(sessionId, "user", content);
    }

    /**
     * 添加助手消息
     *
     * @param sessionId 会话 ID
     * @param content   消息内容
     */
    default void addAssistantMessage(String sessionId, String content) {
        addMessage(sessionId, "assistant", content);
    }

    /**
     * 清除会话历史
     *
     * @param sessionId 会话 ID
     */
    void clear(String sessionId);

    /**
     * 清除所有历史
     */
    void clearAll();

    /**
     * 获取记忆中的消息数量
     *
     * @param sessionId 会话 ID
     * @return 消息数量
     */
    int size(String sessionId);

    /**
     * 记忆消息
     */
    class MemoryMessage {
        
        private final String role;
        private final String content;
        private final long timestamp;

        public MemoryMessage(String role, String content) {
            this(role, content, System.currentTimeMillis());
        }

        public MemoryMessage(String role, String content, long timestamp) {
            this.role = role;
            this.content = content;
            this.timestamp = timestamp;
        }

        public String getRole() {
            return role;
        }

        public String getContent() {
            return content;
        }

        public long getTimestamp() {
            return timestamp;
        }

        @Override
        public String toString() {
            return "MemoryMessage{" +
                    "role='" + role + '\'' +
                    ", content='" + content + '\'' +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }
}
