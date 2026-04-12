package com.shazam.wechat.sdk.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shazam.wechat.sdk.model.request.MenuButton;

import java.util.List;

/**
 * 获取自定义菜单响应
 */
public class MenuGetResponse {

    /**
     * 菜单列表
     */
    @JsonProperty("menu")
    private MenuInfo menu;

    /**
     * 个性化菜单列表
     */
    @JsonProperty("conditionalmenu")
    private List<MenuInfo> conditionalMenu;

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误消息
     */
    private String errmsg;

    public MenuGetResponse() {
    }

    public MenuInfo getMenu() {
        return menu;
    }

    public void setMenu(MenuInfo menu) {
        this.menu = menu;
    }

    public List<MenuInfo> getConditionalMenu() {
        return conditionalMenu;
    }

    public void setConditionalMenu(List<MenuInfo> conditionalMenu) {
        this.conditionalMenu = conditionalMenu;
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
