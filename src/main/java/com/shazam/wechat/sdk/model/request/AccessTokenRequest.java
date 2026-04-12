package com.shazam.wechat.sdk.model.request;

/**
 * 获取接口调用凭据请求（普通版）
 */
public class AccessTokenRequest {

    /**
     * 账号的唯一凭证 AppID
     */
    private String appid;

    /**
     * 唯一凭证密钥 AppSecret
     */
    private String secret;

    /**
     * 凭证类型，填写 client_credential
     */
    private String grant_type;

    public AccessTokenRequest() {
    }

    public AccessTokenRequest(String appid, String secret, String grant_type) {
        this.appid = appid;
        this.secret = secret;
        this.grant_type = grant_type;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String appid;
        private String secret;
        private String grant_type = "client_credential";

        public Builder appid(String appid) {
            this.appid = appid;
            return this;
        }

        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder grant_type(String grant_type) {
            this.grant_type = grant_type;
            return this;
        }

        public AccessTokenRequest build() {
            return new AccessTokenRequest(appid, secret, grant_type);
        }
    }
}
