package com.shazam.wechat.sdk.impl;

import com.shazam.wechat.sdk.api.WechatApi;

/**
 * API 抽象基类
 */
public abstract class AbstractWechatApi implements WechatApi {

    protected String accessToken;

    @Override
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }
}
