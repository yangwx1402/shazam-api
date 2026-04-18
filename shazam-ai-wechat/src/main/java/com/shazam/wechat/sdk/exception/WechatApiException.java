package com.shazam.wechat.sdk.exception;

/**
 * 微信 API 调用异常
 */
public class WechatApiException extends WechatException {

    private final int errorCode;
    private final String errorMsg;

    public WechatApiException(int errorCode, String errorMsg) {
        super(String.format("Wechat API Error: code=%d, message=%s", errorCode, errorMsg));
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public WechatApiException(int errorCode, String errorMsg, Throwable cause) {
        super(String.format("Wechat API Error: code=%d, message=%s", errorCode, errorMsg), cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
