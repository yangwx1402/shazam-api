package com.shazam.ai.agent.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * AgentContext 测试
 *
 * @author shazam
 * @since 1.0
 */
public class AgentContextTest {

    @Test
    public void testDefaultConstructor() {
        AgentContext context = new AgentContext();
        assertNotNull(context.getSessionId());
        assertNotNull(context.getData());
        assertTrue(context.isToolCallEnabled());
        assertTrue(context.isMemoryEnabled());
        assertNull(context.getUserId());
    }

    @Test
    public void testSessionIdConstructor() {
        AgentContext context = new AgentContext("test-session-123");
        assertEquals("test-session-123", context.getSessionId());
        assertNull(context.getUserId());
    }

    @Test
    public void testFullConstructor() {
        AgentContext context = new AgentContext("session-1", "user-1");
        assertEquals("session-1", context.getSessionId());
        assertEquals("user-1", context.getUserId());
    }

    @Test
    public void testCreateFactory() {
        AgentContext context = AgentContext.create();
        assertNotNull(context.getSessionId());
    }

    @Test
    public void testWithSessionFactory() {
        AgentContext context = AgentContext.withSession("custom-session");
        assertEquals("custom-session", context.getSessionId());
    }

    @Test
    public void testWithUserFactory() {
        AgentContext context = AgentContext.withUser("user-123");
        assertEquals("user-123", context.getUserId());
        assertNotNull(context.getSessionId());
    }

    @Test
    public void testSetters() {
        AgentContext context = new AgentContext();
        context.setSessionId("new-session");
        context.setUserId("new-user");
        context.setToolCallEnabled(false);
        context.setMemoryEnabled(false);

        assertEquals("new-session", context.getSessionId());
        assertEquals("new-user", context.getUserId());
        assertFalse(context.isToolCallEnabled());
        assertFalse(context.isMemoryEnabled());
    }

    @Test
    public void testDataOperations() {
        AgentContext context = new AgentContext();
        context.putData("stringKey", "stringValue");
        context.putData("intKey", 42);
        context.putData("boolKey", true);

        assertEquals("stringValue", context.getData("stringKey"));
        assertEquals(Integer.valueOf(42), context.getData("intKey"));
        assertEquals(Boolean.TRUE, context.getData("boolKey"));
    }

    @Test
    public void testToString() {
        AgentContext context = new AgentContext("session-1", "user-1");
        String str = context.toString();
        
        assertTrue(str.contains("session-1"));
        assertTrue(str.contains("user-1"));
    }
}
