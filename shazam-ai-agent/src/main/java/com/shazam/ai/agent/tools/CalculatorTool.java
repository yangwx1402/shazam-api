package com.shazam.ai.agent.tools;

import com.shazam.ai.agent.tool.BaseTool;
import com.shazam.ai.agent.tool.Tool;
import org.springframework.stereotype.Component;

/**
 * 计算器工具
 * 提供基础数学计算功能
 *
 * @author shazam
 * @since 1.0
 */
@Component
public class CalculatorTool extends BaseTool {

    @Override
    public String getToolName() {
        return "CalculatorTool";
    }

    @Override
    public String getToolDescription() {
        return "执行基础数学计算：加减乘除";
    }

    /**
     * 加法
     *
     * @param a 加数
     * @param b 被加数
     * @return 和
     */
    @Tool(description = "两个数相加", paramDescriptions = {"a: 第一个数", "b: 第二个数"})
    public double add(double a, double b) {
        return a + b;
    }

    /**
     * 减法
     *
     * @param a 被减数
     * @param b 减数
     * @return 差
     */
    @Tool(description = "两个数相减", paramDescriptions = {"a: 被减数", "b: 减数"})
    public double subtract(double a, double b) {
        return a - b;
    }

    /**
     * 乘法
     *
     * @param a 乘数
     * @param b 被乘数
     * @return 积
     */
    @Tool(description = "两个数相乘", paramDescriptions = {"a: 第一个数", "b: 第二个数"})
    public double multiply(double a, double b) {
        return a * b;
    }

    /**
     * 除法
     *
     * @param a 被除数
     * @param b 除数
     * @return 商
     */
    @Tool(description = "两个数相除", paramDescriptions = {"a: 被除数", "b: 除数"})
    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("除数不能为零");
        }
        return a / b;
    }

    /**
     * 计算百分比
     *
     * @param value    值
     * @param percent  百分比
     * @return 计算结果
     */
    @Tool(description = "计算一个数的百分比", paramDescriptions = {"value: 原始数值", "percent: 百分比值 (0-100)"})
    public double percentage(double value, double percent) {
        return value * (percent / 100);
    }
}
