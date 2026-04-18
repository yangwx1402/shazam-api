package com.shazam.wechat.sdk.impl.material;

import com.shazam.wechat.sdk.api.AuthApi;
import com.shazam.wechat.sdk.api.material.MaterialApi;
import com.shazam.wechat.sdk.constant.WechatApiEndpoint;
import com.shazam.wechat.sdk.constant.WechatErrorCode;
import com.shazam.wechat.sdk.exception.WechatApiException;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.model.response.UploadImageResponse;

import java.io.File;

/**
 * 素材管理 API 实现
 */
public class MaterialApiImpl extends com.shazam.wechat.sdk.impl.AbstractWechatApi implements MaterialApi {

    private final HttpClient httpClient;
    private final AuthApi authApi;

    public MaterialApiImpl(HttpClient httpClient, AuthApi authApi) {
        this.httpClient = httpClient;
        this.authApi = authApi;
    }

    /**
     * 获取 access_token，如果为空则尝试从 AuthApi 获取
     */
    public String getAccessToken() {
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
    public UploadImageResponse uploadImage(File imageFile) {
        String token = getAccessToken();
        
        if (!imageFile.exists()) {
            throw new WechatApiException(WechatErrorCode.MEDIA_INVALID_ID, "Image file does not exist: " + imageFile.getPath());
        }

        String url = WechatApiEndpoint.API_BASE_URL + WechatApiEndpoint.CGI_BIN_MEDIA_UPLOAD_IMG
                + "?access_token=" + token;

        UploadImageResponse response = httpClient.uploadFile(url, imageFile, "media", UploadImageResponse.class);

        if (!response.isSuccess()) {
            throw new WechatApiException(
                    response.getErrcode() != null ? response.getErrcode() : WechatErrorCode.SYSTEM_ERROR,
                    response.getErrmsg() != null ? response.getErrmsg() : "Unknown error"
            );
        }

        return response;
    }

    @Override
    public UploadImageResponse uploadImage(String imagePath) {
        File imageFile = new File(imagePath);
        return uploadImage(imageFile);
    }
}
