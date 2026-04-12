package com.shazam.wechat.sdk.api;

/**
 * 微信 API 基础接口
 */
public interface WechatApi {

    /**
     * 设置访问 Token
     *
     * @param accessToken 访问 Token
     */
    void setAccessToken(String accessToken);

    /**
     * 获取访问 Token
     *
     * @return 访问 Token
     */
    String getAccessToken();
}
