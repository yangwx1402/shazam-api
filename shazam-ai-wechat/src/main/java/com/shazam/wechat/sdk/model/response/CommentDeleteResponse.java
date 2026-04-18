package com.shazam.wechat.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 删除评论响应
 */
public class CommentDeleteResponse {

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;

    public CommentDeleteResponse() {
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
