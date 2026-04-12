package com.shazam.wechat.sdk.impl.draft;

import com.shazam.wechat.sdk.api.AuthApi;
import com.shazam.wechat.sdk.api.draft.DraftApi;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;
import com.shazam.wechat.sdk.constant.WechatErrorCode;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.impl.AbstractWechatApi;
import com.shazam.wechat.sdk.model.request.DraftAddRequest;
import com.shazam.wechat.sdk.model.request.DraftUpdateRequest;
import com.shazam.wechat.sdk.model.response.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 草稿管理 API 实现
 */
public class DraftApiImpl extends AbstractWechatApi implements DraftApi {

    private final HttpClient httpClient;
    private final AuthApi authApi;

    public DraftApiImpl(HttpClient httpClient, AuthApi authApi) {
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
    public DraftAddResponse addDraft(DraftAddRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_DRAFT_ADD
                + "?access_token=" + token;

        DraftAddResponse response = httpClient.post(url, request, DraftAddResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public DraftGetResponse getDraft(String mediaId) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_DRAFT_GET
                + "?access_token=" + token;

        Map<String, String> body = new HashMap<>();
        body.put("media_id", mediaId);

        DraftGetResponse response = httpClient.post(url, body, DraftGetResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public DraftDeleteResponse deleteDraft(String mediaId) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_DRAFT_DELETE
                + "?access_token=" + token;

        Map<String, String> body = new HashMap<>();
        body.put("media_id", mediaId);

        DraftDeleteResponse response = httpClient.post(url, body, DraftDeleteResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public DraftUpdateResponse updateDraft(DraftUpdateRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_DRAFT_UPDATE
                + "?access_token=" + token;

        DraftUpdateResponse response = httpClient.post(url, request, DraftUpdateResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public DraftBatchGetResponse batchGetDrafts(int offset, int count) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_DRAFT_BATCHGET
                + "?access_token=" + token;

        Map<String, Integer> body = new HashMap<>();
        body.put("offset", offset);
        body.put("count", Math.min(count, 20));  // 最多 20 条

        DraftBatchGetResponse response = httpClient.post(url, body, DraftBatchGetResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public DraftCountResponse countDrafts() {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_DRAFT_COUNT
                + "?access_token=" + token;

        DraftCountResponse response = httpClient.post(url, null, DraftCountResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }
}
