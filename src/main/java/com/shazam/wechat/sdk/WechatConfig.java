package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.cache.DefaultTokenCache;
import com.shazam.wechat.sdk.cache.TokenCache;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;

/**
 * 微信 SDK 配置类
 */
public class WechatConfig {

    /**
     * 账号的唯一凭证 AppID
     */
    private final String appId;

    /**
     * 唯一凭证密钥 AppSecret
     */
    private final String appSecret;

    /**
     * API 基础 URL
     */
    private final String apiBaseUrl;

    /**
     * Token 缓存实现
     */
    private final TokenCache tokenCache;

    /**
     * 连接超时时间（毫秒）
     */
    private final int connectTimeout;

    /**
     * 读取超时时间（毫秒）
     */
    private final int readTimeout;

    private WechatConfig(Builder builder) {
        this.appId = builder.appId;
        this.appSecret = builder.appSecret;
        this.apiBaseUrl = builder.apiBaseUrl != null ? builder.apiBaseUrl : WechatApiEndpoint.API_BASE_URL;
        this.tokenCache = builder.tokenCache != null ? builder.tokenCache : new DefaultTokenCache();
        this.connectTimeout = builder.connectTimeout > 0 ? builder.connectTimeout : 5000;
        this.readTimeout = builder.readTimeout > 0 ? builder.readTimeout : 10000;
    }

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public TokenCache getTokenCache() {
        return tokenCache;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String appId;
        private String appSecret;
        private String apiBaseUrl;
        private TokenCache tokenCache;
        private int connectTimeout = 5000;
        private int readTimeout = 10000;

        /**
         * 设置 AppID
         *
         * @param appId 账号的唯一凭证
         * @return Builder
         */
        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        /**
         * 设置 AppSecret
         *
         * @param appSecret 唯一凭证密钥
         * @return Builder
         */
        public Builder appSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        /**
         * 设置 API 基础 URL
         *
         * @param apiBaseUrl API 基础 URL
         * @return Builder
         */
        public Builder apiBaseUrl(String apiBaseUrl) {
            this.apiBaseUrl = apiBaseUrl;
            return this;
        }

        /**
         * 设置 Token 缓存实现
         *
         * @param tokenCache Token 缓存实现
         * @return Builder
         */
        public Builder tokenCache(TokenCache tokenCache) {
            this.tokenCache = tokenCache;
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param connectTimeout 连接超时时间（毫秒）
         * @return Builder
         */
        public Builder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeout 读取超时时间（毫秒）
         * @return Builder
         */
        public Builder readTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public WechatConfig build() {
            validate();
            return new WechatConfig(this);
        }

        private void validate() {
            if (appId == null || appId.trim().isEmpty()) {
                throw new IllegalArgumentException("appId is required");
            }
            if (appSecret == null || appSecret.trim().isEmpty()) {
                throw new IllegalArgumentException("appSecret is required");
            }
        }
    }
}
