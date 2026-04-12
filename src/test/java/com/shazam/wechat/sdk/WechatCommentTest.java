package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.model.request.*;
import com.shazam.wechat.sdk.model.response.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 微信公众号留言管理功能单元测试
 */
public class WechatCommentTest {

    /**
     * 测试打开评论请求构建器
     */
    @Test
    public void testCommentOpenRequestBuilder() {
        CommentOpenRequest request = new CommentOpenRequest.Builder()
                .msgDataId(1234567890L)
                .index(0)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Integer.valueOf(0), request.getIndex());
    }

    /**
     * 测试打开评论请求构建器 - 自定义 index
     */
    @Test
    public void testCommentOpenRequestWithIndex() {
        CommentOpenRequest request = new CommentOpenRequest.Builder()
                .msgDataId(1234567890L)
                .index(2)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Integer.valueOf(2), request.getIndex());
    }

    /**
     * 测试关闭评论请求构建器
     */
    @Test
    public void testCommentCloseRequestBuilder() {
        CommentCloseRequest request = new CommentCloseRequest.Builder()
                .msgDataId(1234567890L)
                .index(1)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Integer.valueOf(1), request.getIndex());
    }

    /**
     * 测试获取评论请求构建器
     */
    @Test
    public void testCommentGetRequestBuilder() {
        CommentGetRequest request = new CommentGetRequest.Builder()
                .msgDataId(1234567890L)
                .index(0)
                .begin(0)
                .count(20)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Integer.valueOf(0), request.getIndex());
        assertEquals(Integer.valueOf(0), request.getBegin());
        assertEquals(Integer.valueOf(20), request.getCount());
    }

    /**
     * 测试获取评论请求 - 数量限制
     */
    @Test
    public void testCommentGetRequestCountLimit() {
        CommentGetRequest request = new CommentGetRequest.Builder()
                .msgDataId(1234567890L)
                .count(100)  // 超过最大限制 50
                .build();

        // 应该被限制为 50
        assertEquals(Integer.valueOf(50), request.getCount());
    }

    /**
     * 测试标记精选请求构建器
     */
    @Test
    public void testCommentMarkRequestBuilder() {
        CommentMarkRequest request = new CommentMarkRequest.Builder()
                .msgDataId(1234567890L)
                .index(0)
                .commentId(987654321L)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Long.valueOf(987654321L), request.getCommentId());
    }

    /**
     * 测试取消精选请求构建器
     */
    @Test
    public void testCommentUnmarkRequestBuilder() {
        CommentUnmarkRequest request = new CommentUnmarkRequest.Builder()
                .msgDataId(1234567890L)
                .commentId(987654321L)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Long.valueOf(987654321L), request.getCommentId());
    }

    /**
     * 测试删除评论请求构建器
     */
    @Test
    public void testCommentDeleteRequestBuilder() {
        CommentDeleteRequest request = new CommentDeleteRequest.Builder()
                .msgDataId(1234567890L)
                .commentId(987654321L)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Long.valueOf(987654321L), request.getCommentId());
    }

    /**
     * 测试回复评论请求构建器
     */
    @Test
    public void testCommentReplyRequestBuilder() {
        CommentReplyRequest request = new CommentReplyRequest.Builder()
                .msgDataId(1234567890L)
                .commentId(987654321L)
                .content("感谢您的评论！")
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Long.valueOf(987654321L), request.getCommentId());
        assertEquals("感谢您的评论！", request.getContent());
    }

    /**
     * 测试删除回复请求构建器
     */
    @Test
    public void testCommentDeleteReplyRequestBuilder() {
        CommentDeleteReplyRequest request = new CommentDeleteReplyRequest.Builder()
                .msgDataId(1234567890L)
                .commentId(987654321L)
                .build();

        assertEquals(Long.valueOf(1234567890L), request.getMsgDataId());
        assertEquals(Long.valueOf(987654321L), request.getCommentId());
    }

    /**
     * 测试打开评论响应
     */
    @Test
    public void testCommentOpenResponse() {
        CommentOpenResponse response = new CommentOpenResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试关闭评论响应
     */
    @Test
    public void testCommentCloseResponse() {
        CommentCloseResponse response = new CommentCloseResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试获取评论响应
     */
    @Test
    public void testCommentGetResponse() {
        CommentGetResponse response = new CommentGetResponse();
        response.setTotal(10);
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
        assertEquals(Integer.valueOf(10), response.getTotal());
    }

    /**
     * 测试评论信息
     */
    @Test
    public void testCommentInfo() {
        CommentInfo comment = new CommentInfo();
        comment.setCommentId(987654321L);
        comment.setOpenid("openid123");
        comment.setContent("这是一条评论");
        comment.setCreateTime(1234567890L);
        comment.setIsTop(1);
        comment.setIsDeleted(0);

        assertEquals(Long.valueOf(987654321L), comment.getCommentId());
        assertEquals("openid123", comment.getOpenid());
        assertEquals("这是一条评论", comment.getContent());
        assertEquals(Integer.valueOf(1), comment.getIsTop());
        assertEquals(Integer.valueOf(0), comment.getIsDeleted());
    }

    /**
     * 测试评论回复信息
     */
    @Test
    public void testCommentReplyInfo() {
        CommentInfo.ReplyInfo reply = new CommentInfo.ReplyInfo();
        reply.setContent("感谢您的支持！");
        reply.setCreateTime(1234567890L);

        assertEquals("感谢您的支持！", reply.getContent());
        assertEquals(Long.valueOf(1234567890L), reply.getCreateTime());
    }

    /**
     * 测试标记精选响应
     */
    @Test
    public void testCommentMarkResponse() {
        CommentMarkResponse response = new CommentMarkResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试取消精选响应
     */
    @Test
    public void testCommentUnmarkResponse() {
        CommentUnmarkResponse response = new CommentUnmarkResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试删除评论响应
     */
    @Test
    public void testCommentDeleteResponse() {
        CommentDeleteResponse response = new CommentDeleteResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试回复评论响应
     */
    @Test
    public void testCommentReplyResponse() {
        CommentReplyResponse response = new CommentReplyResponse();
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertTrue(response.isSuccess());
    }

    /**
     * 测试评论信息带回复
     */
    @Test
    public void testCommentInfoWithReply() {
        CommentInfo comment = new CommentInfo();
        comment.setCommentId(987654321L);
        comment.setContent("这是一条评论");

        CommentInfo.ReplyInfo reply = new CommentInfo.ReplyInfo();
        reply.setContent("感谢您的评论！");
        reply.setCreateTime(1234567890L);

        comment.setReply(reply);

        assertNotNull(comment.getReply());
        assertEquals("感谢您的评论！", comment.getReply().getContent());
    }
}
