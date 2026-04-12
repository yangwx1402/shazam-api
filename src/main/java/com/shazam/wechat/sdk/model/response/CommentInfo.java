package com.shazam.wechat.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 评论信息
 */
public class CommentInfo {

    /**
     * 评论 ID
     */
    @JsonProperty("comment_id")
    private Long commentId;

    /**
     * 用户 openid
     */
    @JsonProperty("openid")
    private String openid;

    /**
     * 评论内容
     */
    @JsonProperty("content")
    private String content;

    /**
     * 评论时间（时间戳）
     */
    @JsonProperty("create_time")
    private Long createTime;

    /**
     * 是否精选 0:未精选 1:已精选
     */
    @JsonProperty("is_top")
    private Integer isTop;

    /**
     * 是否删除 0:未删除 1:已删除
     */
    @JsonProperty("is_deleted")
    private Integer isDeleted;

    /**
     * 回复内容
     */
    @JsonProperty("reply")
    private ReplyInfo reply;

    public CommentInfo() {
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ReplyInfo getReply() {
        return reply;
    }

    public void setReply(ReplyInfo reply) {
        this.reply = reply;
    }

    /**
     * 回复信息内部类
     */
    public static class ReplyInfo {
        /**
         * 回复内容
         */
        @JsonProperty("content")
        private String content;

        /**
         * 回复时间
         */
        @JsonProperty("create_time")
        private Long createTime;

        public ReplyInfo() {
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }
    }
}
