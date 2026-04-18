# 微信公众号 SDK 设计方案

## 1. 项目结构

```
src/main/java/com/shazam/wechat/sdk/
├── WechatClient.java              # 客户端主入口（门面模式）
├── WechatConfig.java              # 配置类
├── constant/
│   ├── WechatApiEndpoint.java     # API 端点常量
│   └── WechatErrorCode.java       # 错误码常量
├── model/
│   ├── request/                   # 请求模型
│   │   ├── AccessTokenRequest.java
│   │   ├── StableAccessTokenRequest.java
│   │   └── CallbackCheckRequest.java
│   └── response/                  # 响应模型
│       ├── AccessTokenResponse.java
│       ├── CallbackCheckResponse.java
│       └── WechatErrorResponse.java
├── api/
│   ├── WechatApi.java             # API 基础接口
│   ├── auth/
│   │   └── AuthApi.java           # 认证相关 API
│   ├── callback/
│   │   └── CallbackApi.java       # 回调检测 API
│   └── ...                        # 其他扩展 API
├── impl/
│   ├── AbstractWechatApi.java     # API 抽象基类
│   ├── auth/
│   │   └── AuthApiImpl.java
│   └── callback/
│       └── CallbackApiImpl.java
├── http/
│   ├── HttpClient.java            # HTTP 客户端封装
│   ├── HttpRequest.java           # HTTP 请求封装
│   └── HttpResponse.java          # HTTP 响应封装
├── cache/
│   ├── TokenCache.java            # Token 缓存接口
│   └── DefaultTokenCache.java     # 默认内存缓存实现
└── exception/
    ├── WechatException.java       # 基础异常
    └── WechatApiException.java    # API 调用异常
```

## 2. 核心设计

### 2.1 配置类 (WechatConfig)

```java
public class WechatConfig {
    private String appId;              // 必填
    private String appSecret;          // 必填
    private String apiBaseUrl;         // 默认：https://api.weixin.qq.com
    private TokenCache tokenCache;     // 可选，默认使用内存缓存
    private int connectTimeout;        // 连接超时，默认 5000ms
    private int readTimeout;           // 读取超时，默认 10000ms
}
```

### 2.2 客户端主入口 (WechatClient)

```java
public class WechatClient {
    private final WechatConfig config;
    private final HttpClient httpClient;
    private final AuthApi authApi;
    private final CallbackApi callbackApi;
    
    // 构造方法
    public WechatClient(WechatConfig config) { }
    
    // API 访问入口
    public AuthApi auth() { return authApi; }
    public CallbackApi callback() { return callbackApi; }
    
    // 统一关闭资源
    public void shutdown() { }
}
```

### 2.3 API 分层设计

**基础接口**
```java
public interface WechatApi {
    void setAccessToken(String accessToken);
    String getAccessToken();
}
```

**认证 API**
```java
public interface AuthApi extends WechatApi {
    /**
     * 获取接口调用凭据（普通版）
     */
    AccessTokenResponse getAccessToken();
    
    /**
     * 获取稳定版接口调用凭据
     */
    AccessTokenResponse getStableAccessToken();
    
    /**
     * 获取稳定版接口调用凭据（可指定是否强制刷新）
     */
    AccessTokenResponse getStableAccessToken(boolean forceRefresh);
}
```

**回调检测 API**
```java
public interface CallbackApi extends WechatApi {
    /**
     * 网络通信检测
     */
    CallbackCheckResponse check(CallbackCheckAction action, CheckOperator operator);
}
```

### 2.4 Token 缓存机制

```java
public interface TokenCache {
    void put(String key, String token, long expireTime);
    String get(String key);
    void remove(String key);
}
```

默认实现使用 `ConcurrentHashMap` 进行内存缓存，支持自定义实现（如 Redis）。

### 2.5 HTTP 客户端封装

使用 `java.net.http.HttpClient` (Java 11+) 或 `Apache HttpClient` 进行封装：

```java
public interface HttpClient {
    <T> T get(String url, Class<T> responseType);
    <T> T post(String url, Object body, Class<T> responseType);
}
```

## 3. 使用示例

```java
// 1. 创建配置
WechatConfig config = new WechatConfig.Builder()
    .appId("your_app_id")
    .appSecret("your_app_secret")
    .build();

// 2. 创建客户端
WechatClient client = new WechatClient(config);

// 3. 调用认证 API
AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
String accessToken = tokenResp.getAccessToken();

// 4. 调用回调检测 API
CallbackCheckResponse checkResp = client.callback()
    .check(CallbackCheckAction.ALL, CheckOperator.DEFAULT);

// 5. 关闭资源
client.shutdown();
```

## 4. 扩展性设计

### 4.1 新增 API 的步骤

1. 在 `api/` 目录下定义新的接口
2. 在 `impl/` 目录下实现接口
3. 在 `WechatClient` 中注册新的 API

### 4.2 新增请求/响应模型

在 `model/request/` 和 `model/response/` 目录下添加新的模型类

### 4.3 自定义 HTTP 客户端

实现 `HttpClient` 接口，替换默认实现

### 4.4 自定义 Token 缓存

实现 `TokenCache` 接口，支持 Redis 等分布式缓存

## 5. 错误处理

```java
public class WechatApiException extends WechatException {
    private final int errorCode;
    private final String errorMsg;
    
    // getter 方法
}
```

统一错误码处理，参考微信官方错误码文档

## 6. 依赖管理

```xml
<dependencies>
    <!-- JSON 处理 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    
    <!-- HTTP 客户端 (可选，Java 11+ 可使用内置) -->
    <dependency>
        <groupId>org.apache.httpcomponents.client5</groupId>
        <artifactId>httpclient5</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- 日志 -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
    </dependency>
</dependencies>
```

## 7. 待补充接口

根据 docs/base 目录，还有以下接口待补充：
- 获取微信推送服务器 IP
- 获取微信 API 服务器 IP

---

**版本**: v1.0  
**创建时间**: 2026-04-12
