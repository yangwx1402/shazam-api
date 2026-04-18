package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 提交发布请求
 */
public class PublishSubmitRequest {

    /**
     * 草稿 media_id，必填
     */
    @JsonProperty("media_id")
    private String mediaId;

    private PublishSubmitRequest() {
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public static class Builder {
        private String mediaId;

        /**
         * 设置草稿 media_id
         *
         * @param mediaId 草稿 media_id
         * @return Builder
         */
        public Builder mediaId(String mediaId) {
            this.mediaId = mediaId;
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return PublishSubmitRequest
         */
        public PublishSubmitRequest build() {
            PublishSubmitRequest request = new PublishSubmitRequest();
            request.mediaId = this.mediaId;
            return request;
        }
    }
}
