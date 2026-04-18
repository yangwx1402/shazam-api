package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.model.request.DraftAddRequest;
import com.shazam.wechat.sdk.model.request.DraftArticle;
import com.shazam.wechat.sdk.model.request.PublishSubmitRequest;
import com.shazam.wechat.sdk.model.response.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 微信公众号自动发布功能单元测试
 */
public class WechatPublishTest {

    /**
     * 测试草稿文章构建器
     */
    @Test
    public void testDraftArticleBuilder() {
        DraftArticle article = new DraftArticle.Builder()
                .title("测试标题")
                .author("测试作者")
                .content("<p>测试内容</p>")
                .digest("测试摘要")
                .thumbMediaId("test_media_id")
                .needOpenComment(1)
                .onlyFansCanComment(0)
                .articleType("news")
                .contentSourceUrl("https://example.com")
                .build();

        assertEquals("测试标题", article.getTitle());
        assertEquals("测试作者", article.getAuthor());
        assertEquals("<p>测试内容</p>", article.getContent());
        assertEquals("测试摘要", article.getDigest());
        assertEquals("test_media_id", article.getThumbMediaId());
        assertEquals(Integer.valueOf(1), article.getNeedOpenComment());
        assertEquals(Integer.valueOf(0), article.getOnlyFansCanComment());
        assertEquals("news", article.getArticleType());
        assertEquals("https://example.com", article.getContentSourceUrl());
    }

    /**
     * 测试新增草稿请求构建器
     */
    @Test
    public void testDraftAddRequestBuilder() {
        DraftArticle article1 = new DraftArticle.Builder()
                .title("文章 1")
                .content("内容 1")
                .thumbMediaId("media_id_1")
                .build();

        DraftArticle article2 = new DraftArticle.Builder()
                .title("文章 2")
                .content("内容 2")
                .thumbMediaId("media_id_2")
                .build();

        DraftAddRequest request = new DraftAddRequest.Builder()
                .addArticle(article1)
                .addArticle(article2)
                .build();

        assertNotNull(request.getArticles());
        assertEquals(2, request.getArticles().size());
        assertEquals("文章 1", request.getArticles().get(0).getTitle());
        assertEquals("文章 2", request.getArticles().get(1).getTitle());
    }

    /**
     * 测试使用 ArticleBuilder 直接添加文章
     */
    @Test
    public void testDraftAddRequestWithArticleBuilder() {
        DraftAddRequest request = new DraftAddRequest.Builder()
                .addArticle(new DraftAddRequest.ArticleBuilder()
                        .title("直接构建")
                        .content("直接构建内容")
                        .thumbMediaId("media_id"))
                .build();

        assertNotNull(request.getArticles());
        assertEquals(1, request.getArticles().size());
        assertEquals("直接构建", request.getArticles().get(0).getTitle());
    }

    /**
     * 测试提交发布请求构建器
     */
    @Test
    public void testPublishSubmitRequestBuilder() {
        PublishSubmitRequest request = new PublishSubmitRequest.Builder()
                .mediaId("test_media_id")
                .build();

        assertEquals("test_media_id", request.getMediaId());
    }

    /**
     * 测试上传响应
     */
    @Test
    public void testUploadImageResponse() {
        UploadImageResponse response = new UploadImageResponse();
        response.setUrl("https://mmbiz.qpic.cn/test.jpg");
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertEquals("https://mmbiz.qpic.cn/test.jpg", response.getUrl());
        assertTrue(response.isSuccess());
    }

    /**
     * 测试草稿新增响应
     */
    @Test
    public void testDraftAddResponse() {
        DraftAddResponse response = new DraftAddResponse();
        response.setMediaId("test_media_id");
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertEquals("test_media_id", response.getMediaId());
        assertTrue(response.isSuccess());
    }

    /**
     * 测试发布提交响应
     */
    @Test
    public void testPublishSubmitResponse() {
        PublishSubmitResponse response = new PublishSubmitResponse();
        response.setPublishId("test_publish_id");
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertEquals("test_publish_id", response.getPublishId());
        assertTrue(response.isSuccess());
    }

    /**
     * 测试发布状态响应
     */
    @Test
    public void testPublishGetResponse() {
        PublishGetResponse response = new PublishGetResponse();
        response.setPublishStatus(0);
        response.setArticleUrl("https://mp.weixin.qq.com/s/test");
        response.setErrcode(0);
        response.setErrmsg("ok");

        assertEquals(Integer.valueOf(0), response.getPublishStatus());
        assertEquals("https://mp.weixin.qq.com/s/test", response.getArticleUrl());
        assertTrue(response.isSuccess());
        assertTrue(response.isPublished());
    }

    /**
     * 测试发布状态 - 发布中
     */
    @Test
    public void testPublishGetResponsePublishing() {
        PublishGetResponse response = new PublishGetResponse();
        response.setPublishStatus(1);

        assertEquals(Integer.valueOf(1), response.getPublishStatus());
        assertFalse(response.isPublished());
    }

    /**
     * 测试发布状态 - 失败
     */
    @Test
    public void testPublishGetResponseFailed() {
        PublishGetResponse response = new PublishGetResponse();
        response.setPublishStatus(2);

        assertEquals(Integer.valueOf(2), response.getPublishStatus());
        assertFalse(response.isPublished());
    }

    /**
     * 测试草稿批量获取响应
     */
    @Test
    public void testDraftBatchGetResponse() {
        DraftBatchGetResponse response = new DraftBatchGetResponse();
        response.setTotalCount(10);

        assertEquals(Integer.valueOf(10), response.getTotalCount());
    }

    /**
     * 测试草稿计数响应
     */
    @Test
    public void testDraftCountResponse() {
        DraftCountResponse response = new DraftCountResponse();
        response.setTotalCount(5);

        assertEquals(Integer.valueOf(5), response.getTotalCount());
        assertTrue(response.isSuccess());
    }
}
