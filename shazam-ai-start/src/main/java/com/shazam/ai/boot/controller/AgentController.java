package com.shazam.ai.boot.controller;

import com.shazam.ai.agent.core.Agent;
import com.shazam.ai.agent.core.AgentContext;
import com.shazam.ai.agent.core.AgentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Agent REST 控制器
 */
@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);
    private final Agent agent;

    public AgentController(@Autowired(required = false) Agent agent) {
        this.agent = agent;
    }

    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody ChatRequest request) {
        logger.info("Chat request: {}", request.getPrompt());
        Map<String, Object> response = new HashMap<>();
        
        if (agent == null) {
            response.put("success", false);
            response.put("message", "Agent 未配置");
            return response;
        }

        try {
            AgentResponse agentResponse = agent.execute(request.getPrompt());
            response.put("success", agentResponse.isSuccess());
            response.put("content", agentResponse.getContent());
            response.put("model", agentResponse.getModel());
            response.put("tokenUsage", agentResponse.getTokenUsage());
        } catch (Exception e) {
            logger.error("Chat failed", e);
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @PostMapping("/chat/context")
    public Map<String, Object> chatWithContext(@RequestBody ChatWithContextRequest request) {
        logger.info("Chat with context: sessionId={}, userId={}", request.getSessionId(), request.getUserId());
        Map<String, Object> response = new HashMap<>();
        
        if (agent == null) {
            response.put("success", false);
            response.put("message", "Agent 未配置");
            return response;
        }

        try {
            AgentContext context = AgentContext.withSession(request.getSessionId());
            if (request.getUserId() != null) {
                context.setUserId(request.getUserId());
            }
            AgentResponse agentResponse = agent.execute(request.getPrompt(), context);
            response.put("success", agentResponse.isSuccess());
            response.put("content", agentResponse.getContent());
            response.put("sessionId", context.getSessionId());
        } catch (Exception e) {
            logger.error("Chat with context failed", e);
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("agent", agent != null ? "available" : "not configured");
        return response;
    }

    public static class ChatRequest {
        private String prompt;
        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
    }

    public static class ChatWithContextRequest extends ChatRequest {
        private String sessionId;
        private String userId;
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
    }
}
