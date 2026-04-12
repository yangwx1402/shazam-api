package com.shazam.wechat.sdk.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建自定义菜单请求
 */
public class MenuCreateRequest {

    /**
     * 一级菜单数组（最多 3 个）
     */
    @JsonProperty("button")
    private List<MenuButton> button;

    private MenuCreateRequest() {
    }

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        this.button = button;
    }

    public static class Builder {
        private final List<MenuButton> buttons = new ArrayList<>();

        /**
         * 添加一级菜单
         *
         * @param button 菜单按钮
         * @return Builder
         */
        public Builder addButton(MenuButton button) {
            this.buttons.add(button);
            return this;
        }

        /**
         * 添加一级菜单（使用构建器）
         *
         * @param buttonBuilder 菜单按钮构建器
         * @return Builder
         */
        public Builder addButton(MenuButton.Builder buttonBuilder) {
            this.buttons.add(buttonBuilder.build());
            return this;
        }

        /**
         * 构建请求对象
         *
         * @return MenuCreateRequest
         */
        public MenuCreateRequest build() {
            MenuCreateRequest request = new MenuCreateRequest();
            request.button = this.buttons;
            return request;
        }
    }
}
