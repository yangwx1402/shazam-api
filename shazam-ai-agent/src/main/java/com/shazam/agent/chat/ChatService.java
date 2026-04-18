package com.shazam.agent.chat;

import com.shazam.agent.core.AgentContext;
import com.shazam.agent.core.AgentResponse;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 聊天服务接口
 *
 * @author shazam
 * @since 1.0
 */
public interface ChatService {

    /**
     * 简单聊天
     *
     * @param prompt 用户输入
     * @return AI 响应内容
     */
    String chat(String prompt);

    /**
     * 聊天（带上下文）
     *
     * @param prompt  用户输入
     * @param context 上下文
     * @return Agent 响应
     */
    AgentResponse chatWithContext(String prompt, AgentContext context);

    /**
     * 流式聊天
     *
     * @param prompt 用户输入
     * @return 流式响应
     */
    Flux<String> stream(String prompt);

    /**
     * 结构化输出
     *
     * @param prompt      用户输入
     * @param responseType 期望的响应类型
     * @param <T>         响应类型
     * @return 结构化对象
     */
    <T> T chatAs(String prompt, Class<T> responseType);

    /**
     * 批量聊天
     *
     * @param prompts 多个用户输入
     * @return 响应列表
     */
    List<AgentResponse> batchChat(List<String> prompts);
}
