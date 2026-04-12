package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.api.AuthApi;
import com.shazam.wechat.sdk.api.CallbackApi;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.impl.AuthApiImpl;
import com.shazam.wechat.sdk.impl.CallbackApiImpl;

/**
 * 微信 SDK 客户端主入口
 *
 * <p>使用示例：</p>
 * <pre>
 * WechatConfig config = new WechatConfig.Builder()
 *     .appId("your_app_id")
 *     .appSecret("your_app_secret")
 *     .build();
 *
 * WechatClient client = new WechatClient(config);
 *
 * // 获取稳定版 access_token
 * AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
 *
 * // 网络通信检测
 * CallbackCheckResponse checkResp = client.callback().check();
 *
 * client.shutdown();
 * </pre>
 */
public class WechatClient {

    private final WechatConfig config;
    private final HttpClient httpClient;
    private final AuthApi authApi;
    private final CallbackApi callbackApi;

    /**
     * 创建微信 SDK 客户端
     *
     * @param config 配置信息
     */
    public WechatClient(WechatConfig config) {
        this.config = config;
        this.httpClient = new HttpClient(config.getConnectTimeout(), config.getReadTimeout());
        this.authApi = new AuthApiImpl(config.getAppId(), config.getAppSecret(), httpClient, config.getTokenCache());
        this.callbackApi = new CallbackApiImpl(httpClient);
    }

    /**
     * 获取认证 API
     *
     * @return AuthApi 实例
     */
    public AuthApi auth() {
        return authApi;
    }

    /**
     * 获取回调检测 API
     *
     * @return CallbackApi 实例
     */
    public CallbackApi callback() {
        return callbackApi;
    }

    /**
     * 关闭客户端，释放资源
     */
    public void shutdown() {
        // 当前实现无需特殊清理操作
        // 未来可扩展 HTTP 连接池等资源清理
    }

    /**
     * 获取配置信息
     *
     * @return WechatConfig 实例
     */
    public WechatConfig getConfig() {
        return config;
    }
}
