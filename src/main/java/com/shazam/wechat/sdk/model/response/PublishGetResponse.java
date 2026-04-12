package com.shazam.wechat.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 查询发布状态响应
 */
public class PublishGetResponse {

    /**
     * 发布状态
     * 0: 成功
     * 1: 发布中
     * 2: 失败
     */
    @JsonProperty("publish_status")
    private Integer publishStatus;

    /**
     * 发布后的文章链接
     */
    @JsonProperty("article_url")
    private String articleUrl;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;

    public PublishGetResponse() {
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return errcode == null || errcode == 0;
    }

    /**
     * 是否发布成功
     */
    public boolean isPublished() {
        return publishStatus != null && publishStatus == 0;
    }
}
