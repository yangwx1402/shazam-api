package com.shazam.ai.agent.core;

import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

/**
 * Agent 接口定义
 *
 * @author shazam
 * @since 1.0
 */
public interface Agent {

    /**
     * 获取 Agent 名称
     *
     * @return Agent 名称
     */
    String getName();

    /**
     * 获取系统提示词
     *
     * @return 系统提示词
     */
    String getSystemPrompt();

    /**
     * 获取 Agent 绑定的工具对象列表
     * 每个对象中带有 @Tool 注解的方法会被注册为可调用工具
     *
     * @return 工具对象列表
     */
    default List<Object> getTools() {
        return Collections.emptyList();
    }

    /**
     * 执行 Agent 任务
     *
     * @param prompt 用户提示词
     * @return Agent 响应
     */
    AgentResponse execute(String prompt);

    /**
     * 执行 Agent 任务（带上下文）
     *
     * @param prompt  用户提示词
     * @param context Agent 上下文
     * @return Agent 响应
     */
    AgentResponse execute(String prompt, AgentContext context);

    /**
     * 流式执行 Agent 任务
     *
     * @param prompt 用户提示词
     * @return 流式响应
     */
    Flux<String> stream(String prompt);

    /**
     * 流式执行 Agent 任务（带上下文）
     *
     * @param prompt  用户提示词
     * @param context Agent 上下文
     * @return 流式响应
     */
    Flux<String> stream(String prompt, AgentContext context);
}
