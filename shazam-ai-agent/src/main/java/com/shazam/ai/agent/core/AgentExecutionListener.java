package com.shazam.ai.agent.core;

/**
 * Agent 执行监听器接口
 * 提供可观测性钩子，用于监控 Agent 执行过程
 *
 * @author shazam
 * @since 1.0
 */
public interface AgentExecutionListener {

    /**
     * 在 Agent 开始执行前调用
     *
     * @param agentName Agent 名称
     * @param prompt    用户输入
     * @param context   Agent 上下文
     */
    default void beforeExecute(String agentName, String prompt, AgentContext context) {
    }

    /**
     * 在 Agent 执行成功后调用
     *
     * @param agentName Agent 名称
     * @param response  Agent 响应
     * @param context   Agent 上下文
     */
    default void afterExecute(String agentName, AgentResponse response, AgentContext context) {
    }

    /**
     * 在 Agent 执行失败时调用
     *
     * @param agentName Agent 名称
     * @param exception 异常信息
     * @param context   Agent 上下文
     */
    default void onExecuteError(String agentName, Exception exception, AgentContext context) {
    }

    /**
     * 在 ReAct Agent 每次推理迭代后调用
     *
     * @param agentName Agent 名称
     * @param step      推理步骤
     * @param iteration 当前迭代次数
     */
    default void onReasoningStep(String agentName, ReasoningStep step, int iteration) {
    }
}
