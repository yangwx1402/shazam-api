package com.shazam.wechat.sdk.impl.comment;

import com.shazam.wechat.sdk.api.AuthApi;
import com.shazam.wechat.sdk.api.comment.CommentApi;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;
import com.shazam.wechat.sdk.constant.WechatErrorCode;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.impl.AbstractWechatApi;
import com.shazam.wechat.sdk.model.request.*;
import com.shazam.wechat.sdk.model.response.*;

/**
 * 留言管理 API 实现
 */
public class CommentApiImpl extends AbstractWechatApi implements CommentApi {

    private final HttpClient httpClient;
    private final AuthApi authApi;

    public CommentApiImpl(HttpClient httpClient, AuthApi authApi) {
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
    public CommentOpenResponse openComment(CommentOpenRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_OPEN
                + "?access_token=" + token;

        CommentOpenResponse response = httpClient.post(url, request, CommentOpenResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public CommentCloseResponse closeComment(CommentCloseRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_CLOSE
                + "?access_token=" + token;

        CommentCloseResponse response = httpClient.post(url, request, CommentCloseResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public CommentGetResponse getComments(CommentGetRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_GET
                + "?access_token=" + token;

        CommentGetResponse response = httpClient.post(url, request, CommentGetResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public CommentMarkResponse markComment(CommentMarkRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_MARK
                + "?access_token=" + token;

        CommentMarkResponse response = httpClient.post(url, request, CommentMarkResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public CommentUnmarkResponse unmarkComment(CommentUnmarkRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_UNMARK
                + "?access_token=" + token;

        CommentUnmarkResponse response = httpClient.post(url, request, CommentUnmarkResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public CommentDeleteResponse deleteComment(CommentDeleteRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_DELETE
                + "?access_token=" + token;

        CommentDeleteResponse response = httpClient.post(url, request, CommentDeleteResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public CommentReplyResponse replyComment(CommentReplyRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_REPLY
                + "?access_token=" + token;

        CommentReplyResponse response = httpClient.post(url, request, CommentReplyResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public CommentDeleteResponse deleteReply(CommentDeleteReplyRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_COMMENT_DELETE_REPLY
                + "?access_token=" + token;

        CommentDeleteResponse response = httpClient.post(url, request, CommentDeleteResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }
}
