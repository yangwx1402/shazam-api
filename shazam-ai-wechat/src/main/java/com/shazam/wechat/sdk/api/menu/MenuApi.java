package com.shazam.wechat.sdk.api.menu;

import com.shazam.wechat.sdk.api.WechatApi;
import com.shazam.wechat.sdk.model.request.MenuCreateRequest;
import com.shazam.wechat.sdk.model.response.*;

/**
 * 菜单管理 API
 */
public interface MenuApi extends WechatApi {

    /**
     * 创建自定义菜单
     *
     * @param request 菜单创建请求
     * @return 创建结果
     */
    MenuCreateResponse createMenu(MenuCreateRequest request);

    /**
     * 获取自定义菜单配置
     *
     * @return 菜单配置信息
     */
    MenuGetResponse getMenu();

    /**
     * 删除自定义菜单
     *
     * @return 删除结果
     */
    MenuDeleteResponse deleteMenu();
}
