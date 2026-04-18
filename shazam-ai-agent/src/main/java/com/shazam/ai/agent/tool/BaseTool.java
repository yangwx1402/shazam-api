package com.shazam.ai.agent.tool;

/**
 * 工具基类
 *
 * @author shazam
 * @since 1.0
 */
public abstract class BaseTool {

    /**
     * 获取工具名称
     *
     * @return 工具名称
     */
    public String getToolName() {
        return getClass().getSimpleName();
    }

    /**
     * 获取工具描述
     *
     * @return 工具描述
     */
    public String getToolDescription() {
        return "Tool: " + getToolName();
    }

    /**
     * 工具执行前的预处理
     */
    protected void beforeExecute() {
        // 子类可重写
    }

    /**
     * 工具执行后的后处理
     */
    protected void afterExecute() {
        // 子类可重写
    }

    /**
     * 处理工具执行异常
     *
     * @param e 异常
     * @return 错误信息
     */
    protected String handleException(Exception e) {
        return "Tool execution error: " + e.getMessage();
    }
}
