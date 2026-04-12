package com.shazam.wechat.sdk.constant;

/**
 * 微信 API 端点常量
 */
public class WechatApiEndpoint {

    private WechatApiEndpoint() {
        throw new IllegalStateException("Constant class");
    }

    /**
     * API 基础 URL
     */
    public static final String API_BASE_URL = "https://api.weixin.qq.com";

    /**
     * 获取接口调用凭据（普通版）
     */
    public static final String CGI_BIN_TOKEN = "/cgi-bin/token";

    /**
     * 获取稳定版接口调用凭据
     */
    public static final String CGI_BIN_STABLE_TOKEN = "/cgi-bin/stable_token";

    /**
     * 网络通信检测
     */
    public static final String CGI_BIN_CALLBACK_CHECK = "/cgi-bin/callback/check";

    /**
     * 上传图片（获取图片 URL）
     */
    public static final String CGI_BIN_MEDIA_UPLOAD_IMG = "/cgi-bin/media/uploadimg";

    /**
     * 新增草稿
     */
    public static final String CGI_BIN_DRAFT_ADD = "/cgi-bin/draft/add";

    /**
     * 获取草稿详情
     */
    public static final String CGI_BIN_DRAFT_GET = "/cgi-bin/draft/get";

    /**
     * 更新草稿
     */
    public static final String CGI_BIN_DRAFT_UPDATE = "/cgi-bin/draft/update";

    /**
     * 删除草稿
     */
    public static final String CGI_BIN_DRAFT_DELETE = "/cgi-bin/draft/delete";

    /**
     * 获取草稿列表
     */
    public static final String CGI_BIN_DRAFT_BATCHGET = "/cgi-bin/draft/batchget";

    /**
     * 获取草稿总数
     */
    public static final String CGI_BIN_DRAFT_COUNT = "/cgi-bin/draft/count";

    /**
     * 提交发布
     */
    public static final String CGI_BIN_PUBLISH_SUBMIT = "/cgi-bin/whatsnew/publish";

    /**
     * 查询发布状态
     */
    public static final String CGI_BIN_PUBLISH_GET = "/cgi-bin/whatsnew/publish/get";

    /**
     * 创建自定义菜单
     */
    public static final String CGI_BIN_MENU_CREATE = "/cgi-bin/menu/create";

    /**
     * 获取自定义菜单
     */
    public static final String CGI_BIN_MENU_GET = "/cgi-bin/menu/get";

    /**
     * 删除自定义菜单
     */
    public static final String CGI_BIN_MENU_DELETE = "/cgi-bin/menu/delete";

    /**
     * 获取当前自定义菜单配置
     */
    public static final String CGI_BIN_GET_CURRENT_SELFMENU_INFO = "/cgi-bin/get_current_selfmenu_info";

}
