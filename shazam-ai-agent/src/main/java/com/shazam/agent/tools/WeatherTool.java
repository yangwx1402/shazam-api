package com.shazam.agent.tools;

import com.shazam.agent.tool.BaseTool;
import com.shazam.agent.tool.Tool;
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
public class WeatherTool extends BaseTool {

    private static final Map<String, String> CITY_WEATHER = new HashMap<>();
    private static final Random RANDOM = new Random();

    static {
        CITY_WEATHER.put("北京", "晴，25°C");
        CITY_WEATHER.put("上海", "多云，28°C");
        CITY_WEATHER.put("广州", "小雨，26°C");
        CITY_WEATHER.put("深圳", "晴，30°C");
        CITY_WEATHER.put("杭州", "阴，24°C");
    }

    @Override
    public String getToolName() {
        return "WeatherTool";
    }

    @Override
    public String getToolDescription() {
        return "查询指定城市的天气信息";
    }

    /**
     * 查询天气
     *
     * @param city 城市名称
     * @return 天气信息
     */
    @Tool(description = "查询城市天气，返回温度和天气状况", paramDescriptions = {"city: 城市名称，如北京、上海、广州等"})
    public String getWeather(String city) {
        if (city == null || city.trim().isEmpty()) {
            return "请提供城市名称";
        }

        // 模拟天气查询
        String weather = CITY_WEATHER.get(city);
        if (weather != null) {
            return city + "：" + weather;
        }

        // 随机生成天气信息（模拟）
        String[] conditions = {"晴", "多云", "阴", "小雨", "大雨"};
        String condition = conditions[RANDOM.nextInt(conditions.length)];
        int temperature = 20 + RANDOM.nextInt(15);
        
        return city + "：" + condition + "，" + temperature + "°C";
    }

    /**
     * 查询多城市天气
     *
     * @param cities 城市列表
     * @return 多城市天气信息
     */
    @Tool(description = "查询多个城市的天气信息", paramDescriptions = {"cities: 城市名称列表"})
    public String getMultiCityWeather(String[] cities) {
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
