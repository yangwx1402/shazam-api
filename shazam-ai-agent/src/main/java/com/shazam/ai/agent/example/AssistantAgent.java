package com.shazam.ai.agent.example;

import com.shazam.ai.agent.core.BaseAgent;
import com.shazam.ai.agent.tools.CalculatorTool;
import com.shazam.ai.agent.tools.DateTimeTool;
import com.shazam.ai.agent.tools.WeatherTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 助手 Agent 示例
 *
 * @author shazam
 * @since 1.0
 */
@Service
public class AssistantAgent extends BaseAgent {

    @Autowired
    private CalculatorTool calculatorTool;

    @Autowired
    private WeatherTool weatherTool;

    @Autowired
    private DateTimeTool dateTimeTool;

    @Override
    public String getName() {
        return "assistant";
    }

    @Override
    public String getSystemPrompt() {
        return "你是一个智能助手，可以帮助用户回答问题、执行计算、查询天气等任务。" +
                "你可以使用提供的工具来获取实时信息。" +
                "请用简洁、友好的方式回答用户的问题。";
    }

    @Override
    public List<Object> getTools() {
        return List.of(calculatorTool, weatherTool, dateTimeTool);
    }
}
