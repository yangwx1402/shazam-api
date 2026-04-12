package com.shazam.wechat.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shazam.wechat.sdk.model.request.DraftArticle;

import java.util.List;

/**
 * 获取草稿详情响应
 */
public class DraftGetResponse {

    /**
     * 草稿列表（图文消息）
     */
    @JsonProperty("news_item")
    private List<DraftArticle> newsItem;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;

    public DraftGetResponse() {
    }

    public List<DraftArticle> getNewsItem() {
        return newsItem;
    }

    public void setNewsItem(List<DraftArticle> newsItem) {
        this.newsItem = newsItem;
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
}
