package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 删除回复请求
 */
public class CommentDeleteReplyRequest {

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

    /**
     * 评论 ID（必填）
     */
    @JsonProperty("comment_id")
    private Long commentId;

    private CommentDeleteReplyRequest() {
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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public static class Builder {
        private Long msgDataId;
        private Integer index = 0;
        private Long commentId;

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
         * 设置评论 ID
         *
         * @param commentId 评论 ID
         * @return Builder
         */
        public Builder commentId(Long commentId) {
            this.commentId = commentId;
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return CommentDeleteReplyRequest
         */
        public CommentDeleteReplyRequest build() {
            CommentDeleteReplyRequest request = new CommentDeleteReplyRequest();
            request.msgDataId = this.msgDataId;
            request.index = this.index;
            request.commentId = this.commentId;
            return request;
        }
    }
}
