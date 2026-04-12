package com.shazam.wechat.sdk.impl;

import com.shazam.wechat.sdk.api.CallbackApi;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;
import com.shazam.wechat.sdk.constant.WechatErrorCode;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.model.request.CallbackCheckAction;
import com.shazam.wechat.sdk.model.request.CallbackCheckRequest;
import com.shazam.wechat.sdk.model.request.CheckOperator;
import com.shazam.wechat.sdk.model.response.CallbackCheckResponse;

/**
 * 回调检测 API 实现
 */
public class CallbackApiImpl extends AbstractWechatApi implements CallbackApi {

    private final HttpClient httpClient;

    public CallbackApiImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public CallbackCheckResponse check() {
        return check(CallbackCheckAction.ALL, CheckOperator.DEFAULT);
    }

    @Override
    public CallbackCheckResponse check(CallbackCheckAction action, CheckOperator operator) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new WechatApiException(WechatErrorCode.INVALID_CREDENTIAL, "access_token is required");
        }

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_CALLBACK_CHECK
                + "?access_token=" + accessToken;

        CallbackCheckRequest request = CallbackCheckRequest.builder()
                .action(action.getValue())
                .check_operator(operator.getValue())
                .build();

        CallbackCheckResponse response = httpClient.post(url, request, CallbackCheckResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }
}
