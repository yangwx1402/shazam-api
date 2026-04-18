package com.shazam.ai.boot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Shazam AI Spring Boot 启动类
 *
 * @author shazam
 * @since 1.0
 */
@SpringBootApplication(scanBasePackages = {
        "com.shazam.ai.agent",
        "com.shazam.ai.boot"
})
@EnableConfigurationProperties
@EnableScheduling
public class ApplicationBoot {

    public static void main(String[] args) {
        // 加载 .env 文件环境变量
        Dotenv dotenv = Dotenv.configure()
                .directory("./shazam-ai-agent")
                .ignoreIfMissing()
                .load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        SpringApplication.run(ApplicationBoot.class, args);
        System.out.println("========================================");
        System.out.println("  Shazam AI 启动成功!");
        System.out.println("========================================");
    }
}
