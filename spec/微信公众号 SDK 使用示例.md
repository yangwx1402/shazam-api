# 微信公众号 SDK 使用示例

## 1. 快速开始

### 1.1 添加依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```

### 1.2 创建客户端

```java
import com.shazam.wechat.sdk.WechatClient;
import com.shazam.wechat.sdk.WechatConfig;

// 创建配置
WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")           // 替换为你的 AppID
    .appSecret("your_app_secret")   // 替换为你的 AppSecret
    .build();

// 创建客户端
WechatClient client = new WechatClient(config);
```

## 2. API 使用示例

### 2.1 获取稳定版 Access Token（推荐）

```java
import com.shazam.wechat.sdk.model.response.AccessTokenResponse;

// 普通模式（自动缓存，有效期内不重复请求）
AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
String accessToken = tokenResp.getAccess_token();
System.out.println("Access Token: " + accessToken);
System.out.println("Expires In: " + tokenResp.getExpires_in());
```

### 2.2 强制刷新 Access Token

```java
// 强制刷新模式（慎用，每天限用 20 次）
AccessTokenResponse tokenResp = client.auth().getStableAccessToken(true);
String newAccessToken = tokenResp.getAccess_token();
```

### 2.3 获取普通版 Access Token

```java
// 普通版（不推荐，建议使用稳定版）
AccessTokenResponse tokenResp = client.auth().fetchNewAccessToken();
String accessToken = tokenResp.getAccess_token();
```

### 2.4 网络通信检测

```java
import com.shazam.wechat.sdk.model.response.CallbackCheckResponse;
import com.shazam.wechat.sdk.model.request.CallbackCheckAction;
import com.shazam.wechat.sdk.model.request.CheckOperator;

// 默认检测（全部检测 + 自动选择运营商）
CallbackCheckResponse response = client.callback().check();

// 自定义检测
CallbackCheckResponse response = client.callback()
    .check(CallbackCheckAction.ALL, CheckOperator.DEFAULT);

// 查看 DNS 解析结果
response.getDns().forEach(dns -> {
    System.out.println("IP: " + dns.getIp());
    System.out.println("运营商：" + dns.getReal_operator());
});

// 查看 PING 检测结果
response.getPing().forEach(ping -> {
    System.out.println("IP: " + ping.getIp());
    System.out.println("丢包率：" + ping.getPackage_loss());
    System.out.println("耗时：" + ping.getTime());
});
```

## 3. 高级配置

### 3.1 自定义超时时间

```java
WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")
    .appSecret("your_app_secret")
    .connectTimeout(10000)  // 连接超时 10 秒
    .readTimeout(30000)     // 读取超时 30 秒
    .build();
```

### 3.2 自定义 Token 缓存

```java
import com.shazam.wechat.sdk.cache.TokenCache;

// 实现自定义 TokenCache 接口（如使用 Redis）
TokenCache customCache = new RedisTokenCache(); // 自定义实现

WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")
    .appSecret("your_app_secret")
    .tokenCache(customCache)
    .build();
```

### 3.3 自定义 API 基础 URL

```java
WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")
    .appSecret("your_app_secret")
    .apiBaseUrl("https://api.weixin.qq.com")
    .build();
```

## 4. 错误处理

```java
import com.shazam.wechat.sdk.exception.WechatApiException;

try {
    AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
} catch (WechatApiException e) {
    System.err.println("错误码：" + e.getErrorCode());
    System.err.println("错误消息：" + e.getErrorMsg());
}
```

## 5. 常见错误码

| 错误码 | 说明 | 解决方案 |
|-------|------|---------|
| 40001 | AppSecret 错误或 token 无效 | 检查 AppSecret 正确性 |
| 40013 | AppID 不合法 | 检查 AppID 正确性 |
| 40125 | AppSecret 无效 | 检查 AppSecret 正确性 |
| 40164 | IP 不在白名单 | 在微信后台添加 IP 白名单 |
| 40243 | AppSecret 已被冻结 | 解冻 AppSecret |
| 45009 | 超过天级别频率限制 | 调用 clear_quota 接口恢复 |
| 45011 | API 调用太频繁 | 降低调用频率 |

## 6. 关闭客户端

```java
// 使用完毕后关闭客户端
client.shutdown();
```

## 7. 完整示例

```java
import com.shazam.wechat.sdk.WechatClient;
import com.shazam.wechat.sdk.WechatConfig;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.model.response.AccessTokenResponse;
import com.shazam.wechat.sdk.model.response.CallbackCheckResponse;

public class WechatSdkExample {
    public static void main(String[] args) {
        // 创建配置
        WechatConfig config = new WechatConfig.Builder()
            .appId("wx1234567890abcdef")
            .appSecret("your_app_secret_here")
            .connectTimeout(5000)
            .readTimeout(10000)
            .build();

        // 创建客户端
        WechatClient client = new WechatClient(config);

        try {
            // 获取 Access Token
            AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
            System.out.println("Access Token: " + tokenResp.getAccess_token());

            // 网络通信检测
            CallbackCheckResponse checkResp = client.callback().check();
            System.out.println("DNS 结果数量：" + checkResp.getDns().size());
            System.out.println("PING 结果数量：" + checkResp.getPing().size());

        } catch (WechatApiException e) {
            System.err.println("API 调用失败：" + e.getErrorCode() + " - " + e.getErrorMsg());
        } finally {
            // 关闭客户端
            client.shutdown();
        }
    }
}
```

## 8. 扩展指南

### 8.1 添加新的 API

1. 在 `com.shazam.wechat.sdk.api` 包中定义新接口
2. 在 `com.shazam.wechat.sdk.impl` 包中实现接口
3. 在 `WechatClient` 中注册新的 API

### 8.2 添加新的请求/响应模型

在对应的 `model.request` 或 `model.response` 包中添加新类

### 8.3 自定义 HTTP 客户端

实现 `com.shazam.wechat.sdk.http.HttpClient` 接口，替换默认实现

---

**版本**: v1.0  
**创建时间**: 2026-04-12
