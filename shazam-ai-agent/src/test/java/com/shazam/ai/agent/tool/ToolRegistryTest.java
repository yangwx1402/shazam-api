package com.shazam.ai.agent.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

/**
 * ToolRegistry 测试
 *
 * @author shazam
 * @since 1.0
 */
public class ToolRegistryTest {

    private ToolRegistry registry;

    @BeforeEach
    public void setUp() {
        registry = new ToolRegistry();
    }

    @Test
    public void testInitialState() {
        assertFalse(registry.hasTools());
        assertEquals(0, registry.getToolCount());
        assertTrue(registry.getToolNames().isEmpty());
    }

    @Test
    public void testSetScanPackages() {
        registry.setScanPackages(Arrays.asList("com.example.tools", "com.test.tools"));
    }

    @Test
    public void testGetToolFunctionsEmpty() {
        List<java.util.function.Function<Object, Object>> functions = registry.getToolFunctions();
        assertNotNull(functions);
        assertEquals(0, functions.size());
    }

    @Test
    public void testGetToolDescriptionEmpty() {
        String description = registry.getToolDescription("nonexistent");
        assertNull(description);
    }

    @Test
    public void testSetScanPackagesNull() {
        registry.setScanPackages(null);
    }
}
