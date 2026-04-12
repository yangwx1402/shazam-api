package com.shazam.wechat.sdk.impl.publish;

import com.shazam.wechat.sdk.api.AuthApi;
import com.shazam.wechat.sdk.api.publish.PublishApi;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;
import com.shazam.wechat.sdk.constant.WechatErrorCode;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.impl.AbstractWechatApi;
import com.shazam.wechat.sdk.model.request.PublishSubmitRequest;
import com.shazam.wechat.sdk.model.response.PublishGetResponse;
import com.shazam.wechat.sdk.model.response.PublishSubmitResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 发布管理 API 实现
 */
public class PublishApiImpl extends AbstractWechatApi implements PublishApi {

    private final HttpClient httpClient;
    private final AuthApi authApi;

    public PublishApiImpl(HttpClient httpClient, AuthApi authApi) {
        this.httpClient = httpClient;
        this.authApi = authApi;
    }

    /**
     * 获取 access_token，如果为空则尝试从 AuthApi 获取
     */
    private String getAccessToken() {
        String token = this.accessToken;
        if (token == null || token.isEmpty()) {
            token = authApi.getAccessToken();
        }
        if (token == null || token.isEmpty()) {
            throw new WechatApiException(WechatErrorCode.INVALID_CREDENTIAL, "access_token is required");
        }
        return token;
    }

    @Override
    public PublishSubmitResponse submitPublish(PublishSubmitRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_PUBLISH_SUBMIT
                + "?access_token=" + token;

        PublishSubmitResponse response = httpClient.post(url, request, PublishSubmitResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public PublishGetResponse getPublishStatus(String publishId) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_PUBLISH_GET
                + "?access_token=" + token;

        Map<String, String> body = new HashMap<>();
        body.put("publish_id", publishId);

        PublishGetResponse response = httpClient.post(url, body, PublishGetResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }
}
