package com.shazam.ai.agent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * 计算器工具
 * 提供基础数学计算功能
 *
 * @author shazam
 * @since 1.0
 */
@Component
public class CalculatorTool {

    @Tool(description = "两个数相加")
    public double add(@ToolParam(description = "第一个数") double a,
                      @ToolParam(description = "第二个数") double b) {
        return a + b;
    }

    @Tool(description = "两个数相减")
    public double subtract(@ToolParam(description = "被减数") double a,
                           @ToolParam(description = "减数") double b) {
        return a - b;
    }

    @Tool(description = "两个数相乘")
    public double multiply(@ToolParam(description = "第一个数") double a,
                           @ToolParam(description = "第二个数") double b) {
        return a * b;
    }

    @Tool(description = "两个数相除")
    public double divide(@ToolParam(description = "被除数") double a,
                         @ToolParam(description = "除数") double b) {
        if (b == 0) {
            throw new ArithmeticException("除数不能为零");
        }
        return a / b;
    }

    @Tool(description = "计算一个数的百分比")
    public double percentage(@ToolParam(description = "原始数值") double value,
                             @ToolParam(description = "百分比值 (0-100)") double percent) {
        return value * (percent / 100);
    }
}
