package com.shazam.agent.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 内存聊天记忆实现
 * 使用内存存储对话历史，支持最大消息数限制
 *
 * @author shazam
 * @since 1.0
 */
public class InMemoryChatMemory implements ChatMemory {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryChatMemory.class);

    /**
     * 会话存储：sessionId -> 消息队列
     */
    private final Map<String, LinkedBlockingDeque<MemoryMessage>> sessions;

    /**
     * 最大消息数
     */
    private final int maxSize;

    public InMemoryChatMemory() {
        this(100);
    }

    public InMemoryChatMemory(int maxSize) {
        this.sessions = new ConcurrentHashMap<>();
        this.maxSize = maxSize;
    }

    @Override
    public List<MemoryMessage> getHistory(String sessionId) {
        LinkedBlockingDeque<MemoryMessage> queue = sessions.get(sessionId);
        if (queue == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(queue);
    }

    @Override
    public void addMessage(String sessionId, String role, String content) {
        sessions.computeIfAbsent(sessionId, k -> new LinkedBlockingDeque<>(maxSize));
        
        LinkedBlockingDeque<MemoryMessage> queue = sessions.get(sessionId);
        MemoryMessage message = new MemoryMessage(role, content);
        
        // 如果队列已满，移除最早的消息
        if (queue.size() >= maxSize) {
            queue.pollFirst();
            logger.debug("Session {} reached max size, removed oldest message", sessionId);
        }
        
        queue.addLast(message);
        logger.debug("Added {} message to session {}: {}", role, sessionId, content);
    }

    @Override
    public void clear(String sessionId) {
        LinkedBlockingDeque<MemoryMessage> removed = sessions.remove(sessionId);
        if (removed != null) {
            logger.debug("Cleared session {} with {} messages", sessionId, removed.size());
        }
    }

    @Override
    public void clearAll() {
        int totalSessions = sessions.size();
        sessions.clear();
        logger.info("Cleared all sessions: {}", totalSessions);
    }

    @Override
    public int size(String sessionId) {
        LinkedBlockingDeque<MemoryMessage> queue = sessions.get(sessionId);
        return queue != null ? queue.size() : 0;
    }

    /**
     * 获取所有会话 ID
     *
     * @return 会话 ID 列表
     */
    public List<String> getAllSessionIds() {
        return new ArrayList<>(sessions.keySet());
    }

    /**
     * 获取最大消息数限制
     *
     * @return 最大消息数
     */
    public int getMaxSize() {
        return maxSize;
    }
}
