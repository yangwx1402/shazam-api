package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 更新草稿请求
 */
public class DraftUpdateRequest {

    /**
     * 要修改的图文消息 media_id
     */
    @JsonProperty("media_id")
    private String mediaId;

    /**
     * 要修改的文章信息（索引从 0 开始）
     */
    @JsonProperty("index")
    private Integer index;

    /**
     * 文章对象
     */
    @JsonProperty("articles")
    private DraftArticle articles;

    private DraftUpdateRequest() {
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public DraftArticle getArticles() {
        return articles;
    }

    public void setArticles(DraftArticle articles) {
        this.articles = articles;
    }

    public static class Builder {
        private String mediaId;
        private Integer index = 0;
        private DraftArticle articles;

        /**
         * 设置草稿 media_id
         *
         * @param mediaId 草稿 media_id
         * @return Builder
         */
        public Builder mediaId(String mediaId) {
            this.mediaId = mediaId;
            return this;
        }

        /**
         * 设置文章索引（从 0 开始）
         *
         * @param index 文章索引
         * @return Builder
         */
        public Builder index(Integer index) {
            this.index = index;
            return this;
        }

        /**
         * 设置文章对象
         *
         * @param articles 文章对象
         * @return Builder
         */
        public Builder articles(DraftArticle articles) {
            this.articles = articles;
            return this;
        }

        /**
         * 设置文章对象（使用构建器）
         *
         * @param articleBuilder 文章构建器
         * @return Builder
         */
        public Builder articles(DraftAddRequest.ArticleBuilder articleBuilder) {
            this.articles = articleBuilder.build();
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return DraftUpdateRequest
         */
        public DraftUpdateRequest build() {
            DraftUpdateRequest request = new DraftUpdateRequest();
            request.mediaId = this.mediaId;
            request.index = this.index;
            request.articles = this.articles;
            return request;
        }
    }
}
