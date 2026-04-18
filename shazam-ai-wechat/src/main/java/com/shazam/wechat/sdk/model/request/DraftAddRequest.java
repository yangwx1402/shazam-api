package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 新增草稿请求
 */
public class DraftAddRequest {

    /**
     * 图文素材列表
     */
    @JsonProperty("articles")
    private List<DraftArticle> articles;

    private DraftAddRequest() {
    }

    public List<DraftArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<DraftArticle> articles) {
        this.articles = articles;
    }

    public static class Builder {
        private final List<DraftArticle> articles = new ArrayList<>();

        /**
         * 添加文章
         *
         * @param article 文章对象
         * @return Builder
         */
        public Builder addArticle(DraftArticle article) {
            this.articles.add(article);
            return this;
        }

        /**
         * 添加文章（使用构建器）
         *
         * @param articleBuilder 文章构建器
         * @return Builder
         */
        public Builder addArticle(ArticleBuilder articleBuilder) {
            this.articles.add(articleBuilder.build());
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return DraftAddRequest
         */
        public DraftAddRequest build() {
            DraftAddRequest request = new DraftAddRequest();
            request.articles = this.articles;
            return request;
        }
    }

    /**
     * 草稿文章构建器
     */
    public static class ArticleBuilder {
        private String title;
        private String author;
        private String digest;
        private String content;
        private String contentSourceUrl;
        private String thumbMediaId;
        private Integer needOpenComment;
        private Integer onlyFansCanComment;
        private String articleType;

        public ArticleBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ArticleBuilder author(String author) {
            this.author = author;
            return this;
        }

        public ArticleBuilder digest(String digest) {
            this.digest = digest;
            return this;
        }

        public ArticleBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ArticleBuilder contentSourceUrl(String contentSourceUrl) {
            this.contentSourceUrl = contentSourceUrl;
            return this;
        }

        public ArticleBuilder thumbMediaId(String thumbMediaId) {
            this.thumbMediaId = thumbMediaId;
            return this;
        }

        public ArticleBuilder needOpenComment(Integer needOpenComment) {
            this.needOpenComment = needOpenComment;
            return this;
        }

        public ArticleBuilder onlyFansCanComment(Integer onlyFansCanComment) {
            this.onlyFansCanComment = onlyFansCanComment;
            return this;
        }

        public ArticleBuilder articleType(String articleType) {
            this.articleType = articleType;
            return this;
        }

        public DraftArticle build() {
            DraftArticle article = new DraftArticle();
            article.setTitle(this.title);
            article.setAuthor(this.author);
            article.setDigest(this.digest);
            article.setContent(this.content);
            article.setContentSourceUrl(this.contentSourceUrl);
            article.setThumbMediaId(this.thumbMediaId);
            article.setNeedOpenComment(this.needOpenComment);
            article.setOnlyFansCanComment(this.onlyFansCanComment);
            article.setArticleType(this.articleType);
            return article;
        }
    }
}
