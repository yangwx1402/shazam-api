package com.shazam.wechat.sdk.model.request;

/**
 * 检测运营商枚举
 */
public enum CheckOperator {

    /**
     * 电信
     */
    CHINANET("CHINANET"),

    /**
     * 联通
     */
    UNICOM("UNICOM"),

    /**
     * 腾讯
     */
    CAP("CAP"),

    /**
     * 自动
     */
    DEFAULT("DEFAULT");

    private final String value;

    CheckOperator(String value) {
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
