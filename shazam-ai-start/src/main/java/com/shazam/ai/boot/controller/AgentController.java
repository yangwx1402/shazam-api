package com.shazam.ai.boot.controller;

import com.shazam.ai.agent.config.AgentProperties;
import com.shazam.ai.agent.core.Agent;
import com.shazam.ai.agent.core.AgentContext;
import com.shazam.ai.agent.core.AgentResponse;
import com.shazam.ai.agent.core.ReasoningStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Agent REST 控制器
 */
@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

    @Autowired(required = false)
    private List<Agent> agents;

    @Autowired(required = false)
    private AgentProperties agentProperties;

    /**
     * 获取可用的 Agent 列表
     */
    @GetMapping("/agents")
    public Map<String, Object> getAgents() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> agentList = new ArrayList<>();

        if (agents != null) {
            for (Agent agent : agents) {
                Map<String, String> info = new HashMap<>();
                info.put("name", agent.getName());
                info.put("systemPrompt", agent.getSystemPrompt() != null ? agent.getSystemPrompt().substring(0, Math.min(50, agent.getSystemPrompt().length())) + "..." : "");
                agentList.add(info);
            }
        }

        response.put("success", true);
        response.put("agents", agentList);
        return response;
    }

    /**
     * 获取配置信息
     */
    @GetMapping("/config")
    public Map<String, Object> getConfig() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        if (agentProperties != null) {
            Map<String, Object> config = new HashMap<>();
            config.put("provider", agentProperties.getProvider());
            config.put("model", agentProperties.getModel());
            config.put("reactEnabled", agentProperties.getReact().isEnabled());
            config.put("maxIterations", agentProperties.getReact().getMaxIterations());
            config.put("memoryEnabled", agentProperties.getMemory().isEnabled());
            config.put("maxMessages", agentProperties.getMemory().getMaxMessages());
            response.put("config", config);
        }
        return response;
    }

    /**
     * 普通对话
     */
    @PostMapping("/chat")
    public Map<String, Object> chat(@RequestBody ChatRequest request) {
        logger.info("Chat request: agent={}, prompt={}", request.getAgentName(), request.getPrompt());
        Map<String, Object> response = new HashMap<>();

        Agent agent = findAgent(request.getAgentName());
        if (agent == null) {
            response.put("success", false);
            response.put("message", "Agent 未找到: " + request.getAgentName());
            return response;
        }

        try {
            AgentContext context = buildContext(request);
            AgentResponse agentResponse = agent.execute(request.getPrompt(), context);

            response.put("success", agentResponse.isSuccess());
            response.put("content", agentResponse.getContent());
            response.put("model", agentResponse.getModel());
            response.put("tokenUsage", agentResponse.getTokenUsage());
            response.put("sessionId", context.getSessionId());
            response.put("iterationsUsed", agentResponse.getIterationsUsed());

            // 推理链路
            if (agentResponse.hasReasoningTrace()) {
                response.put("reasoningTrace", agentResponse.getReasoningTrace());
            }

        } catch (Exception e) {
            logger.error("Chat failed", e);
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    /**
     * 流式对话（SSE）
     */
    @PostMapping("/chat/stream")
    public SseEmitter chatStream(@RequestBody ChatRequest request) {
        logger.info("Stream chat request: agent={}", request.getAgentName());
        SseEmitter emitter = new SseEmitter(60_000L);

        Agent agent = findAgent(request.getAgentName());
        if (agent == null) {
            try {
                emitter.send(SseEmitter.event().name("error").data("Agent 未找到"));
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
            return emitter;
        }

        AgentContext context = buildContext(request);

        // 异步流式执行
        CompletableFuture.runAsync(() -> {
            try {
                agent.stream(request.getPrompt(), context).subscribe(
                        content -> {
                            try {
                                emitter.send(SseEmitter.event().name("message").data(content));
                            } catch (IOException e) {
                                emitter.completeWithError(e);
                            }
                        },
                        error -> emitter.completeWithError(error),
                        () -> emitter.complete()
                );
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    /**
     * 查找 Agent
     */
    private Agent findAgent(String name) {
        if (agents == null) return null;
        return agents.stream()
                .filter(a -> a.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * 构建 AgentContext
     */
    private AgentContext buildContext(ChatRequest request) {
        AgentContext context;
        if (request.getSessionId() != null && !request.getSessionId().isEmpty()) {
            context = AgentContext.withSession(request.getSessionId());
        } else {
            context = AgentContext.create();
        }

        if (request.getUserId() != null) {
            context.setUserId(request.getUserId());
        }

        context.setToolCallEnabled(request.isToolCallEnabled());
        context.setMemoryEnabled(request.isMemoryEnabled());
        context.setMaxIterations(request.getMaxIterations() > 0 ? request.getMaxIterations() : 0);

        return context;
    }

    // --- Request DTO ---

    public static class ChatRequest {
        private String agentName = "assistant";
        private String prompt;
        private String sessionId;
        private String userId;
        private boolean toolCallEnabled = true;
        private boolean memoryEnabled = true;
        private int maxIterations = 0;

        public String getAgentName() { return agentName; }
        public void setAgentName(String agentName) { this.agentName = agentName; }
        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public boolean isToolCallEnabled() { return toolCallEnabled; }
        public void setToolCallEnabled(boolean toolCallEnabled) { this.toolCallEnabled = toolCallEnabled; }
        public boolean isMemoryEnabled() { return memoryEnabled; }
        public void setMemoryEnabled(boolean memoryEnabled) { this.memoryEnabled = memoryEnabled; }
        public int getMaxIterations() { return maxIterations; }
        public void setMaxIterations(int maxIterations) { this.maxIterations = maxIterations; }
    }
}
