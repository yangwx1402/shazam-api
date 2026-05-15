package com.shazam.ai.agent.core;

/**
 * ReAct 系统提示词模板
 *
 * @author shazam
 * @since 1.0
 */
public final class ReActSystemPrompts {

    private ReActSystemPrompts() {
    }

    /**
     * ReAct 推理指令前缀
     * 引导 LLM 按照 Thought -> Action -> Observation 模式进行推理
     */
    public static final String REACT_INSTRUCTION = """
            你是一个能够使用工具解决问题的智能助手。请按照以下规则进行思考和行动：

            1. 仔细分析用户的问题，思考是否需要使用工具来获取信息
            2. 如果需要使用工具，选择最合适的工具并调用它
            3. 根据工具返回的结果继续思考，判断是否需要进一步操作
            4. 当你收集到足够信息后，给出清晰、完整的最终回答

            注意事项：
            - 每次只调用必要的工具，避免不必要的调用
            - 如果工具调用失败，尝试用其他方式解决或告知用户
            - 最终回答应该整合所有收集到的信息，直接回答用户的问题
            """;

    /**
     * 强制终止提示（达到最大迭代次数时使用）
     */
    public static final String FORCE_FINAL_ANSWER = """
            你已经进行了多轮推理和工具调用。请根据目前已经收集到的所有信息，直接给出最终回答。
            不要再调用任何工具，直接回答用户的问题。
            """;
}
