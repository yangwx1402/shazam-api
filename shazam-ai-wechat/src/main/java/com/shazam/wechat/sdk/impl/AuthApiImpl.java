package com.shazam.wechat.sdk.impl;

import com.shazam.wechat.sdk.cache.TokenCache;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;
import com.shazam.wechat.sdk.constant.WechatErrorCode;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.model.request.StableAccessTokenRequest;
import com.shazam.wechat.sdk.model.response.AccessTokenResponse;
import com.shazam.wechat.sdk.api.AuthApi;

/**
 * 认证 API 实现
 */
public class AuthApiImpl implements AuthApi {

    private final String appId;
    private final String appSecret;
    private final HttpClient httpClient;
    private final TokenCache tokenCache;
    private String accessToken;

    public AuthApiImpl(String appId, String appSecret, HttpClient httpClient, TokenCache tokenCache) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.httpClient = httpClient;
        this.tokenCache = tokenCache;
    }

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * 获取新的接口调用凭据（普通版）
     *
     * @return 访问 Token 响应
     */
    @Override
    public AccessTokenResponse fetchNewAccessToken() {
        // 先尝试从缓存获取
        String cachedToken = getCachedToken();
        if (cachedToken != null) {
            AccessTokenResponse response = new AccessTokenResponse();
            response.setAccess_token(cachedToken);
            return response;
        }

        // 调用 API 获取
        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_TOKEN
                + "?appid=" + appId
                + "&secret=" + appSecret
                + "&grant_type=client_credential";

        AccessTokenResponse response = httpClient.get(url, AccessTokenResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        // 缓存 Token（提前 5 分钟过期）
        cacheToken(response.getAccess_token(), response.getExpires_in());

        return response;
    }

    @Override
    public AccessTokenResponse getStableAccessToken() {
        return getStableAccessToken(false);
    }

    @Override
    public AccessTokenResponse getStableAccessToken(boolean forceRefresh) {
        // 非强制刷新时，先尝试从缓存获取
        if (!forceRefresh) {
            String cachedToken = getCachedToken();
            if (cachedToken != null) {
                AccessTokenResponse response = new AccessTokenResponse();
                response.setAccess_token(cachedToken);
                return response;
            }
        }

        // 调用 API 获取
        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_STABLE_TOKEN;

        StableAccessTokenRequest request = StableAccessTokenRequest.builder()
                .appid(appId)
                .secret(appSecret)
                .grant_type("client_credential")
                .force_refresh(forceRefresh)
                .build();

        AccessTokenResponse response = httpClient.post(url, request, AccessTokenResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        // 缓存 Token（提前 5 分钟过期）
        cacheToken(response.getAccess_token(), response.getExpires_in());

        return response;
    }

    /**
     * 获取缓存的 Token
     */
    private String getCachedToken() {
        if (tokenCache == null) {
            return null;
        }
        return tokenCache.get(buildCacheKey());
    }

    /**
     * 缓存 Token
     *
     * @param token      Token
     * @param expiresIn  有效期（秒）
     */
    private void cacheToken(String token, Integer expiresIn) {
        if (tokenCache == null || expiresIn == null) {
            return;
        }
        // 提前 5 分钟（300 秒）过期
        long expireTime = System.currentTimeMillis() + (expiresIn - 300) * 1000L;
        tokenCache.put(buildCacheKey(), token, expireTime);
    }

    /**
     * 构建缓存键
     */
    private String buildCacheKey() {
        return "wechat:access_token:" + appId;
    }
}
