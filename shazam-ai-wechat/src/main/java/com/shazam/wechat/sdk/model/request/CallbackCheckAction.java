package com.shazam.wechat.sdk.model.request;

/**
 * 检测动作枚举
 */
public enum CallbackCheckAction {

    /**
     * 域名解析
     */
    DNS("dns"),

    /**
     * ping 检测
     */
    PING("ping"),

    /**
     * 全部检测
     */
    ALL("all");

    private final String value;

    CallbackCheckAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
