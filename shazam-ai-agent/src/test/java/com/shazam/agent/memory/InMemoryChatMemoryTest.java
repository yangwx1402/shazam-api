package com.shazam.agent.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * InMemoryChatMemory 测试
 *
 * @author shazam
 * @since 1.0
 */
public class InMemoryChatMemoryTest {

    private InMemoryChatMemory memory;
    private static final String TEST_SESSION = "test-session";

    @BeforeEach
    public void setUp() {
        memory = new InMemoryChatMemory(10);
    }

    @Test
    public void testDefaultConstructor() {
        InMemoryChatMemory mem = new InMemoryChatMemory();
        assertNotNull(mem);
    }

    @Test
    public void testAddUserMessage() {
        memory.addUserMessage(TEST_SESSION, "Hello");
        List<ChatMemory.MemoryMessage> history = memory.getHistory(TEST_SESSION);
        
        assertEquals(1, history.size());
        assertEquals("user", history.get(0).getRole());
        assertEquals("Hello", history.get(0).getContent());
    }

    @Test
    public void testAddAssistantMessage() {
        memory.addAssistantMessage(TEST_SESSION, "Hi there");
        List<ChatMemory.MemoryMessage> history = memory.getHistory(TEST_SESSION);
        
        assertEquals(1, history.size());
        assertEquals("assistant", history.get(0).getRole());
        assertEquals("Hi there", history.get(0).getContent());
    }

    @Test
    public void testMultipleMessages() {
        memory.addUserMessage(TEST_SESSION, "Message 1");
        memory.addAssistantMessage(TEST_SESSION, "Response 1");
        memory.addUserMessage(TEST_SESSION, "Message 2");
        
        List<ChatMemory.MemoryMessage> history = memory.getHistory(TEST_SESSION);
        
        assertEquals(3, history.size());
        assertEquals("user", history.get(0).getRole());
        assertEquals("assistant", history.get(1).getRole());
        assertEquals("user", history.get(2).getRole());
    }

    @Test
    public void testMaxSizeLimit() {
        for (int i = 0; i < 15; i++) {
            memory.addUserMessage(TEST_SESSION, "Message " + i);
        }
        
        List<ChatMemory.MemoryMessage> history = memory.getHistory(TEST_SESSION);
        
        assertEquals(10, history.size());
        assertEquals("Message 5", history.get(0).getContent());
        assertEquals("Message 14", history.get(9).getContent());
    }

    @Test
    public void testClearSession() {
        memory.addUserMessage(TEST_SESSION, "Hello");
        memory.addAssistantMessage(TEST_SESSION, "Hi");
        
        memory.clear(TEST_SESSION);
        
        List<ChatMemory.MemoryMessage> history = memory.getHistory(TEST_SESSION);
        assertTrue(history.isEmpty());
    }

    @Test
    public void testClearAll() {
        memory.addUserMessage("session1", "Hello");
        memory.addUserMessage("session2", "Hi");
        memory.addUserMessage("session3", "Hey");
        
        memory.clearAll();
        
        assertTrue(memory.getHistory("session1").isEmpty());
        assertTrue(memory.getHistory("session2").isEmpty());
        assertTrue(memory.getHistory("session3").isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(0, memory.size(TEST_SESSION));
        
        memory.addUserMessage(TEST_SESSION, "Message 1");
        memory.addUserMessage(TEST_SESSION, "Message 2");
        
        assertEquals(2, memory.size(TEST_SESSION));
    }

    @Test
    public void testMemoryMessage() {
        ChatMemory.MemoryMessage message = new ChatMemory.MemoryMessage("user", "Test content");
        
        assertEquals("user", message.getRole());
        assertEquals("Test content", message.getContent());
        assertTrue(message.getTimestamp() > 0);
    }
}
