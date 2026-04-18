package com.shazam.wechat.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shazam.wechat.sdk.model.request.DraftArticle;

import java.util.List;

/**
 * 批量获取草稿响应
 */
public class DraftBatchGetResponse {

    /**
     * 草稿总数
     */
    @JsonProperty("total_count")
    private Integer totalCount;

    /**
     * 草稿列表
     */
    @JsonProperty("item")
    private List<DraftItem> item;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;

    public DraftBatchGetResponse() {
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<DraftItem> getItem() {
        return item;
    }

    public void setItem(List<DraftItem> item) {
        this.item = item;
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
     * 草稿项
     */
    public static class DraftItem {
        @JsonProperty("media_id")
        private String mediaId;

        @JsonProperty("content")
        private DraftArticle content;

        @JsonProperty("update_time")
        private Long updateTime;

        @JsonProperty("update_account")
        private String updateAccount;

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public DraftArticle getContent() {
            return content;
        }

        public void setContent(DraftArticle content) {
            this.content = content;
        }

        public Long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Long updateTime) {
            this.updateTime = updateTime;
        }

        public String getUpdateAccount() {
            return updateAccount;
        }

        public void setUpdateAccount(String updateAccount) {
            this.updateAccount = updateAccount;
        }
    }
}
