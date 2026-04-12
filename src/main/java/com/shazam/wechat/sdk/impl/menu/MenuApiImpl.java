package com.shazam.wechat.sdk.impl.menu;

import com.shazam.wechat.sdk.api.AuthApi;
import com.shazam.wechat.sdk.api.menu.MenuApi;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;
import com.shazam.wechat.sdk.constant.WechatErrorCode;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.impl.AbstractWechatApi;
import com.shazam.wechat.sdk.model.request.MenuCreateRequest;
import com.shazam.wechat.sdk.model.response.*;

/**
 * 菜单管理 API 实现
 */
public class MenuApiImpl extends AbstractWechatApi implements MenuApi {

    private final HttpClient httpClient;
    private final AuthApi authApi;

    public MenuApiImpl(HttpClient httpClient, AuthApi authApi) {
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
    public MenuCreateResponse createMenu(MenuCreateRequest request) {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_MENU_CREATE
                + "?access_token=" + token;

        MenuCreateResponse response = httpClient.post(url, request, MenuCreateResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public MenuGetResponse getMenu() {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_MENU_GET
                + "?access_token=" + token;

        MenuGetResponse response = httpClient.get(url, MenuGetResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public MenuDeleteResponse deleteMenu() {
        String token = getAccessToken();

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_MENU_DELETE
                + "?access_token=" + token;

        MenuDeleteResponse response = httpClient.get(url, MenuDeleteResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }
}
