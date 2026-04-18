package com.shazam.wechat.sdk.model.request;

/**
 * 获取稳定版接口调用凭据请求
 */
public class StableAccessTokenRequest {

    /**
     * 凭证类型，填写 client_credential
     */
    private String grant_type;

    /**
     * 账号的唯一凭证 AppID
     */
    private String appid;

    /**
     * 唯一凭证密钥 AppSecret
     */
    private String secret;

    /**
     * 是否强制刷新
     * false: 普通模式，access_token 有效期内重复调用不会更新
     * true: 强制刷新模式，会导致上次获取的 access_token 失效
     */
    private boolean force_refresh;

    public StableAccessTokenRequest() {
    }

    public StableAccessTokenRequest(String grant_type, String appid, String secret, boolean force_refresh) {
        this.grant_type = grant_type;
        this.appid = appid;
        this.secret = secret;
        this.force_refresh = force_refresh;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
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

    public boolean isForce_refresh() {
        return force_refresh;
    }

    public void setForce_refresh(boolean force_refresh) {
        this.force_refresh = force_refresh;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String grant_type = "client_credential";
        private String appid;
        private String secret;
        private boolean force_refresh = false;

        public Builder grant_type(String grant_type) {
            this.grant_type = grant_type;
            return this;
        }

        public Builder appid(String appid) {
            this.appid = appid;
            return this;
        }

        public Builder secret(String secret) {
            this.secret = secret;
            return this;
        }

        public Builder force_refresh(boolean force_refresh) {
            this.force_refresh = force_refresh;
            return this;
        }

        public StableAccessTokenRequest build() {
            return new StableAccessTokenRequest(grant_type, appid, secret, force_refresh);
        }
    }
}
