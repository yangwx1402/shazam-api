package com.shazam.wechat.sdk.api.comment;

import com.shazam.wechat.sdk.api.WechatApi;
import com.shazam.wechat.sdk.model.request.*;
import com.shazam.wechat.sdk.model.response.*;

/**
 * 留言管理 API
 */
public interface CommentApi extends WechatApi {

    /**
     * 打开已群发文章评论
     *
     * @param request 打开评论请求
     * @return 操作结果
     */
    CommentOpenResponse openComment(CommentOpenRequest request);

    /**
     * 关闭已群发文章评论
     *
     * @param request 关闭评论请求
     * @return 操作结果
     */
    CommentCloseResponse closeComment(CommentCloseRequest request);

    /**
     * 获取已群发文章评论
     *
     * @param request 获取评论请求
     * @return 评论列表
     */
    CommentGetResponse getComments(CommentGetRequest request);

    /**
     * 将评论标记为精选
     *
     * @param request 标记精选请求
     * @return 操作结果
     */
    CommentMarkResponse markComment(CommentMarkRequest request);

    /**
     * 将评论取消精选
     *
     * @param request 取消精选请求
     * @return 操作结果
     */
    CommentUnmarkResponse unmarkComment(CommentUnmarkRequest request);

    /**
     * 删除评论
     *
     * @param request 删除评论请求
     * @return 操作结果
     */
    CommentDeleteResponse deleteComment(CommentDeleteRequest request);

    /**
     * 回复评论
     *
     * @param request 回复评论请求
     * @return 操作结果
     */
    CommentReplyResponse replyComment(CommentReplyRequest request);

    /**
     * 删除回复
     *
     * @param request 删除回复请求
     * @return 操作结果
     */
    CommentDeleteResponse deleteReply(CommentDeleteReplyRequest request);
}
