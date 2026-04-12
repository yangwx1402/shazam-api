package com.shazam.wechat.sdk;

import com.shazam.wechat.sdk.api.AuthApi;
import com.shazam.wechat.sdk.api.CallbackApi;
import com.shazam.wechat.sdk.api.draft.DraftApi;
import com.shazam.wechat.sdk.api.material.MaterialApi;
import com.shazam.wechat.sdk.api.menu.MenuApi;
import com.shazam.wechat.sdk.api.publish.PublishApi;
import com.shazam.wechat.sdk.http.HttpClient;
import com.shazam.wechat.sdk.impl.AuthApiImpl;
import com.shazam.wechat.sdk.impl.CallbackApiImpl;
import com.shazam.wechat.sdk.impl.draft.DraftApiImpl;
import com.shazam.wechat.sdk.impl.material.MaterialApiImpl;
import com.shazam.wechat.sdk.impl.menu.MenuApiImpl;
import com.shazam.wechat.sdk.impl.publish.PublishApiImpl;

/**
 * 微信 SDK 客户端主入口
 *
 * <p>使用示例：</p>
 * <pre>
 * WechatConfig config = new WechatConfig.Builder()
 *     .appId("your_app_id")
 *     .appSecret("your_app_secret")
 *     .build();
 *
 * WechatClient client = new WechatClient(config);
 *
 * // 获取稳定版 access_token
 * AccessTokenResponse tokenResp = client.auth().getStableAccessToken();
 *
 * // 网络通信检测
 * CallbackCheckResponse checkResp = client.callback().check();
 *
 * // 上传图片
 * UploadImageResponse imgResp = client.material().uploadImage(new File("cover.jpg"));
 *
 * // 新增草稿
 * DraftAddRequest draftRequest = new DraftAddRequest.Builder()
 *     .addArticle(new DraftArticle.Builder()
 *         .title("标题")
 *         .content("内容")
 *         .thumbMediaId(imgResp.getUrl())
 *         .build())
 *     .build();
 * DraftAddResponse draftResp = client.draft().addDraft(draftRequest);
 *
 * // 提交发布
 * PublishSubmitRequest publishRequest = new PublishSubmitRequest.Builder()
 *     .mediaId(draftResp.getMediaId())
 *     .build();
 * PublishSubmitResponse publishResp = client.publish().submitPublish(publishRequest);
 *
 * // 创建菜单
 * MenuCreateRequest menuRequest = new MenuCreateRequest.Builder()
 *     .addButton(new MenuButton.Builder()
 *         .name("点击事件")
 *         .asClick("CLICK_KEY"))
 *     .addButton(new MenuButton.Builder()
 *         .name("访问网页")
 *         .asView("https://example.com"))
 *     .build();
 * client.menu().createMenu(menuRequest);
 *
 * client.shutdown();
 * </pre>
 */
public class WechatClient {

    private final WechatConfig config;
    private final HttpClient httpClient;
    private final AuthApi authApi;
    private final CallbackApi callbackApi;
    private final MaterialApi materialApi;
    private final DraftApi draftApi;
    private final PublishApi publishApi;
    private final MenuApi menuApi;

    /**
     * 创建微信 SDK 客户端
     *
     * @param config 配置信息
     */
    public WechatClient(WechatConfig config) {
        this.config = config;
        this.httpClient = new HttpClient(config.getConnectTimeout(), config.getReadTimeout());
        this.authApi = new AuthApiImpl(config.getAppId(), config.getAppSecret(), httpClient, config.getTokenCache());
        this.callbackApi = new CallbackApiImpl(httpClient);
        this.materialApi = new MaterialApiImpl(httpClient, authApi);
        this.draftApi = new DraftApiImpl(httpClient, authApi);
        this.publishApi = new PublishApiImpl(httpClient, authApi);
        this.menuApi = new MenuApiImpl(httpClient, authApi);
    }

    /**
     * 获取认证 API
     *
     * @return AuthApi 实例
     */
    public AuthApi auth() {
        return authApi;
    }

    /**
     * 获取回调检测 API
     *
     * @return CallbackApi 实例
     */
    public CallbackApi callback() {
        return callbackApi;
    }

    /**
     * 获取素材管理 API
     *
     * @return MaterialApi 实例
     */
    public MaterialApi material() {
        return materialApi;
    }

    /**
     * 获取草稿管理 API
     *
     * @return DraftApi 实例
     */
    public DraftApi draft() {
        return draftApi;
    }

    /**
     * 获取发布管理 API
     *
     * @return PublishApi 实例
     */
    public PublishApi publish() {
        return publishApi;
    }

    /**
     * 获取菜单管理 API
     *
     * @return MenuApi 实例
     */
    public MenuApi menu() {
        return menuApi;
    }

    /**
     * 关闭客户端，释放资源
     */
    public void shutdown() {
        // 当前实现无需特殊清理操作
        // 未来可扩展 HTTP 连接池等资源清理
    }

    /**
     * 获取配置信息
     *
     * @return WechatConfig 实例
     */
    public WechatConfig getConfig() {
        return config;
    }
}
