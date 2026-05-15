package com.shazam.ai.agent.tools;

import org.springframework.ai.tool.annotation.Tool;
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
public class DateTimeTool {

    @Tool(description = "获取当前日期，格式：yyyy-MM-dd")
    public String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Tool(description = "获取当前时间，格式：HH:mm:ss")
    public String getCurrentTime() {
        return LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

    @Tool(description = "获取当前日期时间，格式：yyyy-MM-dd HH:mm:ss")
    public String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Tool(description = "获取今天是星期几")
    public String getDayOfWeek() {
        String[] weekdays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue() % 7;
        return weekdays[dayOfWeek];
    }
}
