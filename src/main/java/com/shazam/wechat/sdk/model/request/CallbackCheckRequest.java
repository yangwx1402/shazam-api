package com.shazam.wechat.sdk.model.request;

/**
 * 网络通信检测请求
 */
public class CallbackCheckRequest {

    /**
     * 检测动作
     * dns: 域名解析
     * ping: ping 检测
     * all: 全部
     */
    private String action;

    /**
     * 检测运营商
     * CHINANET: 电信
     * UNICOM: 联通
     * CAP: 腾讯
     * DEFAULT: 自动
     */
    private String check_operator;

    public CallbackCheckRequest() {
    }

    public CallbackCheckRequest(String action, String check_operator) {
        this.action = action;
        this.check_operator = check_operator;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCheck_operator() {
        return check_operator;
    }

    public void setCheck_operator(String check_operator) {
        this.check_operator = check_operator;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String action = "all";
        private String check_operator = "DEFAULT";

        public Builder action(String action) {
            this.action = action;
            return this;
        }

        public Builder check_operator(String check_operator) {
            this.check_operator = check_operator;
            return this;
        }

        public CallbackCheckRequest build() {
            return new CallbackCheckRequest(action, check_operator);
        }
    }
}
