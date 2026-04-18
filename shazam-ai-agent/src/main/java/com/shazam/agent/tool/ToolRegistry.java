package com.shazam.agent.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 工具注册中心
 * 自动扫描并注册带有 @Tool 注解的方法
 *
 * @author shazam
 * @since 1.0
 */
public class ToolRegistry implements InitializingBean, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ToolRegistry.class);

    /**
     * 扫描的包路径列表
     */
    private List<String> scanPackages = new ArrayList<>();

    /**
     * 已注册的工具
     */
    private final Map<String, ToolWrapper> tools = new ConcurrentHashMap<>();

    /**
     * Spring 应用上下文
     */
    private ApplicationContext applicationContext;

    public ToolRegistry() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

    /**
     * 注册工具对象
     *
     * @param toolObject 包含 @Tool 注解方法的对象
     */
    public void registerTool(Object toolObject) {
        Assert.notNull(toolObject, "Tool object must not be null");

        Class<?> targetClass = AopUtils.getTargetClass(toolObject);

        ReflectionUtils.doWithMethods(targetClass, method -> {
            if (method.isAnnotationPresent(Tool.class)) {
                registerMethodTool(toolObject, method);
            }
        });

        logger.info("Registered tools from: {}", targetClass.getName());
    }

    /**
     * 注销工具对象
     *
     * @param toolObject 工具对象
     */
    public void unregisterTool(Object toolObject) {
        Assert.notNull(toolObject, "Tool object must not be null");

        Class<?> targetClass = AopUtils.getTargetClass(toolObject);

        ReflectionUtils.doWithMethods(targetClass, method -> {
            if (method.isAnnotationPresent(Tool.class)) {
                String toolName = getToolName(method);
                tools.remove(toolName);
                logger.debug("Unregistered tool: {}", toolName);
            }
        });
    }

    /**
     * 获取所有工具函数
     *
     * @return 工具函数列表
     */
    public List<Function<Object, Object>> getToolFunctions() {
        return new ArrayList<>(tools.values().stream()
                .map(ToolWrapper::getFunction)
                .toList());
    }

    /**
     * 是否有已注册的工具
     *
     * @return true 如果有工具
     */
    public boolean hasTools() {
        return !tools.isEmpty();
    }

    /**
     * 获取已注册工具数量
     *
     * @return 工具数量
     */
    public int getToolCount() {
        return tools.size();
    }

    /**
     * 获取所有工具名称
     *
     * @return 工具名称列表
     */
    public List<String> getToolNames() {
        return new ArrayList<>(tools.keySet());
    }

    /**
     * 获取工具描述
     */
    public String getToolDescription(String name) {
        ToolWrapper wrapper = tools.get(name);
        return wrapper != null ? wrapper.getDescription() : null;
    }

    @Override
    public void afterPropertiesSet() {
        // 自动扫描并注册工具
        if (applicationContext != null && !scanPackages.isEmpty()) {
            scanAndRegisterTools();
        }
    }

    /**
     * 扫描并注册工具
     */
    private void scanAndRegisterTools() {
        logger.info("Scanning for tools in packages: {}", scanPackages);

        for (String packageName : scanPackages) {
            try {
                Map<String, Object> beans = applicationContext.getBeansWithAnnotation(org.springframework.stereotype.Component.class);
                
                for (Object bean : beans.values()) {
                    Class<?> beanClass = AopUtils.getTargetClass(bean);
                    if (beanClass.getPackage().getName().startsWith(packageName)) {
                        registerTool(bean);
                    }
                }
            } catch (Exception e) {
                logger.warn("Failed to scan package {}: {}", packageName, e.getMessage());
            }
        }

        logger.info("Tool scanning completed. Registered {} tools.", tools.size());
    }

    /**
     * 注册方法工具
     */
    private void registerMethodTool(Object targetObject, Method method) {
        Tool toolAnnotation = method.getAnnotation(Tool.class);
        String toolName = getToolName(method);

        try {
            // 创建工具包装器
            ToolWrapper wrapper = new ToolWrapper(
                    toolName,
                    toolAnnotation.description(),
                    createFunction(targetObject, method)
            );

            tools.put(toolName, wrapper);
            logger.debug("Registered tool: {} -> {}.{}", toolName, 
                    targetObject.getClass().getSimpleName(), method.getName());

        } catch (Exception e) {
            logger.error("Failed to register tool: {}.{}", 
                    targetObject.getClass().getSimpleName(), method.getName(), e);
        }
    }

    /**
     * 创建函数包装
     */
    @SuppressWarnings("unchecked")
    private Function<Object, Object> createFunction(Object target, Method method) {
        return args -> {
            try {
                method.setAccessible(true);
                if (method.getParameterCount() == 0) {
                    return method.invoke(target);
                }
                // 简单处理：如果有参数，直接传入
                return method.invoke(target, args);
            } catch (Exception e) {
                throw new RuntimeException("Tool execution failed: " + e.getMessage(), e);
            }
        };
    }

    /**
     * 获取工具名称
     */
    private String getToolName(Method method) {
        Tool toolAnnotation = method.getAnnotation(Tool.class);
        if (toolAnnotation != null && !toolAnnotation.name().isEmpty()) {
            return toolAnnotation.name();
        }
        return method.getName();
    }

    /**
     * 工具包装器
     */
    public static class ToolWrapper {
        private final String name;
        private final String description;
        private final Function<Object, Object> function;

        public ToolWrapper(String name, String description, Function<Object, Object> function) {
            this.name = name;
            this.description = description;
            this.function = function;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Function<Object, Object> getFunction() {
            return function;
        }
    }
}
