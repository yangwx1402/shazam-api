package com.shazam.ai.agent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 天气工具（示例）
 * 模拟天气查询功能
 *
 * @author shazam
 * @since 1.0
 */
@Component
public class WeatherTool {

    private static final Map<String, String> CITY_WEATHER = new HashMap<>();
    private static final Random RANDOM = new Random();

    static {
        CITY_WEATHER.put("北京", "晴，25°C");
        CITY_WEATHER.put("上海", "多云，28°C");
        CITY_WEATHER.put("广州", "小雨，26°C");
        CITY_WEATHER.put("深圳", "晴，30°C");
        CITY_WEATHER.put("杭州", "阴，24°C");
    }

    @Tool(description = "查询城市天气，返回温度和天气状况")
    public String getWeather(@ToolParam(description = "城市名称，如北京、上海、广州等") String city) {
        if (city == null || city.trim().isEmpty()) {
            return "请提供城市名称";
        }

        String weather = CITY_WEATHER.get(city);
        if (weather != null) {
            return city + "：" + weather;
        }

        String[] conditions = {"晴", "多云", "阴", "小雨", "大雨"};
        String condition = conditions[RANDOM.nextInt(conditions.length)];
        int temperature = 20 + RANDOM.nextInt(15);

        return city + "：" + condition + "，" + temperature + "°C";
    }

    @Tool(description = "查询多个城市的天气信息")
    public String getMultiCityWeather(@ToolParam(description = "城市名称列表") String[] cities) {
        if (cities == null || cities.length == 0) {
            return "请提供城市名称列表";
        }

        StringBuilder result = new StringBuilder();
        for (String city : cities) {
            result.append(getWeather(city)).append("\n");
        }

        return result.toString().trim();
    }
}
