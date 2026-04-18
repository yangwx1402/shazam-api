package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 草稿文章
 */
public class DraftArticle {

    /**
     * 标题，不超过 32 个字
     */
    @JsonProperty("title")
    private String title;

    /**
     * 作者，不超过 16 个字
     */
    @JsonProperty("author")
    private String author;

    /**
     * 摘要，不超过 128 个字（仅单图文有效）
     */
    @JsonProperty("digest")
    private String digest;

    /**
     * 具体内容，支持 HTML，小于 2 万字符
     */
    @JsonProperty("content")
    private String content;

    /**
     * 原文地址，不超过 1KB
     */
    @JsonProperty("content_source_url")
    private String contentSourceUrl;

    /**
     * 封面图片素材 id（news 类型必填）
     */
    @JsonProperty("thumb_media_id")
    private String thumbMediaId;

    /**
     * 是否打开评论 0/1，选填
     */
    @JsonProperty("need_open_comment")
    private Integer needOpenComment;

    /**
     * 是否粉丝才可评论 0/1，选填
     */
    @JsonProperty("only_fans_can_comment")
    private Integer onlyFansCanComment;

    /**
     * 文章类型 news/newspic，选填，默认 news
     */
    @JsonProperty("article_type")
    private String articleType;

    /**
     * 图片消息里的图片相关信息（newspic 类型必填）
     */
    @JsonProperty("image_info")
    private ImageInfo imageInfo;

    /**
     * 图片消息的封面信息，选填
     */
    @JsonProperty("cover_info")
    private CoverInfo coverInfo;

    /**
     * 商品信息，选填
     */
    @JsonProperty("product_info")
    private ProductInfo productInfo;

    public DraftArticle() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public Integer getNeedOpenComment() {
        return needOpenComment;
    }

    public void setNeedOpenComment(Integer needOpenComment) {
        this.needOpenComment = needOpenComment;
    }

    public Integer getOnlyFansCanComment() {
        return onlyFansCanComment;
    }

    public void setOnlyFansCanComment(Integer onlyFansCanComment) {
        this.onlyFansCanComment = onlyFansCanComment;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public CoverInfo getCoverInfo() {
        return coverInfo;
    }

    public void setCoverInfo(CoverInfo coverInfo) {
        this.coverInfo = coverInfo;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    /**
     * 图片消息里的图片相关信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ImageInfo {
        @JsonProperty("image_list")
        private List<ImageItem> imageList;

        public List<ImageItem> getImageList() {
            return imageList;
        }

        public void setImageList(List<ImageItem> imageList) {
            this.imageList = imageList;
        }

        public static class ImageItem {
            @JsonProperty("image_media_id")
            private String imageMediaId;

            public String getImageMediaId() {
                return imageMediaId;
            }

            public void setImageMediaId(String imageMediaId) {
                this.imageMediaId = imageMediaId;
            }
        }
    }

    /**
     * 图片消息的封面信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CoverInfo {
        @JsonProperty("pic_crop_235_1")
        private String picCrop235_1;

        @JsonProperty("pic_crop_1_1")
        private String picCrop1_1;

        public String getPicCrop235_1() {
            return picCrop235_1;
        }

        public void setPicCrop235_1(String picCrop235_1) {
            this.picCrop235_1 = picCrop235_1;
        }

        public String getPicCrop1_1() {
            return picCrop1_1;
        }

        public void setPicCrop1_1(String picCrop1_1) {
            this.picCrop1_1 = picCrop1_1;
        }
    }

    /**
     * 商品信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProductInfo {
        @JsonProperty("product_id")
        private String productId;

        @JsonProperty("product_type")
        private Integer productType;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Integer getProductType() {
            return productType;
        }

        public void setProductType(Integer productType) {
            this.productType = productType;
        }
    }
}
