package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单按钮
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuButton {

    /**
     * 菜单标题（必填，不超过 16 个汉字，40 个字节）
     */
    @JsonProperty("name")
    private String name;

    /**
     * 菜单类型（选填，见类型说明表）
     */
    @JsonProperty("type")
    private String type;

    /**
     * 菜单 KEY 值（click/scancode 等类型必填）
     */
    @JsonProperty("key")
    private String key;

    /**
     * 网页链接（view 类型必填）
     */
    @JsonProperty("url")
    private String url;

    /**
     * 素材 ID（media_id/view_limited 类型必填）
     */
    @JsonProperty("media_id")
    private String mediaId;

    /**
     * 小程序 AppID（miniprogram 类型必填）
     */
    @JsonProperty("appid")
    private String appId;

    /**
     * 小程序页面路径（miniprogram 类型必填）
     */
    @JsonProperty("pagepath")
    private String pagePath;

    /**
     * 二级菜单数组（一级菜单专用）
     */
    @JsonProperty("sub_button")
    private List<MenuButton> subButtons;

    public MenuButton() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public List<MenuButton> getSubButtons() {
        return subButtons;
    }

    public void setSubButtons(List<MenuButton> subButtons) {
        this.subButtons = subButtons;
    }

    /**
     * 菜单按钮构建器
     */
    public static class Builder {
        private String name;
        private String type = "click";
        private String key;
        private String url;
        private String mediaId;
        private String appId;
        private String pagePath;
        private final List<MenuButton> subButtons = new ArrayList<>();

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder mediaId(String mediaId) {
            this.mediaId = mediaId;
            return this;
        }

        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public Builder pagePath(String pagePath) {
            this.pagePath = pagePath;
            return this;
        }

        /**
         * 添加二级菜单
         */
        public Builder addSubButton(MenuButton subButton) {
            this.subButtons.add(subButton);
            return this;
        }

        /**
         * 设置为 click 类型
         */
        public Builder asClick(String key) {
            this.type = "click";
            this.key = key;
            return this;
        }

        /**
         * 设置为 view 类型
         */
        public Builder asView(String url) {
            this.type = "view";
            this.url = url;
            return this;
        }

        /**
         * 设置为 miniprogram 类型
         */
        public Builder asMiniprogram(String appId, String pagePath, String url) {
            this.type = "miniprogram";
            this.appId = appId;
            this.pagePath = pagePath;
            this.url = url;
            return this;
        }

        /**
         * 设置为 media_id 类型
         */
        public Builder asMediaId(String mediaId) {
            this.type = "media_id";
            this.mediaId = mediaId;
            return this;
        }

        /**
         * 设置为 view_limited 类型
         */
        public Builder asViewLimited(String mediaId) {
            this.type = "view_limited";
            this.mediaId = mediaId;
            return this;
        }

        /**
         * 设置为 scancode_push 类型
         */
        public Builder asScanCodePush(String key) {
            this.type = "scancode_push";
            this.key = key;
            return this;
        }

        /**
         * 设置为 scancode_waitmsg 类型
         */
        public Builder asScanCodeWaitMsg(String key) {
            this.type = "scancode_waitmsg";
            this.key = key;
            return this;
        }

        /**
         * 设置为 pic_sysphoto 类型
         */
        public Builder asPicSysPhoto(String key) {
            this.type = "pic_sysphoto";
            this.key = key;
            return this;
        }

        /**
         * 设置为 pic_photo_or_album 类型
         */
        public Builder asPicPhotoOrAlbum(String key) {
            this.type = "pic_photo_or_album";
            this.key = key;
            return this;
        }

        /**
         * 设置为 pic_weixin 类型
         */
        public Builder asPicWeiXin(String key) {
            this.type = "pic_weixin";
            this.key = key;
            return this;
        }

        /**
         * 设置为 location_select 类型
         */
        public Builder asLocationSelect(String key) {
            this.type = "location_select";
            this.key = key;
            return this;
        }

        public MenuButton build() {
            MenuButton button = new MenuButton();
            button.setName(this.name);
            button.setType(this.type);
            button.setKey(this.key);
            button.setUrl(this.url);
            button.setMediaId(this.mediaId);
            button.setAppId(this.appId);
            button.setPagePath(this.pagePath);
            
            if (!this.subButtons.isEmpty()) {
                button.setSubButtons(this.subButtons);
            }
            
            return button;
        }
    }
}
