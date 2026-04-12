package com.shazam.wechat.sdk.constant;

/**
 * 微信 API 端点常量
 */
public class WechatApiEndpoint {

    private WechatApiEndpoint() {
        throw new IllegalStateException("Constant class");
    }

    /**
     * API 基础 URL
     */
    public static final String API_BASE_URL = "https://api.weixin.qq.com";

    /**
     * 获取接口调用凭据（普通版）
     */
    public static final String CGI_BIN_TOKEN = "/cgi-bin/token";

    /**
     * 获取稳定版接口调用凭据
     */
    public static final String CGI_BIN_STABLE_TOKEN = "/cgi-bin/stable_token";

    /**
     * 网络通信检测
     */
    public static final String CGI_BIN_CALLBACK_CHECK = "/cgi-bin/callback/check";

}
