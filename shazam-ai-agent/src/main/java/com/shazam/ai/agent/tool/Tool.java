package com.shazam.ai.agent.tool;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 工具注解
 * 标记一个方法为 AI 可调用的工具
 *
 * @author shazam
 * @since 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tool {

    /**
     * 工具名称，默认为方法名
     *
     * @return 工具名称
     */
    String name() default "";

    /**
     * 工具描述
     * AI 会根据描述决定何时调用此工具
     *
     * @return 工具描述
     */
    String description();

    /**
     * 参数描述，用于增强 AI 对参数的理解
     * key 为参数名，value 为参数描述
     *
     * @return 参数描述映射
     */
    String[] paramDescriptions() default {};
}
