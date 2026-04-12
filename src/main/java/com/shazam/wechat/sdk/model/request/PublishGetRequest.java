package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 查询发布状态请求
 */
public class PublishGetRequest {

    /**
     * 发布 ID
     */
    @JsonProperty("publish_id")
    private String publishId;

    private PublishGetRequest() {
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

    public static class Builder {
        private String publishId;

        /**
         * 设置发布 ID
         *
         * @param publishId 发布 ID
         * @return Builder
         */
        public Builder publishId(String publishId) {
            this.publishId = publishId;
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return PublishGetRequest
         */
        public PublishGetRequest build() {
            PublishGetRequest request = new PublishGetRequest();
            request.publishId = this.publishId;
            return request;
        }
    }
}
