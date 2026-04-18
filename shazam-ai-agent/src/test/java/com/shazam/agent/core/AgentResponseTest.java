package com.shazam.agent.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;

/**
 * AgentResponse 测试
 *
 * @author shazam
 * @since 1.0
 */
public class AgentResponseTest {

    private AgentResponse response;

    @BeforeEach
    public void setUp() {
        response = new AgentResponse();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(response.getTimestamp());
        assertNotNull(response.getData());
        assertTrue(response.isSuccess());
        assertNull(response.getErrorMessage());
    }

    @Test
    public void testContentConstructor() {
        AgentResponse resp = new AgentResponse("Hello World");
        assertEquals("Hello World", resp.getContent());
        assertTrue(resp.isSuccess());
    }

    @Test
    public void testSuccessFactory() {
        AgentResponse resp = AgentResponse.success("Success response");
        assertEquals("Success response", resp.getContent());
        assertTrue(resp.isSuccess());
    }

    @Test
    public void testErrorFactory() {
        AgentResponse resp = AgentResponse.error("Error occurred");
        assertFalse(resp.isSuccess());
        assertEquals("Error occurred", resp.getErrorMessage());
        assertNull(resp.getContent());
    }

    @Test
    public void testSetters() {
        response.setContent("Test content");
        response.setModel("gpt-4");
        response.setSuccess(false);
        response.setErrorMessage("Test error");

        assertEquals("Test content", response.getContent());
        assertEquals("gpt-4", response.getModel());
        assertFalse(response.isSuccess());
        assertEquals("Test error", response.getErrorMessage());
    }

    @Test
    public void testTokenUsage() {
        AgentResponse.TokenUsage usage = new AgentResponse.TokenUsage();
        usage.setInputTokens(100L);
        usage.setOutputTokens(50L);
        usage.setTotalTokens(150L);

        response.setTokenUsage(usage);

        assertEquals(Long.valueOf(100L), response.getTokenUsage().getInputTokens());
        assertEquals(Long.valueOf(50L), response.getTokenUsage().getOutputTokens());
        assertEquals(Long.valueOf(150L), response.getTokenUsage().getTotalTokens());
    }

    @Test
    public void testDataMap() {
        response.putData("key1", "value1");
        response.putData("key2", 123);

        assertEquals("value1", response.getData("key1"));
        assertEquals(Integer.valueOf(123), response.getData("key2"));
        assertNull(response.getData("nonexistent"));
    }
}
