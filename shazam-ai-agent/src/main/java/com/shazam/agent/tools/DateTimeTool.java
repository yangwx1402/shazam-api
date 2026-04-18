package com.shazam.agent.tools;

import com.shazam.agent.tool.BaseTool;
import com.shazam.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具
 * 提供获取当前日期、时间等功能
 *
 * @author shazam
 * @since 1.0
 */
@Component
public class DateTimeTool extends BaseTool {

    @Override
    public String getToolName() {
        return "DateTimeTool";
    }

    @Override
    public String getToolDescription() {
        return "获取当前日期、时间、日期时间信息";
    }

    /**
     * 获取当前日期
     *
     * @return 格式化的日期字符串
     */
    @Tool(description = "获取当前日期，格式：yyyy-MM-dd")
    public String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 获取当前时间
     *
     * @return 格式化的时间字符串
     */
    @Tool(description = "获取当前时间，格式：HH:mm:ss")
    public String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    /**
     * 获取当前日期时间
     *
     * @return 格式化的日期时间字符串
     */
    @Tool(description = "获取当前日期时间，格式：yyyy-MM-dd HH:mm:ss")
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 获取星期几
     *
     * @return 星期几的中文描述
     */
    @Tool(description = "获取今天是星期几")
    public String getDayOfWeek() {
        String[] weekdays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue() % 7;
        return weekdays[dayOfWeek];
    }
}
