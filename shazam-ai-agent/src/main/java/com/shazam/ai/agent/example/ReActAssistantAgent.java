package com.shazam.ai.agent.example;

import com.shazam.ai.agent.config.AgentProperties;
import com.shazam.ai.agent.core.AgentExecutionListener;
import com.shazam.ai.agent.core.AgentResponse;
import com.shazam.ai.agent.core.ReActAgent;
import com.shazam.ai.agent.core.ReasoningStep;
import com.shazam.ai.agent.tools.CalculatorTool;
import com.shazam.ai.agent.tools.DateTimeTool;
import com.shazam.ai.agent.tools.WeatherTool;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

/**
 * ReAct Agent 示例
 * 使用显式 ReAct 推理循环，支持 Thought -> Action -> Observation
 *
 * @author shazam
 * @since 1.0
 */
@Service
public class ReActAssistantAgent extends ReActAgent {

    @Autowired
    private CalculatorTool calculatorTool;

    @Autowired
    private WeatherTool weatherTool;

    @Autowired
    private DateTimeTool dateTimeTool;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    @Autowired
    private AgentProperties agentProperties;

    @PostConstruct
    public void init() {
        // 构建 ChatClient 并注入
        ChatClient chatClient = chatClientBuilder.build();
        setChatClient(chatClient);
        setAgentProperties(agentProperties);

        // 添加执行监听器（示例）
        addExecutionListener(new AgentExecutionListener() {
            @Override
            public void beforeExecute(String agentName, String prompt, com.shazam.ai.agent.core.AgentContext context) {
                logger.info("ReAct Agent [{}] starting execution: {}", agentName, prompt);
            }

            @Override
            public void afterExecute(String agentName, AgentResponse response, com.shazam.ai.agent.core.AgentContext context) {
                logger.info("ReAct Agent [{}] completed, iterations: {}", agentName, response.getIterationsUsed());
            }

            @Override
            public void onReasoningStep(String agentName, ReasoningStep step, int iteration) {
                logger.debug("ReAct Agent [{}] iteration {}: {}", agentName, iteration, step.getType());
            }
        });
    }

    @Override
    public String getName() {
        return "react-assistant";
    }

    @Override
    public String getSystemPrompt() {
        return "你是一个智能助手，可以使用工具帮助用户回答问题、执行计算、查询天气等任务。" +
                "请按照 ReAct 推理模式进行思考和行动：先思考（Thought），再行动（Action），最后观察结果（Observation）。" +
                "请用简洁、友好的方式回答用户的问题。";
    }

    @Override
    public List<Object> getTools() {
        return List.of(calculatorTool, weatherTool, dateTimeTool);
    }
}
