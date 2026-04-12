package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 打开已群发文章评论请求
 */
public class CommentOpenRequest {

    /**
     * 群发返回的 msg_data_id（必填）
     */
    @JsonProperty("msg_data_id")
    private Long msgDataId;

    /**
     * 多图文时指定第几篇，从 0 开始（选填，默认 0）
     */
    @JsonProperty("index")
    private Integer index;

    private CommentOpenRequest() {
    }

    public Long getMsgDataId() {
        return msgDataId;
    }

    public void setMsgDataId(Long msgDataId) {
        this.msgDataId = msgDataId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public static class Builder {
        private Long msgDataId;
        private Integer index = 0;

        /**
         * 设置群发返回的 msg_data_id
         *
         * @param msgDataId msg_data_id
         * @return Builder
         */
        public Builder msgDataId(Long msgDataId) {
            this.msgDataId = msgDataId;
            return this;
        }

        /**
         * 设置多图文时指定第几篇
         *
         * @param index 索引（从 0 开始）
         * @return Builder
         */
        public Builder index(Integer index) {
            this.index = index;
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return CommentOpenRequest
         */
        public CommentOpenRequest build() {
            CommentOpenRequest request = new CommentOpenRequest();
            request.msgDataId = this.msgDataId;
            request.index = this.index;
            return request;
        }
    }
}
