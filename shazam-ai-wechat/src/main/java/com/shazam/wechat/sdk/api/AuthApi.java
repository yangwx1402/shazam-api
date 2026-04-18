package com.shazam.wechat.sdk.api;

import com.shazam.wechat.sdk.model.response.AccessTokenResponse;

/**
 * 认证相关 API
 */
public interface AuthApi extends WechatApi {

    /**
     * 获取接口调用凭据（普通版）
     *
     * @return 访问 Token 响应
     */
    AccessTokenResponse fetchNewAccessToken();

    /**
     * 获取稳定版接口调用凭据（普通模式）
     *
     * @return 访问 Token 响应
     */
    AccessTokenResponse getStableAccessToken();

    /**
     * 获取稳定版接口调用凭据
     *
     * @param forceRefresh 是否强制刷新
     *                     false: 普通模式，access_token 有效期内重复调用不会更新
     *                     true: 强制刷新模式，会导致上次获取的 access_token 失效
     * @return 访问 Token 响应
     */
    AccessTokenResponse getStableAccessToken(boolean forceRefresh);
}
