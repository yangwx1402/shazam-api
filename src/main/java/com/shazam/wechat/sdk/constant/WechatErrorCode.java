package com.shazam.wechat.sdk.constant;

/**
 * 微信 API 错误码常量
 */
public class WechatErrorCode {

    private WechatErrorCode() {
        throw new IllegalStateException("Constant class");
    }

    /**
     * 系统繁忙，此时请开发者稍候再试
     */
    public static final int SYSTEM_ERROR = -1;

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 获取 access_token 时 AppSecret 错误，或者 access_token 无效
     */
    public static final int INVALID_CREDENTIAL = 40001;

    /**
     * 不合法的凭证类型
     */
    public static final int INVALID_GRANT_TYPE = 40002;

    /**
     * 不合法的 AppID
     */
    public static final int INVALID_APPID = 40013;

    /**
     * 不合法的 secret
     */
    public static final int INVALID_APPSECRET = 40125;

    /**
     * 调用接口的 IP 地址不在白名单中
     */
    public static final int INVALID_IP = 40164;

    /**
     * AppSecret 已被冻结
     */
    public static final int APPSECRET_FROZEN = 40243;

    /**
     * 未设置回调 URL
     */
    public static final int INVALID_URL = 40201;

    /**
     * 不正确的 action 参数
     */
    public static final int INVALID_ACTION = 40202;

    /**
     * 不正确的运营商参数
     */
    public static final int INVALID_CHECK_OPERATOR = 40203;

    /**
     * 缺少 appid 参数
     */
    public static final int APPID_MISSING = 41002;

    /**
     * 缺少 secret 参数
     */
    public static final int APPSECRET_MISSING = 41004;

    /**
     * 需要 POST 请求
     */
    public static final int REQUIRE_POST_METHOD = 43002;

    /**
     * 调用超过天级别频率限制
     */
    public static final int API_DAILY_QUOTA_LIMIT = 45009;

    /**
     * API 调用太频繁，请稍候再试
     */
    public static final int API_MINUTE_QUOTA_LIMIT = 45011;

    /**
     * 此次调用需要管理员确认
     */
    public static final int NEED_ADMIN_CONFIRM = 89503;

    /**
     * 该 IP 调用请求已被公众号管理员拒绝 (24 小时)
     */
    public static final int IP_REJECTED_24H = 89506;

    /**
     * 该 IP 调用请求已被公众号管理员拒绝 (1 小时)
     */
    public static final int IP_REJECTED_1H = 89507;

    /**
     * 禁止使用 token 接口
     */
    public static final int TOKEN_FORBIDDEN = 50004;

    /**
     * 账号已冻结
     */
    public static final int ACCOUNT_FROZEN = 50007;

    /**
     * 素材相关错误码
     */
    public static final int MEDIA_INVALID_TYPE = 40004;
    public static final int MEDIA_SIZE_EXCEED = 40005;
    public static final int MEDIA_INVALID_ID = 40007;

    /**
     * 草稿相关错误码
     */
    public static final int DRAFT_NOT_EXIST = 40007;
    public static final int DRAFT_INVALID_CONTENT = 40015;

    /**
     * 发布相关错误码
     */
    public static final int PUBLISH_LIMIT_EXCEED = 45009;
    public static final int PUBLISH_STATUS_FAIL = 53404;

    /**
     * 菜单相关错误码
     */
    public static final int MENU_INVALID_BUTTON_NAME = 40018;
    public static final int MENU_BUTTON_NAME_TOO_LONG = 40019;
    public static final int MENU_INVALID_BUTTON_TYPE = 40020;
    public static final int MENU_BUTTON_COUNT_EXCEED = 40021;
    public static final int MENU_BUTTON_LEVEL_EXCEED = 40022;
    public static final int MENU_INVALID_KEY = 40023;
    public static final int MENU_INVALID_URL = 40024;
    public static final int MENU_PERMISSION_DENIED = 45008;
    public static final int MENU_DAILY_LIMIT_EXCEED = 45029;

}
