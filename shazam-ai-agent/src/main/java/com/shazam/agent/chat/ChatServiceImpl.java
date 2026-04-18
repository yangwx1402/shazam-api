package com.shazam.agent.chat;

import com.shazam.agent.core.AgentContext;
import com.shazam.agent.core.AgentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天服务实现
 *
 * @author shazam
 * @since 1.0
 */
@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    private final ChatClient chatClient;
    private final ChatModel chatModel;

    public ChatServiceImpl(ChatClient chatClient, ChatModel chatModel) {
        this.chatClient = chatClient;
        this.chatModel = chatModel;
    }

    @Override
    public String chat(String prompt) {
        logger.debug("Chat request: {}", prompt);
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public AgentResponse chatWithContext(String prompt, AgentContext context) {
        logger.debug("Chat with context: {}, prompt: {}", context, prompt);

        try {
            ChatResponse chatResponse = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .chatResponse();

            return buildResponse(chatResponse);

        } catch (Exception e) {
            logger.error("Chat failed: {}", e.getMessage(), e);
            return AgentResponse.error(e.getMessage());
        }
    }

    @Override
    public Flux<String> stream(String prompt) {
        logger.debug("Stream request: {}", prompt);
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }

    @Override
    public <T> T chatAs(String prompt, Class<T> responseType) {
        logger.debug("Chat as {} request: {}", responseType.getSimpleName(), prompt);
        return chatClient.prompt()
                .user(prompt)
                .call()
                .entity(responseType);
    }

    @Override
    public List<AgentResponse> batchChat(List<String> prompts) {
        logger.debug("Batch chat request: {} prompts", prompts.size());

        List<AgentResponse> responses = new ArrayList<>(prompts.size());

        for (String prompt : prompts) {
            try {
                String content = chat(prompt);
                responses.add(AgentResponse.success(content));
            } catch (Exception e) {
                logger.error("Batch chat failed for prompt: {}", prompt, e);
                responses.add(AgentResponse.error(e.getMessage()));
            }
        }

        return responses;
    }

    /**
     * 构建响应对象
     */
    private AgentResponse buildResponse(ChatResponse chatResponse) {
        AgentResponse response = new AgentResponse();

        if (chatResponse != null && chatResponse.getResult() != null) {
            response.setContent(chatResponse.getResult().getOutput().getContent());

            if (chatResponse.getMetadata() != null && chatResponse.getMetadata().getUsage() != null) {
                var usage = chatResponse.getMetadata().getUsage();
                AgentResponse.TokenUsage tokenUsage = new AgentResponse.TokenUsage();
                tokenUsage.setInputTokens(usage.getPromptTokens());
                tokenUsage.setOutputTokens(usage.getGenerationTokens());
                tokenUsage.setTotalTokens(usage.getTotalTokens());
                response.setTokenUsage(tokenUsage);
            }
        }

        response.setSuccess(true);
        return response;
    }
}
