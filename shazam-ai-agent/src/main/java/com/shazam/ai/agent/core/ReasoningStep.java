package com.shazam.ai.agent.core;

import java.time.Instant;

/**
 * 推理步骤
 * 记录 ReAct Agent 每一步的推理过程
 *
 * @author shazam
 * @since 1.0
 */
public class ReasoningStep {

    public enum StepType {
        THOUGHT,
        ACTION,
        OBSERVATION,
        FINAL_ANSWER
    }

    private StepType type;
    private String content;
    private String toolName;
    private String toolInput;
    private Instant timestamp;
    private int iterationNumber;

    public ReasoningStep() {
        this.timestamp = Instant.now();
    }

    public ReasoningStep(StepType type, String content, int iterationNumber) {
        this();
        this.type = type;
        this.content = content;
        this.iterationNumber = iterationNumber;
    }

    public static ReasoningStep thought(String content, int iteration) {
        return new ReasoningStep(StepType.THOUGHT, content, iteration);
    }

    public static ReasoningStep action(String toolName, String toolInput, int iteration) {
        ReasoningStep step = new ReasoningStep(StepType.ACTION, "调用工具: " + toolName, iteration);
        step.setToolName(toolName);
        step.setToolInput(toolInput);
        return step;
    }

    public static ReasoningStep observation(String content, int iteration) {
        return new ReasoningStep(StepType.OBSERVATION, content, iteration);
    }

    public static ReasoningStep finalAnswer(String content, int iteration) {
        return new ReasoningStep(StepType.FINAL_ANSWER, content, iteration);
    }

    // --- Getters and Setters ---

    public StepType getType() {
        return type;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolInput() {
        return toolInput;
    }

    public void setToolInput(String toolInput) {
        this.toolInput = toolInput;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public int getIterationNumber() {
        return iterationNumber;
    }

    public void setIterationNumber(int iterationNumber) {
        this.iterationNumber = iterationNumber;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + content;
    }
}
