package com.shazam.wechat.sdk.api.publish;

import com.shazam.wechat.sdk.api.WechatApi;
import com.shazam.wechat.sdk.model.request.PublishSubmitRequest;
import com.shazam.wechat.sdk.model.response.PublishGetResponse;
import com.shazam.wechat.sdk.model.response.PublishSubmitResponse;

/**
 * 发布管理 API
 */
public interface PublishApi extends WechatApi {

    /**
     * 提交发布
     *
     * @param request 发布请求
     * @return 提交结果，包含 publish_id
     */
    PublishSubmitResponse submitPublish(PublishSubmitRequest request);

    /**
     * 查询发布状态
     *
     * @param publishId 发布 ID
     * @return 发布状态
     */
    PublishGetResponse getPublishStatus(String publishId);
}
