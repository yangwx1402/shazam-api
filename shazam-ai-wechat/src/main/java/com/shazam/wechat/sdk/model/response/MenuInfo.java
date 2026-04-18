package com.shazam.wechat.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shazam.wechat.sdk.model.request.MenuButton;

import java.util.List;

/**
 * 菜单信息
 */
public class MenuInfo {

    /**
     * 菜单按钮数组
     */
    @JsonProperty("button")
    private List<MenuButton> button;

    /**
     * 菜单 ID
     */
    @JsonProperty("menuid")
    private String menuId;

    public MenuInfo() {
    }

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        this.button = button;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
}
