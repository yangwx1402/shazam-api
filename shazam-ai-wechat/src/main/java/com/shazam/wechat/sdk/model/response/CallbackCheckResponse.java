package com.shazam.wechat.sdk.model.response;

import java.util.List;

/**
 * 网络通信检测响应
 */
public class CallbackCheckResponse {

    /**
     * DNS 解析结果列表
     */
    private List<DnsResult> dns;

    /**
     * PING 检测结果列表
     */
    private List<PingResult> ping;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;

    public CallbackCheckResponse() {
    }

    public List<DnsResult> getDns() {
        return dns;
    }

    public void setDns(List<DnsResult> dns) {
        this.dns = dns;
    }

    public List<PingResult> getPing() {
        return ping;
    }

    public void setPing(List<PingResult> ping) {
        this.ping = ping;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return errcode == null || errcode == 0;
    }

    /**
     * DNS 解析结果
     */
    public static class DnsResult {

        /**
         * 解析出来的 ip
         */
        private String ip;

        /**
         * ip 对应的运营商
         */
        private String real_operator;

        public DnsResult() {
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getReal_operator() {
            return real_operator;
        }

        public void setReal_operator(String real_operator) {
            this.real_operator = real_operator;
        }
    }

    /**
     * PING 检测结果
     */
    public static class PingResult {

        /**
         * ping 的 ip
         */
        private String ip;

        /**
         * ping 的源头的运营商
         */
        private String from_operator;

        /**
         * ping 的丢包率，0% 表示无丢包，100% 表示全部丢包
         */
        private String package_loss;

        /**
         * 耗时
         */
        private String time;

        public PingResult() {
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getFrom_operator() {
            return from_operator;
        }

        public void setFrom_operator(String from_operator) {
            this.from_operator = from_operator;
        }

        public String getPackage_loss() {
            return package_loss;
        }

        public void setPackage_loss(String package_loss) {
            this.package_loss = package_loss;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
