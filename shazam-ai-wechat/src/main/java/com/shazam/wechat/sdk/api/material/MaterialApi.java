package com.shazam.wechat.sdk.api.material;

import com.shazam.wechat.sdk.api.WechatApi;
import com.shazam.wechat.sdk.model.response.UploadImageResponse;

import java.io.File;

/**
 * 素材管理 API
 */
public interface MaterialApi extends WechatApi {

    /**
     * 上传图片（获取图片 URL）
     * <p>
     * 用于图文消息封面或内容中的图片
     *
     * @param imageFile 图片文件
     * @return 上传结果，包含图片 URL
     */
    UploadImageResponse uploadImage(File imageFile);

    /**
     * 上传图片（指定文件路径）
     * <p>
     * 用于图文消息封面或内容中的图片
     *
     * @param imagePath 图片文件路径
     * @return 上传结果，包含图片 URL
     */
    UploadImageResponse uploadImage(String imagePath);
}
